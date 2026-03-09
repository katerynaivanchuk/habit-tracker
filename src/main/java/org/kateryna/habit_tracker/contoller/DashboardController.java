package org.kateryna.habit_tracker.contoller;

import org.kateryna.habit_tracker.model.HabitCompletion;
import org.kateryna.habit_tracker.repository.HabitCompletionRepository;
import org.springframework.ui.Model;

import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final HabitCompletionRepository habitCompletionRepository;

    public DashboardController(UserRepository userRepository,
                               HabitRepository habitRepository,
                               HabitCompletionRepository habitCompletionRepository) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.habitCompletionRepository = habitCompletionRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Habit> habits = habitRepository.findAllByUser(user);
        LocalDate today = LocalDate.now();

        // Для кожної звички створюємо запис на сьогодні, якщо його ще немає
        for (Habit habit : habits) {
            Optional<HabitCompletion> existingCompletion =
                    habitCompletionRepository.findByHabitAndDate(habit, today);

            if (existingCompletion.isEmpty()) {
                HabitCompletion newCompletion = new HabitCompletion();
                newCompletion.setHabit(habit);
                newCompletion.setDate(today);
                newCompletion.setCompleted(false);
                habitCompletionRepository.save(newCompletion);
                habit.setCompleted(false);
            } else {
                habit.setCompleted(existingCompletion.get().isCompleted());
            }
        }

        // Фільтруємо тільки активні звички
        List<Habit> activeHabits = habits.stream()
                .filter(habit -> !habit.isPaused())
                .toList();

        int totalHabits = activeHabits.size();
        long completedHabits = activeHabits.stream()
                .filter(Habit::isCompleted)
                .count();

        // Уникаємо ділення на нуль
        int progressPercent = 0;
        if (totalHabits > 0) {
            progressPercent = (int) ((completedHabits * 100.0) / totalHabits);
        }
        model.addAttribute("user", user);
        model.addAttribute("activeHabits", activeHabits);
        model.addAttribute("habits", activeHabits);
        model.addAttribute("userName", user.getFirstName());
        model.addAttribute("totalHabits", totalHabits);
        model.addAttribute("completedHabits", completedHabits);
        model.addAttribute("progressPercent", progressPercent);

        return "dashboard";
    }
}
