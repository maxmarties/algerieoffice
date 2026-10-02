package com.rinitec.algerieoffice.web.modal.admins.easylist;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmEasylistDocument implements Serializable {
	private static final long serialVersionUID = -1907018390880188453L;
	
	private final String id;
	private final String urlAvatar;
	private final String username;
	private final String tradename;
	private final String easyname;
	private final String esayDate;
	private final int potentiel;
	private final int type;
	
	public AdmEasylistDocument(final EasylistDocument easylistDocument, final User user, final String tradename) {
		this.id = easylistDocument.getId().toString();
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.easyname = easylistDocument.getEasyname();
		this.esayDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(easylistDocument.getEasyDate());
		this.potentiel = easylistDocument.getDocuments().size();
		this.type = ParseUtil.parseTypeDocument(easylistDocument.getType());
	}

	public String getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getTradename() {
		return tradename;
	}

	public String getEasyname() {
		return easyname;
	}

	public String getEsayDate() {
		return esayDate;
	}

	public int getPotentiel() {
		return potentiel;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AdmEasylistDocument [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", tradename="
				+ tradename + ", easyname=" + easyname + ", esayDate=" + esayDate + ", potentiel=" + potentiel
				+ ", type=" + type + "]";
	}

}
