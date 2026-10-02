package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetectList implements Serializable {
	private static final long serialVersionUID = 3041841774924292757L;
	
	private final List<DetectAccess> lines;
	
	public DetectList() {
		this.lines = new ArrayList<DetectAccess>();
	}
	
	private final int indexLine(final DetectAccess detectLine) {
		for (int i = 0; i < lines.size(); i++) {
			final DetectAccess line = lines.get(i);
			if(detectLine.getCountAccess() > line.getCountAccess()) return i; 
		}
		return lines.size();
	}
	
	public List<DetectAccess> getLines() {
		return lines;
	}
	
	public void addLine(final DetectAccess line) {
		lines.add(indexLine(line), line);
	}

	@Override
	public String toString() {
		return "DetectList [lines=" + lines + "]";
	}

}
