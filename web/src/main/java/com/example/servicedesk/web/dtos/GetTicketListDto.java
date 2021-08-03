package com.example.servicedesk.web.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetTicketListDto {
  private List<GetTicketDto> items;
  private Long total;
  private Integer totalPages;
}
