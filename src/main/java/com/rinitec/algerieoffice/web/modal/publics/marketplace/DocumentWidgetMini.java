package com.rinitec.algerieoffice.web.modal.publics.marketplace;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DocumentWidgetMini implements Serializable {
	private static final long serialVersionUID = -7542454230253058937L;
	
	private final UUID id;
	private final String title;
	private final String identifyURL;
	private final String keysword;
	private final String description;
	private final Long companyId;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String address;
	private final String language;
	private final boolean favorite;
	
	public DocumentWidgetMini(final Post post, final Company company, final String url, final Long userId) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = post.getId();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePostURL(post.getIdentify(), url);
		this.keysword = post.getKeysword();
		this.description = post.getDescription();
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.language = company.getLang();
		this.favorite = userId != null;
	}
	
	public DocumentWidgetMini(final Annonce annonce, final Company company, final String url, final Long userId) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = annonce.getId();
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceAnnonceURL(annonce.getIdentify(), url);
		this.keysword = annonce.getKeysword();
		this.description = annonce.getDescription();
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.language = company.getLang();
		this.favorite = userId != null;
	}
	
	public DocumentWidgetMini(final Employe employe, final Company company, final String url, final Long userId) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = employe.getId();
		this.title = employe.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceEmployeURL(employe.getIdentify(), url);
		this.keysword = employe.getKeysword();
		this.description = employe.getDescription();
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.language = company.getLang();
		this.favorite = userId != null;
	}
	
	public DocumentWidgetMini(final Event event, final String description, final Company company, final String url, final Long userId) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = event.getId();
		this.title = event.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceEventURL(event.getIdentify(), url);
		this.keysword = event.getKeysword();
		this.description = description;
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.language = company.getLang();
		this.favorite = userId != null;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getKeysword() {
		return keysword;
	}

	public String getDescription() {
		return description;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}
	
	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getAddress() {
		return address;
	}

	public String getLanguage() {
		return language;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public String[] buildKeysword() {
		return keysword.split(",");
	}
	
	public String parsKey(final String keyword) {
		return keyword.replaceAll(" ", "\\+");
	}

	@Override
	public String toString() {
		return "DocumentWidgetMini [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", keysword="
				+ keysword + ", description=" + description + ", companyId=" + companyId + ", tradename=" + tradename
				+ ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar + ", address=" + address + ", language="
				+ language + ", favorite=" + favorite + "]";
	}

}
