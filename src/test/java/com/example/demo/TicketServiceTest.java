package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    void create_rejectsDuplicateTicketNo() {
        ticketService.create(ticket("T-100", "First"));

        assertThatThrownBy(() -> ticketService.create(ticket("T-100", "Second")))
                .isInstanceOf(DuplicateTicketNoException.class);
    }

    @Test
    void findById_throwsWhenMissing() {
        assertThatThrownBy(() -> ticketService.findById(999L))
                .isInstanceOf(TicketNotFoundException.class);
    }

    @Test
    void search_byKeywordAndStatus() {
        ticketService.create(ticket("T-1", "Payment failed", TicketStatus.OPEN));
        ticketService.create(ticket("T-2", "Payment failed", TicketStatus.DONE));
        ticketService.create(ticket("T-3", "Other", TicketStatus.OPEN));

        assertThat(ticketService.search("Payment", TicketStatus.OPEN)).hasSize(1);
        assertThat(ticketService.search("", "")).hasSize(3);
    }

    @Test
    void update_keepsSameId() {
        Ticket created = ticketService.create(ticket("T-10", "Old title"));
        Long id = created.getId();

        Ticket updated = ticket("T-10", "New title");
        Ticket saved = ticketService.update(id, updated);

        assertThat(saved.getId()).isEqualTo(id);
        assertThat(saved.getTitle()).isEqualTo("New title");
    }

    private Ticket ticket(String no, String title) {
        return ticket(no, title, TicketStatus.OPEN);
    }

    private Ticket ticket(String no, String title, String status) {
        Ticket t = new Ticket();
        t.setTicketNo(no);
        t.setTitle(title);
        t.setStatus(status);
        t.setPriority(TicketPriority.MEDIUM);
        t.setRequesterName("Tester");
        return t;
    }
}
