
package com.refactor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * EXERCÍCIO: Code Smell - Long Parameter List
 * Objetivo:
 *  - Introduce Parameter Object: criar MeetingOptions e substituir a lista longa
 * Critério de aceite:
 *  - Método com até 3 parâmetros
 */
public class LongParameterListExample {

    // Record que encapsula todos os parâmetros da reunião
    public record MeetingOptions(LocalDate date, LocalTime start, LocalTime end,
                                 String room, boolean online, String meetingLink,
                                 List<String> participants) {}

    // Método refatorado usando o objeto de parâmetros
    public String scheduleMeeting(String title, MeetingOptions opts) {
        String mode = opts.online() ? ("ONLINE (" + opts.meetingLink() + ")") : ("ROOM " + opts.room());
        return "%s on %s from %s to %s, %s, participants=%d"
                .formatted(title, opts.date(), opts.start(), opts.end(), mode, opts.participants().size());
    }
}
