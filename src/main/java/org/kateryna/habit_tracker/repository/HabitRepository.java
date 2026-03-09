package org.kateryna.habit_tracker.repository;

import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUser(User user);
    List<Habit> findByUser(User user);
    List<Habit> findByUserAndIsPaused(User user, Boolean isPaused);
}
