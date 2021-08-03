package com.example.servicedesk.web.services;

import com.example.servicedesk.core.model.Status;
import com.example.servicedesk.core.model.Ticket;
import com.example.servicedesk.core.repositories.TicketRepository;
import com.example.servicedesk.web.dtos.GetTicketDto;
import com.example.servicedesk.web.dtos.GetTicketListDto;
import com.example.servicedesk.web.dtos.PostTicketDto;
import com.example.servicedesk.web.dtos.PutTicketDto;
import com.example.servicedesk.web.exceptions.TicketNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
  private final TicketRepository ticketRepository;
  private final ModelMapper modelMapper;

  public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper) {
    this.ticketRepository = ticketRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public GetTicketDto getById(UUID id) {
    return ticketRepository
        .findById(id)
        .map(ticket -> modelMapper.map(ticket, GetTicketDto.class))
        .orElseThrow(() -> new TicketNotFoundException(id));
  }

  @Override
  public GetTicketListDto getTicketsExceptWithClosedStatus(Pageable pageable) {

    Page<Ticket> page =
        ticketRepository.findAllByStatusIn(
            Arrays.asList(Status.NEW, Status.TODO, Status.IN_PROGRESS, Status.DONE), pageable);

    return new GetTicketListDto()
        .setTotal(page.getTotalElements())
        .setTotalPages(page.getTotalPages())
        .setItems(
            page.getContent().stream()
                .map(ticket -> modelMapper.map(ticket, GetTicketDto.class))
                .collect(Collectors.toList()));
  }

  @Override
  public GetTicketDto create(PostTicketDto postTicketDto) {
    Ticket ticket =
        ticketRepository.save(modelMapper.map(postTicketDto, Ticket.class).setStatus(Status.NEW));
    return modelMapper.map(ticket, GetTicketDto.class);
  }

  @Override
  public GetTicketDto update(UUID id, PutTicketDto putTicketDto) {
    return ticketRepository
        .findById(id)
        .map(
            ticket -> {
              ticket
                  .setTitle(putTicketDto.getTitle())
                  .setEmail(putTicketDto.getEmail())
                  .setDescription(putTicketDto.getDescription())
                  .setPriority(putTicketDto.getPriority())
                  .setStatus(putTicketDto.getStatus());
              return modelMapper.map(ticketRepository.save(ticket), GetTicketDto.class);
            })
        .orElseThrow(() -> new TicketNotFoundException(id));
  }
}
