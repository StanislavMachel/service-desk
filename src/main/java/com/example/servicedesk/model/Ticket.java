package com.example.servicedesk.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Enumerated(EnumType.STRING)
	private Status status;

	@CreationTimestamp
	private ZonedDateTime created;
}
