package com.rinitec.algerieoffice.persistence.modal.admins.premium;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "premium_formule")
public class PremiumFormule implements Serializable {
	private static final long serialVersionUID = 4548193618284775567L;
	
	@Id
	private Integer id;
	
	@Column(nullable = false)
	private Integer start;
	
	@Column(nullable = false)
	private Integer medium;
	
	@Column(nullable = false)
	private Integer pro;
	
	@Column(nullable = false)
	private Integer expert;
	
	private boolean begginer;
	private boolean promoted;
	
	@Column(nullable = true, length = 1024)
	private String socialFrame;
	
	public PremiumFormule() {
		this.begginer = this.promoted = false;
	}
	
	public PremiumFormule(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getMedium() {
		return medium;
	}

	public void setMedium(Integer medium) {
		this.medium = medium;
	}

	public Integer getPro() {
		return pro;
	}

	public void setPro(Integer pro) {
		this.pro = pro;
	}

	public Integer getExpert() {
		return expert;
	}

	public void setExpert(Integer expert) {
		this.expert = expert;
	}
	
	public boolean isBegginer() {
		return begginer;
	}
	
	public void setBegginer(boolean begginer) {
		this.begginer = begginer;
	}
	
	public boolean isPromoted() {
		return promoted;
	}
	
	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}
	
	public String getSocialFrame() {
		return socialFrame;
	}
	
	public void setSocialFrame(String socialFrame) {
		this.socialFrame = socialFrame;
	}
	
	public Integer[] parseValues() {
		final Integer[] values = {start, medium, pro, expert};
		return values;
	}

	@Override
	public String toString() {
		return "PremiumFormule [id=" + id + ", start=" + start + ", medium=" + medium + ", pro=" + pro + ", expert="
				+ expert + ", begginer=" + begginer + ", promoted=" + promoted + ", socialFrame=" + socialFrame + "]";
	}

}
