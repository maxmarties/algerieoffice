package com.rinitec.algerieoffice.persistence.result;

import java.util.UUID;

public class UUIDMini {

	private final UUID id;
	private final String name;
	
	public UUIDMini(final UUID id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String uuid() {
		return id.toString();
	}

	@Override
	public String toString() {
		return "UUIDMini [id=" + id + ", name=" + name + "]";
	}
	
}
