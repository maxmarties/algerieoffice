package com.rinitec.algerieoffice.persistence.modal.forums;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "topics_vote")
public class TopicVote implements Serializable {
	private static final long serialVersionUID = 9221285428572794715L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "vote_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID topicId;
	
	@Max(5)
	@Column(nullable = false)
	private Integer quiz;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public TopicVote() {
	}
	
	public TopicVote(final Long userId, final UUID topicId) {
		this.userId = userId;
		this.topicId = topicId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UUID getTopicId() {
		return topicId;
	}

	public void setTopicId(UUID topicId) {
		this.topicId = topicId;
	}

	public Integer getQuiz() {
		return quiz;
	}

	public void setQuiz(Integer quiz) {
		this.quiz = quiz;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public String toString() {
		return "TopicVote [id=" + id + ", userId=" + userId + ", topicId=" + topicId + ", quiz=" + quiz
				+ ", postedDate=" + postedDate + "]";
	}
	
}
