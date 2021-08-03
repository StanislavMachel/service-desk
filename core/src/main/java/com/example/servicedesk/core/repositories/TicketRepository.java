package com.example.servicedesk.core.repositories;

import com.example.servicedesk.core.model.Status;
import com.example.servicedesk.core.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
  Page<Ticket> findAllByStatusIn(List<Status> statuses, Pageable pageable);
}
