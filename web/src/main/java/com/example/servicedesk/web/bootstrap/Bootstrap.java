package com.example.servicedesk.web.bootstrap;

import com.example.servicedesk.core.model.Priority;
import com.example.servicedesk.core.model.Status;
import com.example.servicedesk.core.model.Ticket;
import com.example.servicedesk.core.repositories.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Bootstrap implements CommandLineRunner {

  private static final Random random = new Random();

  private final TicketRepository ticketRepository;

  public Bootstrap(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Override
  public void run(String... args) {

    for (int i = 0; i < 100; i++) {
      Ticket ticket =
          new Ticket()
              .setTitle("Ticket title " + i)
              .setDescription("Ticket " + i + " description...")
              .setEmail("example" + i + "@example.com")
              .setPriority(Priority.values()[random.nextInt(5)])
              .setStatus(Status.values()[random.nextInt(5)]);

      ticketRepository.save(ticket);
    }
  }
}
