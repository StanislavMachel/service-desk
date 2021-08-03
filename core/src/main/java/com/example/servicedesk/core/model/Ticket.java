package com.example.servicedesk.core.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Ticket {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String title;

  @Setter(AccessLevel.PROTECTED)
  @GeneratorType(type = TicketSerialNumberGenerator.class, when = GenerationTime.INSERT)
  @Column(updatable = false)
  private String number;

  private String email;
  private String description;

  private Priority priority;

  private Status status;

  @Setter(AccessLevel.PROTECTED)
  @CreationTimestamp
  private ZonedDateTime created;
}
