package com.rinitec.algerieoffice.web.modal.premium;

import java.io.Serializable;

public class PremiumPost implements Serializable {
	private static final long serialVersionUID = 2638544463138888L;

	final int maxKeywords;
	final boolean hasConsumer;
	
	public PremiumPost(final int maxKeywords, final boolean hasConsumer) {
		this.maxKeywords = maxKeywords;
		this.hasConsumer = hasConsumer;
	}
	
	public int getMaxKeywords() {
		return maxKeywords;
	}
	
	public boolean isHasConsumer() {
		return hasConsumer;
	}

	@Override
	public String toString() {
		return "PremiumPost [maxKeywords=" + maxKeywords + ", hasConsumer=" + hasConsumer + "]";
	}
	
}
