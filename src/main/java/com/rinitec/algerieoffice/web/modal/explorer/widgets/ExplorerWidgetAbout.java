package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainabout;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetAbout implements Serializable {
	private static final long serialVersionUID = 8355519951980132255L;
	
	private final String urlCover;
	private final String name;
	private final String function;
	private final String word;
	private final Integer size;
	
	public ExplorerWidgetAbout(final Mainabout mainabout) {
		if(mainabout != null) {
			this.urlCover = mainabout.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + mainabout.getCompanyId() + "&type=" + AvatarType.about 
					: "/static/picts/aobns/about-min.jpg";
			this.name = mainabout.getName();
			this.function = mainabout.getFunction();
			this.word = mainabout.getWord();
			this.size = mainabout.getSize();
		} else {
			this.urlCover = this.name = this.function = this.word = null;
			this.size = null;
		}
	}

	public String getUrlCover() {
		return urlCover;
	}

	public String getName() {
		return name;
	}

	public String getFunction() {
		return function;
	}

	public String getWord() {
		return word;
	}

	public Integer getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetAbout [urlCover=" + urlCover + ", name=" + name + ", function=" + function + ", word=" + word + ", size=" + size + "]";
	}

}
