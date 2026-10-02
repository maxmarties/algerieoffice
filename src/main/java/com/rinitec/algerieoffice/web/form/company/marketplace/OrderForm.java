package com.rinitec.algerieoffice.web.form.company.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class OrderForm implements Serializable {
	private static final long serialVersionUID = -7270299325280107987L;
	
	@NotNull
	private String id;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer pack;
	
	private List<Integer> orderCredit;
	private List<String> orderFormule;
	
	private MultipartFile file;
	
	public OrderForm() {
		this.orderCredit = new ArrayList<Integer>();
		this.orderFormule = new ArrayList<String>();
	}
	
	public OrderForm(final String id, final Long companyId, final boolean hasCampaign) {
		this();
		this.id = id;
		this.companyId = companyId;
		if(hasCampaign) {
			this.orderCredit = Arrays.asList(ConstraintesForm.CAMPAIGNS_CREDIT);
			this.orderFormule = Arrays.asList(ConstraintesForm.CAMPAIGNS_FORMULE);
		} else {
			this.orderCredit = Arrays.asList(ConstraintesForm.ORDERS_CREDIT);
			this.orderFormule = Arrays.asList(ConstraintesForm.ORDERS_FORMULE);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	public List<Integer> getOrderCredit() {
		return orderCredit;
	}

	public void setOrderCredit(List<Integer> orderCredit) {
		this.orderCredit = orderCredit;
	}

	public List<String> getOrderFormule() {
		return orderFormule;
	}

	public void setOrderFormule(List<String> orderFormule) {
		this.orderFormule = orderFormule;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getFormattedCredit(int index) {
		return ParseUtil.getFormattedOrder(orderCredit.get(index));
	}
	
	public String getFormattedFormule(int index) {
		return ParseUtil.getFormattedCapital(orderFormule.get(index));
	}
	
	public float getEstimattedFormule(int index) {
		return Float.valueOf(orderFormule.get(index)) / orderCredit.get(index);
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", companyId=" + companyId + ", pack=" + pack + ", orderCredit=" + orderCredit
				+ ", orderFormule=" + orderFormule + "]";
	}

}
