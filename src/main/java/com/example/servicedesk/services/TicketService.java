package com.example.servicedesk.services;

import com.example.servicedesk.dtos.GetTicketDto;
import com.example.servicedesk.dtos.PostTicketDto;
import com.example.servicedesk.dtos.PutTicketDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TicketService {
	GetTicketDto getById(UUID id);
	List<GetTicketDto> getOpenTickets(Pageable pageable);
	GetTicketDto create(PostTicketDto postTicketDto);
	GetTicketDto update(UUID id, PutTicketDto putTicketDto);
}
