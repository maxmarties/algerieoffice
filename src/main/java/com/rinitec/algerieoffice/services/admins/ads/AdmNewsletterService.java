package com.rinitec.algerieoffice.services.admins.ads;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.ads.NewsletterRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmMarketplaceForm;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

@Service
public class AdmNewsletterService implements IAdmNewsletterService {

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private NewsletterRepository newsletterRepository;
	private BlogRepository blogRepository;
	private AnnonceRepository annonceRepository;
	
	@Autowired
	public AdmNewsletterService(UserRepository userRepository, AccountRepository accountRepository, NewsletterRepository newsletterRepository, 
			BlogRepository blogRepository, AnnonceRepository annonceRepository) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.newsletterRepository = newsletterRepository;
		this.blogRepository = blogRepository;
		this.annonceRepository = annonceRepository;
	}
	
	@Transactional
	private final boolean hasAlreadyRegisterUser(final String email) {
		final Optional<Long> uOptional = userRepository.findIdByEmail(email);
		if(uOptional.isPresent()) {
			final Account account = accountRepository.findById(uOptional.get()).get();
			account.setHasAccepte(true);
			accountRepository.save(account);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public Newsletter registerNewsletter(final String email) {
		if(newsletterRepository.existsByEmail(email) || hasAlreadyRegisterUser(email)) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Newsletter newsletter = new Newsletter();
		newsletter.setEmail(email);
		newsletter.setSuscribeDate(new DateTime(Date.from(Instant.now())));
		return newsletterRepository.save(newsletter);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findAllNewsletter(final int limit) {
		final List<String> users = userRepository.findAllNewsletter(limit);
		if(users.size() == limit) {
			return users;
		}
		final List<String> newsletters = newsletterRepository.findAllNewsletter(users, limit - users.size());
		users.addAll(newsletters);
		return users;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllBolgNewsletter(final int limit) {
		return blogRepository.findLastBlogNewsletter(limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogNewsletterMini> findAllBlogNewsletterMini(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			return blogRepository.findAllBlogNewsletterMini(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmMarketplaceForm readMarketplaceForm(final int limitUser, final int limitAds) {
		final AdmMarketplaceForm marketplaceForm = new AdmMarketplaceForm();
		final DateTime begin = ParseUtil.getBeginDate(ParseUtil.TODAY);
		final DateTime end = ParseUtil.getEndDate(ParseUtil.TODAY);
		marketplaceForm.setTodays(annonceRepository.countAllNewsAnnonce(begin, end, null).intValue());
		marketplaceForm.setOffers(annonceRepository.countAllNewsAnnonce(begin, end, 1).intValue());
		marketplaceForm.setUsers(findAllNewsletter(limitUser));
		marketplaceForm.setAnnonces(annonceRepository.findAllAnnonceNewsletter(limitAds));
		return marketplaceForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AnnonceNewsletterMini> findAllAnnonceNewsletterMini(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			return annonceRepository.findAllAnnonceNewsletterMini(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
