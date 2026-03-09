package org.kateryna.habit_tracker.repository;

import org.kateryna.habit_tracker.model.Habit;
import org.kateryna.habit_tracker.model.HabitCompletion;
import org.kateryna.habit_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {

    Optional<HabitCompletion> findByHabitAndDate(Habit habit, LocalDate date);

    void deleteAllByHabit(Habit habit);

    List<HabitCompletion> findAllByHabitAndDateBetween(Habit habit, LocalDate start, LocalDate end);

    long countByHabitAndCompletedTrue(Habit habit);


    @Query("""
    SELECT DISTINCT hc.date 
    FROM HabitCompletion hc 
    WHERE hc.habit.user.id = :userId
      AND hc.completed = true
      AND hc.date BETWEEN :start AND :end
    ORDER BY hc.date
""")
    List<LocalDate> findDistinctDatesByUserAndMonth(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
