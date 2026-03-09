package org.kateryna.habit_tracker.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitCompletionRepository;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Controller
public class ProgressController {

    private UserRepository userRepository;
    private HabitRepository habitRepository;
    private HabitCompletionRepository habitCompletionRepository;

    public ProgressController(UserRepository userRepository, HabitRepository habitRepository, HabitCompletionRepository habitCompletionRepository) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.habitCompletionRepository = habitCompletionRepository;
    }


    @GetMapping("/dashboard/progress")
    public String showProgress(Model model) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Habit> habits = habitRepository.findAllByUser(user);
        LocalDate now = LocalDate.now();
        Month month = now.getMonth();
        String formattedMonth = month.toString().substring(0,1).toUpperCase() + month.toString().substring(1).toLowerCase();

        long totalHabits = habits.size();

        //streak
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        List<LocalDate> dates = habitCompletionRepository.findDistinctDatesByUserAndMonth(user.getId(), firstDayOfMonth, lastDayOfMonth);
        Collections.sort(dates);
        int currentStreak = 1;
        int maxStreak = 1;

        if (dates.size() > 1) {
            for (int i = 1; i < dates.size(); i++) {
                LocalDate previousDate = dates.get(i - 1);
                LocalDate currentDate = dates.get(i);

                if (currentDate.equals(previousDate.plusDays(1))) {
                    currentStreak++;
                } else {
                    currentStreak = 1;
                }

                maxStreak = Math.max(maxStreak, currentStreak);
            }
        } else if (dates.size() == 1) {
            // тільки один день — стрік = 1
            currentStreak = 1;
            maxStreak = 1;
        } else {
            // якщо список порожній
            currentStreak = 0;
            maxStreak = 0;
        }


        //monthly progress
        List<LocalDate> allDays = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList();
        List<LocalDate> completedDays = habitCompletionRepository
                .findDistinctDatesByUserAndMonth(user.getId(), firstDayOfMonth, lastDayOfMonth);

        Map<Integer, Boolean> monthlyProgress = new LinkedHashMap<>();
        for (LocalDate day : allDays) {
            monthlyProgress.put(day.getDayOfMonth(), completedDays.contains(day));
        }

        List<Habit> activeHabits = habits.stream()
                .filter(habit -> !habit.isPaused())
                .toList();

        // --- Розподіл звичок за категоріями ---
        Map<String, Long> categoryCount = new LinkedHashMap<>();

        for (Habit habit : activeHabits) {
            String category = habit.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0L) + 1);
        }



// рахуємо відсоток
        Map<String, Double> categoryPercentages = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {
            double percent = Math.round(((double) entry.getValue() / totalHabits) * 100);
            categoryPercentages.put(entry.getKey(), percent);
        }

        ObjectMapper mapper = new ObjectMapper();
        String categoryJson = mapper.writeValueAsString(categoryPercentages);
        model.addAttribute("categoryProgress", categoryJson);

        model.addAttribute("user", user);
        model.addAttribute("monthlyProgress", monthlyProgress);
        model.addAttribute("totalHabits", totalHabits);
        model.addAttribute("month", formattedMonth);
        model.addAttribute("currentStreak", currentStreak);
        model.addAttribute("maxStreak", maxStreak);
        return "progress";
    }
}
