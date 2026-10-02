package com.rinitec.algerieoffice.persistence.modal.admins.realtime;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "contactus")
public class Contactus implements Serializable {
	private static final long serialVersionUID = -223492656666551803L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "contact_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = true)
	private Long userId;
	
	@Max(3)
	@Column(nullable = false)
	private Integer object;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, length = 10)
	private String phone;
	
	@Column(nullable = false, length = 100)
	private String email;
	
	@Column(nullable = true, length = 60)
	private String company;
	
	@Column(nullable = false, length = 512)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean consulted;
	
	public Contactus() {
		this.consulted = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getObject() {
		return object;
	}

	public void setObject(Integer object) {
		this.object = object;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public void setConsulted(boolean consulted) {
		this.consulted = consulted;
	}

	@Override
	public String toString() {
		return "Homecontact [id=" + id + ", userId=" + userId + ", object=" + object + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", company=" + company
				+ ", message=" + message + ", postedDate=" + postedDate + ", consulted=" + consulted + "]";
	}

}
