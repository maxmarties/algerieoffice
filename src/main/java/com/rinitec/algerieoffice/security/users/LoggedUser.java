package com.rinitec.algerieoffice.security.users;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.stereotype.Component;

@Component
public class LoggedUser implements HttpSessionBindingListener {

	private String email;
    private ActiveUserStore activeUserStore;
    
    public LoggedUser() {
	}
    
    public LoggedUser(String email, ActiveUserStore activeUserStore) {
		this.email = email;
		this.activeUserStore = activeUserStore;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ActiveUserStore getActiveUserStore() {
		return activeUserStore;
	}

	public void setActiveUserStore(ActiveUserStore activeUserStore) {
		this.activeUserStore = activeUserStore;
	}
    
	@Override
    public void valueBound(HttpSessionBindingEvent event) {
    	final List<String> users = activeUserStore.getUsers();
    	final LoggedUser loggedUser = (LoggedUser) event.getValue();
    	if (loggedUser != null && !users.contains(loggedUser.getEmail())) {
    		users.add(loggedUser.getEmail());
    	}
    }
    
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
    	final List<String> users = activeUserStore.getUsers();
    	final LoggedUser loggedUser = (LoggedUser) event.getValue();
    	if (loggedUser != null && users.contains(loggedUser.getEmail())) {
    		users.remove(loggedUser.getEmail());
    	}
    }

	@Override
	public String toString() {
		return "LoggedUser [email=" + email + ", activeUserStore=" + activeUserStore + "]";
	}
	
}
