package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "promotes_activities")
public class PromoteActivity implements Serializable {
	private static final long serialVersionUID = -5146459078467857792L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "target_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID promoteUUID;
	
	@Max(ConstraintesForm.COUNT_SECTOR_ACTIITY)
	@Column(nullable = false)
	private Integer sector;
	
	public PromoteActivity() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getPromoteUUID() {
		return promoteUUID;
	}

	public void setPromoteUUID(UUID promoteUUID) {
		this.promoteUUID = promoteUUID;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "PromoteActivity [id=" + id + ", promoteUUID=" + promoteUUID + ", sector=" + sector + "]";
	}

}
