package com.rinitec.algerieoffice.web.modal.company.team;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AgentLine implements Serializable {
	private static final long serialVersionUID = 119671868206458569L;
	
	private final Long id;
	private final String username;
	private final String function;
	private final String email;
	private final String phone;
	private final String user;
	private final String urlAvatar;
	private final boolean hasPingled;
	
	public AgentLine(final Agent agent, final String username) {
		this.id = agent.getId();
		this.username = agent.getFirstName().concat(" ").concat(agent.getLastName());
		this.function = agent.getFunction();
		this.email = agent.getEmail();
		this.phone = ParseUtil.getFormattedPhone(agent.getPhone());
		this.user = !StringUtils.isEmpty(username) ? username : "-";
		this.urlAvatar = agent.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + agent.getId() + "&type=" + AvatarType.agent + "&width=32&height=32" 
				: "/static/picts/avatars/".concat(agent.getSexe() ? "agent-male_mini-min.jpg" : "agent-female_mini-min.jpg");
		this.hasPingled = agent.getHasPingled();
	}

	public Long getId() {
		return id;
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

	public String getUser() {
		return user;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public boolean isHasPingled() {
		return hasPingled;
	}

	@Override
	public String toString() {
		return "AgentLine [id=" + id + ", username=" + username + ", function=" + function + ", email=" + email
				+ ", phone=" + phone + ", user=" + user + ", urlAvatar=" + urlAvatar + ", hasPingled=" + hasPingled + "]";
	}
	
}
