package com.example.demo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTitleContaining(String keyword);

    List<Ticket> findByStatus(String status);

    List<Ticket> findByTitleContainingAndStatus(String keyword, String status);

    Optional<Ticket> findByTicketNo(String ticketNo);

    boolean existsByTicketNo(String ticketNo);

    boolean existsByTicketNoAndIdNot(String ticketNo, Long id);
}
