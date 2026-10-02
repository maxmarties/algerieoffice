package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

public class TopicQuizResponse implements Serializable {
	private static final long serialVersionUID = -7060053807090140391L;
	
	private Long userId;
	private String topicId;
	private Integer quiz;
	
	public TopicQuizResponse() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public Integer getQuiz() {
		return quiz;
	}

	public void setQuiz(Integer quiz) {
		this.quiz = quiz;
	}

	@Override
	public String toString() {
		return "TopicQuizResponse [userId=" + userId + ", topicId=" + topicId + ", quiz=" + quiz + "]";
	}

}
