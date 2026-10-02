package com.rinitec.algerieoffice.services.admins.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.BannerType;
import com.rinitec.algerieoffice.persistence.dao.admins.ads.SponsoreRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.persistence.modal.medias.Banner;
import com.rinitec.algerieoffice.services.medias.IBannerService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.ads.SponsoreForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.SponsoreLine;

@Service
public class SponsoreService implements ISponsoreService {

	private SponsoreRepository sponsoreRepository;
	private IBannerService bannerService;
	
	@Autowired
	public SponsoreService(SponsoreRepository sponsoreRepository, IBannerService bannerService) {
		this.sponsoreRepository = sponsoreRepository;
		this.bannerService = bannerService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SponsoreForm readSponsoreForm(final String id) {
		try {
			final Optional<Sponsore> uOptional = sponsoreRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Sponsore sponsore = uOptional.get();
				final SponsoreForm sponsoreForm = new SponsoreForm();
				sponsoreForm.setId(id);
				sponsoreForm.setType(sponsore.getType());
				sponsoreForm.setUrl(sponsore.getUrl());
				sponsoreForm.setCreditCount(sponsore.getCreditCount());
				sponsoreForm.setHasPublished(sponsore.getHasPublished());
				sponsoreForm.setHasFile(true);
				sponsoreForm.setFilename(sponsore.getBannerUUID().toString());
				sponsoreForm.setBannerURL(ConstraintesURL.URL_AOBNN + "?aobnId=".concat(sponsore.getBannerUUID().toString()));
				return sponsoreForm;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	private final Sponsore postSponsore(final Sponsore sponsore, final SponsoreForm sponsoreForm, final UUID bannerUUID) {
		sponsore.setUrl(sponsoreForm.getUrl());
		sponsore.setType(sponsoreForm.getType());
		sponsore.setCreditCount(sponsoreForm.getCreditCount());
		sponsore.setHasPublished(sponsoreForm.getHasPublished());
		sponsore.setBannerUUID(bannerUUID);
		return sponsoreRepository.save(sponsore);
	}
	
	@Override
	@Transactional
	public Sponsore addSponsore(final SponsoreForm sponsoreForm) {
		final Banner banner = bannerService.addBanner(sponsoreForm.getFile(), BannerType.pubpage);
		return postSponsore(new Sponsore(), sponsoreForm, banner.getId());
	}
	
	@Override
	@Transactional
	public Sponsore updateSponsore(final SponsoreForm sponsoreForm) {
		final Optional<Sponsore> uOptional = sponsoreRepository.findById(UUID.fromString(sponsoreForm.getId()));
		if(uOptional.isPresent()) {
			final Sponsore sponsore = uOptional.get();
			if(sponsoreForm.isHasFileChanged()) {
				bannerService.updateBanner(sponsore.getBannerUUID(), sponsoreForm.getFile());
			}
			return postSponsore(sponsore, sponsoreForm, sponsore.getBannerUUID());
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findSponsoresList(final Integer filter, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = sponsoreRepository.countAllSponsoreCriteria(filter, search);
		final List<SponsoreLine> lines = countResult == 0L ? new ArrayList<SponsoreLine>() 
				: sponsoreRepository.findAllSponsoreCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Sponsore deleteSponsore(final String id) {
		try {
			final Optional<Sponsore> uOptional = sponsoreRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Sponsore sponsore = uOptional.get();
			bannerService.deleteBanner(sponsore.getBannerUUID());
			sponsoreRepository.delete(sponsore);
			return sponsore;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteSponsores(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) sponsoreRepository.countSponsores(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesBanner = sponsoreRepository.findAllBannerUUIDById(linesUUID);
			sponsoreRepository.deleteSponsores(linesUUID);
			bannerService.deleteAllBanners(linesBanner);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
