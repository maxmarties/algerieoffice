package com.rinitec.algerieoffice.web.form.forums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class TopicForm implements Serializable {
	private static final long serialVersionUID = 2010479942305639213L;
	
	private String id;
	
	@NotNull
	private Long userId;
	
	private Integer category;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	@NotNull(message = "{message.input.required}")
	private String language;
	
	@NotNull
	private Boolean hasQuiz;
	
	private List<String> proposals;
	
	public TopicForm() {
		this.hasQuiz = false;
		this.proposals = new ArrayList<String>();
	}
	
	public TopicForm(final Long userId, final String language) {
		this();
		this.userId = userId;
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Boolean getHasQuiz() {
		return hasQuiz;
	}
	
	public void setHasQuiz(Boolean hasQuiz) {
		this.hasQuiz = hasQuiz;
	}
	
	public List<String> getProposals() {
		return proposals;
	}
	
	public void setProposals(List<String> proposals) {
		this.proposals = proposals;
	}

	@Override
	public String toString() {
		return "TopicForm [id=" + id + ", userId=" + userId + ", category=" + category + ", title=" + title
				+ ", detail=" + detail + ", language=" + language + ", hasQuiz=" + hasQuiz + ", proposals=" + proposals + "]";
	}

}
