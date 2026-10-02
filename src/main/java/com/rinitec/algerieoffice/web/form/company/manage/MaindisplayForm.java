package com.rinitec.algerieoffice.web.form.company.manage;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class MaindisplayForm implements Serializable {
	private static final long serialVersionUID = 241388732323530583L;
	
	@NotNull
	private Long id;
	
	@ValidChose
	private Integer pageStyle;
	
	@ValidChose
	private Integer postStyle;

	private boolean[] mainmenu = new boolean[4];
	private boolean[] society = new boolean[6];
	private boolean[] mainsidbar = new boolean[6];
	private boolean[] mainfooter = new boolean[7];
	private boolean[] widget = new boolean[10];
	
	private boolean lateral;
	
	public MaindisplayForm() {
	}
	
	public MaindisplayForm(final Long id, final Maindisplay maindisplay) {
		this.id = id;
		if(maindisplay != null) {
			this.pageStyle = Integer.valueOf(String.valueOf(maindisplay.getDisplay().charAt(0)));
			this.postStyle = Integer.valueOf(String.valueOf(maindisplay.getDisplay().charAt(1)));
			for (int i = 0; i < 4; i++) {
				this.mainmenu[i] = (maindisplay.getMainmenu().charAt(i) == '1');
			}
			for (int i = 0; i < 6; i++) {
				this.society[i] = (maindisplay.getSociety().charAt(i) == '1');
				this.mainsidbar[i] = (maindisplay.getMainsidbar().charAt(i) == '1');
			}
			for (int i = 0; i < 7; i++) {
				this.mainfooter[i] = (maindisplay.getMainfooter().charAt(i) == '1');
			}
			for (int i = 0; i < 10; i++) {
				this.widget[i] = (maindisplay.getDisplay().charAt(i + 2) == '1');
			}
			this.lateral = (maindisplay.getDisplay().charAt(12) == '1');
		} else {
			this.pageStyle = this.postStyle = 1;
			for (int i = 0; i < 4; i++) {
				this.mainmenu[i] = true;
			}
			for (int i = 0; i < 6; i++) {
				this.society[i] = false;
				this.mainsidbar[i] = true;
			}
			for (int i = 0; i < 7; i++) {
				this.mainfooter[i] = (i < 3 || i > 4);
			}
			for (int i = 0; i < 10; i++) {
				this.widget[i] = i != 5;
			}
			this.lateral = true;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPageStyle() {
		return pageStyle;
	}

	public void setPageStyle(Integer pageStyle) {
		this.pageStyle = pageStyle;
	}

	public Integer getPostStyle() {
		return postStyle;
	}

	public void setPostStyle(Integer postStyle) {
		this.postStyle = postStyle;
	}

	public boolean[] getMainmenu() {
		return mainmenu;
	}

	public void setMainmenu(boolean[] mainmenu) {
		this.mainmenu = mainmenu;
	}

	public boolean[] getSociety() {
		return society;
	}

	public void setSociety(boolean[] society) {
		this.society = society;
	}

	public boolean[] getMainsidbar() {
		return mainsidbar;
	}

	public void setMainsidbar(boolean[] mainsidbar) {
		this.mainsidbar = mainsidbar;
	}

	public boolean[] getMainfooter() {
		return mainfooter;
	}

	public void setMainfooter(boolean[] mainfooter) {
		this.mainfooter = mainfooter;
	}

	public boolean[] getWidget() {
		return widget;
	}

	public void setWidget(boolean[] widget) {
		this.widget = widget;
	}
	
	public boolean isLateral() {
		return lateral;
	}
	
	public void setLateral(boolean lateral) {
		this.lateral = lateral;
	}
	
	public String builderMainmenu() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			builder.append(mainmenu[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderSociety() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			builder.append(society[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderMainsidbar() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			builder.append(mainsidbar[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderMainfooter() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			builder.append(mainfooter[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderDisplay() {
		final StringBuilder builder = new StringBuilder();
		builder.append(String.valueOf(pageStyle));
		builder.append(String.valueOf(postStyle));
		for (int i = 0; i < 10; i++) {
			builder.append(widget[i] ? "1" : "0");
		}
		builder.append(lateral ? "1" : "0");
		for (int i = 0; i < 11; i++) {
			builder.append("1");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "MaindisplayForm [id=" + id + ", pageStyle=" + pageStyle + ", postStyle=" + postStyle + ", mainmenu="
				+ Arrays.toString(mainmenu) + ", society=" + Arrays.toString(society) + ", mainsidbar="
				+ Arrays.toString(mainsidbar) + ", mainfooter=" + Arrays.toString(mainfooter) + ", widget="
				+ Arrays.toString(widget) + ", lateral=" + lateral + "]";
	}

}
