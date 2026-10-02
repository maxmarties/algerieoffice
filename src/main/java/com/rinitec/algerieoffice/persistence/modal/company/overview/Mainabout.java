package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "mainabouts")
public class Mainabout implements Serializable {
	private static final long serialVersionUID = 8340904862739345294L;
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasCover;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 60)
	private String function;
	
	@Column(nullable = false, length = 250)
	private String word;
	
	@Max(3)
	@Column(nullable = false)
	private Integer size;
	
	public Mainabout() {
	}
	
	public Mainabout(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasCover() {
		return hasCover;
	}

	public void setHasCover(Boolean hasCover) {
		this.hasCover = hasCover;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Mainabout [companyId=" + companyId + ", hasCover=" + hasCover + ", name=" + name + ", function="
				+ function + ", word=" + word + ", size=" + size + "]";
	}

}
