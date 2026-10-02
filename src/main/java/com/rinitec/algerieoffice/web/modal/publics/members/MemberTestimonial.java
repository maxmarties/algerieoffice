package com.rinitec.algerieoffice.web.modal.publics.members;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;

public class MemberTestimonial implements Serializable {
	private static final long serialVersionUID = -1451046903105472628L;
	
	private final String username;
	private final String function;
	private final String message;
	private final int note;
	
	public MemberTestimonial(final Testimonial testimonial) {
		this.username = testimonial.getUsername();
		this.function = testimonial.getFunction();
		this.message = testimonial.getMessage();
		this.note = testimonial.getNote();
	}

	public String getUsername() {
		return username;
	}

	public String getFunction() {
		return function;
	}

	public String getMessage() {
		return message;
	}

	public int getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "MemberTestimonial [username=" + username + ", function=" + function + ", message=" + message + ", note="
				+ note + "]";
	}

}
