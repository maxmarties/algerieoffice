package com.rinitec.algerieoffice.persistence.modal.inbox;

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
@Table(name = "talks")
public class Talk implements Serializable {
	private static final long serialVersionUID = 8665701038697332808L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "talk_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	//TODO 14 MORE VERSION
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = true, length = 512)
	private String link;
	
	@Column(nullable = false)
	private DateTime talkedDate;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	public Talk() {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public DateTime getTalkedDate() {
		return talkedDate;
	}

	public void setTalkedDate(DateTime talkedDate) {
		this.talkedDate = talkedDate;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", companyId=" + companyId + ", type=" + type + ", link=" + link + ", talkedDate="
				+ talkedDate + ", consulted=" + consulted + "]";
	}
	
}
