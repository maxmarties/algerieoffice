package com.rinitec.algerieoffice.web.form.company.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class OrderPremiumForm implements Serializable {
	private static final long serialVersionUID = 3952514241893154626L;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer pack;
	
	@ValidChose
	private Integer monthly;
	
	private List<Integer> prices;
	
	private MultipartFile file;
	
	public OrderPremiumForm() {
		this.prices = new ArrayList<Integer>();
	}
	
	public OrderPremiumForm(final Long companyId, final PremiumFormule premiumFormule) {
		this.companyId = companyId;
		if(premiumFormule != null) {
			this.prices = Arrays.asList(premiumFormule.parseValues());
		} else {
			this.prices = Arrays.asList(ConstraintesForm.PREMIUM_FORMULE);
		}
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

	public Integer getMonthly() {
		return monthly;
	}

	public void setMonthly(Integer monthly) {
		this.monthly = monthly;
	}

	public List<Integer> getPrices() {
		return prices;
	}

	public void setPrices(List<Integer> prices) {
		this.prices = prices;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getFormattedPrice(int index) {
		return ParseUtil.getFormattedOrder(prices.get(index));
	}

	@Override
	public String toString() {
		return "OrderPremiumForm [companyId=" + companyId + ", pack=" + pack + ", monthly=" + monthly + ", prices="
				+ prices + ", file=" + file + "]";
	}

}
