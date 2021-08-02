package com.example.servicedesk.services;

import com.example.servicedesk.dtos.GetTicketDto;
import com.example.servicedesk.dtos.GetTicketListDto;
import com.example.servicedesk.dtos.PostTicketDto;
import com.example.servicedesk.dtos.PutTicketDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {

  GetTicketDto getById(UUID id);

  GetTicketListDto getTicketsExceptWithClosedStatus(Pageable pageable);

  GetTicketDto create(PostTicketDto postTicketDto);

  GetTicketDto update(UUID id, PutTicketDto putTicketDto);
}
