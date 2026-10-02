package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicVote;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class TopicQuizVote implements Serializable {
	private static final long serialVersionUID = -7394387110363386706L;
	
	private final Integer vote;
	private final DateTime postedDate;
	private final List<String> proposals;
	private final List<Long> counts;
	private final Long sumCounts;
	
	public TopicQuizVote(final TopicVote topicVote, final List<String> proposals, final List<Long> counts) {
		if(topicVote != null) {
			this.vote = topicVote.getQuiz();
			this.postedDate = topicVote.getPostedDate();
		} else {
			this.vote = null;
			this.postedDate = null;
		}
		this.proposals = proposals;
		this.counts = counts;
		this.sumCounts = this.sumCounts();
	}
	
	private final long sumCounts() {
		long sum = 0L;
		for (final Long count : counts) {
			sum += count;
		}
		return sum;
	}
	
	public Integer getVote() {
		return vote;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public List<String> getProposals() {
		return proposals;
	}

	public List<Long> getCounts() {
		return counts;
	}

	public Long getSumCounts() {
		return sumCounts;
	}

	public int getPesrsentProposal(int index) {
		if(sumCounts == 0L) {
			return 0;
		}
		return (int) ((counts.get(index - 1) * 100) / sumCounts);
	}
	
	public String getFormattedCount(int index) {
		return ParseUtil.getFormattedTopicValue(counts.get(index - 1));
	}
	
	public String getFormattedSumCount() {
		return ParseUtil.getFormattedTopicValue(sumCounts);
	}
	
	public String countsToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < counts.size(); i++) {
			builder.append(String.valueOf(counts.get(i)));
			if(i < counts.size() - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "TopicQuizVote [voteUser=" + vote + ", postedDate=" + postedDate + ", proposals=" + proposals
				+ ", counts=" + counts + ", sumCounts=" + sumCounts + "]";
	}

}
