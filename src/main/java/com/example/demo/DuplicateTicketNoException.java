package com.example.demo;

public class DuplicateTicketNoException extends RuntimeException {

    public DuplicateTicketNoException(String ticketNo) {
        super("Ticket number already exists: " + ticketNo);
    }
}
