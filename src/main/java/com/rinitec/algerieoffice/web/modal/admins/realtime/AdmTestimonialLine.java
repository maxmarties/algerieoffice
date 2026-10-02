package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;

public class AdmTestimonialLine implements Serializable {
	private static final long serialVersionUID = 3041299350641720253L;
	
	private final String id;
	private final String username;
	private final String function;
	private final String tradename;
	private final String message;
	private final String postedDate;
	private final int note;
	private final boolean approuved;
	
	public AdmTestimonialLine(final Testimonial testimonial) {
		this.id = testimonial.getId().toString();
		this.username = testimonial.getUsername();
		this.function = testimonial.getFunction();
		this.tradename = !StringUtils.isEmpty(testimonial.getTradename()) ? testimonial.getTradename() : "--";
		this.message = testimonial.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(testimonial.getPostedDate());
		this.note = testimonial.getNote();
		this.approuved = testimonial.isApprouved();
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFunction() {
		return function;
	}

	public String getTradename() {
		return tradename;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public int getNote() {
		return note;
	}

	public boolean isApprouved() {
		return approuved;
	}

	@Override
	public String toString() {
		return "AdmTestimonialLine [id=" + id + ", username=" + username + ", function=" + function + ", tradename="
				+ tradename + ", message=" + message + ", postedDate=" + postedDate + ", note=" + note + ", approuved="
				+ approuved + "]";
	}

}
