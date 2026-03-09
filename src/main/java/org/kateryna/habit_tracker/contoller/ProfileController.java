package org.kateryna.habit_tracker.contoller;

import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.kateryna.habit_tracker.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProfileController {

    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userEmail = user.getEmail();
        model.addAttribute("user", user);
        model.addAttribute("userFirstName", firstName);
        model.addAttribute("userLastName", lastName);
        model.addAttribute("userEmail", userEmail);
        return "profile";
    }

    @PostMapping("/profile/upload-photo")
    public String uploadPhoto(@RequestParam("image") MultipartFile file) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!file.isEmpty()) {

            // створюємо директорію
            Path uploadPath = Paths.get("uploads/profile-images");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // завжди jpg → стабільний формат
            String fileName = "user_" + user.getId() + ".jpg";
            Path filePath = uploadPath.resolve(fileName);

            // читаємо
            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            // ресайз до 200×200
            BufferedImage resized = resizeImage(originalImage, 200, 200);

            // зберігаємо
            ImageIO.write(resized, "jpg", filePath.toFile());

            // шлях у БД
            user.setProfileImage("/uploads/profile-images/" + fileName);
        }

        userRepository.save(user);

        return "redirect:/profile";
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();

        // фон → білий (PNG transparency fix)
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, targetWidth, targetHeight);

        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    @PostMapping("/profile/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1️⃣ Поточний пароль неправильний
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(400).body("Current password is incorrect.");
        }

        // 2️⃣ Паролі не співпадають
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(400).body("New passwords do not match.");
        }

        // 3️⃣ Мінімальна довжина
        if (newPassword.length() < 8) {
            return ResponseEntity.status(400).body("Password must be at least 8 characters.");
        }

        // 4️⃣ Зберігаємо новий пароль
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Success");
    }



}

