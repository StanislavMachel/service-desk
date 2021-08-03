package com.example.servicedesk.web.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class PostTicketDto {
  @NotBlank String title;

  @NotBlank @Email String email;

  @NotBlank String description;

  @NotNull String priority;
}
