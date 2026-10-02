package com.rinitec.algerieoffice.persistence.modal.medias;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.AvatarID;

@Entity
@Table(name = "avatars")
public class Avatar implements Serializable {
	private static final long serialVersionUID = -7230051179469558407L;

	@EmbeddedId
	private AvatarID avatarID;
	
	@Column(nullable = false, length = 30)
	private String filename;
	
	@Column(nullable = false, length = 30)
	private String contentType;
	
	public Avatar() {
	}
	
	public Avatar(AvatarID avatarID) {
		this.avatarID = avatarID;
	}

	public AvatarID getAvatarID() {
		return avatarID;
	}

	public void setAvatarID(AvatarID avatarID) {
		this.avatarID = avatarID;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "Avatar [avatarID=" + avatarID + ", filename=" + filename + ", contentType=" + contentType + "]";
	}
	
}
