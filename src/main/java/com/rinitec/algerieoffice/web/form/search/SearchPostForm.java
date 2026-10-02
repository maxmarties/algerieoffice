package com.rinitec.algerieoffice.web.form.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class SearchPostForm extends SearchFilterForm {
	private static final long serialVersionUID = -3459350624719432623L;
	
	private boolean[] sectors = new boolean[ConstraintesForm.COUNT_SECTOR_ACTIITY];
	private Integer type;
	private Integer priceType;
	private int indexValue;
	private String priceBegin;
	private String priceEnd;
	private boolean[] priceMore = new boolean[2];
	private boolean digital;
	private Integer state;
	
	public SearchPostForm() {
		super(null);
	}
	
	public SearchPostForm(final Long userId, final String token, final String keyword, final Integer sector, final Integer wilaya, final int row) {
		super(userId, token, keyword, wilaya, row);
		this.digital = false;
		this.type = this.state = 1;
		if(sector != null && sector != 0) {
			this.sectors[sector - 1] = true;
		}
	}

	public boolean[] getSectors() {
		return sectors;
	}

	public void setSectors(boolean[] sectors) {
		this.sectors = sectors;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public int getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(int indexValue) {
		this.indexValue = indexValue;
	}

	public String getPriceBegin() {
		return priceBegin;
	}
	
	public void setPriceBegin(String priceBegin) {
		this.priceBegin = priceBegin;
	}
	
	public String getPriceEnd() {
		return priceEnd;
	}
	
	public void setPriceEnd(String priceEnd) {
		this.priceEnd = priceEnd;
	}

	public boolean[] getPriceMore() {
		return priceMore;
	}

	public void setPriceMore(boolean[] priceMore) {
		this.priceMore = priceMore;
	}

	public boolean isDigital() {
		return digital;
	}

	public void setDigital(boolean digital) {
		this.digital = digital;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public boolean hasPresentSectors() {
		for (final boolean sector : sectors) {
			if(sector) return true;
		}
		return false;
	}
	
	public List<Integer> parseSectors() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < sectors.length; i++) {
			if(sectors[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public boolean hasPresentValue() {
		return !StringUtils.isEmpty(priceBegin);
	}
	
	public Integer parseValueBegin() {
		return Integer.valueOf(priceBegin);
	}
	
	public Integer parseValueEnd() {
		return Integer.valueOf(priceEnd);
	}
	
	public boolean hasPresentMores() {
		for (final boolean more : priceMore) {
			if(more) return true;
		}
		return false;
	}
	
	public boolean hasPresentParrain() {
		return priceMore[0];
	}
	
	public boolean hasPresentPrecision() {
		return priceMore[1];
	}
	
	public boolean hasPresentURL() {
		return digital;
	}
	
	public boolean hasPresentDetail() {
		return priceType != null || hasPresentValue() || hasPresentMores() || hasPresentURL() || state != 1;
	}
	
	public boolean hasPresentFilter() {
		return hasPresentSectors() || hasPresentWilayas() || type != 1 || hasPresentDetail();
	}
	
	public boolean hasBeginReaden() {
		return page == 1 && !hasPresentToken() && !hasPresentKeysword() && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchPostForm [sectors=" + Arrays.toString(sectors) + ", type=" + type + ", priceType=" + priceType
				+ ", indexValue=" + indexValue + ", priceBegin=" + priceBegin + ", priceEnd=" + priceEnd
				+ ", priceMore=" + Arrays.toString(priceMore) + ", digital=" + digital + ", state=" + state + "]";
	}

}
