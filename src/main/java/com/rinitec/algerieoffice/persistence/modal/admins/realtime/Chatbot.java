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
@Table(name = "chatbots")
public class Chatbot implements Serializable {
	private static final long serialVersionUID = 2196132679349426471L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "chatbot_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = true)
	private Long companyId;
	
	@Max(5)
	@Column(nullable = false)
	private Integer domaine;
	
	@Column(nullable = false)
	private Boolean discute;
	
	@Column(nullable = true)
	private Boolean account;
	
	@Column(nullable = true, length = 100)
	private String email;
	
	@Column(nullable = true, length = 256)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean consulted;
	
	@Column(nullable = true)
	private Long consultedBy;
	
	public Chatbot() {
		this.consulted = false;
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

	public Integer getDomaine() {
		return domaine;
	}

	public void setDomaine(Integer domaine) {
		this.domaine = domaine;
	}

	public Boolean getDiscute() {
		return discute;
	}

	public void setDiscute(Boolean discute) {
		this.discute = discute;
	}

	public Boolean getAccount() {
		return account;
	}

	public void setAccount(Boolean account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Long getConsultedBy() {
		return consultedBy;
	}

	public void setConsultedBy(Long consultedBy) {
		this.consultedBy = consultedBy;
	}

	@Override
	public String toString() {
		return "Chatbot [id=" + id + ", companyId=" + companyId + ", domaine=" + domaine + ", discute=" + discute
				+ ", account=" + account + ", email=" + email + ", message=" + message + ", postedDate=" + postedDate
				+ ", consulted=" + consulted + ", consultedBy=" + consultedBy + "]";
	}

}
