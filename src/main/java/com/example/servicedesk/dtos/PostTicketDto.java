package com.example.servicedesk.dtos;

import lombok.Data;

@Data
public class PostTicketDto {
	String title;
	String email;
	String description;
	String priority;
}
