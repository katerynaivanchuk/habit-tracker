package org.kateryna.habit_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="habit_completions")
public class HabitCompletion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Habit habit;
    private LocalDate date;
    private boolean completed;


    public HabitCompletion(Long id, Habit habit, LocalDate date, boolean completed) {
        this.id = id;
        this.habit = habit;
        this.date = date;
        this.completed = completed;
    }

    public HabitCompletion() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
