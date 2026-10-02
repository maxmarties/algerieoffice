package com.rinitec.algerieoffice.services.medias;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Service
public class LinkedMenuService implements ILinkedMenuService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	
	@Autowired
	public LinkedMenuService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository,
			PostRepository postRepository, AnnonceRepository annonceRepository) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasCompanyPublished(final Long companyId) {
		return companyRepository.findCompanyIdApprouved(companyId).isPresent();
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getUrlCompany(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return ConstraintesURL.getCompanyLinkedURL(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final String readCompanyURL(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final List<PostMini> findLinkedPost(final Long companyId) {
		final List<PostMini> linkeds = new ArrayList<PostMini>();
		final String companyURL = readCompanyURL(companyId);
		final List<PostMini> posts = postRepository.findAllPublishedPost(companyId);
		for (final PostMini post : posts) {
			final String postURL = ConstraintesURL.getPostLinkedURL(companyURL, post.getIdentify());
			linkeds.add(new PostMini(post.getTitle(), postURL));
		}
		return linkeds;
	}
	
	@Transactional(readOnly = true)
	private final List<PostMini> findLinkedAnnonce(final Long companyId) {
		final List<PostMini> linkeds = new ArrayList<PostMini>();
		final String companyURL = readCompanyURL(companyId);
		final List<PostMini> annonces = annonceRepository.findAllPublishedAnnonce(companyId);
		for (final PostMini annonce : annonces) {
			final String annonceURL = ConstraintesURL.getMarketplaceLinkedURL(companyURL, annonce.getIdentify());
			linkeds.add(new PostMini(annonce.getTitle(), annonceURL));
		}
		return linkeds;
	}
	
	@Override
	public List<PostMini> findChoseLinked(final Long companyId, final int linkType) {
		switch(linkType) {
		case LINKED_POST: return findLinkedPost(companyId);
		case LINKED_ANNONCE: return findLinkedAnnonce(companyId);
		}
		return null;
	}
	
}
