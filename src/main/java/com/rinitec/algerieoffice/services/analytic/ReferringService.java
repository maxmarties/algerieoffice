package com.rinitec.algerieoffice.services.analytic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.analytic.FollowCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.OutlookRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.analytic.FollowCompany;
import com.rinitec.algerieoffice.persistence.modal.analytic.Outlook;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompletedEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringPostEvent;

@Service
public class ReferringService implements IReferringService {

	private OutlookRepository outlookRepository;
	private FollowCompanyRepository followCompanyRepository;
	private PostSearchRepository postSearchRepository;
	private CompanySearchRepository companySearchRepository;
	private AccountRepository accountRepository;
	
	@Autowired
	public ReferringService(OutlookRepository outlookRepository, FollowCompanyRepository followCompanyRepository, PostSearchRepository postSearchRepository, 
			CompanySearchRepository companySearchRepository, AccountRepository accountRepository) {
		this.outlookRepository = outlookRepository;
		this.followCompanyRepository = followCompanyRepository;
		this.postSearchRepository = postSearchRepository;
		this.companySearchRepository = companySearchRepository;
		this.accountRepository = accountRepository;
	}
	
	@Override
	@Transactional
	public FollowCompany postOrIncrementFollowCompany(final Long companyId, final String out) {
		final Optional<FollowCompany> uOptional = followCompanyRepository.findById(companyId);
		final FollowCompany follow = uOptional.isPresent() ? uOptional.get() : new FollowCompany(companyId);
		switch(out) {
		case "facebook": follow.setFacebook(follow.getFacebook() + 1); break;
		case "twitter": follow.setTwitter(follow.getTwitter() + 1); break;
		case "google": follow.setGoogle(follow.getGoogle() + 1); break;
		case "linkedin": follow.setLinkedin(follow.getLinkedin() + 1); break;
		case "viadeo": follow.setViadeo(follow.getViadeo() + 1);
		}
		return followCompanyRepository.save(follow);
	}
	
	@Override
	@Transactional
	public Outlook postOrIncrementOutlook(final Long companyId, final int out) {
		final Optional<Outlook> uOptional = outlookRepository.findById(companyId);
		final Outlook outlook = uOptional.isPresent() ? uOptional.get() : new Outlook(companyId);
		switch(out) {
		case GET_PHONE: outlook.setGetPhone(outlook.getGetPhone() + 1); break;
		case APP_PHONE: outlook.setAppPhone(outlook.getAppPhone() + 1); break;
		case SEND_MAIL: outlook.setSendMail(outlook.getSendMail() + 1);
		}
		return outlookRepository.save(outlook);
	}
	
	@Override
	@Transactional
	public void incrementReferringCompanies(final OnReferringCompanyEvent event) {
		final List<Long> lines = event.getLines();
		if(!lines.isEmpty()) {
			companySearchRepository.incrementViews(lines);
			if(event.hasPresentReferring()) {
				if(event.isToken()) {
					companySearchRepository.incrementTokens(lines);
				}
				if(event.isTag()) {
					companySearchRepository.incrementTags(lines);
				}
				if(event.isFilter()) {
					companySearchRepository.incrementFilters(lines);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public void incrementReferringPosts(final OnReferringPostEvent event) {
		final List<UUID> lines = event.getLines();
		if(!lines.isEmpty()) {
			postSearchRepository.incrementViews(lines);
			if(event.hasPresentReferring()) {
				if(event.isToken()) {
					postSearchRepository.incrementTokens(lines);
				}
				if(event.isTag()) {
					postSearchRepository.incrementTags(lines);
				}
				if(event.isFilter()) {
					postSearchRepository.incrementFilters(lines);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public void updateReferringCompleted(final OnReferringCompletedEvent event) {
		if(event.isHasUser()) {
			accountRepository.updateCompletedById(event.getCompleted(), event.getId());
		} else {
			companySearchRepository.updateCompletedById(event.getCompleted(), event.getId());
		}
	}
	
}
