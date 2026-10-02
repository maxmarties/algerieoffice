package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "appearances")
public class Appearance implements Serializable {
	private static final long serialVersionUID = 2850451455493374387L;
	
	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Max(17)
	@Column(nullable = false)
	private Integer theme;
	
	@Column(nullable = true, length = 22)
	private String primaryColor;
	
	@Column(nullable = true, length = 22)
	private String segondColor;
	
	@Column(nullable = true, length = 22)
	private String treenColor;
	
	@Column(nullable = true, length = 22)
	private String menuBack;
	
	@Column(nullable = true, length = 22)
	private String menuColor;
	
	@Column(nullable = true, length = 22)
	private String menuHover;
	
	@Column(nullable = true, length = 22)
	private String popupBack;
	
	@Column(nullable = true, length = 22)
	private String popupColor;
	
	@Column(nullable = true, length = 22)
	private String popupHover;
	
	@Column(nullable = true, length = 22)
	private String sharedBack;
	
	@Column(nullable = true, length = 22)
	private String sharedColor;
	
	@Column(nullable = true, length = 22)
	private String sharedHover;
	
	@Column(nullable = true, length = 22)
	private String sharedFocus;
	
	@Column(nullable = true, length = 22)
	private String titleColor;
	
	@Column(nullable = true, length = 22)
	private String titleAfter;
	
	@Column(nullable = true, length = 22)
	private String titleProduct;
	
	@Column(nullable = true, length = 22)
	private String titleHover;
	
	@Column(nullable = true, length = 22)
	private String titleEditor;
	
	@Column(nullable = true, length = 22)
	private String pageColor;
	
	@Column(nullable = true, length = 22)
	private String textColor;
	
	@Column(nullable = true, length = 22)
	private String buttonColor;
	
	@Column(nullable = true, length = 22)
	private String footerColor;
	
	@Column(nullable = true, length = 22)
	private String primaryTop;
	
	@Column(nullable = true, length = 22)
	private String primaryBottom;
	
	@Column(nullable = true, length = 22)
	private String segondTop;
	
	@Column(nullable = true, length = 22)
	private String segondBottom;
	
	public Appearance() {
	}
	
	public Appearance(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getTheme() {
		return theme;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSegondColor() {
		return segondColor;
	}

	public void setSegondColor(String segondColor) {
		this.segondColor = segondColor;
	}

	public String getTreenColor() {
		return treenColor;
	}

	public void setTreenColor(String treenColor) {
		this.treenColor = treenColor;
	}

	public String getMenuBack() {
		return menuBack;
	}

	public void setMenuBack(String menuBack) {
		this.menuBack = menuBack;
	}

	public String getMenuColor() {
		return menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public String getMenuHover() {
		return menuHover;
	}

	public void setMenuHover(String menuHover) {
		this.menuHover = menuHover;
	}

	public String getPopupBack() {
		return popupBack;
	}

	public void setPopupBack(String popupBack) {
		this.popupBack = popupBack;
	}

	public String getPopupColor() {
		return popupColor;
	}

	public void setPopupColor(String popupColor) {
		this.popupColor = popupColor;
	}

	public String getPopupHover() {
		return popupHover;
	}

	public void setPopupHover(String popupHover) {
		this.popupHover = popupHover;
	}

	public String getSharedBack() {
		return sharedBack;
	}

	public void setSharedBack(String sharedBack) {
		this.sharedBack = sharedBack;
	}

	public String getSharedColor() {
		return sharedColor;
	}

	public void setSharedColor(String sharedColor) {
		this.sharedColor = sharedColor;
	}

	public String getSharedHover() {
		return sharedHover;
	}

	public void setSharedHover(String sharedHover) {
		this.sharedHover = sharedHover;
	}

	public String getSharedFocus() {
		return sharedFocus;
	}

	public void setSharedFocus(String sharedFocus) {
		this.sharedFocus = sharedFocus;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public void setTitleAfter(String titleAfter) {
		this.titleAfter = titleAfter;
	}

	public String getTitleProduct() {
		return titleProduct;
	}

	public void setTitleProduct(String titleProduct) {
		this.titleProduct = titleProduct;
	}

	public String getTitleHover() {
		return titleHover;
	}

	public void setTitleHover(String titleHover) {
		this.titleHover = titleHover;
	}

	public String getTitleEditor() {
		return titleEditor;
	}

	public void setTitleEditor(String titleEditor) {
		this.titleEditor = titleEditor;
	}

	public String getPageColor() {
		return pageColor;
	}

	public void setPageColor(String pageColor) {
		this.pageColor = pageColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getFooterColor() {
		return footerColor;
	}

	public void setFooterColor(String footerColor) {
		this.footerColor = footerColor;
	}

	public String getPrimaryTop() {
		return primaryTop;
	}

	public void setPrimaryTop(String primaryTop) {
		this.primaryTop = primaryTop;
	}

	public String getPrimaryBottom() {
		return primaryBottom;
	}

	public void setPrimaryBottom(String primaryBottom) {
		this.primaryBottom = primaryBottom;
	}

	public String getSegondTop() {
		return segondTop;
	}

	public void setSegondTop(String segondTop) {
		this.segondTop = segondTop;
	}

	public String getSegondBottom() {
		return segondBottom;
	}

	public void setSegondBottom(String segondBottom) {
		this.segondBottom = segondBottom;
	}

	@Override
	public String toString() {
		return "Appearance [companyId=" + companyId + ", theme=" + theme + ", primaryColor=" + primaryColor
				+ ", segondColor=" + segondColor + ", treenColor=" + treenColor + ", menuBack=" + menuBack
				+ ", menuColor=" + menuColor + ", menuHover=" + menuHover + ", popupBack=" + popupBack + ", popupColor="
				+ popupColor + ", popupHover=" + popupHover + ", sharedBack=" + sharedBack + ", sharedColor="
				+ sharedColor + ", sharedHover=" + sharedHover + ", sharedFocus=" + sharedFocus + ", titleColor="
				+ titleColor + ", titleAfter=" + titleAfter + ", titleProduct=" + titleProduct + ", titleHover="
				+ titleHover + ", titleEditor=" + titleEditor + ", pageColor=" + pageColor + ", textColor=" + textColor
				+ ", buttonColor=" + buttonColor + ", footerColor=" + footerColor + ", primaryTop=" + primaryTop
				+ ", primaryBottom=" + primaryBottom + ", segondTop=" + segondTop + ", segondBottom=" + segondBottom + "]";
	}

}
