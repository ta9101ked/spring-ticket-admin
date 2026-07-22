package com.example.demo;

import java.util.List;

public final class TicketStatus {

    public static final String OPEN = "Open";
    public static final String IN_PROGRESS = "In_Progress";
    public static final String DONE = "Done";

    public static final List<String> ALL = List.of(OPEN, IN_PROGRESS, DONE);

    private TicketStatus() {
    }
}
