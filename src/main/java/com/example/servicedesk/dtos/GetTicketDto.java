package com.example.servicedesk.dtos;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

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
