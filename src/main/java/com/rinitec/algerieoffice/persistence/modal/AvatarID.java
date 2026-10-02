package com.rinitec.algerieoffice.persistence.modal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.rinitec.algerieoffice.enums.AvatarType;

@Embeddable
public class AvatarID implements Serializable {
	private static final long serialVersionUID = -6181659737737232645L;
	
	@Column(nullable = false, updatable = false)
	private Long postedId;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AvatarType avatarType;
	
	public AvatarID() {
	}
	
	public AvatarID(Long postedId, AvatarType avatarType) {
		this.postedId = postedId;
		this.avatarType = avatarType;
	}
	
	public Long getPostedId() {
		return postedId;
	}
	
	public void setPostedId(Long postedId) {
		this.postedId = postedId;
	}

	public AvatarType getAvatarType() {
		return avatarType;
	}

	public void setAvatarType(AvatarType avatarType) {
		this.avatarType = avatarType;
	}

	@Override
	public String toString() {
		return "AvatarID [postedId=" + postedId + ", avatarType=" + avatarType + "]";
	}

}
