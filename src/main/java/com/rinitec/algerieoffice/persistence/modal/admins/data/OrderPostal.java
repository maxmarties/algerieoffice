package com.rinitec.algerieoffice.persistence.modal.admins.data;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "orders_postal")
public class OrderPostal implements Serializable {
	private static final long serialVersionUID = 7646601619116306902L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 24)
	private String amount;
	
	@Column(nullable = false)
	private DateTime orderDate;
	
	@Column(nullable = true, unique = true, length = 60)
	private String serial;
	
	public OrderPostal() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public DateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(DateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public String toString() {
		return "OrderPostal [id=" + id + ", companyId=" + companyId + ", amount=" + amount + ", orderDate=" + orderDate
				+ ", serial=" + serial + "]";
	}
	
}
