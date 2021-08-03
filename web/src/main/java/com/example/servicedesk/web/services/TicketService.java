package com.example.servicedesk.web.services;

import com.example.servicedesk.web.dtos.GetTicketDto;
import com.example.servicedesk.web.dtos.GetTicketListDto;
import com.example.servicedesk.web.dtos.PostTicketDto;
import com.example.servicedesk.web.dtos.PutTicketDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {

  GetTicketDto getById(UUID id);

  GetTicketListDto getTicketsExceptWithClosedStatus(Pageable pageable);

  GetTicketDto create(PostTicketDto postTicketDto);

  GetTicketDto update(UUID id, PutTicketDto putTicketDto);
}
