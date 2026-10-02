package com.rinitec.algerieoffice.services.company.portfolio;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.WorkForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.portfolio.WorkLine;

@Service
public class WorkService implements IWorkService {

	private WorkRepository workRepository;
	private WorkDetailRepository workDetailRepository;
	private PartnerRepository partnerRepository;
	private IPhotoService photoService;
	
	@Autowired
	public WorkService(WorkRepository workRepository, WorkDetailRepository workDetailRepository, 
			PartnerRepository partnerRepository, IPhotoService photoService) {
		this.workRepository = workRepository;
		this.workDetailRepository = workDetailRepository;
		this.partnerRepository = partnerRepository;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countWork(final Long companyId) {
		return workRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllChosePartner(final Long companyId) {
		return partnerRepository.findAllChosePartnerMini(companyId);
	}
	
	private final WorkForm parseWorkForm(final Work work, final WorkDetail workDetail) {
		final WorkForm workForm = new WorkForm();
		workForm.setId(work.getId().toString());
		workForm.setCompanyId(work.getCompanyId());
		workForm.setWorkDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(work.getWorkDate()));
		workForm.setTitle(work.getTitle());
		workForm.setIdentify(work.getIdentify());
		workForm.setDescription(workDetail.getDescription());
		workForm.setDetail(new String(workDetail.getDetail()));
		workForm.setExpertise(work.getExpertise());
		workForm.setUrlExtern(workDetail.getUrlExtern());
		workForm.setPartner(work.getPartnerUUID() != null ? work.getPartnerUUID().toString() :  null);
		workForm.setHasPublished(work.getHasPublished());
		workForm.setHasAvatar(true);
		workForm.setUrlAvatar(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(workDetail.getPhotoUUID().toString()));
		return workForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public WorkForm readWorkForm(final String id, final Long companyId) {
		try {
			final Optional<Work> uOptional = workRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Work work = uOptional.get();
				final WorkDetail workDetail = workDetailRepository.findById(work.getId()).get();
				return parseWorkForm(work, workDetail);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Work postWork(final Work work, final WorkForm workForm, final Long userId) {
		work.setWorkDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(workForm.getWorkDate()));
		work.setTitle(workForm.getTitle());
		work.setIdentify(workForm.getIdentify());
		work.setExpertise(workForm.getExpertise());
		work.setPartnerUUID(workForm.hasPresentPartner() ? UUID.fromString(workForm.getPartner()) : null);
		work.setModifiedDate(new DateTime(Date.from(Instant.now())));
		work.setAutorId(userId);
		work.setHasPublished(workForm.getHasPublished());
		return workRepository.save(work);
	}
	
	@Transactional
	private final WorkDetail postWorkDetail(final WorkDetail workDetail, final WorkForm workForm, final UUID photoUUID) {
		workDetail.setDescription(workForm.getDescription());
		workDetail.setDetail(workForm.getDetail().getBytes());
		workDetail.setUrlExtern(!StringUtils.isEmpty(workForm.getUrlExtern()) ? workForm.getUrlExtern() : null);
		workDetail.setPhotoUUID(photoUUID);
		return workDetailRepository.save(workDetail);
	}
	
	@Override
	@Transactional
	public Work addWork(final WorkForm workForm, final int maxKeysword, final Long userId) {
		if(!workForm.isHasAvatar()) {
			throw new InvalidImageException("message.input.required");
		}
		if(workRepository.findIdByIdentify(workForm.getCompanyId(), workForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(workForm.hasPresentPartner() && !partnerRepository.existsById(UUID.fromString(workForm.getPartner()))) {
			throw new NotFoundException("message.input.notfound");
		}
		if(workForm.getExpertise().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(!StringUtils.isEmpty(workForm.getUrlExtern()) && workDetailRepository.existsByUrlExtern(workForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Photo photo = photoService.addPhoto(workForm.getFile(), workForm.getCompanyId(), PhotoType.work);
		final Work work = postWork(new Work(workForm.getCompanyId()), workForm, userId);
		postWorkDetail(new WorkDetail(work), workForm, photo.getId());
		return work;
	}
	
	@Override
	@Transactional
	public Work updateWork(final WorkForm workForm, final int maxKeysword, final Long userId) {
		if(!workForm.isHasAvatar()) {
			throw new InvalidImageException("message.input.required");
		}
		final Optional<Work> uOptional = workRepository.findById(UUID.fromString(workForm.getId()));
		if(uOptional.isPresent() && workForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Work work = uOptional.get();
			final WorkDetail workDetail = workDetailRepository.findById(work.getId()).get();
			final String partner = work.getPartnerUUID() != null ? work.getPartnerUUID().toString() : null;
			if(!workForm.getIdentify().equalsIgnoreCase(work.getIdentify()) 
					&& workRepository.findIdByIdentify(workForm.getCompanyId(), workForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(workForm.hasPresentPartner() && !workForm.getPartner().equals(partner) 
					&& !partnerRepository.existsById(UUID.fromString(workForm.getPartner()))) {
				throw new NotFoundException("message.input.notfound");
			}
			if(workForm.getExpertise().split(",").length > maxKeysword) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			if(!StringUtils.isEmpty(workForm.getUrlExtern()) && !workForm.getUrlExtern().equalsIgnoreCase(workDetail.getUrlExtern()) 
					&& workDetailRepository.existsByUrlExtern(workForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			if(workForm.isHasFileChanged()) {
				photoService.updatePhoto(workDetail.getPhotoUUID(), workForm.getFile());
			}
			postWork(work, workForm, userId);
			postWorkDetail(workDetail, workForm, workDetail.getPhotoUUID());
			return work;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findWorksList(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = workRepository.countAllWorkCriteria(companyId, filter, search);
		final List<WorkLine> lines = countResult == 0L ? new ArrayList<WorkLine>() 
				: workRepository.findAllWorkCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllFilterPartner(final Long companyId) {
		return partnerRepository.findAllFilterPartnerMini(companyId);
	}
	
	@Override
	@Transactional
	public Work deleteWork(final String id, final Long companyId) {
		try {
			final Optional<Work> uOptional = workRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Work work = uOptional.get();
			final UUID photoId = workDetailRepository.findPhotoUUIDById(work.getId()).get();
			photoService.deletePhoto(photoId);
			workDetailRepository.deleteById(work.getId());
			workRepository.delete(work);
			return work;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteWorks(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) workRepository.countWorks(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesPhoto = workDetailRepository.findAllPhotoUUIDById(linesUUID);
			workDetailRepository.deleteWorksDetail(linesUUID);
			workRepository.deleteWorks(linesUUID);
			photoService.deleteAllPhotos(linesPhoto);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllWorks(final Long companyId) {
		final List<UUID> linesUUID = workRepository.findAllIdByCompanyId(companyId);
		if(!linesUUID.isEmpty()) {
			final List<UUID> linesPhoto = workDetailRepository.findAllPhotoUUIDById(linesUUID);
			workDetailRepository.deleteWorksDetail(linesUUID);
			workRepository.deleteByCompanyId(companyId);
			photoService.deleteAllPhotos(linesPhoto);
		}
	}
	
}
