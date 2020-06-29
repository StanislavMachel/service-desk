package com.example.servicedesk.dtos;

import com.example.servicedesk.model.Priority;
import com.example.servicedesk.model.Status;
import lombok.Data;

@Data
public class PutTicketDto {
	private String title;
	private String email;
	private String description;
	private Priority priority;
	private Status status;
}
