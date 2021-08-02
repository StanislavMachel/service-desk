package com.example.servicedesk.repositories;

import com.example.servicedesk.model.Status;
import com.example.servicedesk.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
  Page<Ticket> findAllByStatusIn(List<Status> statuses, Pageable pageable);
}
