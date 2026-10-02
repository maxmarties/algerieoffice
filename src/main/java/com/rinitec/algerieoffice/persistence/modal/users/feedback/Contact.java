package com.rinitec.algerieoffice.persistence.modal.users.feedback;

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
@Table(name = "contacts")
public class Contact implements Serializable {
	private static final long serialVersionUID = -6599058928299181193L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "contact_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean pro;
	
	@Column(nullable = false)
	private Boolean sexe;
	
	@Max(5)
	@Column(nullable = false)
	private Integer object;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = true, length = 60)
	private String function;
	
	@Column(nullable = false, length = 10)
	private String phone;
	
	@Column(nullable = false, length = 100)
	private String email;
	
	@Column(nullable = false, length = 5)
	private String postal;
	
	@Column(nullable = false, length = 512)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean approuved;
	
	@Column(nullable = true)
	private Long approuvedBy;
	
	public Contact() {
		this.approuved = false;
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

	public Boolean getPro() {
		return pro;
	}

	public void setPro(Boolean pro) {
		this.pro = pro;
	}

	public Boolean getSexe() {
		return sexe;
	}

	public void setSexe(Boolean sexe) {
		this.sexe = sexe;
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
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

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	public Long getApprouvedBy() {
		return approuvedBy;
	}

	public void setApprouvedBy(Long approuvedBy) {
		this.approuvedBy = approuvedBy;
	}
	
	public String getDisplayName() {
		return firstName.concat(" ").concat(lastName);
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", companyId=" + companyId + ", pro=" + pro + ", sexe=" + sexe + ", object="
				+ object + ", firstName=" + firstName + ", lastName=" + lastName + ", function=" + function + ", phone="
				+ phone + ", email=" + email + ", postal=" + postal + ", message=" + message + ", postedDate="
				+ postedDate + ", approuved=" + approuved + ", approuvedBy=" + approuvedBy + "]";
	}

}
