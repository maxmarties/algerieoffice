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
@Table(name = "employes_locations")
public class EmployeLocation implements Serializable {
	private static final long serialVersionUID = 2655193238563767001L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "target_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID employeUUID;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = false)
	private Integer location;
	
	public EmployeLocation() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEmployeUUID() {
		return employeUUID;
	}

	public void setEmployeUUID(UUID employeUUID) {
		this.employeUUID = employeUUID;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "EmployeLocation [id=" + id + ", employeUUID=" + employeUUID + ", location=" + location + "]";
	}
	
}
