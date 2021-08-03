package com.example.servicedesk.web.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.UUID;

@Accessors(chain = true)
@Data
public class GetTicketDto {
  UUID id;
  String title;
  String number;
  String email;
  String description;
  String priority;
  String status;
  ZonedDateTime created;
}
