package com.rinitec.algerieoffice.web.form.company.newsletter;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterSocial;

public class NewsletterForm implements Serializable {
	private static final long serialVersionUID = -1820902651037024246L;
	
	@NotNull
	private String tradename;
	
	@NotNull
	private String companymail;
	
	@NotNull
	private String subject;
	
	private String title;
	private String detail;
	
	@NotNull
	private String paneColor;
	
	@NotNull
	private String textColor;
	
	private String footer;
	private String target;
	private Integer label;
	
	private String description;
	
	private File logo;
	private File cover;
	
	private List<NewsletterItem> items;
	private NewsletterSocial social;
	
	private List<String> emails;
	
	private String sender;
	
	public NewsletterForm() {
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getCompanymail() {
		return companymail;
	}

	public void setCompanymail(String companymail) {
		this.companymail = companymail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPaneColor() {
		return paneColor;
	}

	public void setPaneColor(String paneColor) {
		this.paneColor = paneColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public File getCover() {
		return cover;
	}

	public void setCover(File cover) {
		this.cover = cover;
	}

	public List<NewsletterItem> getItems() {
		return items;
	}

	public void setItems(List<NewsletterItem> items) {
		this.items = items;
	}

	public NewsletterSocial getSocial() {
		return social;
	}

	public void setSocial(NewsletterSocial social) {
		this.social = social;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "NewsletterForm [tradename=" + tradename + ", companymail=" + companymail + ", subject=" + subject
				+ ", title=" + title + ", detail=" + detail + ", paneColor=" + paneColor + ", textColor=" + textColor
				+ ", footer=" + footer + ", target=" + target + ", label=" + label + ", description=" + description
				+ ", items=" + items + ", social=" + social + ", emails=" + emails + ", sender=" + sender + "]";
	}

}
