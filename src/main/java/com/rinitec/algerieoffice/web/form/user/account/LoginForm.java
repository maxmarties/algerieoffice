package com.rinitec.algerieoffice.web.form.user.account;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidEmail;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class LoginForm implements Serializable {
	private static final long serialVersionUID = 458753750834783069L;
	
	@NotNull
	private Long id;
	
	@ValidUrl
	@NotNull
	private String pseudo;
	
	private String checkedPseudo;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String firstname;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String lastname;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	private String newpassword;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String password;
	
	private boolean hasAccepte;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public LoginForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getCheckedPseudo() {
		return checkedPseudo;
	}
	
	public void setCheckedPseudo(String checkedPseudo) {
		this.checkedPseudo = checkedPseudo;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHasAccepte() {
		return hasAccepte;
	}

	public void setHasAccepte(boolean hasAccepte) {
		this.hasAccepte = hasAccepte;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public boolean isHasFileChanged() {
		return hasFileChanged;
	}

	public void setHasFileChanged(boolean hasFileChanged) {
		this.hasFileChanged = hasFileChanged;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "LoginForm [id=" + id + ", pseudo=" + pseudo + ", checkedPseudo=" + checkedPseudo + ", firstname="
				+ firstname + ", lastname=" + lastname + ", email=" + email + ", newpassword=" + newpassword
				+ ", password=" + password + ", hasAccepte=" + hasAccepte + ", hasAvatar=" + hasAvatar
				+ ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar + "]";
	}
	
	/*public LoginForm(final User user, final boolean hasAccepte) {
		this.id = user.getId();
		this.firstname = user.getFirstName();
		this.lastname = user.getLastName();
		this.email = user.getEmail();
		this.hasAvatar = user.getHasAvatar();
		this.urlAvatar = user.getHasAvatar() ? "/media/avatar?postedId=" + user.getId() + "&type=" + AvatarType.account 
				: "/static/picts/avatars/account-min.jpg";
		this.hasAccepte = hasAccepte;
	}*/

	

}
