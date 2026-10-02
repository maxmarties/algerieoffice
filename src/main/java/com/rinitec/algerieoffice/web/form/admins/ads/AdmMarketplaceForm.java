package com.rinitec.algerieoffice.web.form.admins.ads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletter;

public class AdmMarketplaceForm implements Serializable {
	private static final long serialVersionUID = 1377672574336519407L;
	
	@NotNull
	private Integer todays;
	
	@NotNull
	private Integer offers;
	
	private List<String> users;
	private List<AnnonceNewsletter> annonces;
	private List<String> news;
	
	public AdmMarketplaceForm() {
		this.users = new ArrayList<String>();
		this.annonces = new ArrayList<AnnonceNewsletter>();
		this.news = new ArrayList<String>();
	}

	public Integer getTodays() {
		return todays;
	}

	public void setTodays(Integer todays) {
		this.todays = todays;
	}

	public Integer getOffers() {
		return offers;
	}

	public void setOffers(Integer offers) {
		this.offers = offers;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<AnnonceNewsletter> getAnnonces() {
		return annonces;
	}

	public void setAnnonces(List<AnnonceNewsletter> annonces) {
		this.annonces = annonces;
	}

	public List<String> getNews() {
		return news;
	}

	public void setNews(List<String> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "MarketplaceForm [todays=" + todays + ", offers=" + offers + ", users=" + users + ", annonces="
				+ annonces + ", news=" + news + "]";
	}

}
