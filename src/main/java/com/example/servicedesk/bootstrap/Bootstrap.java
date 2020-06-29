package com.example.servicedesk.bootstrap;

import com.example.servicedesk.model.Priority;
import com.example.servicedesk.model.Status;
import com.example.servicedesk.model.Ticket;
import com.example.servicedesk.repositories.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements CommandLineRunner {

	private final TicketRepository ticketRepository;

	public Bootstrap(TicketRepository ticketRepository){
		this.ticketRepository = ticketRepository;
	}

	@Override
	public void run(String... args) {
		Ticket ticket = new Ticket()
				.setTitle("I have problem")
				.setDescription("Sample description")
				.setPriority(Priority.TRIVIAL)
				.setEmail("example@example.com")
				.setStatus(Status.OPEN);

		Ticket ticket1 = new Ticket()
				.setTitle("I major problem")
				.setDescription("OMG")
				.setPriority(Priority.MAJOR)
				.setEmail("example1@example.com")
				.setStatus(Status.CLOSED);

		Ticket ticket3 = new Ticket()
				.setTitle("Oooo")
				.setDescription("Sample description")
				.setPriority(Priority.BLOCKER)
				.setEmail("example@example.com")
				.setStatus(Status.OPEN);

		ticketRepository.save(ticket);
		ticketRepository.save(ticket1);
		ticketRepository.save(ticket3);
	}
}
