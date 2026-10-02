package com.rinitec.algerieoffice.web.modal.admins.data;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class PostalLine implements Serializable {
	private static final long serialVersionUID = 7060205307175850487L;
	
	private final String id;
	private final String company;
	private final String amount;
	private final String orderDate;
	private final String serial;
	
	public PostalLine(final OrderPostal orderPostal, final String tradename) {
		this.id = orderPostal.getId().toString();
		this.company = tradename;
		this.amount = ParseUtil.getFormattedCapital(orderPostal.getAmount());
		this.orderDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(orderPostal.getOrderDate());
		this.serial = !StringUtils.isEmpty(orderPostal.getSerial()) ? orderPostal.getSerial() : "-";
	}

	public String getId() {
		return id;
	}

	public String getCompany() {
		return company;
	}

	public String getAmount() {
		return amount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getSerial() {
		return serial;
	}

	@Override
	public String toString() {
		return "PostalLine [id=" + id + ", company=" + company + ", amount=" + amount + ", orderDate=" + orderDate
				+ ", serial=" + serial + "]";
	}

}
