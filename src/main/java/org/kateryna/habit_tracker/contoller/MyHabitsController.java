package org.kateryna.habit_tracker.contoller;

import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyHabitsController {
    private HabitRepository habitRepository;
    private UserRepository userRepository;
    public MyHabitsController(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard/my-habits")
    public String myHabits(@RequestParam(defaultValue = "active") String filter,Model model) {
        List<Habit> habits;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        switch (filter) {
            case "paused":
                habits = habitRepository.findByUserAndIsPaused(user, Boolean.TRUE);
                break;
            case "all":
                habits = habitRepository.findAllByUser(user);
                break;
            default:
                habits = habitRepository.findByUserAndIsPaused(user, Boolean.FALSE);
                break;
        }

        model.addAttribute("user", user);
        model.addAttribute("habits", habits);
        model.addAttribute("currentFilter", filter);
        return "my-habits";
    }


}


