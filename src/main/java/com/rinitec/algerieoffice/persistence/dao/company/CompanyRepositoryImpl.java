package com.rinitec.algerieoffice.persistence.dao.company;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.persistence.result.CompanyAvatar;
import com.rinitec.algerieoffice.persistence.result.CompanyLive;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyCustomer;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyFeature;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyProfile;
import com.rinitec.algerieoffice.web.modal.company.CurrentCommunication;
import com.rinitec.algerieoffice.web.modal.company.CurrentProspect;
import com.rinitec.algerieoffice.web.modal.mapsite.CompanyMapsite;
import com.rinitec.algerieoffice.web.modal.publics.CompanyAutorMini;

@Repository
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long checkCompanyPublished(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(rootCompany.get("id")).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public CurrentCompany getCurrentCompany(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CurrentCompany> criteriaQuery = builder.createQuery(CurrentCompany.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(CurrentCompany.class, rootCompany, rootSeo.get("url"), rootAccount.get("createdDate"), premium));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<CurrentCompany> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long getCompanyIdExplorer(final String url) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootSeo.get("url"), url));
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(rootSeo.get("companyId")).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public CompanyAvatar readCompanyAvatar(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyAvatar> criteriaQuery = builder.createQuery(CompanyAvatar.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(builder.construct(CompanyAvatar.class, root.get("tradename"), root.get("hasAvatar")));
		criteriaQuery.where(builder.equal(root.get("id"), companyId));
		final TypedQuery<CompanyAvatar> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public CompanyLive readCompanyLive(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyLive> criteriaQuery = builder.createQuery(CompanyLive.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(CompanyLive.class, rootCompany.get("id"), rootCompany.get("tradename"), 
				rootCompany.get("hasAvatar"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<CompanyLive> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public CompanyAutorMini findCompanyAutorMini(final String url) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyAutorMini> criteriaQuery = builder.createQuery(CompanyAutorMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootSeo.get("url"), url));
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(CompanyAutorMini.class, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<CompanyAutorMini> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public CurrentCommunication readCurrentCommunication(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CurrentCommunication> criteriaQuery = builder.createQuery(CurrentCommunication.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<Long> subqueryNotice = criteriaQuery.subquery(Long.class);
		final Root<Notice> subrootNotice = subqueryNotice.from(Notice.class);
		final Subquery<Long> subqueryContact = criteriaQuery.subquery(Long.class);
		final Root<Contact> subrootContact = subqueryContact.from(Contact.class);
		final Subquery<Long> subqueryAppointment = criteriaQuery.subquery(Long.class);
		final Root<Appointment> subrootAppointment = subqueryAppointment.from(Appointment.class);
		final Subquery<Long> subqueryCollaborator = criteriaQuery.subquery(Long.class);
		final Root<Collaborator> subrootCollaborator = subqueryCollaborator.from(Collaborator.class);
		final Subquery<Long> subqueryGuestPartner = criteriaQuery.subquery(Long.class);
		final Root<GuestPartner> subrootGuestPartner = subqueryGuestPartner.from(GuestPartner.class);
		final Subquery<Long> subqueryChatbot = criteriaQuery.subquery(Long.class);
		final Root<Chatbot> subrootChatbot = subqueryChatbot.from(Chatbot.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesNotice = new ArrayList<>();
		final List<Predicate> predicatesContact = new ArrayList<>();
		final List<Predicate> predicatesAppointment = new ArrayList<>();
		final List<Predicate> predicatesCollaborator = new ArrayList<>();
		final List<Predicate> predicatesGuestPartner = new ArrayList<>();
		final List<Predicate> predicatesChatbot = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicatesNotice.add(builder.equal(subrootNotice.get("companyId"), rootCompany.get("id")));
		predicatesNotice.add(builder.isFalse(subrootNotice.get("approuved")));
		predicatesContact.add(builder.equal(subrootContact.get("companyId"), rootCompany.get("id")));
		predicatesContact.add(builder.isFalse(subrootContact.get("approuved")));
		predicatesAppointment.add(builder.equal(subrootAppointment.get("companyId"), rootCompany.get("id")));
		predicatesAppointment.add(builder.isFalse(subrootAppointment.get("approuved")));
		predicatesCollaborator.add(builder.equal(subrootCollaborator.get("companyId"), rootCompany.get("id")));
		predicatesCollaborator.add(builder.isFalse(subrootCollaborator.get("approuved")));
		predicatesGuestPartner.add(builder.equal(subrootGuestPartner.get("partnerId"), rootCompany.get("id")));
		predicatesGuestPartner.add(builder.isFalse(subrootGuestPartner.get("approuved")));
		predicatesChatbot.add(builder.equal(subrootChatbot.get("companyId"), rootCompany.get("id")));
		predicatesChatbot.add(builder.isNull(subrootChatbot.get("account")));
		predicatesChatbot.add(builder.isFalse(subrootChatbot.get("consulted")));
		subqueryNotice.select(builder.count(subrootNotice)).where(predicatesNotice.toArray(new Predicate[0]));
		subqueryContact.select(builder.count(subrootContact)).where(predicatesContact.toArray(new Predicate[0]));
		subqueryAppointment.select(builder.count(subrootAppointment)).where(predicatesAppointment.toArray(new Predicate[0]));
		subqueryCollaborator.select(builder.count(subrootCollaborator)).where(predicatesCollaborator.toArray(new Predicate[0]));
		subqueryGuestPartner.select(builder.count(subrootGuestPartner)).where(predicatesGuestPartner.toArray(new Predicate[0]));
		subqueryChatbot.select(builder.count(subrootChatbot)).where(predicatesChatbot.toArray(new Predicate[0]));
		final Expression<Long> notice = subqueryNotice.getSelection();
		final Expression<Long> contact = subqueryContact.getSelection();
		final Expression<Long> appoint = subqueryAppointment.getSelection();
		final Expression<Long> collaborator = subqueryCollaborator.getSelection();
		final Expression<Long> partner = subqueryGuestPartner.getSelection();
		final Expression<Long> chatbot = subqueryChatbot.getSelection();
		criteriaQuery.select(builder.construct(CurrentCommunication.class, notice, contact, appoint, collaborator, partner, chatbot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<CurrentCommunication> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public CurrentProspect readCurrentProspect(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CurrentProspect> criteriaQuery = builder.createQuery(CurrentProspect.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<Long> subqueryPost = criteriaQuery.subquery(Long.class);
		final Root<GuestDocument> subrootPost = subqueryPost.from(GuestDocument.class);
		final Subquery<Long> subqueryAnnonce = criteriaQuery.subquery(Long.class);
		final Root<GuestDocument> subrootAnnonce = subqueryAnnonce.from(GuestDocument.class);
		final Subquery<Long> subqueryEvent = criteriaQuery.subquery(Long.class);
		final Root<GuestDocument> subrootEvent = subqueryEvent.from(GuestDocument.class);
		final Subquery<Long> subqueryEmploye = criteriaQuery.subquery(Long.class);
		final Root<GuestDocument> subrootEmploye = subqueryEmploye.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPost = new ArrayList<>();
		final List<Predicate> predicatesAnnonce = new ArrayList<>();
		final List<Predicate> predicatesEvent = new ArrayList<>();
		final List<Predicate> predicatesEmploye = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicatesPost.add(builder.equal(subrootPost.get("companyId"), rootCompany.get("id")));
		predicatesPost.add(builder.equal(subrootPost.get("type"), DocumentType.post));
		predicatesPost.add(builder.isFalse(subrootPost.get("consulted")));
		predicatesAnnonce.add(builder.equal(subrootAnnonce.get("companyId"), rootCompany.get("id")));
		predicatesAnnonce.add(builder.equal(subrootAnnonce.get("type"), DocumentType.annonce));
		predicatesAnnonce.add(builder.isFalse(subrootAnnonce.get("consulted")));
		predicatesEvent.add(builder.equal(subrootEvent.get("companyId"), rootCompany.get("id")));
		predicatesEvent.add(builder.equal(subrootEvent.get("type"), DocumentType.event));
		predicatesEvent.add(builder.isFalse(subrootEvent.get("consulted")));
		predicatesEmploye.add(builder.equal(subrootEmploye.get("companyId"), rootCompany.get("id")));
		predicatesEmploye.add(builder.equal(subrootEmploye.get("type"), DocumentType.employe));
		predicatesEmploye.add(builder.isFalse(subrootEmploye.get("consulted")));
		subqueryPost.select(builder.count(subrootPost)).where(predicatesPost.toArray(new Predicate[0]));
		subqueryAnnonce.select(builder.count(subrootAnnonce)).where(predicatesAnnonce.toArray(new Predicate[0]));
		subqueryEvent.select(builder.count(subrootEvent)).where(predicatesEvent.toArray(new Predicate[0]));
		subqueryEmploye.select(builder.count(subrootEmploye)).where(predicatesEmploye.toArray(new Predicate[0]));
		final Expression<Long> post = subqueryPost.getSelection();
		final Expression<Long> annonce = subqueryAnnonce.getSelection();
		final Expression<Long> event = subqueryEvent.getSelection();
		final Expression<Long> employe = subqueryEmploye.getSelection();
		criteriaQuery.select(builder.construct(CurrentProspect.class, post, annonce, event, employe));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<CurrentProspect> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countCompaniesForSector(final Integer sector, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countCompaniesByBuildForSector(final Integer sector, final Integer wilaya, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.between(root.get("buildDate"), begin, end));
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countCompaniesByRegionForSector(final Integer sector, final int region) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.in(joinAddress.get("wilaya")).value(ParseUtil.getRegionList(region)));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countCompaniesForActivity(final String code, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countCompaniesByBuildForActivity(final String code, final Integer wilaya, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.between(root.get("buildDate"), begin, end));
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countCompaniesByRegionForActivity(final String code, final int region) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.in(joinAddress.get("wilaya")).value(ParseUtil.getRegionList(region)));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Object[] findCompanyHrefFromUserId(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryTradename = criteriaQuery.subquery(String.class);
		final Root<Company> subrootTradename = subqueryTradename.from(Company.class);
		final Subquery<String> subqueryUrl = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootUrl = subqueryUrl.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootUser.get("id"), userId));
		subqueryTradename.select(subrootTradename.get("tradename")).where(builder.equal(subrootTradename.get("id"), rootUser.get("companyId")));
		subqueryUrl.select(subrootUrl.get("url")).where(builder.equal(subrootUrl.get("companyId"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryTradename.getSelection();
		final Expression<String> url = subqueryUrl.getSelection();
		criteriaQuery.multiselect(rootUser.get("email"), tradename, url).where(predicates.toArray(new Predicate[0]));
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Object[] findCompanyInfoFromUserId(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryTradename = criteriaQuery.subquery(String.class);
		final Root<Company> subrootTradename = subqueryTradename.from(Company.class);
		final Subquery<String> subqueryUrl = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootUrl = subqueryUrl.from(CompanySeo.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootUser.get("id"), userId));
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootUser.get("companyId")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryTradename.select(subrootTradename.get("tradename")).where(builder.equal(subrootTradename.get("id"), rootUser.get("companyId")));
		subqueryUrl.select(subrootUrl.get("url")).where(builder.equal(subrootUrl.get("companyId"), rootUser.get("companyId")));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<String> tradename = subqueryTradename.getSelection();
		final Expression<String> url = subqueryUrl.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.multiselect(rootUser.get("email"), tradename, url, premium).where(predicates.toArray(new Predicate[0]));
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllCompanyAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(root.get("tradename"), "empty_tradename"));
		if(filter != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(root.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.countDistinct(root));
		} else {
			criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmCompanyLine> findAllCompanyAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCompanyLine> criteriaQuery = builder.createQuery(AdmCompanyLine.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.notEqual(rootCompany.get("tradename"), "empty_tradename"));
		if(filter != null) {
			predicates.add(builder.equal(joinAddress.get("wilaya"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(joinAddress.get("wilaya")) : builder.asc(joinAddress.get("wilaya")); break;
		default: order = hasDesc ? builder.desc(joinActivity.get("code")) : builder.asc(joinActivity.get("code"));
		}
		criteriaQuery.select(builder.construct(AdmCompanyLine.class, rootCompany, rootSeo.get("url"), rootCompany.get("tradename"), 
				joinAddress.get("wilaya"), joinActivity.get("code"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCompanyLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<AdmCompanyProfile> findAllCompanyProfileAdmin(final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCompanyProfile> criteriaQuery = builder.createQuery(AdmCompanyProfile.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootAccount.get("createdById"), rootUser.get("id")));
		predicates.add(builder.notEqual(rootCompany.get("tradename"), "empty_tradename"));
		if(filter != null) {
			predicates.add(builder.equal(joinAddress.get("wilaya"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAccount.get("modifiedDate")) : builder.asc(rootAccount.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAccount.get("createdDate")) : builder.asc(rootAccount.get("createdDate")); break;
		case 3: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 4: order = hasDesc ? builder.desc(joinAddress.get("wilaya")) : builder.asc(joinAddress.get("wilaya")); break;
		default: order = hasDesc ? builder.desc(joinActivity.get("code")) : builder.asc(joinActivity.get("code"));
		}
		criteriaQuery.select(builder.construct(AdmCompanyProfile.class, rootCompany, rootAccount, rootSeo.get("url"), exp, rootAccount.get("modifiedDate"), 
				rootAccount.get("createdDate"), rootCompany.get("tradename"), joinAddress.get("wilaya"), joinActivity.get("code"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCompanyProfile> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<AdmCompanyFeature> findAllCompanyFeatureAdmin(final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCompanyFeature> criteriaQuery = builder.createQuery(AdmCompanyFeature.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final Subquery<Long> subqueryUser = criteriaQuery.subquery(Long.class);
		final Root<User> subrootUser = subqueryUser.from(User.class);
		final Subquery<Double> subqueryNote = criteriaQuery.subquery(Double.class);
		final Root<Evaluation> subrootNote = subqueryNote.from(Evaluation.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.notEqual(rootCompany.get("tradename"), "empty_tradename"));
		if(filter != null) {
			predicates.add(builder.equal(joinAddress.get("wilaya"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryUser.select(builder.count(subrootUser)).where(builder.equal(subrootUser.get("companyId"), rootCompany.get("id")));
		subqueryNote.select(builder.avg(subrootNote.get("note"))).where(builder.equal(subrootNote.get("companyId"), rootCompany.get("id")));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Long> users = subqueryUser.getSelection();
		final Expression<Double> note = subqueryNote.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(joinAddress.get("wilaya")) : builder.asc(joinAddress.get("wilaya")); break;
		case 3: order = hasDesc ? builder.desc(joinActivity.get("code")) : builder.asc(joinActivity.get("code")); break;
		default: order = hasDesc ? builder.desc(rootSearch.get("completed")) : builder.asc(rootSearch.get("completed"));
		}
		criteriaQuery.select(builder.construct(AdmCompanyFeature.class, rootCompany, rootSeo.get("url"), rootSearch.get("completed"), users, 
				premium, note, rootCompany.get("tradename"), joinAddress.get("wilaya"), joinActivity.get("code"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCompanyFeature> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllCompanyB2CAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<WidgetB2C> rootB2C = criteriaQuery.from(WidgetB2C.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootB2C.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootB2C.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootB2C)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmCompanyCustomer> findAllCompanyB2CAdmin(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCompanyCustomer> criteriaQuery = builder.createQuery(AdmCompanyCustomer.class);
		final Root<WidgetB2C> rootB2C = criteriaQuery.from(WidgetB2C.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootB2C.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootB2C.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(rootB2C.get("category")) : builder.asc(rootB2C.get("category")); break;
		case 3: order = hasDesc ? builder.desc(rootB2C.get("activity")) : builder.asc(rootB2C.get("activity")); break;
		default: order = hasDesc ? builder.desc(rootB2C.get("enabled")) : builder.asc(rootB2C.get("enabled"));
		}
		criteriaQuery.select(builder.construct(AdmCompanyCustomer.class, rootB2C, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCompanyCustomer> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllCompanyForMapsite() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CompanyMapsite> findAllCompanyMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyMapsite> criteriaQuery = builder.createQuery(CompanyMapsite.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(CompanyMapsite.class, rootSeo.get("url"), rootAccount.get("modifiedDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAccount.get("modifiedDate")));
		final TypedQuery<CompanyMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActiveCompanyCompleted(final int completed) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.greaterThanOrEqualTo(rootSearch.get("completed"), completed));
		criteriaQuery.select(builder.count(rootCompany)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllCompany(final DateTime begin, final DateTime end, final boolean create) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.between(create ? rootAccount.get("createdDate") : rootAccount.get("modifiedDate"), begin, end));
		criteriaQuery.select(builder.count(rootCompany)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UserMini> findAllNewsletterCompany(final Long companyId, final Integer sector, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		criteriaQuery.select(builder.construct(UserMini.class, rootCompany.get("id"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootCompany.get("tradename")));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
