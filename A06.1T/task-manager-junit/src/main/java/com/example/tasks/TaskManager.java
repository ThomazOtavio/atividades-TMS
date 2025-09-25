package com.example.tasks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();

    // ------------------ CRUD ---------------------
    public Task addTask(String title, String description) {
        Task t = new Task(UUID.randomUUID(), title, description, Instant.now(), TaskStatus.PENDING);
        tasks.add(t);
        return t;
    }

    public boolean removeTask(UUID id) {
        return tasks.removeIf(t -> t.getId().equals(id));
    }

    public int removeAllByTitle(String title) {
        int before = tasks.size();
        tasks.removeIf(t -> t.getTitle().equals(title));
        return before - tasks.size();
    }

    public boolean markCompleted(UUID id) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.markCompleted();
                return true;
            }
        }
        return false;
    }

    public void markAllCompleted() {
        tasks.forEach(Task::markCompleted);
    }

    // ------------------ QUERIES ------------------
    public List<Task> filterByStatus(TaskStatus status) {
        return tasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toUnmodifiableList());
    }

    public List<Task> filterByTitleContains(String query) {
        String q = query == null ? "" : query.toLowerCase(Locale.ROOT);
        return tasks.stream().filter(t -> t.getTitle().toLowerCase(Locale.ROOT).contains(q))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Task> filter(Predicate<Task> predicate) {
        return tasks.stream().filter(predicate).collect(Collectors.toUnmodifiableList());
    }

    public List<Task> getAllSortedByCreationDate(boolean ascending) {
        Comparator<Task> cmp = Comparator.comparing(Task::getCreatedAt);
        if (!ascending) cmp = cmp.reversed();
        return tasks.stream().sorted(cmp).collect(Collectors.toUnmodifiableList());
    }

    public List<Task> getAll() {
        return Collections.unmodifiableList(tasks);
    }

    public void clear() {
        tasks.clear();
    }

    // ------------------ PERSISTENCE (CSV) --------
    // CSV header: id,title,description,status,createdAtEpochMilli
    public void saveToFile(Path file) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            w.write("id,title,description,status,createdAt\n");
            w.newLine();
            for (Task t : tasks) {
                String line = String.join(",",
                        quote(t.getId().toString()),
                        quote(t.getTitle()),
                        quote(t.getDescription()),
                        quote(t.getStatus().name()),
                        quote(Long.toString(t.getCreatedAt().toEpochMilli()))
                );
                w.write(line);
                w.newLine();
            }
        }
    }

    public static TaskManager loadFromFile(Path file) throws IOException {
        TaskManager tm = new TaskManager();
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String header = r.readLine(); // skip header
            if (header == null) return tm;
            String line;
            while ((line = r.readLine()) != null) {
                List<String> cols = parseCsvLine(line);
                if (cols.size() != 5) continue; // skip malformed
                UUID id = UUID.fromString(cols.get(0));
                String title = cols.get(1);
                String description = cols.get(2);
                TaskStatus status = TaskStatus.valueOf(cols.get(3));
                long epoch = Long.parseLong(cols.get(4));
                Task t = new Task(id, title, description, Instant.ofEpochMilli(epoch), status);
                tm.tasks.add(t);
            }
        }
        return tm;
    }

    private static String quote(String s) {
        if (s == null) s = "";
        String escaped = s.replace(""", """");
        return """ + escaped + """;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> cols = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == ',') {
                    cols.add(sb.toString());
                    sb.setLength(0);
                } else if (c == '"') {
                    inQuotes = true;
                } else {
                    sb.append(c);
                }
            }
        }
        cols.add(sb.toString());
        return cols;
    }
}
