package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCampaignOrderLine implements Serializable {
	private static final long serialVersionUID = 4896719996853088176L;
	
	private final String id;
	private final String company;
	private final int pack;
	private final String amount;
	private final String orderDate;
	private final String fileUrl;
	private final boolean consulted;
	private final boolean validated;
	private final String validateBy;
	
	public AdmCampaignOrderLine(final DocumentOrder documentOrder, final String company, final String validateBy) {
		this.id = documentOrder.getId().toString();
		this.company = company;
		this.pack = ConstraintesForm.CAMPAIGNS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ParseUtil.getFormattedCapital(ConstraintesForm.CAMPAIGNS_FORMULE[documentOrder.getPack() - 1]);
		this.orderDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(documentOrder.getOrderDate());
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
		this.consulted = documentOrder.getConsulted();
		this.validated = documentOrder.getValidated();
		this.validateBy = !StringUtils.isEmpty(validateBy) ? validateBy : "-";
	}

	public String getId() {
		return id;
	}

	public String getCompany() {
		return company;
	}

	public int getPack() {
		return pack;
	}

	public String getAmount() {
		return amount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public boolean isValidated() {
		return validated;
	}

	public String getValidateBy() {
		return validateBy;
	}

	@Override
	public String toString() {
		return "AdmCampaignOrderLine [id=" + id + ", company=" + company + ", pack=" + pack + ", amount=" + amount
				+ ", orderDate=" + orderDate + ", fileUrl=" + fileUrl + ", consulted=" + consulted + ", validated="
				+ validated + ", validateBy=" + validateBy + "]";
	}

}
