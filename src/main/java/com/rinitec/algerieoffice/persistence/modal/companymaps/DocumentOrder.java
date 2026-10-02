package com.rinitec.algerieoffice.persistence.modal.companymaps;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.OrderType;

@Entity
@Table(name = "documents_order")
public class DocumentOrder implements Serializable {
	private static final long serialVersionUID = 6675253060281330996L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID documentUUID;
	
	@Column(nullable = false)
	private Long userId;
	
	@Max(4)
	@Column(nullable = false)
	private Integer pack;
	
	@Max(4)
	@Column(nullable = true)
	private Integer monthly;
	
	@Column(nullable = false)
	private DateTime orderDate;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID fileUUID;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
	private OrderType type;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	@Column(nullable = false)
	private Boolean validated;
	
	@Column(nullable = true)
	private Long validateById;
	
	public DocumentOrder() {
		this.consulted = this.validated = false;
	}
	
	public DocumentOrder(final OrderType type) {
		this();
		this.type = type;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(UUID documentUUID) {
		this.documentUUID = documentUUID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}
	
	public Integer getMonthly() {
		return monthly;
	}
	
	public void setMonthly(Integer monthly) {
		this.monthly = monthly;
	}

	public DateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(DateTime orderDate) {
		this.orderDate = orderDate;
	}

	public UUID getFileUUID() {
		return fileUUID;
	}

	public void setFileUUID(UUID fileUUID) {
		this.fileUUID = fileUUID;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Long getValidateById() {
		return validateById;
	}

	public void setValidateById(Long validateById) {
		this.validateById = validateById;
	}

	@Override
	public String toString() {
		return "DocumentOrder [id=" + id + ", documentUUID=" + documentUUID + ", userId=" + userId + ", pack=" + pack
				+ ", monthly=" + monthly + ", orderDate=" + orderDate + ", fileUUID=" + fileUUID + ", type=" + type
				+ ", consulted=" + consulted + ", validated=" + validated + ", validateById=" + validateById + "]";
	}
	
}
