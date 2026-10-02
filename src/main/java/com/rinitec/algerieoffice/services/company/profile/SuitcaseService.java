package com.rinitec.algerieoffice.services.company.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAddressRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyCreditRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.BriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.CreditTaxeRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.CreditTruckRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.DaySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.LinkedWebsiteRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.Briefcase;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTaxe;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTruck;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.DayShedule;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.CompanymailExistException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.MobileExistException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.profile.BriefcaseForm;
import com.rinitec.algerieoffice.web.form.company.profile.CreditForm;
import com.rinitec.algerieoffice.web.form.company.profile.DaySheduleForm;
import com.rinitec.algerieoffice.web.form.company.profile.LinkedForm;
import com.rinitec.algerieoffice.web.form.company.profile.LocationForm;
import com.rinitec.algerieoffice.web.form.company.profile.SheduleForm;

@Service
public class SuitcaseService implements ISuitcaseService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private BriefcaseRepository briefcaseRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private CompanySheduleRepository companySheduleRepository;
	private DaySheduleRepository daySheduleRepository;
	private CompanyAddressRepository companyAddressRepository;
	private CompanyLocationRepository companyLocationRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private LinkedWebsiteRepository linkedWebsiteRepository;
	private CompanyCreditRepository companyCreditRepository;
	private CreditTaxeRepository creditTaxeRepository;
	private CreditTruckRepository creditTruckRepository;
	private IPhotoService photoService;
	
	@Autowired
	public SuitcaseService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, BriefcaseRepository briefcaseRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository, CompanySheduleRepository companySheduleRepository, DaySheduleRepository daySheduleRepository, 
			CompanyAddressRepository companyAddressRepository, CompanyLocationRepository companyLocationRepository, CompanyLinkedRepository companyLinkedRepository, 
			LinkedWebsiteRepository linkedWebsiteRepository, CompanyCreditRepository companyCreditRepository, CreditTaxeRepository creditTaxeRepository, 
			CreditTruckRepository creditTruckRepository, IPhotoService photoService) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.briefcaseRepository = briefcaseRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.daySheduleRepository = daySheduleRepository;
		this.companyAddressRepository = companyAddressRepository;
		this.companyLocationRepository = companyLocationRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.linkedWebsiteRepository = linkedWebsiteRepository;
		this.companyCreditRepository = companyCreditRepository;
		this.creditTaxeRepository = creditTaxeRepository;
		this.creditTruckRepository = creditTruckRepository;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BriefcaseForm readBriefcaseForm(final Long companyId) {
		final BriefcaseForm briefcaseForm = new BriefcaseForm();
		final Optional<CompanyBriefcase> uOptional = companyBriefcaseRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyBriefcase companyBriefcase = uOptional.get();
			final List<Briefcase> briefcases = new ArrayList<Briefcase>(companyBriefcase.getBriefcases());
			briefcaseForm.setBriefcase(companyBriefcase.getBriefcase());
			briefcaseForm.setWarehouse(companyBriefcase.getWarehouse());
			briefcaseForm.setCapital(companyBriefcase.getCapital() != null ? String.valueOf(companyBriefcase.getCapital()) : null);
			briefcaseForm.setType(companyBriefcase.getType());
			briefcaseForm.setNrc(companyBriefcase.getNrc());
			briefcaseForm.setNif(companyBriefcase.getNif());
			briefcaseForm.setNis(companyBriefcase.getNis());
			for (final Briefcase briefcase : briefcases) {
				briefcaseForm.getIdents().add(briefcase.getId());
				briefcaseForm.getLabels().add(briefcase.getLabel());
				briefcaseForm.getInfos().add(briefcase.getInfo());
			}
		}
		briefcaseForm.setId(companyId);
		return briefcaseForm;
	}
	
	@Transactional
	private final Briefcase updateBriefcase(final Long idItem, final String label, final String info) {
		final Briefcase briefcase = briefcaseRepository.findById(idItem).get();
		briefcase.setLabel(label);
		briefcase.setInfo(info);
		return briefcaseRepository.save(briefcase);
	}
	
	@Transactional
	private final void createBriefcases(final CompanyBriefcase companyBriefcase, final BriefcaseForm briefcaseForm) {
		final List<Briefcase> briefcases = new ArrayList<Briefcase>();
		for (int i = 0; i < briefcaseForm.getIdents().size(); i++) {
			if(briefcaseForm.getIdents().get(i) == -1) {
				if(briefcaseForm.getUpdated().get(i) == -1) {
					final Briefcase briefcase = new Briefcase();
					briefcase.setLabel(briefcaseForm.getLabels().get(i));
					briefcase.setInfo(briefcaseForm.getInfos().get(i));
					briefcase.setCompanybriefcase(companyBriefcase);
					briefcases.add(briefcase);
				} else {
					updateBriefcase(briefcaseForm.getUpdated().get(i), briefcaseForm.getLabels().get(i), 
							briefcaseForm.getInfos().get(i));
				}
			}
		}
		if(!briefcases.isEmpty()) {
			briefcaseRepository.saveAll(briefcases);
		}
	}
	
	@Transactional
	private final CompanyBriefcase createOrUpdateCompanyBriefcase(final BriefcaseForm briefcaseForm) {
		final Optional<CompanyBriefcase> uOptional = companyBriefcaseRepository.findById(briefcaseForm.getId());
		final CompanyBriefcase companyBriefcase = uOptional.isPresent() ? uOptional.get() : new CompanyBriefcase(briefcaseForm.getId());
		companyBriefcase.setBriefcase(briefcaseForm.getBriefcase());
		companyBriefcase.setWarehouse(briefcaseForm.isWarehouse());
		companyBriefcase.setCapital(!StringUtils.isEmpty(briefcaseForm.getCapital()) ? Integer.valueOf(briefcaseForm.getCapital()) : null);
		companyBriefcase.setType(briefcaseForm.getType());
		companyBriefcase.setNrc(briefcaseForm.getNrc().replaceAll("\\.", ""));
		companyBriefcase.setNif(briefcaseForm.getNif().replaceAll("\\.", ""));
		companyBriefcase.setNis(briefcaseForm.getNis().replaceAll("\\.", ""));
		return companyBriefcaseRepository.save(companyBriefcase);
	}
	
	@Override
	@Transactional
	public CompanyBriefcase updateCompanyBriefcase(final BriefcaseForm briefcaseForm) {
		final CompanyBriefcase companyBriefcase = createOrUpdateCompanyBriefcase(briefcaseForm);
		if(briefcaseForm.isUpdateInfo()) {
			if(!briefcaseForm.getTrashed().isEmpty()) {
				briefcaseRepository.deleteLinesBriefcase(briefcaseForm.getTrashed());
			}
			if(!briefcaseForm.getLabels().isEmpty()) {
				createBriefcases(companyBriefcase, briefcaseForm);
			}
		}
		return companyBriefcase;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SheduleForm readSheduleForm(final Long companyId) {
		final SheduleForm sheduleForm = new SheduleForm();
		sheduleForm.setId(companyId);
		sheduleForm.setEmail(companyRepository.findCompanymailById(companyId).get());
		sheduleForm.setPhone(companyRepository.findPhoneById(companyId).get());
		final Optional<CompanyShedule> uOptional = companySheduleRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyShedule companyShedule = uOptional.get();
			final List<DayShedule> days = new ArrayList<DayShedule>(companyShedule.getDays());
			sheduleForm.setFax(!StringUtils.isEmpty(companyShedule.getFax()) ? companyShedule.getFax() : null);
			sheduleForm.setMobile(!StringUtils.isEmpty(companyShedule.getMobile()) ? companyShedule.getMobile() : null);
			for(int i = 0; i < 7; i++) {
				sheduleForm.getDays()[i].parseDayShedule(days.get(i));
			}
		} else {
			for(int i = 0; i < 7; i++) {
				sheduleForm.getDays()[i].initDayShedule();
			}
		}
		return sheduleForm;
	}
	
	@Transactional
	private final DayShedule updateDayShedule(final DayShedule dayShedule, final DaySheduleForm daySheduleForm) {
		dayShedule.setStateday(daySheduleForm.getStateday() == 0 ? null : daySheduleForm.getStateday() == 1);
		dayShedule.setTimeone(daySheduleForm.getStateday() == 0 ? null : daySheduleForm.getTimeday()[0]);
		dayShedule.setTimetho(daySheduleForm.getStateday() == 0 ? null : daySheduleForm.getTimeday()[1]);
		dayShedule.setTimetree(daySheduleForm.getStateday() != 2 ? null : daySheduleForm.getTimeday()[2]);
		dayShedule.setTimefour(daySheduleForm.getStateday() != 2 ? null : daySheduleForm.getTimeday()[3]);
		return dayShedule;
	}
	
	@Transactional
	private final void createDayShedules(final CompanyShedule companyShedule, final DaySheduleForm[] days) {
		final List<DayShedule> dayShedules = new ArrayList<DayShedule>();
		for(int i = 0; i < days.length; i++) {
			final DayShedule dayShedule = new DayShedule();
			dayShedule.setIndexday(i + 1);
			dayShedule.setCompanyshedule(companyShedule);
			dayShedules.add(updateDayShedule(dayShedule, days[i]));
		}
		if(!dayShedules.isEmpty()) {
			daySheduleRepository.saveAll(dayShedules);
		}
	}
	
	@Transactional
	private final void updateDayShedules(final List<DayShedule> dayShedules, final DaySheduleForm[] days) {
		final List<DayShedule> updatedDayShedules = new ArrayList<DayShedule>();
		for(int i = 0; i < dayShedules.size(); i++) {
			updatedDayShedules.add(updateDayShedule(dayShedules.get(i), days[i]));
		}
		if(!updatedDayShedules.isEmpty()) {
			daySheduleRepository.saveAll(updatedDayShedules);
		}
	}
	
	@Transactional
	private final CompanyShedule createOrUpdateCompanyShedule(final CompanyShedule companyShedule, final SheduleForm sheduleForm) {
		companyShedule.setFax(!StringUtils.isEmpty(sheduleForm.getFax()) ? sheduleForm.getFax() : null);
		companyShedule.setMobile(!StringUtils.isEmpty(sheduleForm.getMobile()) ? sheduleForm.getMobile() : null);
		return companySheduleRepository.save(companyShedule);
	}
	
	@Override
	@Transactional
	public CompanyShedule updateCompanyShedule(final SheduleForm sheduleForm) {
		final Long companyId = sheduleForm.getId();
		final String email = companyRepository.findCompanymailById(companyId).get();
		final String phone = companyRepository.findPhoneById(companyId).get();
		if(!sheduleForm.getEmail().equalsIgnoreCase(email) && companyRepository.existsByCompanymail(sheduleForm.getEmail())) {
			throw new CompanymailExistException("message.error.companymailexist");
		}
		if(!sheduleForm.getPhone().equals(phone) && companyRepository.existsByPhone(sheduleForm.getPhone())) {
			throw new PhoneExistException("message.error.phoneexist");
		}
		boolean hasUpdate = false;
		final Optional<CompanyShedule> uOptional = companySheduleRepository.findById(companyId);
		CompanyShedule companyShedule = (hasUpdate = uOptional.isPresent()) ? uOptional.get() : new CompanyShedule(companyId);
		if(!StringUtils.isEmpty(sheduleForm.getFax()) && !sheduleForm.getFax().equals(companyShedule.getFax()) 
				&& companySheduleRepository.existsByFax(sheduleForm.getFax())) {
			throw new AlreadyExistException("message.error.faxexist");
		}
		if(!StringUtils.isEmpty(sheduleForm.getMobile()) && !sheduleForm.getMobile().equals(companyShedule.getMobile()) 
				&& companySheduleRepository.existsByMobile(sheduleForm.getMobile())) {
			throw new MobileExistException("message.error.mobileexist");
		}
		if(!sheduleForm.getEmail().equalsIgnoreCase(email)) {
			companyRepository.updateCompanymailById(sheduleForm.getEmail(), companyId);
		}
		if(!sheduleForm.getPhone().equals(phone)) {
			companyRepository.updatePhoneById(sheduleForm.getPhone(), companyId);
		}
		companyShedule = createOrUpdateCompanyShedule(companyShedule, sheduleForm);
		if(!hasUpdate) {
			createDayShedules(companyShedule, sheduleForm.getDays());
		} else {
			updateDayShedules(new ArrayList<DayShedule>(companyShedule.getDays()), sheduleForm.getDays());
		}
		return companyShedule;
	}
	
	@Override
	@Transactional(readOnly = true)
	public LocationForm readLocationForm(final Long companyId) {
		final LocationForm locationForm = new LocationForm();
		final List<CompanyAddress> companiesAddress = companyAddressRepository.findAllByCompanyId(companyId);
		final CompanyAddress companyAddress = companiesAddress.get(0);
		locationForm.setAddress(companyAddress.getAddress());
		locationForm.setPostal(companyAddress.getPostal());
		locationForm.setWilaya(companyAddress.getWilaya());
		if(companiesAddress.size() > 1) {
			for (int i = 1; i < companiesAddress.size(); i++) {
				locationForm.getIdents().add(companiesAddress.get(i).getId());
				locationForm.getAddrs().add(companiesAddress.get(i).getAddress());
				locationForm.getPostals().add(companiesAddress.get(i).getPostal());
				locationForm.getWilayas().add(companiesAddress.get(i).getWilaya());
			}
		}
		final Optional<CompanyLocation> uOptional = companyLocationRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyLocation companyLocation = uOptional.get();
			locationForm.setHasCarte(true);
			locationForm.setHasEmpded(companyLocation.getHasEmpded());
			locationForm.setUrlmap(companyLocation.getUrlmap());
			locationForm.setEmpded(companyLocation.getEmpded());
		} else {
			locationForm.setHasCarte(false);
		}
		locationForm.setId(companyId);
		return locationForm;
	}
	
	@Transactional
	private final CompanyAddress updateAddress(final CompanyAddress companyAddress, final String address, final String postal, final Integer wilaya) {
		companyAddress.setAddress(address);
		companyAddress.setPostal(postal);
		companyAddress.setWilaya(wilaya);
		return companyAddressRepository.save(companyAddress);
	}
	
	@Transactional
	private final CompanyAddress updateAddressItem(final Long idItem, final String address, final String postal, final Integer wilaya) {
		final CompanyAddress companyAddress = companyAddressRepository.findById(idItem).get();
		return updateAddress(companyAddress, address, postal, wilaya);
	}
	
	@Transactional
	private final void createNewCompaniesAddress(final Company company, final LocationForm locationForm) {
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>();
		for(int i = 0; i < locationForm.getIdents().size(); i++) {
			if(locationForm.getIdents().get(i) == -1) {
				if(locationForm.getUpdated().get(i) == -1) {
					final CompanyAddress companyAddress = new CompanyAddress();
					companyAddress.setAddress(locationForm.getAddrs().get(i));
					companyAddress.setPostal(locationForm.getPostals().get(i));
					companyAddress.setWilaya(locationForm.getWilayas().get(i));
					companyAddress.setCompany(company);
					companiesAddress.add(companyAddress);
				} else {
					updateAddressItem(locationForm.getUpdated().get(i), locationForm.getAddrs().get(i), 
							locationForm.getPostals().get(i), locationForm.getWilayas().get(i));
				}
			}
		}
		if(!companiesAddress.isEmpty()) {
			companyAddressRepository.saveAll(companiesAddress);
		}
	}
	
	@Transactional
	private final void updateCompanyAddress(final LocationForm locationForm) {
		final Company company = companyRepository.findById(locationForm.getId()).get();
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		if(!companyAddress.getAddress().equals(locationForm.getAddress()) || !companyAddress.getPostal().equals(locationForm.getPostal()) 
				|| !companyAddress.getWilaya().equals(locationForm.getWilaya())) {
			updateAddress(companyAddress, locationForm.getAddress(), locationForm.getPostal(), locationForm.getWilaya());
		}
		if(locationForm.isUpdateAddr()) {
			if(!locationForm.getTrashed().isEmpty()) {
				companyAddressRepository.deleteLinesCompanyAddress(locationForm.getTrashed());
			}
			if(!locationForm.getAddrs().isEmpty()) {
				createNewCompaniesAddress(company, locationForm);
			}
		}
	}
	
	@Transactional
	private final CompanyLocation createOrUpdateCompanyLocation(final CompanyLocation companyLocation, final LocationForm locationForm) {
		companyLocation.setHasEmpded(locationForm.isHasEmpded());
		companyLocation.setEmpded(locationForm.isHasEmpded() ? locationForm.getEmpded() : null);
		companyLocation.setUrlmap(!locationForm.isHasEmpded() ? locationForm.getUrlmap() : null);
		return companyLocationRepository.save(companyLocation);
	}
	
	@Override
	@Transactional
	public CompanyLocation updateCompanyLocation(final LocationForm locationForm) {
		boolean hasUpdate = false;
		final Optional<CompanyLocation> uOptional = companyLocationRepository.findById(locationForm.getId());
		final CompanyLocation companyLocation = (hasUpdate = uOptional.isPresent()) ? uOptional.get() : new CompanyLocation(locationForm.getId());
		if(locationForm.getHasCarte()) {
			if(!locationForm.isHasEmpded() && !locationForm.getUrlmap().equalsIgnoreCase(companyLocation.getUrlmap()) 
					&& companyLocationRepository.existsByUrlmap(locationForm.getUrlmap())) {
				throw new UrlUnavailableException("message.error.urlmap");
			}
			createOrUpdateCompanyLocation(companyLocation, locationForm);
		} else if(hasUpdate) {
			companyLocationRepository.deleteById(locationForm.getId());
		}
		updateCompanyAddress(locationForm);
		return companyLocation;
	}
	
	@Transactional(readOnly = true)
	private final LinkedForm readLinkedFormSeo(final Long companyId) {
		final LinkedForm linkedForm = new LinkedForm();
		final CompanySeo companySeo = companySeoRepository.findById(companyId).get();
		linkedForm.setId(companyId);
		linkedForm.setUrl(companySeo.getUrl());
		linkedForm.setCheckedURL(companySeo.getUrl());
		linkedForm.setTageline(companySeo.getTageline());
		linkedForm.setKeysword(companySeo.getKeysword());
		linkedForm.setDescription(companySeo.getDescription());
		return linkedForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public LinkedForm readLinkedForm(final Long companyId) {
		final LinkedForm linkedForm = readLinkedFormSeo(companyId);
		final Optional<CompanyLinked> uOptional = companyLinkedRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyLinked companyLinked = uOptional.get();
			final List<LinkedWebsite> websites = new ArrayList<LinkedWebsite>(companyLinked.getWebsites());
			linkedForm.setFacebook(companyLinked.getFacebook());
			linkedForm.setTwitter(companyLinked.getTwitter());
			linkedForm.setLinkedin(companyLinked.getLinkedin());
			linkedForm.setYoutube(companyLinked.getYoutube());
			linkedForm.setGoogle(companyLinked.getGoogle());
			linkedForm.setInstagram(companyLinked.getInstagram());
			for (final LinkedWebsite linkedWebsite : websites) {
				linkedForm.getIdents().add(linkedWebsite.getId());
				linkedForm.getWebsitesName().add(linkedWebsite.getName());
				linkedForm.getWebsitesUrl().add(linkedWebsite.getUrl());
				linkedForm.getWebsitesType().add(linkedWebsite.getType());
				linkedForm.getPhotosUUID().add(linkedWebsite.getPhotoUUID().toString());
			}
		}
		return linkedForm;
	}
	
	@Transactional
	private final CompanySeo updateCompanySeo(final LinkedForm linkedForm) {
		final CompanySeo companySeo = companySeoRepository.findById(linkedForm.getId()).get();
		if(!StringUtils.isEmpty(linkedForm.getUrl())) {
			companySeo.setUrl(linkedForm.getUrl());
		}
		companySeo.setTageline(linkedForm.getTageline());
		companySeo.setKeysword(linkedForm.getKeysword());
		companySeo.setDescription(linkedForm.getDescription());
		return companySeoRepository.save(companySeo);
	}
	
	@Transactional
	private final void clearLinkedWebsites(final List<Long> lines, final List<String> photosUUID) {
		if(!lines.isEmpty()) {
			linkedWebsiteRepository.deleteLinesLinkedWebsite(lines);
		}
		if(!photosUUID.isEmpty()) {
			photoService.deleteAllPhoto(photosUUID);
		}
	}
	
	@Transactional
	private final LinkedWebsite updateLinkedWebsite(final Long idItem, final String name, final String url, final Integer type) {
		final LinkedWebsite linkedWebsite = linkedWebsiteRepository.findById(idItem).get();
		linkedWebsite.setName(name);
		linkedWebsite.setUrl(url);
		linkedWebsite.setType(type);
		return linkedWebsiteRepository.save(linkedWebsite);
	}
	
	@Transactional
	private final void updateLinkedWebsites(final CompanyLinked companyLinked, final LinkedForm linkedForm) {
		int j = 0;
		final List<LinkedWebsite> linkedsWebsite = new ArrayList<LinkedWebsite>();
		for(int i = 0; i < linkedForm.getIdents().size(); i++) {
			if(linkedForm.getIdents().get(i) == -1) {
				if(linkedForm.getUpdated().get(i) == -1) {
					final Photo photo = photoService.addPhoto(linkedForm.getFiles()[j++], linkedForm.getId(), PhotoType.linked);
					final LinkedWebsite linkedWebsite = new LinkedWebsite();
					linkedWebsite.setName(linkedForm.getWebsitesName().get(i));
					linkedWebsite.setUrl(linkedForm.getWebsitesUrl().get(i));
					linkedWebsite.setType(linkedForm.getWebsitesType().get(i));
					linkedWebsite.setPhotoUUID(photo.getId());
					linkedWebsite.setCompanylinked(companyLinked);
					linkedsWebsite.add(linkedWebsite);
				} else {
					final LinkedWebsite linkedWebsite = updateLinkedWebsite(linkedForm.getUpdated().get(i),
							linkedForm.getWebsitesName().get(i), linkedForm.getWebsitesUrl().get(i), linkedForm.getWebsitesType().get(i));
					photoService.updatePhoto(linkedWebsite.getPhotoUUID(), linkedForm.getFiles()[j++]);
				}
			} else if(linkedForm.getIdents().get(i) == 0) {
				updateLinkedWebsite(linkedForm.getUpdated().get(i), linkedForm.getWebsitesName().get(i), 
						linkedForm.getWebsitesUrl().get(i), linkedForm.getWebsitesType().get(i));
			}
		}
		if(!linkedsWebsite.isEmpty()) {
			linkedWebsiteRepository.saveAll(linkedsWebsite);
		}
	}
	
	@Transactional
	private final CompanyLinked createOrUpdateCompanyLinked(final CompanyLinked companyLinked, final LinkedForm linkedForm) {
		companyLinked.setFacebook(!StringUtils.isEmpty(linkedForm.getFacebook()) ? linkedForm.getFacebook() : null);
		companyLinked.setTwitter(!StringUtils.isEmpty(linkedForm.getTwitter()) ? linkedForm.getTwitter() : null);
		companyLinked.setLinkedin(!StringUtils.isEmpty(linkedForm.getLinkedin()) ? linkedForm.getLinkedin() : null);
		companyLinked.setGoogle(!StringUtils.isEmpty(linkedForm.getGoogle()) ? linkedForm.getGoogle() : null);
		companyLinked.setYoutube(!StringUtils.isEmpty(linkedForm.getYoutube()) ? linkedForm.getYoutube() : null);
		companyLinked.setInstagram(!StringUtils.isEmpty(linkedForm.getInstagram()) ? linkedForm.getInstagram() : null);
		return companyLinkedRepository.save(companyLinked);
	}
	
	@Override
	@Transactional
	public CompanyLinked updateCompanyLinked(final LinkedForm linkedForm, final int maxKeysword) {
		if(!StringUtils.isEmpty(linkedForm.getUrl()) && !linkedForm.getUrl().equalsIgnoreCase(linkedForm.getCheckedURL()) 
				&& companySeoRepository.existsByUrl(linkedForm.getUrl())) {
			throw new UrlUnavailableException("message.error.url");
		}
		if(!StringUtils.isEmpty(linkedForm.getKeysword()) && linkedForm.getKeysword().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(linkedForm.isUpdateWebsite() && !linkedForm.getWebsitesUrl().isEmpty()) {
			for(int i = 0; i < linkedForm.getIdents().size(); i++) {
				if(linkedForm.getIdents().get(i) == -1 && linkedForm.getUpdated().get(i) == -1 
						&& linkedWebsiteRepository.existsByUrl(linkedForm.getWebsitesUrl().get(i))) {
					throw new AlreadyExistException("message.error.websiteexist");
				}
			}
		}
		boolean hasUpdate = false;
		final Optional<CompanyLinked> uOptional = companyLinkedRepository.findById(linkedForm.getId());
		final CompanyLinked companyLinked = (hasUpdate = uOptional.isPresent()) ? uOptional.get() : new CompanyLinked(linkedForm.getId());
		if(!StringUtils.isEmpty(linkedForm.getFacebook()) && !linkedForm.getFacebook().equalsIgnoreCase(companyLinked.getFacebook()) 
				&& companyLinkedRepository.existsByFacebook(linkedForm.getFacebook())) {
			throw new SocialExistException("facebook");
		}
		if(!StringUtils.isEmpty(linkedForm.getTwitter()) && !linkedForm.getTwitter().equalsIgnoreCase(companyLinked.getTwitter()) 
				&& companyLinkedRepository.existsByTwitter(linkedForm.getTwitter())) {
			throw new SocialExistException("twitter");
		}
		if(!StringUtils.isEmpty(linkedForm.getLinkedin()) && !linkedForm.getLinkedin().equalsIgnoreCase(companyLinked.getLinkedin()) 
				&& companyLinkedRepository.existsByLinkedin(linkedForm.getLinkedin())) {
			throw new SocialExistException("linkedin");
		}
		if(!StringUtils.isEmpty(linkedForm.getYoutube()) && !linkedForm.getYoutube().equalsIgnoreCase(companyLinked.getYoutube()) 
				&& companyLinkedRepository.existsByYoutube(linkedForm.getYoutube())) {
			throw new SocialExistException("youtube");
		}
		if(!StringUtils.isEmpty(linkedForm.getGoogle()) && !linkedForm.getGoogle().equalsIgnoreCase(companyLinked.getGoogle()) 
				&& companyLinkedRepository.existsByGoogle(linkedForm.getGoogle())) {
			throw new SocialExistException("google");
		}
		if(!StringUtils.isEmpty(linkedForm.getInstagram()) && !linkedForm.getInstagram().equalsIgnoreCase(companyLinked.getInstagram()) 
				&& companyLinkedRepository.existsByInstagram(linkedForm.getInstagram())) {
			throw new SocialExistException("instagram");
		}
		updateCompanySeo(linkedForm);
		if(linkedForm.isUpdateWebsite()) {
			clearLinkedWebsites(linkedForm.getTrashed(), linkedForm.getPhotosUUID());
		}
		if(linkedForm.hasSocial() || !linkedForm.getWebsitesUrl().isEmpty()) {
			updateLinkedWebsites(createOrUpdateCompanyLinked(companyLinked, linkedForm), linkedForm);
		} else if(hasUpdate) {
			companyLinkedRepository.deleteById(linkedForm.getId());
		}
		return companyLinked;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CreditForm readCreditForm(final Long companyId) {
		final CreditForm creditForm = new CreditForm();
		final Optional<CompanyCredit> uOptional = companyCreditRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyCredit companyCredit = uOptional.get();
			final List<CreditTaxe> taxes = new ArrayList<CreditTaxe>(companyCredit.getTaxes());
			final List<CreditTruck> trucks = new ArrayList<CreditTruck>(companyCredit.getTrucks());
			creditForm.setCheque(companyCredit.getCheque());
			creditForm.setVersement(companyCredit.getVersement());
			creditForm.setEspece(companyCredit.getEspece());
			creditForm.setCarte(companyCredit.getCarte());
			creditForm.setPaypal(companyCredit.getPaypal());
			for (final CreditTaxe creditTaxe : taxes) {
				creditForm.getIdentsTaxe().add(creditTaxe.getId());
				creditForm.getTaxesname().add(creditTaxe.getTaxename());
				creditForm.getTaxestaux().add(creditTaxe.getTaxetaux());
			}
			for (final CreditTruck creditTruck : trucks) {
				creditForm.getIdentsTruck().add(creditTruck.getId());
				creditForm.getIndstruck().add(creditTruck.getIndtruck());
			}
		}
		creditForm.setId(companyId);
		return creditForm;
	}
	
	@Transactional
	private final CompanyCredit createOrUpdateCompanyCredit(final CompanyCredit companyCredit, final CreditForm creditForm) {
		companyCredit.setCheque(creditForm.isCheque());
		companyCredit.setVersement(creditForm.isVersement());
		companyCredit.setEspece(creditForm.isEspece());
		companyCredit.setCarte(creditForm.isCarte());
		companyCredit.setPaypal(creditForm.isPaypal());
		return companyCreditRepository.save(companyCredit);
	}
	
	@Transactional
	private final CreditTaxe updateCreditTaxe(final Long idItem, final String name, final Integer taux) {
		final CreditTaxe creditTaxe = creditTaxeRepository.findById(idItem).get();
		creditTaxe.setTaxename(name);
		creditTaxe.setTaxetaux(taux);
		return creditTaxeRepository.save(creditTaxe);
	}
	
	@Transactional
	private final void updateCreditTaxes(final CompanyCredit companyCredit, final CreditForm creditForm) {
		final List<CreditTaxe> creditsTaxe = new ArrayList<CreditTaxe>();
		for(int i = 0; i < creditForm.getIdentsTaxe().size(); i++) {
			if(creditForm.getIdentsTaxe().get(i) == -1) {
				if(creditForm.getUpdatedTaxe().get(i) == -1) {
					final CreditTaxe creditTaxe = new CreditTaxe();
					creditTaxe.setTaxename(creditForm.getTaxesname().get(i));
					creditTaxe.setTaxetaux(creditForm.getTaxestaux().get(i));
					creditTaxe.setCompanycredit(companyCredit);
					creditsTaxe.add(creditTaxe);
				} else {
					updateCreditTaxe(creditForm.getUpdatedTaxe().get(i), creditForm.getTaxesname().get(i), 
							creditForm.getTaxestaux().get(i));
				}
			}
		}
		if(!creditsTaxe.isEmpty()) {
			creditTaxeRepository.saveAll(creditsTaxe);
		}
	}
	
	@Transactional
	private final CreditTruck updateCreditTruck(final Long idItem, final String ind) {
		final CreditTruck creditTruck = creditTruckRepository.findById(idItem).get();
		creditTruck.setIndtruck(ind);
		return creditTruckRepository.save(creditTruck);
	}
	
	@Transactional
	private final void updateCreditTrucks(final CompanyCredit companyCredit, final CreditForm creditForm) {
		final List<CreditTruck> creditsTruck = new ArrayList<CreditTruck>();
		for(int i = 0; i < creditForm.getIdentsTruck().size(); i++) {
			if(creditForm.getIdentsTruck().get(i) == -1) {
				if(creditForm.getUpdatedTruck().get(i) == -1) {
					final CreditTruck creditTruck = new CreditTruck();
					creditTruck.setIndtruck(creditForm.getIndstruck().get(i));
					creditTruck.setCompanycredit(companyCredit);
					creditsTruck.add(creditTruck);
				} else {
					updateCreditTruck(creditForm.getUpdatedTruck().get(i), creditForm.getIndstruck().get(i));
				}
			}
		}
		if(!creditsTruck.isEmpty()) {
			creditTruckRepository.saveAll(creditsTruck);
		}
	}
	
	@Override
	@Transactional
	public CompanyCredit updateCompanyCredit(final CreditForm creditForm) {
		if(!creditForm.hasCredit() && creditForm.getTaxesname().isEmpty() && creditForm.getIndstruck().isEmpty()) {
			if(companyCreditRepository.existsById(creditForm.getId())) {
				companyCreditRepository.deleteById(creditForm.getId());
			}
			return null;
		}
		final Optional<CompanyCredit> uOptional = companyCreditRepository.findById(creditForm.getId());
		final CompanyCredit companyCredit = createOrUpdateCompanyCredit(uOptional.isPresent() ? uOptional.get() 
				: new CompanyCredit(creditForm.getId()), creditForm);
		if(creditForm.isUpdateTaxe()) {
			if(!creditForm.getTrashedTaxe().isEmpty()) {
				creditTaxeRepository.deleteLinesCreditTaxe(creditForm.getTrashedTaxe());
			}
			updateCreditTaxes(companyCredit, creditForm);
		}
		if(creditForm.isUpdateTruck()) {
			if(!creditForm.getTrashedTruck().isEmpty()) {
				creditTruckRepository.deleteLinesCreditTruck(creditForm.getTrashedTruck());
			}
			updateCreditTrucks(companyCredit, creditForm);
		}
		return companyCredit;
	}
	
}
