package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmPromoteOrderLine implements Serializable {
	private static final long serialVersionUID = -1174635060715474143L;
	
	private final String id;
	private final String promoteId;
	private final String title;
	private final int pack;
	private final String amount;
	private final String orderDate;
	private final String fileUrl;
	private final boolean consulted;
	private final boolean validated;
	private final String validateBy;
	
	public AdmPromoteOrderLine(final DocumentOrder documentOrder, final String title, final String validateBy) {
		this.id = documentOrder.getId().toString();
		this.promoteId = documentOrder.getDocumentUUID().toString();
		this.title = title;
		this.pack = ConstraintesForm.ORDERS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ParseUtil.getFormattedCapital(ConstraintesForm.ORDERS_FORMULE[documentOrder.getPack() - 1]);
		this.orderDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(documentOrder.getOrderDate());
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
		this.consulted = documentOrder.getConsulted();
		this.validated = documentOrder.getValidated();
		this.validateBy = !StringUtils.isEmpty(validateBy) ? validateBy : "-";
	}

	public String getId() {
		return id;
	}

	public String getPromoteId() {
		return promoteId;
	}

	public String getTitle() {
		return title;
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
		return "AdmPromoteOrderLine [id=" + id + ", promoteId=" + promoteId + ", title=" + title + ", pack=" + pack
				+ ", amount=" + amount + ", orderDate=" + orderDate + ", fileUrl=" + fileUrl + ", consulted="
				+ consulted + ", validated=" + validated + ", validateBy=" + validateBy + "]";
	}

}
