package org.kateryna.habit_tracker.service;

import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.HabitCompletion;
import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.HabitCompletionRepository;
import org.kateryna.habit_tracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    private final HabitCompletionRepository habitCompletionRepository;

    public HabitService(HabitRepository habitRepository, HabitCompletionRepository habitCompletionRepository) {
        this.habitRepository = habitRepository;
        this.habitCompletionRepository = habitCompletionRepository;
    }

    //find habit by id
    public Habit findById(Long id) {
        return habitRepository.findById(id).orElse(null);
    }

    // create
    public void createHabit(Habit habit, User user) {
        habit.setUser(user);
        habitRepository.save(habit);
    }


    //retrieve
    public List<Habit> getAllHabitsByUser(User user) {
        return habitRepository.findAllByUser(user);
    }

    //update
    public void updateHabit(Long id, String newName, String newDescription, String newCategory, String newEmoji) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit is not found"));
        if (newName != null) habit.setHabitName(newName);
        if (newDescription != null) habit.setDescription(newDescription);
        if (newCategory != null) habit.setCategory(newCategory);
        if (newEmoji != null) habit.setEmoji(newEmoji);
        habitRepository.save(habit);
    }

    //delete
    public void deleteHabit(Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit is not found"));
        habitCompletionRepository.deleteAllByHabit(habit);
        habitRepository.delete(habit);
    }

    //all completions for month
    public List<HabitCompletion> getCompletionsForMonth(Habit habit, YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return habitCompletionRepository.findAllByHabitAndDateBetween(habit, start, end);
    }

    //count completion days
    public long countTotalCompletions(Habit habit) {
        return habitCompletionRepository.countByHabitAndCompletedTrue(habit);
    }

    //mark habit as completed
    public void markHabitCompleted(Long habitId, LocalDate date) {
        Habit habit = habitRepository.findById(habitId).orElseThrow();
        Optional<HabitCompletion> existing = habitCompletionRepository.findByHabitAndDate(habit, date);
        if (existing.isPresent()) {
            HabitCompletion completion = existing.get();
            completion.setCompleted(!completion.isCompleted()); // true ↔ false
            habitCompletionRepository.save(completion);
        } else {
            HabitCompletion completion = new HabitCompletion();
            completion.setHabit(habit);
            completion.setDate(date);
            completion.setCompleted(true);
            habitCompletionRepository.save(completion);
        }
    }
}
