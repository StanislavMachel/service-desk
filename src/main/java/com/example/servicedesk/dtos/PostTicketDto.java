package com.example.servicedesk.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostTicketDto {
	@NotBlank
	String title;

	@NotBlank
	@Email
	String email;

	@NotBlank
	String description;

	@NotNull
	String priority;
}
