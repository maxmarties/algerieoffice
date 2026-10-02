package com.rinitec.algerieoffice.web.form.company.newsletter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class EmailingForm implements Serializable {
	private static final long serialVersionUID = 2111079011378002913L;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer type;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String subject;
	
	private String title;
	
	@ValidChose
	private Integer contact;
	
	private String detail;
	private String custom;
	
	private List<String> items;
	private List<Long> contacts;
	
	private Integer sector;
	private Integer wilaya;
	
	private String easyid;
	
	private boolean hasResend;
	
	public EmailingForm() {
		this.items = new ArrayList<String>();
		this.contacts = new ArrayList<Long>();
	}
	
	public EmailingForm(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getContact() {
		return contact;
	}

	public void setContact(Integer contact) {
		this.contact = contact;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public List<Long> getContacts() {
		return contacts;
	}

	public void setContacts(List<Long> contacts) {
		this.contacts = contacts;
	}
	
	public Integer getSector() {
		return sector;
	}
	
	public void setSector(Integer sector) {
		this.sector = sector;
	}
	
	public Integer getWilaya() {
		return wilaya;
	}
	
	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}
	
	public String getEasyid() {
		return easyid;
	}
	
	public void setEasyid(String easyid) {
		this.easyid = easyid;
	}
	
	public boolean isHasResend() {
		return hasResend;
	}
	
	public void setHasResend(boolean hasResend) {
		this.hasResend = hasResend;
	}

	@Override
	public String toString() {
		return "EmailingForm [companyId=" + companyId + ", type=" + type + ", subject=" + subject + ", title=" + title
				+ ", contact=" + contact + ", detail=" + detail + ", custom=" + custom + ", items=" + items
				+ ", contacts=" + contacts + ", sector=" + sector + ", wilaya=" + wilaya + ", easyid=" + easyid
				+ ", hasResend=" + hasResend + "]";
	}

}
