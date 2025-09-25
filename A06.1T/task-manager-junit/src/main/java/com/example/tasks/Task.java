package com.example.tasks;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private final UUID id;
    private final String title;
    private final String description;
    private final Instant createdAt;
    private TaskStatus status;

    public Task(UUID id, String title, String description, Instant createdAt, TaskStatus status) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null/blank");
        }
        this.id = Objects.requireNonNull(id, "id");
        this.title = title;
        this.description = description == null ? "" : description;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.status = Objects.requireNonNull(status, "status");
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void markCompleted() {
        this.status = TaskStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
