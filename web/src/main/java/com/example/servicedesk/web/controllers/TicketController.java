package com.example.servicedesk.web.controllers;

import com.example.servicedesk.web.assemblers.GetTicketDtoAssembler;
import com.example.servicedesk.web.dtos.GetTicketDto;
import com.example.servicedesk.web.dtos.PostTicketDto;
import com.example.servicedesk.web.dtos.PutTicketDto;
import com.example.servicedesk.web.services.TicketService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("http://localhost:4200")
@RequestMapping(TicketController.URL)
@RestController
public class TicketController {

  public static final String URL = "/api/tickets";

  private final TicketService ticketService;
  private final GetTicketDtoAssembler getTicketDtoAssembler;

  public TicketController(
      TicketService ticketService, GetTicketDtoAssembler getTicketDtoAssembler) {
    this.ticketService = ticketService;
    this.getTicketDtoAssembler = getTicketDtoAssembler;
  }

  @GetMapping("/{id}")
  public EntityModel<GetTicketDto> getById(@PathVariable UUID id) {
    return getTicketDtoAssembler.toModel(ticketService.getById(id));
  }

  @GetMapping
  public CollectionModel<EntityModel<GetTicketDto>> get(Pageable pageable) {

    var tickets =
        ticketService.getTicketsExceptWithClosedStatus(pageable).getItems().stream()
            .map(getTicketDtoAssembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(tickets, linkTo(methodOn(TicketController.class).get(null)).withSelfRel());
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
