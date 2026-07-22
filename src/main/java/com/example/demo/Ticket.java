package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ticket No is required")
    @Size(max = 40, message = "Ticket No must be 40 characters or less")
    @Column(name = "ticket_no", nullable = false, unique = true, length = 40)
    private String ticketNo;

    @NotBlank(message = "Title is required")
    @Size(max = 120, message = "Title must be 120 characters or less")
    @Column(nullable = false, length = 120)
    private String title;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Open|In_Progress|Done", message = "Status must be Open, In_Progress, or Done")
    @Column(nullable = false, length = 20)
    private String status;

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "Low|Medium|High", message = "Priority must be Low, Medium, or High")
    @Column(nullable = false, length = 20)
    private String priority;

    @NotBlank(message = "Requester name is required")
    @Size(max = 80, message = "Requester name must be 80 characters or less")
    @Column(name = "requester_name", nullable = false, length = 80)
    private String requesterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
