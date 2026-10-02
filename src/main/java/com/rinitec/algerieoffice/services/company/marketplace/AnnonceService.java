package com.rinitec.algerieoffice.services.company.marketplace;

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

import com.rinitec.algerieoffice.enums.FileType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceWilayaRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceActivity;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceWilaya;
import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.AnnonceForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.marketplace.AnnonceLine;

@Service
public class AnnonceService implements IAnnonceService {
	
	private AnnonceRepository annonceRepository;
	private AnnonceDetailRepository annonceDetailRepository;
	private AnnonceActivityRepository annonceActivityRepository;
	private AnnonceWilayaRepository annonceWilayaRepository;
	private IFilereaderService filereaderService;
	
	@Autowired
	public AnnonceService(AnnonceRepository annonceRepository, AnnonceDetailRepository annonceDetailRepository, 
			AnnonceActivityRepository annonceActivityRepository, AnnonceWilayaRepository annonceWilayaRepository, 
			IFilereaderService filereaderService) {
		this.annonceRepository = annonceRepository;
		this.annonceDetailRepository = annonceDetailRepository;
		this.annonceActivityRepository = annonceActivityRepository;
		this.annonceWilayaRepository = annonceWilayaRepository;
		this.filereaderService = filereaderService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasMaxAnnonce(final Long companyId) {
		final long count = annonceRepository.countByCompanyId(companyId);
		return count >= ConstraintesForm.MAX_COUNT_DATA;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countTrashedAnnonce(final Long companyId) {
		return annonceRepository.countByCompanyIdAndHasTrashed(companyId, true);
	}
	
	private final AnnonceForm parseAnnonceForm(final Annonce annonce, final AnnonceDetail annonceDetail, 
			final List<Integer> annonceActivities, final List<Integer> annonceWilayas) {
		final AnnonceForm annonceForm = new AnnonceForm();
		annonceForm.setId(annonce.getId().toString());
		annonceForm.setCompanyId(annonce.getCompanyId());
		annonceForm.setTitle(annonce.getTitle());
		annonceForm.setIdentify(annonce.getIdentify());
		annonceForm.setType(annonce.getType());
		annonceForm.setStartDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate()));
		annonceForm.setEndDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate()));
		annonceForm.setDescription(annonce.getDescription());
		annonceForm.setKeysword(annonce.getKeysword());
		annonceForm.setUrlExtern(annonceDetail.getUrlExtern());
		annonceForm.setDetail(new String(annonceDetail.getDetail()));
		annonceForm.setVisibility(annonceDetail.getVisibility());
		annonceForm.setHasPublished(annonce.getHasPublished());
		annonceForm.setSectors(annonceActivities);
		annonceForm.setWilayas(annonceWilayas);
		annonceForm.setHasFile(annonceDetail.getFileUUID() != null);
		annonceForm.setFilename(annonceDetail.getFileUUID() != null ? filereaderService.getFilename(annonceDetail.getFileUUID()) : null);
		return annonceForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnnonceForm readAnnonceForm(final String id, final Long companyId) {
		try {
			final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Annonce annonce = uOptional.get();
				final AnnonceDetail annonceDetail = annonceDetailRepository.findById(annonce.getId()).get();
				final List<Integer> annonceActivities = annonceActivityRepository.findSectorsByAnnonceId(annonce.getId());
				final List<Integer> annonceWilayas = annonceWilayaRepository.findWilayasByAnnonceId(annonce.getId());
				return parseAnnonceForm(annonce, annonceDetail, annonceActivities, annonceWilayas);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Annonce postAnnonce(final Annonce annonce, final AnnonceForm annonceForm, final Long userId) {
		annonce.setTitle(annonceForm.getTitle());
		annonce.setIdentify(annonceForm.getIdentify());
		annonce.setType(annonceForm.getType());
		annonce.setStartDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(annonceForm.getStartDate()));
		annonce.setEndDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(annonceForm.getEndDate()));
		annonce.setDescription(annonceForm.getDescription());
		annonce.setKeysword(annonceForm.getKeysword());
		annonce.setAutorId(userId);
		annonce.setModifiedDate(new DateTime(Date.from(Instant.now())));
		annonce.setHasPublished(annonceForm.getHasPublished());
		return annonceRepository.save(annonce);
	}
	
	@Transactional
	private final AnnonceDetail postAnnonceDetail(final AnnonceDetail annonceDetail, final AnnonceForm annonceForm, final UUID FileId) {
		annonceDetail.setDetail(annonceForm.getDetail().getBytes());
		annonceDetail.setUrlExtern(!StringUtils.isEmpty(annonceForm.getUrlExtern()) ? annonceForm.getUrlExtern() : null);
		annonceDetail.setFileUUID(FileId);
		annonceDetail.setVisibility(annonceForm.getVisibility());
		return annonceDetailRepository.save(annonceDetail);
	}
	
	@Transactional
	private final void createAnnonceActivities(final UUID annonceId, final List<Integer> sectors) {
		final List<AnnonceActivity> annonceActivities = new ArrayList<AnnonceActivity>();
		for (final Integer sector : sectors) {
			final AnnonceActivity annonceActivity = new AnnonceActivity();
			annonceActivity.setAnnonceUUID(annonceId);
			annonceActivity.setSector(sector);
			annonceActivities.add(annonceActivity);
		}
		annonceActivityRepository.saveAll(annonceActivities);
	}
	
	@Transactional
	private final void createAnnonceWilayas(final UUID annonceId, final List<Integer> wilayas) {
		if(wilayas.isEmpty()) {
			final AnnonceWilaya annonceWilaya = new AnnonceWilaya();
			annonceWilaya.setAnnonceUUID(annonceId);
			annonceWilayaRepository.save(annonceWilaya);
		} else {
			final List<AnnonceWilaya> annonceWilayas = new ArrayList<AnnonceWilaya>();
			for (final Integer wilaya : wilayas) {
				final AnnonceWilaya annonceWilaya = new AnnonceWilaya();
				annonceWilaya.setAnnonceUUID(annonceId);
				annonceWilaya.setWilaya(wilaya);
				annonceWilayas.add(annonceWilaya);
			}
			annonceWilayaRepository.saveAll(annonceWilayas);
		}
	}
	
	@Override
	@Transactional
	public Annonce addAnnonce(final AnnonceForm annonceForm, final int maxKeysword, final Long userId) {
		if(annonceRepository.findIdByIdentify(annonceForm.getCompanyId(), annonceForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(!StringUtils.isEmpty(annonceForm.getKeysword()) && annonceForm.getKeysword().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(!StringUtils.isEmpty(annonceForm.getUrlExtern()) && annonceDetailRepository.existsByUrlExtern(annonceForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		UUID fileId = null;
		if(annonceForm.isHasFile()) {
			final Filereader filereader = filereaderService.addFilereader(annonceForm.getFile(), annonceForm.getCompanyId(), FileType.annonce);
			fileId = filereader.getId();
		}
		final Annonce annonce = postAnnonce(new Annonce(annonceForm.getCompanyId()), annonceForm, userId);
		postAnnonceDetail(new AnnonceDetail(annonce), annonceForm, fileId);
		createAnnonceActivities(annonce.getId(), annonceForm.getSectors());
		createAnnonceWilayas(annonce.getId(), annonceForm.getWilayas());
		return annonce;
	}
	
	@Transactional
	private final void updateAnnonceActivities(final UUID annonceId, final List<Integer> sectors) {
		annonceActivityRepository.deleteByAnnonceUUID(annonceId);
		createAnnonceActivities(annonceId, sectors);
	}
	
	@Transactional
	private final void updateAnnonceWilayas(final UUID annonceId, final List<Integer> wilayas) {
		annonceWilayaRepository.deleteByAnnonceUUID(annonceId);
		createAnnonceWilayas(annonceId, wilayas);
	}
	
	@Override
	@Transactional
	public Annonce updateAnnonce(final AnnonceForm annonceForm, final int maxKeysword, final Long userId) {
		final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(annonceForm.getId()));
		if(uOptional.isPresent() && annonceForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Annonce annonce = uOptional.get();
			final AnnonceDetail annonceDetail = annonceDetailRepository.findById(annonce.getId()).get();
			if(!annonceForm.getIdentify().equalsIgnoreCase(annonce.getIdentify()) 
					&& annonceRepository.findIdByIdentify(annonceForm.getCompanyId(), annonceForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(!StringUtils.isEmpty(annonceForm.getKeysword()) && annonceForm.getKeysword().split(",").length > maxKeysword) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			if(!StringUtils.isEmpty(annonceForm.getUrlExtern()) && !annonceForm.getUrlExtern().equalsIgnoreCase(annonceDetail.getUrlExtern()) 
					&& annonceDetailRepository.existsByUrlExtern(annonceForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			UUID fileId = annonceDetail.getFileUUID();
			if(annonceForm.isHasFileChanged()) {
				if(annonceForm.isHasFile()) {
					if(fileId == null) {
						final Filereader filereader = filereaderService.addFilereader(annonceForm.getFile(), annonceForm.getCompanyId(), FileType.annonce);
						fileId = filereader.getId();
					} else {
						filereaderService.updateFilereader(fileId, annonceForm.getFile());
					}
				} else if(fileId != null) {
					filereaderService.deleteFilereader(fileId);
					fileId = null;
				}
			}
			postAnnonce(annonce, annonceForm, userId);
			postAnnonceDetail(annonceDetail, annonceForm, fileId);
			if(annonceForm.isUpdateSectors()) {
				updateAnnonceActivities(annonce.getId(), annonceForm.getSectors());
			}
			if(annonceForm.isUpdateWilayas()) {
				updateAnnonceWilayas(annonce.getId(), annonceForm.getWilayas());
			}
			return annonce;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAnnoncesList(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = annonceRepository.countAllAnnonceCriteria(companyId, filter, search);
		final List<AnnonceLine> lines = countResult == 0L ? new ArrayList<AnnonceLine>() 
				: annonceRepository.findAllAnnonceCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Annonce publishAnnonce(final String id, final Long companyId) {
		try {
			final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Annonce annonce = uOptional.get();
			annonce.setHasPublished(!annonce.getHasPublished());
			return annonceRepository.save(annonce);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Annonce trashAnnonce(final String id, final Long companyId) {
		try {
			final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Annonce annonce = uOptional.get();
			annonce.setHasTrashed(true);
			return annonceRepository.save(annonce);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashAnnonces(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) annonceRepository.countAnnonces(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			annonceRepository.trashAnnonces(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashAllAnnonces(final Long companyId) {
		annonceRepository.trashAllAnnonces(companyId);
	}

}
