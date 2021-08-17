package com.example.servicedesk.web.assemblers;

import com.example.servicedesk.web.controllers.TicketController;
import com.example.servicedesk.web.dtos.GetTicketDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GetTicketDtoAssembler
    implements RepresentationModelAssembler<GetTicketDto, EntityModel<GetTicketDto>> {
  @Override
  public EntityModel<GetTicketDto> toModel(GetTicketDto entity) {
    return EntityModel.of(
        entity,
        linkTo(methodOn(TicketController.class).getById(entity.getId())).withSelfRel(),
        linkTo(methodOn(TicketController.class).get(null)).withRel("tickets"));
  }
}
