package com.example.servicedesk.exceptions;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {
	public TicketNotFoundException(UUID id) {
		super("Could not find ticket " + id);
	}
}
