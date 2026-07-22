package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    void findByStatus_returnsOnlyMatchingRows() {
        ticketRepository.save(ticket("T-1", "Alpha", TicketStatus.OPEN, TicketPriority.HIGH));
        ticketRepository.save(ticket("T-2", "Beta", TicketStatus.DONE, TicketPriority.LOW));

        List<Ticket> result = ticketRepository.findByStatus(TicketStatus.OPEN);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTicketNo()).isEqualTo("T-1");
    }

    @Test
    void findByTitleContainingAndStatus_filtersByBoth() {
        ticketRepository.save(ticket("T-1", "Login bug", TicketStatus.OPEN, TicketPriority.HIGH));
        ticketRepository.save(ticket("T-2", "Login help", TicketStatus.DONE, TicketPriority.LOW));
        ticketRepository.save(ticket("T-3", "Other", TicketStatus.OPEN, TicketPriority.MEDIUM));

        List<Ticket> result = ticketRepository.findByTitleContainingAndStatus("Login", TicketStatus.OPEN);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTicketNo()).isEqualTo("T-1");
    }

    @Test
    void existsByTicketNo_detectsDuplicate() {
        ticketRepository.save(ticket("T-DUP", "One", TicketStatus.OPEN, TicketPriority.LOW));

        assertThat(ticketRepository.existsByTicketNo("T-DUP")).isTrue();
        assertThat(ticketRepository.existsByTicketNo("T-NEW")).isFalse();
    }

    private Ticket ticket(String no, String title, String status, String priority) {
        Ticket t = new Ticket();
        t.setTicketNo(no);
        t.setTitle(title);
        t.setStatus(status);
        t.setPriority(priority);
        t.setRequesterName("Tester");
        return t;
    }
}
