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

@Entity
@Table(name = "topics_quiz")
public class TopicQuiz implements Serializable {
	private static final long serialVersionUID = -990812671705797343L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "quiz_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID topicId;
	
	@Max(5)
	@Column(nullable = false)
	private Integer quiz;
	
	@Column(nullable = false, length = 128)
	private String proposal;
	
	public TopicQuiz() {
	}
	
	public TopicQuiz(final UUID topicId) {
		this.topicId = topicId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	@Override
	public String toString() {
		return "TopicQuiz [id=" + id + ", topicId=" + topicId + ", quiz=" + quiz + ", proposal=" + proposal + "]";
	}

}
