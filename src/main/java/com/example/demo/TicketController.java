package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ModelAttribute
    public void addFormOptions(Model model) {
        model.addAttribute("statuses", TicketStatus.ALL);
        model.addAttribute("priorities", TicketPriority.ALL);
    }

    @GetMapping("/tickets")
    public String getAllTickets(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        model.addAttribute("tickets", ticketService.search(keyword, status));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("status", status == null ? "" : status);
        return "tickets/index";
    }

    @GetMapping("/tickets/new")
    public String newForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/new";
    }

    @PostMapping("/tickets")
    public String create(
            @Valid @ModelAttribute("ticket") Ticket ticket,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tickets/new";
        }
        try {
            ticketService.create(ticket);
        } catch (DuplicateTicketNoException ex) {
            bindingResult.rejectValue("ticketNo", "duplicate", ex.getMessage());
            return "tickets/new";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Ticket created.");
        return "redirect:/tickets";
    }

    @GetMapping("/tickets/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "tickets/edit";
    }

    @PostMapping("/tickets/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("ticket") Ticket ticket,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tickets/edit";
        }
        try {
            ticketService.update(id, ticket);
        } catch (DuplicateTicketNoException ex) {
            bindingResult.rejectValue("ticketNo", "duplicate", ex.getMessage());
            return "tickets/edit";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Ticket updated.");
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ticketService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket deleted.");
        return "redirect:/tickets";
    }
}
