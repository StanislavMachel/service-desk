package com.example.servicedesk.dtos;

import com.example.servicedesk.model.Priority;
import com.example.servicedesk.model.Status;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class PutTicketDto {

	@NotBlank
	private String title;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String description;

	@NotNull
	private Priority priority;

	@NotNull
	private Status status;
}
