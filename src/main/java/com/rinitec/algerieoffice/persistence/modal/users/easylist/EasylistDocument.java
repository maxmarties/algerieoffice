package com.rinitec.algerieoffice.persistence.modal.users.easylist;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;

@Entity
@Table(name = "easylist_documents")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class EasylistDocument implements Serializable {
	private static final long serialVersionUID = -6769149359368444393L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "easylist_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 60)
	private String easyname;
	
	@Column(columnDefinition = "uuid[]", nullable = false)
    @Type(type = "list-array")
    private List<UUID> documents;
	
	@Column(nullable = false)
	private DateTime easyDate;
	
	@Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
	private DocumentType type;
	
	public EasylistDocument() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEasyname() {
		return easyname;
	}

	public void setEasyname(String easyname) {
		this.easyname = easyname;
	}

	public List<UUID> getDocuments() {
		return documents;
	}

	public void setDocuments(List<UUID> documents) {
		this.documents = documents;
	}

	public DateTime getEasyDate() {
		return easyDate;
	}

	public void setEasyDate(DateTime easyDate) {
		this.easyDate = easyDate;
	}

	public DocumentType getType() {
		return type;
	}
	
	public void setType(DocumentType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EasylistDocument [id=" + id + ", userId=" + userId + ", easyname=" + easyname + ", documents="
				+ documents + ", easyDate=" + easyDate + ", type=" + type + "]";
	}

}
