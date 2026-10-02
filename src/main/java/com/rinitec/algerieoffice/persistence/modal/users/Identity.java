package com.rinitec.algerieoffice.persistence.modal.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.IdentityID;

@Entity
@Table(name = "identities")
public class Identity implements Serializable {
	private static final long serialVersionUID = 6459434910298714239L;

	@EmbeddedId
	private IdentityID identityID;
	
	@Column(nullable = false, length = 100)
    private String usermail;
	
	@Column(nullable = false)
    private String displayname;
	
	@Column(nullable = true)
	private String imageurl;
	
	public Identity() {
	}

	public IdentityID getIdentityID() {
		return identityID;
	}
	
	public void setIdentityID(IdentityID identityID) {
		this.identityID = identityID;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	@Override
	public String toString() {
		return "Identity [IdentityID=" + identityID + ", usermail=" + usermail + ", displayname=" + displayname + ", imageurl=" + imageurl + "]";
	}
	
}
