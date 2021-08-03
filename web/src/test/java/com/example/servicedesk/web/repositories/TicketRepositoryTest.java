package com.example.servicedesk.web.repositories;

import com.example.servicedesk.core.model.Priority;
import com.example.servicedesk.core.model.Status;
import com.example.servicedesk.core.model.Ticket;
import com.example.servicedesk.core.repositories.TicketRepository;
import com.example.servicedesk.web.ServiceDeskApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(
    classes = ServiceDeskApplication.class,
    loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TicketRepositoryTest {

  @Autowired private TicketRepository ticketRepository;

  @Autowired private TestEntityManager testEntityManager;

  @BeforeEach
  void setUp() {
    Ticket ticket1 =
        new Ticket()
            .setTitle("Ticket title 1")
            .setDescription("Ticket 1 description...")
            .setEmail("example1@example.com")
            .setPriority(Priority.BLOCKER)
            .setStatus(Status.NEW);

    Ticket ticket2 =
        new Ticket()
            .setTitle("Ticket title 2")
            .setDescription("Ticket 2 description...")
            .setEmail("example2@example.com")
            .setPriority(Priority.CRITICAL)
            .setStatus(Status.TODO);

    Ticket ticket3 =
        new Ticket()
            .setTitle("Ticket title 3")
            .setDescription("Ticket 3 description...")
            .setEmail("example3@example.com")
            .setPriority(Priority.MAJOR)
            .setStatus(Status.IN_PROGRESS);

    Ticket ticket4 =
        new Ticket()
            .setTitle("Ticket title 4")
            .setDescription("Ticket 4 description...")
            .setEmail("example4@example.com")
            .setPriority(Priority.MINOR)
            .setStatus(Status.DONE);

    Ticket ticket5 =
        new Ticket()
            .setTitle("Ticket title 5")
            .setDescription("Ticket 5 description...")
            .setEmail("example5@example.com")
            .setPriority(Priority.MAJOR)
            .setStatus(Status.DONE);

    Ticket ticket6 =
        new Ticket()
            .setTitle("Ticket title 6")
            .setDescription("Ticket 6 description...")
            .setEmail("example6@example.com")
            .setPriority(Priority.TRIVIAL)
            .setStatus(Status.CLOSED);

    testEntityManager.persist(ticket1);
    testEntityManager.persist(ticket2);
    testEntityManager.persist(ticket3);
    testEntityManager.persist(ticket4);
    testEntityManager.persist(ticket5);
    testEntityManager.persist(ticket6);
  }

  @Test
  void findAllByStatusInAllStatusExceptClosedPageIndex0PageSize3SortByPriorityDesc() {

    Page<Ticket> page =
        ticketRepository.findAllByStatusIn(
            Arrays.asList(Status.NEW, Status.TODO, Status.IN_PROGRESS, Status.DONE),
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "priority")));

    assertNotNull(page);
    assertEquals(5, page.getTotalElements());
    assertNotNull(page.getContent());
    assertEquals(3, page.getContent().size());
    assertEquals(Priority.MINOR, page.getContent().get(0).getPriority());
  }

  @Test
  void findAllByStatusInAllStatusExceptClosedPageIndex0PageSize3SortByStatusDesc2() {

    Page<Ticket> page =
        ticketRepository.findAllByStatusIn(
            Arrays.asList(Status.NEW, Status.TODO, Status.IN_PROGRESS, Status.DONE),
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "status")));

    assertNotNull(page);
    assertEquals(5, page.getTotalElements());
    assertNotNull(page.getContent());
    assertEquals(3, page.getContent().size());
    assertEquals(Status.DONE, page.getContent().get(0).getStatus());
  }

  @Test
  void findAllByStatusInAllStatusExceptClosedPageIndex0PageSize3SortByCreatedDesc2() {

    Page<Ticket> page =
        ticketRepository.findAllByStatusIn(
            Arrays.asList(Status.NEW, Status.TODO, Status.IN_PROGRESS, Status.DONE),
            PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "created")));

    assertNotNull(page);
    assertEquals(5, page.getTotalElements());
    assertNotNull(page.getContent());
    assertEquals(3, page.getContent().size());
    assertEquals("Ticket title 5", page.getContent().get(0).getTitle());
  }
}
