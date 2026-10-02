package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetAgent implements Serializable {
	private static final long serialVersionUID = 656443136148445348L;
	
	private final String urlAvatar;
	private final String username;
	private final String function;
	private final String email;
	private final String phone;
	private final Long userId;
	private final String[] socialURL = new String[3];
	
	public ExplorerWidgetAgent(final Agent agent, final boolean isIcon) {
		this.urlAvatar = agent.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + agent.getId() + "&type=" + AvatarType.agent + (isIcon ? "&width=48&height=48" : "") 
				: "/static/picts/avatars/agent-".concat(agent.getSexe() ? "male" : "female").concat(isIcon ? "_mini" : "").concat("-min.jpg");
		this.username = agent.getFirstName().concat(" ").concat(agent.getLastName());
		this.function = agent.getFunction();
		this.email = agent.getEmail();
		this.phone = agent.getPhone();
		this.userId = agent.getUserId();
		for (int i = 0; i < 3; i++) {
			this.socialURL[i] = agent.getSocial(i);
		}
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getFunction() {
		return function;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Long getUserId() {
		return userId;
	}

	public String[] getSocialURL() {
		return socialURL;
	}
	
	public boolean hasPresentSocial() {
		for (int i = 0; i < 3; i++) {
			if(!StringUtils.isEmpty(socialURL[i])) return true;
		}
		return false;
	}
	
	public String getFormatedPhone() {
		return ParseUtil.getFormattedPhone(phone);
	}

	@Override
	public String toString() {
		return "ExplorerWidgetAgent [urlAvatar=" + urlAvatar + ", username=" + username + ", function=" + function
				+ ", email=" + email + ", phone=" + phone + ", userId=" + userId + ", socialURL="
				+ Arrays.toString(socialURL) + "]";
	}

}
