package org.kateryna.habit_tracker.contoller;

import jakarta.servlet.http.HttpServletRequest;
import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.HabitCompletion;
import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitCompletionRepository;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class HabitController {
    private HabitRepository habitRepository;
    private UserRepository userRepository;
    private HabitCompletionRepository habitCompletionRepository;

    public HabitController(UserRepository userRepository, HabitRepository habitRepository, HabitCompletionRepository habitCompletionRepository) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.habitCompletionRepository = habitCompletionRepository;
    }


    @PostMapping("/habits/create")
    public String createHabit(@ModelAttribute Habit habit, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        habit.setUser(user);
        habit.setCompleted(false);
        habit.setStartDate(LocalDate.now());
        habit.setPaused(false);

        habitRepository.save(habit);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    @PostMapping("/habits/{id}/toggle")
    public String toggleHabit(@PathVariable("id") Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        LocalDate now = LocalDate.now();

        Optional<HabitCompletion> existingCompletion = habitCompletionRepository.findByHabitAndDate(habit, now);

        if (existingCompletion.isPresent()) {
            HabitCompletion habitCompletion = existingCompletion.get();
            habitCompletion.setCompleted(!habitCompletion.isCompleted());
            habitCompletionRepository.save(habitCompletion);

            // і одночасно оновлюємо стан звички
            habit.setCompleted(habitCompletion.isCompleted());
            habitRepository.save(habit);
        }
        else {
            HabitCompletion habitCompletion = new HabitCompletion();
            habitCompletion.setHabit(habit);
            habitCompletion.setDate(now);
            habitCompletion.setCompleted(true);
            habitCompletionRepository.save(habitCompletion);

            habit.setCompleted(true);
            habitRepository.save(habit);
        }

        return "redirect:/dashboard";

    }

    @PostMapping("/habits/update")
    public String updateHabit(@ModelAttribute Habit updatedHabit, HttpServletRequest request) {
        Habit habit = habitRepository.findById(updatedHabit.getId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        habit.setHabitName(updatedHabit.getHabitName());
        habit.setDescription(updatedHabit.getDescription());
        habit.setEmoji(updatedHabit.getEmoji());
        habit.setCategory(updatedHabit.getCategory());

        habitRepository.save(habit);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    @PostMapping("/habits/pause")
    public String pauseHabit(@ModelAttribute Habit pauseHabit, HttpServletRequest request) {
        Habit habit = habitRepository.findById(pauseHabit.getId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setPaused(true);
        habitRepository.save(habit);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    @PostMapping("/habits/resume")
    public String resumeHabit(@ModelAttribute Habit pauseHabit, HttpServletRequest request) {
        Habit habit = habitRepository.findById(pauseHabit.getId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setPaused(false);
        habitRepository.save(habit);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }

    @PostMapping("/habits/delete")
    public String deleteHabit(@ModelAttribute Habit deleteHabit, HttpServletRequest request) {
        Habit habit = habitRepository.findById(deleteHabit.getId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habitRepository.delete(habit);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }


}

