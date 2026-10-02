package com.rinitec.algerieoffice.persistence.modal.company.team;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "guests")
public class Guest implements Serializable {
	private static final long serialVersionUID = 3042245822234359498L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guest_id")
	private Long id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Integer role;
	
	@Column(nullable = false)
	private DateTime guestDate;
	
	@Column(nullable = false)
	private Long guestBy;
	
	public Guest() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public DateTime getGuestDate() {
		return guestDate;
	}

	public void setGuestDate(DateTime guestDate) {
		this.guestDate = guestDate;
	}

	public Long getGuestBy() {
		return guestBy;
	}

	public void setGuestBy(Long guestBy) {
		this.guestBy = guestBy;
	}

	@Override
	public String toString() {
		return "Guest [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", role=" + role + ", guestDate="
				+ guestDate + ", guestBy=" + guestBy + "]";
	}

}
