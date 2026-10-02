package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidPostal;

public class LocationForm implements Serializable {
	private static final long serialVersionUID = 9159097595865683710L;

	@NotNull
	private Long id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_ADRRESS, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String address;
	
	@ValidPostal
	@NotNull
	private String postal;
	
	@ValidChose
	private Integer wilaya;
	
	private List<Long> idents;
	private List<String> addrs;
	private List<String> postals;
	private List<Integer> wilayas;
	private List<Long> updated;
	private List<Long> trashed;
	
	@NotNull
	private Boolean hasCarte;
	
	private String urlmap;
	private String empded;
	
	private boolean hasEmpded;
	private boolean updateAddr = false;
	
	public LocationForm() {
		this.idents = new ArrayList<Long>();
		this.addrs = new ArrayList<String>();
		this.postals = new ArrayList<String>();
		this.wilayas = new ArrayList<Integer>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}
	
	public List<Long> getIdents() {
		return idents;
	}
	
	 public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getAddrs() {
		return addrs;
	}

	public void setAddrs(List<String> addrs) {
		this.addrs = addrs;
	}

	public List<String> getPostals() {
		return postals;
	}

	public void setPostals(List<String> postals) {
		this.postals = postals;
	}

	public List<Integer> getWilayas() {
		return wilayas;
	}
	
	public void setWilayas(List<Integer> wilayas) {
		this.wilayas = wilayas;
	}
	
	public List<Long> getUpdated() {
		return updated;
	}
	
	public void setUpdated(List<Long> updated) {
		this.updated = updated;
	}
	
	public List<Long> getTrashed() {
		return trashed;
	}
	
	public void setTrashed(List<Long> trashed) {
		this.trashed = trashed;
	}

	public Boolean getHasCarte() {
		return hasCarte;
	}

	public void setHasCarte(Boolean hasCarte) {
		this.hasCarte = hasCarte;
	}

	public String getUrlmap() {
		return urlmap;
	}

	public void setUrlmap(String urlmap) {
		this.urlmap = urlmap;
	}
	
	public String getEmpded() {
		return empded;
	}
	
	public void setEmpded(String empded) {
		this.empded = empded;
	}
	
	public boolean isHasEmpded() {
		return hasEmpded;
	}
	
	public void setHasEmpded(boolean hasEmpded) {
		this.hasEmpded = hasEmpded;
	}
	
	public boolean isUpdateAddr() {
		return updateAddr;
	}
	
	public void setUpdateAddr(boolean updateAddr) {
		this.updateAddr = updateAddr;
	}

	@Override
	public String toString() {
		return "LocationForm [id=" + id + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya
				+ ", idents=" + idents + ", addrs=" + addrs + ", postals=" + postals + ", wilayas=" + wilayas
				+ ", updated=" + updated + ", trashed=" + trashed + ", hasCarte=" + hasCarte + ", urlmap=" + urlmap
				+ ", empded=" + empded + ", hasEmpded=" + hasEmpded + ", updateAddr=" + updateAddr + "]";
	}
	
}
