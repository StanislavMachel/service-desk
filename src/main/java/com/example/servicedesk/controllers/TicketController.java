package com.example.servicedesk.controllers;

import com.example.servicedesk.dtos.GetTicketDto;
import com.example.servicedesk.dtos.PostTicketDto;
import com.example.servicedesk.dtos.PutTicketDto;
import com.example.servicedesk.services.TicketService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/tickets")
@RestController
public class TicketController {
	private final TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@GetMapping("/{id}")
	ResponseEntity<GetTicketDto> getById(@PathVariable UUID id){
		return ResponseEntity.ok(ticketService.getById(id));
	}

	@GetMapping
	ResponseEntity<List<GetTicketDto>> get(Pageable pageable) {
		return ResponseEntity.ok(ticketService.getOpenTickets(pageable));
	}

	@PostMapping
	ResponseEntity<GetTicketDto> post(@RequestBody PostTicketDto postTicketDto) {
		return new ResponseEntity<>(ticketService.create(postTicketDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	ResponseEntity<GetTicketDto> put(@PathVariable UUID id, @RequestBody PutTicketDto putTicketDto){
		return ResponseEntity.ok(ticketService.update(id, putTicketDto));
	}
}
