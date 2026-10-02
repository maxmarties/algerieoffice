package com.rinitec.algerieoffice.services.company;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestPartnerRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartnerguestForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.communication.PartnerGuestLine;

@Service
public class GuestPartnerService implements IGuestPartnerService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private GuestPartnerRepository guestPartnerRepository;
	private PartnerRepository partnerRepository;
	private IAvatarService avatarService;
	private IPhotoService photoService;
	
	@Autowired
	public GuestPartnerService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, 
			GuestPartnerRepository guestPartnerRepository, PartnerRepository partnerRepository, 
			IAvatarService avatarService, IPhotoService photoService) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.guestPartnerRepository = guestPartnerRepository;
		this.partnerRepository = partnerRepository;
		this.avatarService = avatarService;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional
	public GuestPartner addGuestPartner(final PartnerguestForm partnerguestForm, final Long companyId, final Long autorId) {
		final Company company = companyRepository.findByCompanymail(partnerguestForm.getEmail());
		if(company == null || !company.isPublished()) {
			throw new NotFoundException("message.input.notfound");
		}
		if(companyId.equals(company.getId())) {
			throw new AccessLeaderException("message.error.partner");
		}
		if(guestPartnerRepository.existsByCompanyIdAndPartnerId(companyId, company.getId())) {
			throw new AlreadyExistException("message.warning.partner");
		}
		final GuestPartner guestPartner = new GuestPartner();
		guestPartner.setCompanyId(companyId);
		guestPartner.setPartnerId(company.getId());
		guestPartner.setGuestDate(new DateTime(Date.from(Instant.now())));
		guestPartner.setGuestBy(autorId);
		guestPartner.setHasPingled(partnerguestForm.getHasGuestpingled());
		return guestPartnerRepository.save(guestPartner);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPartnerGuestList(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = guestPartnerRepository.countAllGuestPartnerCriteria(companyId, filter, search);
		final List<PartnerGuestLine> lines = countResult == 0L ? new ArrayList<PartnerGuestLine>() 
				: guestPartnerRepository.findAllGuestPartnerCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	private final UUID readPhotoUUID(final Long companyId, final Company company) {
		if(company.getHasAvatar()) {
			final File file = avatarService.readFile(company.getId(), AvatarType.company);
			final Photo photo = photoService.addPhotoFile(file, companyId, PhotoType.partner);
			return photo.getId();
		}
		return null;
	}
	
	@Transactional
	private final Partner addPartner(final Long companyId, final Long partnerId, final Long autorId, final boolean hasPingled) {
		final Partner partner = new Partner();
		final Company company = companyRepository.findById(partnerId).get();
		final CompanySeo companySeo = companySeoRepository.findById(partnerId).get();
		final UUID photoUUID = readPhotoUUID(companyId, company);
		partner.setCompanyId(companyId);
		partner.setName(company.getTradename());
		partner.setBiography(companySeo.getDescription());
		partner.setUrl(ConstraintesURL.getCompanyLinkedURL(companySeo.getUrl()));
		partner.setModifiedDate(new DateTime(Date.from(Instant.now())));
		partner.setAutorId(autorId);
		partner.setHasPingled(hasPingled);
		partner.setPhotoUUID(photoUUID);
		return partnerRepository.save(partner);
	}
	
	@Override
	@Transactional
	public GuestPartner validateGuestPartner(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<GuestPartner> uOptional = guestPartnerRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getPartnerId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final GuestPartner guestPartner = uOptional.get();
			if(guestPartner.isApprouved()) {
				throw new AccessLeaderException("message.warning.partnerguest");
			}
			addPartner(guestPartner.getCompanyId(), guestPartner.getPartnerId(), guestPartner.getGuestBy(), guestPartner.getHasPingled());
			addPartner(guestPartner.getPartnerId(), guestPartner.getCompanyId(), userId, false);
			guestPartner.setApprouved(true);
			guestPartner.setApprouvedBy(userId);
			return guestPartnerRepository.save(guestPartner);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Long deleteGuestPartner(final String id, final Long companyId) {
		try {
			final Optional<GuestPartner> uOptional = guestPartnerRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getPartnerId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final GuestPartner guestPartner = uOptional.get();
			final Long userId = !guestPartner.isApprouved() ? guestPartner.getGuestBy() : null;
			guestPartnerRepository.delete(guestPartner);
			return userId;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public List<Long> deleteGuestPartners(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) guestPartnerRepository.countGuestPartners(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<Long> usersId = guestPartnerRepository.findAllGuestedByCompanyId(companyId, linesUUID);
			guestPartnerRepository.deleteGuestPartners(linesUUID);
			return usersId;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public List<Long> deleteAllGuestPartners(final Long companyId) {
		final List<Long> usersId = guestPartnerRepository.findAllGuestedsByCompanyId(companyId);
		guestPartnerRepository.deleteByPartnerId(companyId);
		return usersId;
	}
	
}
