package com.rinitec.algerieoffice.persistence.result;

import java.util.UUID;

public class CategoryMini {

	private final UUID id;
	private final String name;
	private final Long count;
	
	public CategoryMini(final UUID id, final String name, final Long count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public Long getCount() {
		return count;
	}
	
	public String uuid() {
		return id.toString();
	}

	@Override
	public String toString() {
		return "CategoryMini [id=" + id + ", name=" + name + ", count=" + count + "]";
	}
	
}
