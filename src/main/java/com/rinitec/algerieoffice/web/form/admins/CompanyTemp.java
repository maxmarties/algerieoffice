package com.rinitec.algerieoffice.web.form.admins;

import java.io.Serializable;

public class CompanyTemp implements Serializable {
	private static final long serialVersionUID = 7004287532073664884L;
	
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String tradename;
	private String activity;
	private String description;
	private String lang;
	private String address;
	private String postal;
	private Integer wilaya;
	private String phone;
	private String url;
	private String date;
	private Integer capital;
	private Integer type;
	
	public CompanyTemp() {
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCapital() {
		return capital;
	}

	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CompanyTemp [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", tradename=" + tradename + ", activity=" + activity + ", description=" + description
				+ ", lang=" + lang + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya + ", phone="
				+ phone + ", url=" + url + ", date=" + date + ", capital=" + capital + ", type=" + type + "]";
	}

}
