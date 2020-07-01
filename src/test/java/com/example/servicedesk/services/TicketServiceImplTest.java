package com.example.servicedesk.services;

import com.example.servicedesk.dtos.GetTicketDto;
import com.example.servicedesk.dtos.GetTicketListDto;
import com.example.servicedesk.model.Priority;
import com.example.servicedesk.model.Status;
import com.example.servicedesk.model.Ticket;
import com.example.servicedesk.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

	@Mock
	TicketRepository ticketRepository;

	@InjectMocks
	TicketServiceImpl ticketService;

	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		ModelMapper modelMapper = new ModelMapper();

		ticketService = new TicketServiceImpl(ticketRepository, modelMapper);
	}

	@Test
	void getById() {

		UUID id = UUID.randomUUID();
		String title = "Ticket title";
		String number = "0000000000000001";
		String email = "example@example.com";
		String description = "Ticket description";
		Priority priority = Priority.TRIVIAL;
		Status status = Status.NEW;
		ZonedDateTime created = ZonedDateTime.now();

		Ticket ticket = new Ticket()
				.setTitle(title)
				.setId(id)
				.setEmail(email)
				.setDescription(description)
				.setPriority(priority)
				.setStatus(status);

		ReflectionTestUtils.setField(ticket, "id", id);
		ReflectionTestUtils.setField(ticket, "number", number);
		ReflectionTestUtils.setField(ticket, "created", created);

		Mockito.when(ticketRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ticket));

		GetTicketDto getTicketDto = ticketService.getById(id);

		Mockito.verify(ticketRepository, Mockito.times(1)).findById(id);

		assertNotNull(getTicketDto);
		assertEquals(id, getTicketDto.getId());
		assertEquals(title, getTicketDto.getTitle());
		assertEquals(number, getTicketDto.getNumber());
		assertEquals(email, getTicketDto.getEmail());
		assertEquals(description, getTicketDto.getDescription());
		assertEquals(priority.toString(), getTicketDto.getPriority());
		assertEquals(status.toString(), getTicketDto.getStatus());
		assertEquals(created, getTicketDto.getCreated());
	}

	@Test
	void getOpenTickets() {

		UUID id1 = UUID.randomUUID();
		String title1 = "Ticket title 1";
		String number1 = "0000000000000001";
		String email1 = "example1@example.com";
		String description1 = "Ticket description 1";
		Priority priority1 = Priority.TRIVIAL;
		Status status1 = Status.NEW;
		ZonedDateTime created1 = ZonedDateTime.now();

		Ticket ticket = new Ticket()
				.setTitle(title1)
				.setId(id1)
				.setEmail(email1)
				.setDescription(description1)
				.setPriority(priority1)
				.setStatus(status1);

		ReflectionTestUtils.setField(ticket, "id", id1);
		ReflectionTestUtils.setField(ticket, "number", number1);
		ReflectionTestUtils.setField(ticket, "created", created1);

		List<Ticket> ticketList = Arrays.asList(ticket, new Ticket());

		PageImpl<Ticket> ticketPage = new PageImpl<>(ticketList, PageRequest.of(0, 20), ticketList.size());

		Mockito.when(ticketRepository.findAllByStatusIn(Mockito.anyList(), Mockito.any())).thenReturn(ticketPage);

		GetTicketListDto getTicketListDto = ticketService.getOpenTickets(PageRequest.of(0, 20));

		Mockito.verify(ticketRepository, Mockito.times(1)).findAllByStatusIn(Mockito.anyList(), Mockito.any());


		assertNotNull(getTicketListDto);
		assertEquals(2, getTicketListDto.getTotal());
		assertEquals(1, getTicketListDto.getTotalPages());
		assertNotNull(getTicketListDto.getItems());
		assertEquals(2, getTicketListDto.getItems().size());
		GetTicketDto firstItem = getTicketListDto.getItems().get(0);
		assertNotNull(firstItem);
		assertEquals(id1, firstItem.getId());
		assertEquals(title1, firstItem.getTitle());
		assertEquals(number1, firstItem.getNumber());
		assertEquals(email1, firstItem.getEmail());
		assertEquals(description1, firstItem.getDescription());
		assertEquals(priority1.toString(), firstItem.getPriority());
		assertEquals(status1.toString(), firstItem.getStatus());
		assertEquals(created1, firstItem.getCreated());
	}

	@Test
	void create() {
	}

	@Test
	void update() {
	}
}