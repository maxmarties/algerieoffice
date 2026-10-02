package com.rinitec.algerieoffice.web.form.company.portfolio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class EventForm implements Serializable {
	private static final long serialVersionUID = -5199473828834827018L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String description;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	private String keysword;
	private String urlExtern;
	
	@NotNull
	private Boolean hasPublished;
	
	private List<String> idents;
	private List<String> eventsDate;
	private List<Integer> clocksOpen;
	private List<Integer> clocksClose;
	private List<Integer> wilayas;
	private List<String> updated;
	private List<String> trashed;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	private boolean hasNotified;
	private boolean updateCalendar = false;
	
	public EventForm() {
		this.idents = new ArrayList<String>();
		this.eventsDate = new ArrayList<String>();
		this.clocksOpen = new ArrayList<Integer>();
		this.clocksClose = new ArrayList<Integer>();
		this.wilayas = new ArrayList<Integer>();
	}
	
	public EventForm(final Long companyId) {
		this();
		this.companyId = companyId;
		this.hasAvatar = false;
		this.hasPublished = this.hasNotified = true;
		this.urlAvatar = "/static/picts/avatars/actuality-min.jpg";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	public List<String> getIdents() {
		return idents;
	}

	public void setIdents(List<String> idents) {
		this.idents = idents;
	}

	public List<String> getEventsDate() {
		return eventsDate;
	}

	public void setEventsDate(List<String> eventsDate) {
		this.eventsDate = eventsDate;
	}

	public List<Integer> getClocksOpen() {
		return clocksOpen;
	}

	public void setClocksOpen(List<Integer> clocksOpen) {
		this.clocksOpen = clocksOpen;
	}

	public List<Integer> getClocksClose() {
		return clocksClose;
	}

	public void setClocksClose(List<Integer> clocksClose) {
		this.clocksClose = clocksClose;
	}

	public List<Integer> getWilayas() {
		return wilayas;
	}

	public void setWilayas(List<Integer> wilayas) {
		this.wilayas = wilayas;
	}

	public List<String> getUpdated() {
		return updated;
	}

	public void setUpdated(List<String> updated) {
		this.updated = updated;
	}

	public List<String> getTrashed() {
		return trashed;
	}

	public void setTrashed(List<String> trashed) {
		this.trashed = trashed;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public boolean isHasFileChanged() {
		return hasFileChanged;
	}

	public void setHasFileChanged(boolean hasFileChanged) {
		this.hasFileChanged = hasFileChanged;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public boolean isHasNotified() {
		return hasNotified;
	}

	public void setHasNotified(boolean hasNotified) {
		this.hasNotified = hasNotified;
	}

	public boolean isUpdateCalendar() {
		return updateCalendar;
	}

	public void setUpdateCalendar(boolean updateCalendar) {
		this.updateCalendar = updateCalendar;
	}
	
	public String parseClock(int index) {
		final Integer open = clocksOpen.get(index);
		final Integer close = clocksClose.get(index);
		return (open == 24 ? "00:00" : (open < 10 ? "0" : "").concat(open.toString()).concat(":00")).concat(" - ")
				.concat(close == 24 ? "00:00" : (close < 10 ? "0" : "").concat(close.toString()).concat(":00"));
	}

	@Override
	public String toString() {
		return "EventForm [id=" + id + ", companyId=" + companyId + ", title=" + title + ", identify=" + identify
				+ ", description=" + description + ", detail=" + detail + ", keysword=" + keysword + ", urlExtern="
				+ urlExtern + ", hasPublished=" + hasPublished + ", idents=" + idents + ", eventsDate=" + eventsDate
				+ ", clocksOpen=" + clocksOpen + ", clocksClose=" + clocksClose + ", wilayas=" + wilayas + ", updated="
				+ updated + ", trashed=" + trashed + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged
				+ ", urlAvatar=" + urlAvatar + ", hasNotified=" + hasNotified + ", updateCalendar=" + updateCalendar+ "]";
	}

}
