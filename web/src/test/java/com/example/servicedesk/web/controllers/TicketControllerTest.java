package com.example.servicedesk.web.controllers;

import com.example.servicedesk.core.model.Priority;
import com.example.servicedesk.core.model.Status;
import com.example.servicedesk.web.dtos.GetTicketDto;
import com.example.servicedesk.web.dtos.GetTicketListDto;
import com.example.servicedesk.web.dtos.PostTicketDto;
import com.example.servicedesk.web.dtos.PutTicketDto;
import com.example.servicedesk.web.exceptions.TicketNotFoundException;
import com.example.servicedesk.web.services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@WebMvcTest
@ContextConfiguration(classes = {TicketController.class, GlobalControllerExceptionHandler.class})
class TicketControllerTest {

  @MockBean private TicketService ticketService;

  @Autowired private MockMvc mockMvc;

  @Test
  void getByIdOk() throws Exception {
    UUID id = UUID.randomUUID();
    ZonedDateTime time = ZonedDateTime.now();
    String description = "Sample description";
    String title = "Sample title";
    String number = "0000000000000001";
    String email = "example@example.com";
    Priority priority = Priority.TRIVIAL;
    Status status = Status.NEW;

    Mockito.when(ticketService.getById(Mockito.any()))
        .thenReturn(
            new GetTicketDto()
                .setId(id)
                .setDescription(description)
                .setTitle(title)
                .setNumber(number)
                .setEmail(email)
                .setPriority(priority.toString())
                .setStatus(status.toString())
                .setCreated(time));

    mockMvc
        .perform(MockMvcRequestBuilders.get(TicketController.URL + "/" + id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", Matchers.is(id.toString())))
        .andExpect(jsonPath("$.title", Matchers.is(title)))
        .andExpect(jsonPath("$.description", Matchers.is(description)))
        .andExpect(jsonPath("$.number", Matchers.is(number)))
        .andExpect(jsonPath("$.email", Matchers.is(email)))
        .andExpect(jsonPath("$.priority", Matchers.is(priority.toString())))
        .andExpect(jsonPath("$.status", Matchers.is(status.toString())))
        .andExpect(
            jsonPath(
                "$.created", Matchers.is(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))));

    Mockito.verify(ticketService, Mockito.times(1)).getById(id);
  }

  @Test
  void getByIdNotFound() throws Exception {
    Mockito.when(ticketService.getById(Mockito.any())).thenThrow(TicketNotFoundException.class);

    mockMvc
        .perform(MockMvcRequestBuilders.get(TicketController.URL + "/" + UUID.randomUUID()))
        .andExpect(status().isNotFound());
  }

  @Test
  void getOk() throws Exception {

    UUID id = UUID.randomUUID();
    ZonedDateTime time = ZonedDateTime.now();
    String description = "Sample description";
    String title = "Sample title";
    String number = "0000000000000001";
    String email = "example@example.com";
    Priority priority = Priority.TRIVIAL;
    Status status = Status.NEW;

    Mockito.when(ticketService.getTicketsExceptWithClosedStatus(Mockito.any()))
        .thenReturn(
            new GetTicketListDto()
                .setItems(
                    Arrays.asList(
                        new GetTicketDto()
                            .setId(id)
                            .setDescription(description)
                            .setTitle(title)
                            .setNumber(number)
                            .setEmail(email)
                            .setPriority(priority.toString())
                            .setStatus(status.toString())
                            .setCreated(time),
                        new GetTicketDto()
                            .setId(UUID.randomUUID())
                            .setDescription("Sample description 2")
                            .setTitle("Sample title 2")
                            .setNumber("0000000000000002")
                            .setEmail("example2@example.com")
                            .setPriority(Priority.BLOCKER.toString())
                            .setStatus(Status.DONE.toString())
                            .setCreated(time)))
                .setTotal(2L)
                .setTotalPages(1));

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(TicketController.URL + "?page=0&size=20&sort=number,desc"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.total", Matchers.is(2)))
        .andExpect(jsonPath("$.totalPages", Matchers.is(1)))
        .andExpect(jsonPath("$.items", Matchers.hasSize(2)))
        .andExpect(jsonPath("$.items[0].id", Matchers.is(id.toString())))
        .andExpect(jsonPath("$.items[0].id", Matchers.is(id.toString())))
        .andExpect(jsonPath("$.items[0].title", Matchers.is(title)))
        .andExpect(jsonPath("$.items[0].description", Matchers.is(description)))
        .andExpect(jsonPath("$.items[0].number", Matchers.is(number)))
        .andExpect(jsonPath("$.items[0].email", Matchers.is(email)))
        .andExpect(jsonPath("$.items[0].priority", Matchers.is(priority.toString())))
        .andExpect(jsonPath("$.items[0].status", Matchers.is(status.toString())))
        .andExpect(
            jsonPath(
                "$.items[0].created",
                Matchers.is(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))));
  }

  @Test
  void postOk() throws Exception {
    UUID id = UUID.randomUUID();
    ZonedDateTime time = ZonedDateTime.now();
    String description = "Sample description";
    String title = "Sample title";
    String number = "0000000000000001";
    String email = "example@example.com";
    Priority priority = Priority.TRIVIAL;
    Status status = Status.NEW;

    Mockito.when(ticketService.create(Mockito.any()))
        .thenReturn(
            new GetTicketDto()
                .setId(id)
                .setDescription(description)
                .setTitle(title)
                .setNumber(number)
                .setEmail(email)
                .setPriority(priority.toString())
                .setStatus(status.toString())
                .setCreated(time));

    PostTicketDto postTicketDto =
        new PostTicketDto()
            .setDescription(description)
            .setEmail(email)
            .setPriority(priority.toString())
            .setTitle(title);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(TicketController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postTicketDto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", Matchers.is(id.toString())))
        .andExpect(jsonPath("$.title", Matchers.is(title)))
        .andExpect(jsonPath("$.description", Matchers.is(description)))
        .andExpect(jsonPath("$.number", Matchers.is(number)))
        .andExpect(jsonPath("$.email", Matchers.is(email)))
        .andExpect(jsonPath("$.priority", Matchers.is(priority.toString())))
        .andExpect(jsonPath("$.status", Matchers.is(status.toString())))
        .andExpect(
            jsonPath(
                "$.created", Matchers.is(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))));

    Mockito.verify(ticketService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  void postBadRequest() throws Exception {
    UUID id = UUID.randomUUID();
    ZonedDateTime time = ZonedDateTime.now();
    String description = "Sample description";
    String title = "Sample title";
    String number = "0000000000000001";
    String email = "example@example.com";
    Priority priority = Priority.TRIVIAL;
    Status status = Status.NEW;

    Mockito.when(ticketService.create(Mockito.any()))
        .thenReturn(
            new GetTicketDto()
                .setId(id)
                .setDescription(description)
                .setTitle(title)
                .setNumber(number)
                .setEmail(email)
                .setPriority(priority.toString())
                .setStatus(status.toString())
                .setCreated(time));

    PostTicketDto postTicketDto = new PostTicketDto();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(TicketController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postTicketDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.description", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.email", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.priority", Matchers.is("must not be null")));

    Mockito.verify(ticketService, Mockito.never()).create(Mockito.any());
  }

  @Test
  void putOk() throws Exception {

    UUID id = UUID.randomUUID();
    ZonedDateTime time = ZonedDateTime.now();
    String descriptionUpdated = "Sample description updated";
    String titleUpdated = "Sample title updated";
    String emailUpdated = "example.updated@example.com";
    Priority priorityUpdated = Priority.BLOCKER;
    Status statusUpdated = Status.DONE;
    String number = "0000000000000001";

    PutTicketDto putTicketDto =
        new PutTicketDto()
            .setTitle(titleUpdated)
            .setEmail(emailUpdated)
            .setDescription(descriptionUpdated)
            .setPriority(priorityUpdated)
            .setStatus(statusUpdated);

    Mockito.when(ticketService.update(Mockito.any(), Mockito.any()))
        .thenReturn(
            new GetTicketDto()
                .setId(id)
                .setDescription(descriptionUpdated)
                .setTitle(titleUpdated)
                .setNumber(number)
                .setEmail(emailUpdated)
                .setPriority(priorityUpdated.toString())
                .setStatus(statusUpdated.toString())
                .setCreated(time));

    mockMvc
        .perform(
            MockMvcRequestBuilders.put(TicketController.URL + "/" + id)
                .content(asJsonString(putTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", Matchers.is(id.toString())))
        .andExpect(jsonPath("$.title", Matchers.is(titleUpdated)))
        .andExpect(jsonPath("$.description", Matchers.is(descriptionUpdated)))
        .andExpect(jsonPath("$.number", Matchers.is(number)))
        .andExpect(jsonPath("$.email", Matchers.is(emailUpdated)))
        .andExpect(jsonPath("$.priority", Matchers.is(priorityUpdated.toString())))
        .andExpect(jsonPath("$.status", Matchers.is(statusUpdated.toString())))
        .andExpect(
            jsonPath(
                "$.created", Matchers.is(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))));

    Mockito.verify(ticketService, Mockito.times(1)).update(eq(id), Mockito.any());
  }

  @Test
  void putNotFound() throws Exception {

    PutTicketDto putTicketDto =
        new PutTicketDto()
            .setTitle("Sample title updated")
            .setEmail("example.updated@example.com")
            .setDescription("Sample description updated")
            .setPriority(Priority.BLOCKER)
            .setStatus(Status.DONE);

    Mockito.when(ticketService.getById(Mockito.any())).thenThrow(TicketNotFoundException.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(TicketController.URL + "/" + UUID.randomUUID())
                .content(asJsonString(putTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    Mockito.verify(ticketService, Mockito.never()).update(any(), any());
  }

  @Test
  void putBadRequest() throws Exception {

    UUID id = UUID.randomUUID();
    PutTicketDto putTicketDto = new PutTicketDto();

    mockMvc
        .perform(
            MockMvcRequestBuilders.put(TicketController.URL + "/" + id)
                .content(asJsonString(putTicketDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.description", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.email", Matchers.is("must not be blank")))
        .andExpect(jsonPath("$.priority", Matchers.is("must not be null")));

    Mockito.verify(ticketService, Mockito.times(0)).update(Mockito.any(), Mockito.any());
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
