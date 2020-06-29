package com.example.servicedesk.services;

import com.example.servicedesk.dtos.GetTicketDto;
import com.example.servicedesk.dtos.PostTicketDto;
import com.example.servicedesk.dtos.PutTicketDto;
import com.example.servicedesk.exceptions.TicketNotFoundException;
import com.example.servicedesk.model.Status;
import com.example.servicedesk.model.Ticket;
import com.example.servicedesk.repositories.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
		return ticketRepository.findById(id)
				.map(ticket -> modelMapper.map(ticket, GetTicketDto.class))
				.orElseThrow(() -> new TicketNotFoundException(id));
	}

	@Override
	public List<GetTicketDto> getOpenTickets(Pageable pageable) {
		return ticketRepository.findAllByStatus(Status.OPEN, pageable).stream()
				.map(ticket -> modelMapper.map(ticket, GetTicketDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public GetTicketDto create(PostTicketDto postTicketDto) {
		Ticket ticket = ticketRepository.save(modelMapper.map(postTicketDto, Ticket.class).setStatus(Status.OPEN));
		return modelMapper.map(ticket, GetTicketDto.class);
	}

	@Override
	public GetTicketDto update(UUID id, PutTicketDto putTicketDto) {
		return ticketRepository.findById(id)
				.map(ticket -> {
					ticket.setTitle(putTicketDto.getTitle())
							.setEmail(putTicketDto.getEmail())
							.setDescription(putTicketDto.getDescription())
							.setPriority(putTicketDto.getPriority())
							.setStatus(putTicketDto.getStatus());
					return modelMapper.map(ticketRepository.save(ticket), GetTicketDto.class);
				})
				.orElseThrow(() -> new TicketNotFoundException(id));
	}
}
