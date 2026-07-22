package com.example.demo;

import java.util.List;

public final class TicketPriority {

    public static final String LOW = "Low";
    public static final String MEDIUM = "Medium";
    public static final String HIGH = "High";

    public static final List<String> ALL = List.of(LOW, MEDIUM, HIGH);

    private TicketPriority() {
    }
}
