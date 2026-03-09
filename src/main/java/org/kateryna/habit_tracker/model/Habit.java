package org.kateryna.habit_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String habitName;
    private String description;
    private LocalDate startDate;
    private boolean isCompleted;
    private boolean isPaused;
    private String category;
    private String emoji;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Habit() {

    }

    public Habit(Long id, String habitName, String description, LocalDate startDate, boolean isCompleted, boolean isPaused, String category, String emoji, User user) {
        this.id = id;
        this.habitName = habitName;
        this.description = description;
        this.startDate = startDate;
        this.isCompleted = isCompleted;
        this.isPaused = isPaused;
        this.category = category;
        this.emoji = emoji;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User creator) {
        this.user = creator;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
