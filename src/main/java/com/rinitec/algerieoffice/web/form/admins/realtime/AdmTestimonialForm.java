package com.rinitec.algerieoffice.web.form.admins.realtime;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class AdmTestimonialForm implements Serializable {
	private static final long serialVersionUID = 3902659567140456768L;
	
	private String id;
	
	@ValidChose
	@NotNull
	private Integer note;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String username;
	
	private String tradename;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String function;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_MESSAGE, message = "{message.input.lenght}")
	private String message;
	
	private boolean approuved;
	
	private Long userId;
	private String displayname;
	private String companyname;
	private String urlAvatar;
	
	public AdmTestimonialForm() {
	}
	
	public AdmTestimonialForm(final Testimonial testimonial, final User user, final String tradename) {
		this.id = testimonial.getId().toString();
		this.note = testimonial.getNote();
		this.username = testimonial.getUsername();
		this.tradename = testimonial.getTradename();
		this.function = testimonial.getFunction();
		this.message = testimonial.getMessage();
		this.approuved = testimonial.isApprouved();
		if(user != null) {
			this.userId = user.getId();
			this.displayname = user.getDisplayName();
			this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
					: "/static/picts/avatars/account_mini-min.jpg";
		}
		this.companyname = !StringUtils.isEmpty(tradename) ? tradename : "--";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	@Override
	public String toString() {
		return "AdmTestimonialForm [id=" + id + ", note=" + note + ", username=" + username + ", tradename=" + tradename
				+ ", function=" + function + ", message=" + message + ", approuved=" + approuved + ", userId=" + userId
				+ ", displayname=" + displayname + ", companyname=" + companyname + ", urlAvatar=" + urlAvatar + "]";
	}

}
