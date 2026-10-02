package com.rinitec.algerieoffice.services.company.newsletter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.BudgetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.MaintemplateRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Maintemplate;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.newsletter.BudgetForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.EmailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.TemplateForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterBudget;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterSocial;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterTemplate;

@Service
public class NewsletterService implements INewsletterService {

	private MaintemplateRepository maintemplateRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private BudgetRepository budgetRepository;
	private DocumentOrderRepository documentOrderRepository;
	private ActualityRepository actualityRepository;
	private EventRepository eventRepository;
	private AnnonceRepository annonceRepository;
	private EmployeRepository employeRepository;
	private PostRepository postRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private UserRepository userRepository;
	private EasylistCompanyRepository easylistCompanyRepository;
	private IAvatarService avatarService;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public NewsletterService(MaintemplateRepository maintemplateRepository, CompanyLinkedRepository companyLinkedRepository, BudgetRepository budgetRepository, 
			DocumentOrderRepository documentOrderRepository, ActualityRepository actualityRepository, EventRepository eventRepository, AnnonceRepository annonceRepository, 
			EmployeRepository employeRepository, PostRepository postRepository, FavoriteCompanyRepository favoriteCompanyRepository, CompanyRepository companyRepository, 
			CompanySeoRepository companySeoRepository, UserRepository userRepository, EasylistCompanyRepository easylistCompanyRepository, IAvatarService avatarService, 
			IEnvelopeService envelopeService) {
		this.maintemplateRepository = maintemplateRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.budgetRepository = budgetRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.actualityRepository = actualityRepository;
		this.eventRepository = eventRepository;
		this.annonceRepository = annonceRepository;
		this.employeRepository = employeRepository;
		this.postRepository = postRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.userRepository = userRepository;
		this.easylistCompanyRepository = easylistCompanyRepository;
		this.avatarService = avatarService;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean checkOrder(final Long userId) {
		return documentOrderRepository.existsByUserIdAndType(userId, OrderType.emailing);
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsletterSocial readNewsletterSocial(final Long companyId) {
		final Optional<CompanyLinked> uOptional = companyLinkedRepository.findById(companyId);
		return new NewsletterSocial(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TemplateForm readTemplateForm(final Long companyId) {
		final TemplateForm templateForm = new TemplateForm();
		final Optional<Maintemplate> uOptional = maintemplateRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Maintemplate maintemplate = uOptional.get();
			templateForm.setPaneColor(maintemplate.getPaneColor());
			templateForm.setTextColor(maintemplate.getTextColor());
			templateForm.setTitle(maintemplate.getTitle());
			templateForm.setTarget(maintemplate.getTargetIndex());
			templateForm.setUrl(maintemplate.getTargetUrl());
			templateForm.setLabel(maintemplate.getLabel());
			templateForm.setDescription(maintemplate.getDescription());
			templateForm.setHasAvatar(maintemplate.getHasCover());
			templateForm.parseParams(maintemplate.getParams());
		} else {
			templateForm.setPaneColor("#2C3F50");
			templateForm.setTextColor("#FFFFFF");
			templateForm.setTarget(1);
			templateForm.setLabel(1);
			templateForm.setHasAvatar(false);
			templateForm.initParams();
		}
		templateForm.setId(companyId);
		templateForm.setUrlAvatar(templateForm.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.template
				: "/static/picts/avatars/template-min.jpg");
		return templateForm;
	}
	
	@Override
	@Transactional
	public Maintemplate updateMaintemplate(final TemplateForm templateForm) {
		final Optional<Maintemplate> uOptional = maintemplateRepository.findById(templateForm.getId());
		final Maintemplate maintemplate = uOptional.isPresent() ? uOptional.get() : new Maintemplate(templateForm.getId());
		maintemplate.setHasCover(templateForm.isHasAvatar());
		maintemplate.setPaneColor(templateForm.getPaneColor());
		maintemplate.setTextColor(templateForm.getTextColor());
		maintemplate.setTitle(!StringUtils.isEmpty(templateForm.getTitle()) ? templateForm.getTitle() : null);
		maintemplate.setTargetIndex(templateForm.getTarget());
		maintemplate.setTargetUrl(templateForm.getTarget() == 3 && !StringUtils.isEmpty(templateForm.getUrl()) ? templateForm.getUrl() : null);
		maintemplate.setLabel(templateForm.getLabel());
		maintemplate.setDescription(!StringUtils.isEmpty(templateForm.getDescription()) ? templateForm.getDescription() : null);
		maintemplate.setParams(templateForm.builderParams());
		if(templateForm.isHasFileChanged()) {
			if(templateForm.isHasAvatar()) {
				avatarService.postOrUpdate(templateForm.getFile(), templateForm.getId(), AvatarType.template);
			} else {
				avatarService.deleteAvatar(templateForm.getId(), AvatarType.template);
			}
		}
		return maintemplateRepository.save(maintemplate);
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsletterBudget readNewsletterBudget(final Long companyId) {
		final Optional<Budget> uOptional = budgetRepository.findById(companyId);
		return new NewsletterBudget(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional
	private final DocumentOrder postDocumentOrder(final DocumentOrder documentOrder, final BudgetForm budgetForm, final Long userId, final UUID fileUUID) {
		documentOrder.setUserId(userId);
		documentOrder.setPack(budgetForm.getPack());
		documentOrder.setOrderDate(new DateTime(Date.from(Instant.now())));
		documentOrder.setFileUUID(fileUUID);
		return documentOrderRepository.save(documentOrder);
	}
	
	@Override
	@Transactional
	public DocumentOrder addDocumentOrder(final Long userId, final BudgetForm budgetForm) {
		final Envelope envelope = envelopeService.addEnvelope(budgetForm.getFile(), EnvelopeType.emailing);
		return postDocumentOrder(new DocumentOrder(OrderType.emailing), budgetForm, userId, envelope.getId());
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsletterTemplate readNewsletterTemplate(final Long companyId) {
		final Optional<Maintemplate> uMaintemplate = maintemplateRepository.findById(companyId);
		final Optional<CompanyLinked> uLinked = companyLinkedRepository.findById(companyId);
		final Maintemplate maintemplate = uMaintemplate.isPresent() ? uMaintemplate.get() : null;
		final NewsletterSocial newsletterSocial = uLinked.isPresent() ? new NewsletterSocial(uLinked.get()) : null;
		return new NewsletterTemplate(maintemplate, newsletterSocial);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllNewsletterArticles(final Long companyId, final Integer type) {
		if(type != null) {
			switch(type) {
			case 1: return actualityRepository.findAllActualityNewsletterMini(companyId);
			case 2: return eventRepository.findAllEventCampaignMini(companyId);
			case 3: return annonceRepository.findAllAnnonceCampaignMini(companyId);
			case 4: return employeRepository.findAllEmployeNewsletterMini(companyId);
			case 5: return postRepository.findAllPostCampaignMini(companyId);
			}
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllNewsletterUserCompany(final Long companyId) {
		return favoriteCompanyRepository.findAllNewsletterUserCompany(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllNewsletterCompanies(final Long companyId, final Integer sector, final Integer wilaya) {
		if(sector == null || wilaya == null) {
			final long count = companyRepository.countCompaniesForSector(sector, wilaya);
			if(count >= ConstraintesForm.MAX_NEWSLETTER_CHOSER) {
				return null;
			}
		}
		return companyRepository.findAllNewsletterCompany(companyId, sector, wilaya);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllEasylistCompany(final Long userId) {
		return easylistCompanyRepository.findAllEasylistCompany(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllNewsletterEasylist(final String id, final Long userId) {
		try {
			final Optional<EasylistCompany> uOptional = easylistCompanyRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && uOptional.get().getUserId().equals(userId)) {
				final List<Long> companies = uOptional.get().getCompanies();
				return easylistCompanyRepository.findAllNewsletterEasylistCompany(companies);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NewsletterItem> findAllNewsletterItem(final Long companyId, final Integer type, final List<String> lines) {
		if(type != null && !lines.isEmpty()) {
			try {
				final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
				switch(type) {
				case 1: return actualityRepository.findAllNewsletterItem(companyId, linesUUID);
				case 2: return eventRepository.findAllNewsletterItem(companyId, linesUUID);
				case 3: return annonceRepository.findAllNewsletterItem(companyId, linesUUID);
				case 4: return employeRepository.findAllNewsletterItem(companyId, linesUUID);
				case 5: return postRepository.findAllNewsletterItem(companyId, linesUUID);
				}
			} catch (IllegalArgumentException e) {
				throw new InvalidResourceException();
			}
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final boolean checkBudget(final Long companyId, final int contacts) {
		final Optional<Budget> uOptional = budgetRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Budget budget = uOptional.get();
			final int sendings = (contacts / ConstraintesForm.LIMIT_EMAILING) + (contacts % ConstraintesForm.LIMIT_EMAILING > 0 ? 1 : 0);
			return budget.getEmails() >= contacts && budget.getSendings() >= sendings;
		}
		return false;
	}
	
	private final String findTargetURL(final int type, final String companyURL) {
		switch(type) {
		case 1: return ConstraintesURL.getCompanyNewsletterURL(companyURL, ConstraintesURL.URL_NEWS);
		case 2: return ConstraintesURL.getCompanyNewsletterURL(companyURL, ConstraintesURL.URL_EVENTS);
		case 3: return ConstraintesURL.getCompanyNewsletterURL(companyURL, ConstraintesURL.URL_ANNONCES);
		case 4: return ConstraintesURL.getCompanyNewsletterURL(companyURL, ConstraintesURL.URL_EMPLOYE);
		case 5: return ConstraintesURL.getCompanyNewsletterURL(companyURL, ConstraintesURL.URL_POSTS);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final List<String> findAllTargetEmails(final int contact, final List<Long> contacts, final String custom) {
		switch(contact) {
		case 1: return userRepository.findAllEmailByUsersId(contacts);
		case 2: case 3: return companyRepository.findAllEmailByCompaniesId(contacts);
		case 4: return Arrays.asList(custom.split(","));
		}
		return new ArrayList<String>();
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsletterForm readNewsletterForm(final EmailingForm emailingForm, final String email) {
		if((emailingForm.getType() != 6 && emailingForm.getItems().isEmpty()) || (emailingForm.getContact() != 4 && emailingForm.getContacts().isEmpty())) {
			throw new EmptyElementException("message.input.item");
		}
		if(!checkBudget(emailingForm.getCompanyId(), emailingForm.getContact() != 4 ? emailingForm.getContacts().size() : emailingForm.getCustom().split(",").length)) {
			throw new MaxKeyswordException("message.input.budget");
		}
		final Long companyId = emailingForm.getCompanyId();
		final NewsletterForm newsletterForm = new NewsletterForm();
		final TemplateForm templateForm = readTemplateForm(companyId);
		final Company company = companyRepository.findById(companyId).get();
		final String companyURL = companySeoRepository.findUrlById(companyId).get();
		newsletterForm.setTradename(company.getTradename());
		newsletterForm.setCompanymail(company.getCompanymail());
		newsletterForm.setSubject(emailingForm.getSubject());
		newsletterForm.setPaneColor(templateForm.getPaneColor());
		newsletterForm.setTextColor(templateForm.getTextColor());
		if(company.getHasAvatar()) {
			newsletterForm.setLogo(avatarService.readFile(companyId, AvatarType.company));
		}
		if(templateForm.getParams()[0] && templateForm.isHasAvatar()) {
			newsletterForm.setCover(avatarService.readFile(companyId, AvatarType.template));
		}
		if(templateForm.getParams()[1]) {
			newsletterForm.setTitle(!StringUtils.isEmpty(emailingForm.getTitle()) ? emailingForm.getTitle() : emailingForm.getSubject());
		}
		if(emailingForm.getType() == 6) {
			final String detail = emailingForm.getDetail().replaceAll("src=\"/", "src=\"".concat(ConstraintesURL.URL_APPLICATION).concat("/"))
					.replaceAll("href=\"/", "href=\"".concat(ConstraintesURL.URL_APPLICATION).concat("/"));
			newsletterForm.setDetail(detail);
		} else {
			newsletterForm.setItems(findAllNewsletterItem(companyId, emailingForm.getType(), emailingForm.getItems()));
		}
		if(templateForm.getParams()[3]) {
			if(!StringUtils.isEmpty(templateForm.getTitle())) {
				newsletterForm.setFooter(templateForm.getTitle());
			}
			newsletterForm.setLabel(templateForm.getLabel() == 1 ? 2 : templateForm.getLabel());
			switch(templateForm.getTarget()) {
			case 1: newsletterForm.setTarget(findTargetURL(emailingForm.getType(), companyURL)); break;
			case 2: newsletterForm.setTarget(ConstraintesURL.getCompanyMapsiteURL(companyURL)); break;
			case 3: newsletterForm.setTarget(templateForm.getUrl());
			}
		}
		if(templateForm.getParams()[4]) {
			newsletterForm.setSocial(readNewsletterSocial(companyId));
		}
		newsletterForm.setDescription(!StringUtils.isEmpty(templateForm.getDescription()) ? templateForm.getDescription() : null);
		newsletterForm.setEmails(findAllTargetEmails(emailingForm.getContact(), emailingForm.getContacts(), emailingForm.getCustom()));
		if(emailingForm.isHasResend()) {
			newsletterForm.setSender(email);
		}
		return newsletterForm;
	}
	
	@Override
	@Transactional
	public Budget updateConsumeBudget(final Long companyId, final int contacts) {
		final Optional<Budget> uOptional = budgetRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Budget budget = uOptional.get();
			final int sendings = (contacts / ConstraintesForm.LIMIT_EMAILING) + (contacts % ConstraintesForm.LIMIT_EMAILING > 0 ? 1 : 0);
			budget.setEmails(budget.getEmails() - contacts);
			budget.setSendings(budget.getSendings() - sendings);
			return budgetRepository.save(budget);
		}
		return null;
	}
	
}
