package com.rinitec.algerieoffice.web.form.admins.marketplace;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidCalendar;

public class AdmOrderForm implements Serializable {
	private static final long serialVersionUID = 5938360770980901157L;
	
	@NotNull
	private String id;
	
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String amount;
	
	@ValidCalendar
	@NotNull
	private String orderDate;
	
	private String serial;
	private boolean response;
	
	public AdmOrderForm() {
	}
	
	public AdmOrderForm(final String id) {
		this.id = id;
		this.response = false;
	}
	
	public AdmOrderForm(final String id, final Long companyId) {
		this.id = id;
		this.companyId = companyId;
		this.response = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "AdmOrderForm [id=" + id + ", companyId=" + companyId + ", amount=" + amount + ", orderDate=" + orderDate
				+ ", serial=" + serial + ", response=" + response + "]";
	}

}
