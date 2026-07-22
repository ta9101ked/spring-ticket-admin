package com.example.demo;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<Ticket> search(String keyword, String status) {
        String safeKeyword = keyword == null ? "" : keyword.trim();
        String safeStatus = status == null ? "" : status.trim();

        if (safeKeyword.isBlank() && safeStatus.isBlank()) {
            return ticketRepository.findAll();
        }
        if (safeKeyword.isBlank()) {
            return ticketRepository.findByStatus(safeStatus);
        }
        if (safeStatus.isBlank()) {
            return ticketRepository.findByTitleContaining(safeKeyword);
        }
        return ticketRepository.findByTitleContainingAndStatus(safeKeyword, safeStatus);
    }

    @Transactional(readOnly = true)
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket create(Ticket ticket) {
        if (ticketRepository.existsByTicketNo(ticket.getTicketNo())) {
            throw new DuplicateTicketNoException(ticket.getTicketNo());
        }
        ticket.setId(null);
        return ticketRepository.save(ticket);
    }

    public Ticket update(Long id, Ticket ticket) {
        findById(id);
        if (ticketRepository.existsByTicketNoAndIdNot(ticket.getTicketNo(), id)) {
            throw new DuplicateTicketNoException(ticket.getTicketNo());
        }
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    public void delete(Long id) {
        findById(id);
        ticketRepository.deleteById(id);
    }
}
