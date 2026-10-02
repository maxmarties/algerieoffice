package com.rinitec.algerieoffice.web.form.admins.ads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UUIDMini;

public class AdmNewsletterForm implements Serializable {
	private static final long serialVersionUID = 4195232018787144502L;
	
	private List<String> users;
	private List<UUIDMini> articles;
	private List<String> news;
	
	public AdmNewsletterForm() {
		this.users = new ArrayList<String>();
		this.articles = new ArrayList<UUIDMini>();
		this.news = new ArrayList<String>();
	}
	
	public AdmNewsletterForm(final List<String> users, final List<UUIDMini> articles) {
		this.users = users;
		this.articles = articles;
		this.news = new ArrayList<String>();
	}
	
	public List<String> getUsers() {
		return users;
	}
	
	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	public List<UUIDMini> getArticles() {
		return articles;
	}
	
	public void setArticles(List<UUIDMini> articles) {
		this.articles = articles;
	}
	
	public List<String> getNews() {
		return news;
	}
	
	public void setNews(List<String> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "NewsletterForm [users=" + users + ", articles=" + articles + ", news=" + news + "]";
	}

}
