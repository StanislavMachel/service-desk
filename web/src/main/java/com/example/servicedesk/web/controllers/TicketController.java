package com.example.servicedesk.web.controllers;

import com.example.servicedesk.web.dtos.GetTicketDto;
import com.example.servicedesk.web.dtos.GetTicketListDto;
import com.example.servicedesk.web.dtos.PostTicketDto;
import com.example.servicedesk.web.dtos.PutTicketDto;
import com.example.servicedesk.web.services.TicketService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@RequestMapping(TicketController.URL)
@RestController
public class TicketController {

  public static final String URL = "/api/tickets";

  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetTicketDto> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(ticketService.getById(id));
  }

  @GetMapping
  public ResponseEntity<GetTicketListDto> get(Pageable pageable) {
    return ResponseEntity.ok(ticketService.getTicketsExceptWithClosedStatus(pageable));
  }

  @PostMapping
  public ResponseEntity<GetTicketDto> post(@Valid @RequestBody PostTicketDto postTicketDto) {
    return new ResponseEntity<>(ticketService.create(postTicketDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<GetTicketDto> put(
      @PathVariable UUID id, @Valid @RequestBody PutTicketDto putTicketDto) {
    return ResponseEntity.ok(ticketService.update(id, putTicketDto));
  }
}
