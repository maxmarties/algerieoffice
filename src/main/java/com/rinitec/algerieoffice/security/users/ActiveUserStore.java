package com.rinitec.algerieoffice.security.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserStore {

	public List<String> users;
	
	public ActiveUserStore() {
		users = new ArrayList<>();
	}
	
	public List<String> getUsers() {
		return users;
	}
	
	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	public boolean hasLogged(final String email) {
		return users.contains(email);
	}
	
	public void delete(final String email) {
		if(users.contains(email)) {
			users.remove(email);
		}
	}
	
	public int countLogged() {
		return users.size();
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ActiveUserStore [users=");
		if(!users.isEmpty()) {
			builder.append("(");
			for (String user : users) {
				builder.append(user + ",");
			}
			builder.append(")");
		}
		else {
			builder.append("empty");
		}
		builder.append("]");
		return builder.toString();
	}
	
}
