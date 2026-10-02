package com.rinitec.algerieoffice.web.form.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

public class SearchEmployeForm extends SearchFilterForm {
	private static final long serialVersionUID = 5862180939257933711L;
	
	private boolean[] domaines = new boolean[11];
	private String dateBegin;
	private String dateEnd;
	private boolean[] types = new boolean[4];
	private Integer discover;
	private boolean digital;
	private Integer state;
	
	public SearchEmployeForm() {
		super(null);
	}
	
	public SearchEmployeForm(final Long userId, final String token, final String keyword, final Integer domaine, final Integer wilaya, final int row) {
		super(userId, token, keyword, wilaya, row);
		this.digital = false;
		this.state = 1;
		if(domaine != null && domaine != 0) {
			this.domaines[domaine - 1] = true;
		}
	}

	public boolean[] getDomaines() {
		return domaines;
	}

	public void setDomaines(boolean[] domaines) {
		this.domaines = domaines;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public boolean[] getTypes() {
		return types;
	}

	public void setTypes(boolean[] types) {
		this.types = types;
	}

	public Integer getDiscover() {
		return discover;
	}

	public void setDiscover(Integer discover) {
		this.discover = discover;
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
	
	public boolean hasPresentDomaines() {
		for (final boolean domaine : domaines) {
			if(domaine) return true;
		}
		return false;
	}
	
	public List<Integer> parseDomaines() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < domaines.length; i++) {
			if(domaines[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public DateTime parseDateBegin() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateBegin);
	}
	
	public DateTime parseDateEnd() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateEnd);
	}
	
	public boolean hasPresentTypes() {
		for (final boolean type : types) {
			if(type) return true;
		}
		return false;
	}
	
	public List<Integer> parseTypes() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < types.length; i++) {
			if(types[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public boolean hasPresentURL() {
		return digital;
	}
	
	public boolean hasPresentDetail() {
		return hasPresentDomaines() || hasPresentURL() || discover != null;
	}
	
	public boolean hasPresentFilter() {
		return hasPresentDetail() || hasPresentWilayas() || !StringUtils.isEmpty(dateBegin) || hasPresentTypes() || state != 1;
	}
	
	public boolean hasBeginReaden() {
		return page == 1 && !hasPresentToken() && !hasPresentKeysword() && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchEmployeForm [domaines=" + Arrays.toString(domaines) + ", dateBegin=" + dateBegin + ", dateEnd="
				+ dateEnd + ", types=" + Arrays.toString(types) + ", discover=" + discover + ", digital=" + digital
				+ ", state=" + state + "]";
	}

}
