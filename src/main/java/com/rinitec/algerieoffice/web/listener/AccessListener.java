package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.analytic.IAccessService;
import com.rinitec.algerieoffice.services.company.ICompanyService;
import com.rinitec.algerieoffice.web.listener.events.OnAccessBlogEvent;
import com.rinitec.algerieoffice.web.listener.events.OnAccessCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnAccessDocumentEvent;

@Component
public class AccessListener {

	private IAccessService accessService;
	private ICompanyService companyService;
	
	@Autowired
	public AccessListener(IAccessService accessService, ICompanyService companyService) {
		this.accessService = accessService;
		this.companyService = companyService;
	}
	
	@Async
	@EventListener
	public void pushAccessCompany(final OnAccessCompanyEvent event) {
		accessService.addAccessCompany(event);
		companyService.incrementVisits(event.getCompanyId());
	}
	
	@Async
	@EventListener
	public void pushAccessDocument(final OnAccessDocumentEvent event) {
		if(event.isHasClick()) {
			accessService.incrementClickDocument(event.getDocumentId(), event.getType());
		} else {
			accessService.incrementWorkDocument(event.getDocumentId(), event.getType());
		}
	}
	
	@Async
	@EventListener
	public void pushAccessBlog(final OnAccessBlogEvent event) {
		if(event.isViewed()) {
			accessService.incrementViewBlog(event.getBlogId());
		} else {
			accessService.incrementMarketBlog(event.getBlogId());
		}
	}
	
}
