package com.rinitec.algerieoffice.persistence.modal.users.easylist;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.joda.time.DateTime;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

@Entity
@Table(name = "easylist_companies")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class EasylistCompany implements Serializable {
	private static final long serialVersionUID = -9203274992499598411L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "easylist_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 60)
	private String easyname;
	
    @Column(columnDefinition = "bigint[]", nullable = false)
    @Type(type = "list-array")
    private List<Long> companies;
    
    @Column(nullable = false)
	private DateTime easyDate;
    
    public EasylistCompany() {
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

	public String getEasyname() {
		return easyname;
	}

	public void setEasyname(String easyname) {
		this.easyname = easyname;
	}

	public List<Long> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Long> companies) {
		this.companies = companies;
	}

	public DateTime getEasyDate() {
		return easyDate;
	}

	public void setEasyDate(DateTime easyDate) {
		this.easyDate = easyDate;
	}

	@Override
	public String toString() {
		return "EasylistCompany [id=" + id + ", userId=" + userId + ", easyname=" + easyname + ", companies="
				+ companies + ", easyDate=" + easyDate + "]";
	}

}
