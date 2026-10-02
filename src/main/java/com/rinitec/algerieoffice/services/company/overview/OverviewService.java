package com.rinitec.algerieoffice.services.company.overview;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainaboutRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MaincatalogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainheaderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainoverviewRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainsliderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainthinkRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.CatalogItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.SliderItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.ThinkItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.TimelineRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainabout;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainheader;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainoverview;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.CatalogItem;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.SliderItem;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.ThinkItem;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.Timeline;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.overview.MainaboutForm;
import com.rinitec.algerieoffice.web.form.company.overview.MaincatalogForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainheaderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainsliderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainthinkForm;
import com.rinitec.algerieoffice.web.form.company.overview.PresentationForm;
import com.rinitec.algerieoffice.web.form.company.overview.TimelineForm;

@Service
public class OverviewService implements IOverviewService {

	private CompanyRepository companyRepository;
	private CompanyAccountRepository companyAccountRepository;
	private MainheaderRepository mainheaderRepository;
	private MainoverviewRepository mainoverviewRepository;
	private MaincatalogRepository maincatalogRepository;
	private CatalogItemRepository catalogItemRepository;
	private MainsliderRepository mainsliderRepository;
	private SliderItemRepository sliderItemRepository;
	private TimelineRepository timelineRepository;
	private MainaboutRepository mainaboutRepository;
	private MainthinkRepository mainthinkRepository;
	private ThinkItemRepository thinkItemRepository;
	private IAvatarService avatarService;
	private IPhotoService photoService;
	
	@Autowired
	public OverviewService(CompanyRepository companyRepository, CompanyAccountRepository companyAccountRepository, MainheaderRepository mainheaderRepository, 
			MainoverviewRepository mainoverviewRepository, MaincatalogRepository maincatalogRepository, CatalogItemRepository catalogItemRepository, 
			MainsliderRepository mainsliderRepository, SliderItemRepository sliderItemRepository, TimelineRepository timelineRepository, 
			MainaboutRepository mainaboutRepository, MainthinkRepository mainthinkRepository, ThinkItemRepository thinkItemRepository, 
			IAvatarService avatarService, IPhotoService photoService) {
		this.companyRepository = companyRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.mainheaderRepository = mainheaderRepository;
		this.mainoverviewRepository = mainoverviewRepository;
		this.maincatalogRepository = maincatalogRepository;
		this.catalogItemRepository = catalogItemRepository;
		this.mainsliderRepository = mainsliderRepository;
		this.sliderItemRepository = sliderItemRepository;
		this.timelineRepository = timelineRepository;
		this.mainaboutRepository = mainaboutRepository;
		this.mainthinkRepository = mainthinkRepository;
		this.thinkItemRepository = thinkItemRepository;
		this.avatarService = avatarService;
		this.photoService = photoService;
	}
	
	@Transactional(readOnly = true)
	private final MainheaderForm getMainheaderForm(final Long companyId) {
		final MainheaderForm mainheaderForm = new MainheaderForm();
		final DateTime modifiedDate = companyAccountRepository.findModifiedDateById(companyId).get();
		final Company company = companyRepository.findById(companyId).get();
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		final Activity activity = (Activity) company.getActivities().toArray()[0];
		mainheaderForm.setId(companyId);
		mainheaderForm.setHasLogo(company.getHasAvatar());
		mainheaderForm.setUrlLogo(company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company 
				: "/static/picts/avatars/company-min.jpg");
		mainheaderForm.setLanguage(company.getLang());
		mainheaderForm.setAddress(companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal()));
		mainheaderForm.setWilaya(companyAddress.getWilaya());
		mainheaderForm.setActivity(activity.getCode());
		mainheaderForm.setModifiedDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(modifiedDate));
		return mainheaderForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public MainheaderForm readMainheaderForm(final Long companyId) {
		final MainheaderForm mainheaderForm = getMainheaderForm(companyId);
		final Optional<Mainheader> uOptional = mainheaderRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Mainheader mainheader = uOptional.get();
			mainheaderForm.setHasCover(mainheader.getHasCover());
			mainheaderForm.setCanva(mainheader.getCanva());
			mainheaderForm.setCanvaColor(mainheader.getCanvaColor());
			mainheaderForm.setTextColor(mainheader.getTextColor());
		} else {
			mainheaderForm.setHasCover(false);
			mainheaderForm.setCanva("0.6");
			mainheaderForm.setCanvaColor("#2C3F50");
			mainheaderForm.setTextColor("#FFFFFF");
		}
		mainheaderForm.setUrlCover(mainheaderForm.isHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.cover
				: "/static/picts/avatars/cover-min.jpg");
		return mainheaderForm;
	}
	
	@Transactional
	private final Mainheader createOrUpdate(final MainheaderForm mainheaderForm) {
		final Optional<Mainheader> uOptional = mainheaderRepository.findById(mainheaderForm.getId());
		final Mainheader mainheader = uOptional.isPresent() ? uOptional.get() : new Mainheader(mainheaderForm.getId());
		mainheader.setHasCover(mainheaderForm.isHasCover());
		mainheader.setCanva(mainheaderForm.getCanva());
		mainheader.setCanvaColor(mainheaderForm.getCanvaColor());
		mainheader.setTextColor(mainheaderForm.getTextColor());
		return mainheaderRepository.save(mainheader);
	}
	
	@Override
	@Transactional
	public Mainheader updateMainheader(final MainheaderForm mainheaderForm) {
		final Mainheader mainheader = createOrUpdate(mainheaderForm);
		if(mainheaderForm.isHasLogoChanged()) {
			companyRepository.updateHasAvatarById(mainheaderForm.isHasLogo(), mainheaderForm.getId());
			if(mainheaderForm.isHasLogo()) {
				avatarService.postOrUpdate(mainheaderForm.getFiles()[0], mainheaderForm.getId(), AvatarType.company);
			} else {
				avatarService.deleteAvatar(mainheaderForm.getId(), AvatarType.company);
			}
		}
		if(mainheaderForm.isHasCoverChanged()) {
			if(mainheaderForm.isHasCover()) {
				avatarService.postOrUpdate(mainheaderForm.getFiles()[mainheaderForm.isHasLogoChanged() ? 1 : 0], mainheaderForm.getId(), AvatarType.cover);
			} else {
				avatarService.deleteAvatar(mainheaderForm.getId(), AvatarType.cover);
			}
		}
		return mainheader;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PresentationForm readPresentationForm(final Long companyId) {
		final PresentationForm presentationForm = new PresentationForm();
		final Optional<Mainoverview> uOptional = mainoverviewRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Mainoverview mainoverview = uOptional.get();
			presentationForm.setDetail(mainoverview.getPresentation() != null ? new String(mainoverview.getPresentation()) : null);
			presentationForm.setHasAvatar(mainoverview.getHasOverview());
			presentationForm.setUrlAvatar(mainoverview.getHasOverview() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.extra 
					: "/static/picts/avatars/extrawid-min.jpg");
		} else {
			presentationForm.setHasAvatar(false);
			presentationForm.setUrlAvatar("/static/picts/avatars/extrawid-min.jpg");
		}
		presentationForm.setId(companyId);
		return presentationForm;
	}
	
	@Override
	@Transactional
	public Mainoverview updatePresentation(final PresentationForm presentationForm) {
		final Optional<Mainoverview> uOptional = mainoverviewRepository.findById(presentationForm.getId());
		final Mainoverview mainoverview = uOptional.isPresent() ? uOptional.get() : new Mainoverview(presentationForm.getId());
		mainoverview.setHasOverview(presentationForm.isHasAvatar());
		mainoverview.setPresentation(presentationForm.getDetail().getBytes());
		if(presentationForm.isHasFileChanged()) {
			if(presentationForm.isHasAvatar()) {
				avatarService.postOrUpdate(presentationForm.getFile(), presentationForm.getId(), AvatarType.extra);
			} else {
				avatarService.deleteAvatar(presentationForm.getId(), AvatarType.extra);
			}
		}
		return mainoverviewRepository.save(mainoverview);
	}
	
	@Override
	@Transactional(readOnly = true)
	public MaincatalogForm readMaincatalogForm(final Long companyId) {
		final MaincatalogForm maincatalogForm = new MaincatalogForm();
		final Optional<Maincatalog> uOptional = maincatalogRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Maincatalog maincatalog = uOptional.get();
			final List<CatalogItem> items = new ArrayList<CatalogItem>(maincatalog.getItems());
			maincatalogForm.setStyle(maincatalog.getStyle());
			for (final CatalogItem item : items) {
				maincatalogForm.getIdents().add(item.getId());
				maincatalogForm.getTitles().add(item.getTitle());
				maincatalogForm.getTextsAlt().add(StringUtils.isEmpty(item.getTextAlt()) ? "-" : item.getTextAlt());
				maincatalogForm.getPhotosUUID().add(item.getPhotoUUID().toString());
			}
		} else {
			maincatalogForm.setStyle(false);
		}
		maincatalogForm.setId(companyId);
		return maincatalogForm;
	}
	
	@Transactional
	private final void clearCatalogItems(final List<Long> lines, final List<String> photosUUID) {
		if(!lines.isEmpty()) {
			catalogItemRepository.deleteLinesCatalogItem(lines);
		}
		if(!photosUUID.isEmpty()) {
			photoService.deleteAllPhoto(photosUUID);
		}
	}
	
	@Transactional
	private final Maincatalog createOrUpdateMaincatalog(final MaincatalogForm maincatalogForm) {
		final Optional<Maincatalog> uOptional = maincatalogRepository.findById(maincatalogForm.getId());
		final Maincatalog maincatalog = uOptional.isPresent() ? uOptional.get() : new Maincatalog(maincatalogForm.getId());
		maincatalog.setStyle(maincatalogForm.getStyle());
		return maincatalogRepository.save(maincatalog);
	}
	
	@Transactional
	private final CatalogItem updateCatalogItem(final Long idItem, final String title, final String textAlt) {
		final CatalogItem catalogItem = catalogItemRepository.findById(idItem).get();
		catalogItem.setTitle(title);
		catalogItem.setTextAlt(textAlt);
		return catalogItemRepository.save(catalogItem);
	}
	
	@Transactional
	private final void updateCatalogItems(final Maincatalog maincatalog, final MaincatalogForm maincatalogForm) {
		int j = 0;
		final List<CatalogItem> catalogsItem = new ArrayList<CatalogItem>();
		for(int i = 0; i < maincatalogForm.getIdents().size(); i++) {
			if(maincatalogForm.getIdents().get(i) == -1) {
				if(maincatalogForm.getUpdated().get(i) == -1) {
					final String textAlt = maincatalogForm.getTextsAlt().get(i);
					final Photo photo = photoService.addPhoto(maincatalogForm.getFiles()[j++], maincatalogForm.getId(), PhotoType.catalog);
					final CatalogItem catalogItem = new CatalogItem();
					catalogItem.setTitle(maincatalogForm.getTitles().get(i));
					catalogItem.setTextAlt(textAlt.equals("-") ? "" : textAlt);
					catalogItem.setPhotoUUID(photo.getId());
					catalogItem.setMaincatalog(maincatalog);
					catalogsItem.add(catalogItem);
				} else {
					final CatalogItem catalogItem = updateCatalogItem(maincatalogForm.getUpdated().get(i), 
							maincatalogForm.getTitles().get(i), maincatalogForm.getTextsAlt().get(i));
					photoService.updatePhoto(catalogItem.getPhotoUUID(), maincatalogForm.getFiles()[j++]);
				}
			} else if(maincatalogForm.getIdents().get(i) == 0) {
				updateCatalogItem(maincatalogForm.getUpdated().get(i), maincatalogForm.getTitles().get(i),
						maincatalogForm.getTextsAlt().get(i));
			}
		}
		if(!catalogsItem.isEmpty()) {
			catalogItemRepository.saveAll(catalogsItem);
		}
	}
	
	@Override
	@Transactional
	public Maincatalog updateMaincatalog(final MaincatalogForm maincatalogForm) {
		if(maincatalogForm.isUpdateCatalog()) {
			clearCatalogItems(maincatalogForm.getTrashed(), maincatalogForm.getPhotosUUID());
		}
		if(maincatalogForm.getTitles().isEmpty()) {
			if(maincatalogRepository.existsById(maincatalogForm.getId())) {
				maincatalogRepository.deleteById(maincatalogForm.getId());
			}
			return null;
		}
		final Maincatalog maincatalog = createOrUpdateMaincatalog(maincatalogForm);
		if(maincatalogForm.isUpdateCatalog()) {
			updateCatalogItems(maincatalog, maincatalogForm);
		}
		return maincatalog;
	}
	
	@Override
	@Transactional(readOnly = true)
	public MainsliderForm readMainsliderForm(final Long companyId) {
		final MainsliderForm mainsliderForm = new MainsliderForm();
		final Optional<Mainslider> uOptional = mainsliderRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Mainslider mainslider = uOptional.get();
			final List<SliderItem> items = new ArrayList<SliderItem>(mainslider.getItems());
			mainsliderForm.setHasAutoplay(mainslider.getHasAutoplay());
			mainsliderForm.setHasHover(mainslider.getHasHover());
			mainsliderForm.setHasNavigation(mainslider.getHasNavigation());
			mainsliderForm.setHasDots(mainslider.getHasDots());
			mainsliderForm.setSpeed(mainslider.getSpeed());
			mainsliderForm.setTimeout(mainslider.getTimeout());
			mainsliderForm.setAnimateIn(mainslider.getAnimateIn());
			mainsliderForm.setAnimateOut(mainslider.getAnimateOut());
			mainsliderForm.setAnimateFade(mainslider.getAnimateFade());
			mainsliderForm.setFadeColor(mainslider.getFadeColor());
			mainsliderForm.setTextColor(mainslider.getTextColor());
			for (final SliderItem item : items) {
				mainsliderForm.getIdents().add(item.getId());
				mainsliderForm.getTitles().add(item.getTitle());
				mainsliderForm.getDescriptions().add(item.getDescription());
				mainsliderForm.getPhotosUUID().add(item.getPhotoUUID().toString());
			}
		} else {
			mainsliderForm.setHasAutoplay(true);
			mainsliderForm.setHasHover(true);
			mainsliderForm.setHasNavigation(true);
			mainsliderForm.setHasDots(true);
			mainsliderForm.setSpeed("1.5");
			mainsliderForm.setTimeout("6");
			mainsliderForm.setAnimateIn("fadeScaleIn");
			mainsliderForm.setAnimateOut("fadeScaleOut");
			mainsliderForm.setAnimateFade("fadeIn");
			mainsliderForm.setFadeColor("#3DAC4E");
			mainsliderForm.setTextColor("#FFFFFF");
		}
		mainsliderForm.setId(companyId);
		return mainsliderForm;
	}
	
	@Transactional
	private final void clearSliderItems(final List<Long> lines, final List<String> photosUUID) {
		if(!lines.isEmpty()) {
			sliderItemRepository.deleteLinesSliderItem(lines);
		}
		if(!photosUUID.isEmpty()) {
			photoService.deleteAllPhoto(photosUUID);
		}
	}
	
	@Transactional
	private final Mainslider createOrUpdateMainslider(final MainsliderForm mainsliderForm) {
		final Optional<Mainslider> uOptional = mainsliderRepository.findById(mainsliderForm.getId());
		final Mainslider mainslider = uOptional.isPresent() ? uOptional.get() : new Mainslider(mainsliderForm.getId());
		mainslider.setHasAutoplay(mainsliderForm.getHasAutoplay());
		mainslider.setHasHover(mainsliderForm.getHasHover());
		mainslider.setHasNavigation(mainsliderForm.getHasNavigation());
		mainslider.setHasDots(mainsliderForm.getHasDots());
		mainslider.setSpeed(mainsliderForm.getSpeed());
		mainslider.setTimeout(mainsliderForm.getTimeout());
		mainslider.setAnimateIn(mainsliderForm.getAnimateIn());
		mainslider.setAnimateOut(mainsliderForm.getAnimateOut());
		mainslider.setAnimateFade(mainsliderForm.getAnimateFade());
		mainslider.setFadeColor(mainsliderForm.getFadeColor());
		mainslider.setTextColor(mainsliderForm.getTextColor());
		return mainsliderRepository.save(mainslider);
	}
	
	@Transactional
	private final SliderItem updateSliderItem(final Long idItem, final String title, final String description) {
		final SliderItem sliderItem = sliderItemRepository.findById(idItem).get();
		sliderItem.setTitle(title);
		sliderItem.setDescription(description);
		return sliderItemRepository.save(sliderItem);
	}
	
	@Transactional
	private final void updateSliderItems(final Mainslider mainslider, final MainsliderForm mainsliderForm) {
		int j = 0;
		final List<SliderItem> slidersItem = new ArrayList<SliderItem>();
		for(int i = 0; i < mainsliderForm.getIdents().size(); i++) {
			if(mainsliderForm.getIdents().get(i) == -1) {
				if(mainsliderForm.getUpdated().get(i) == -1) {
					final Photo photo = photoService.addPhoto(mainsliderForm.getFiles()[j++], mainsliderForm.getId(), PhotoType.slider);
					final SliderItem sliderItem = new SliderItem();
					sliderItem.setTitle(mainsliderForm.getTitles().get(i));
					sliderItem.setDescription(mainsliderForm.getDescriptions().get(i));
					sliderItem.setPhotoUUID(photo.getId());
					sliderItem.setMainslider(mainslider);
					slidersItem.add(sliderItem);
				} else {
					final SliderItem sliderItem = updateSliderItem(mainsliderForm.getUpdated().get(i), 
							mainsliderForm.getTitles().get(i), mainsliderForm.getDescriptions().get(i));
					photoService.updatePhoto(sliderItem.getPhotoUUID(), mainsliderForm.getFiles()[j++]);
				}
			} else if(mainsliderForm.getIdents().get(i) == 0) {
				updateSliderItem(mainsliderForm.getUpdated().get(i), mainsliderForm.getTitles().get(i), 
						mainsliderForm.getDescriptions().get(i));
			}
		}
		if(!slidersItem.isEmpty()) {
			sliderItemRepository.saveAll(slidersItem);
		}
	}
	
	@Override
	@Transactional
	public Mainslider updateMainslider(final MainsliderForm mainsliderForm) {
		if(mainsliderForm.isUpdateSlider()) {
			clearSliderItems(mainsliderForm.getTrashed(), mainsliderForm.getPhotosUUID());
		}
		if(mainsliderForm.getTitles().isEmpty()) {
			if(mainsliderRepository.existsById(mainsliderForm.getId())) {
				mainsliderRepository.deleteById(mainsliderForm.getId());
			}
			return null;
		}
		final Mainslider mainslider = createOrUpdateMainslider(mainsliderForm);
		if(mainsliderForm.isUpdateSlider()) {
			updateSliderItems(mainslider, mainsliderForm);
		}
		return mainslider;
	}
	
	@Transactional(readOnly = true)
	private final String getHistory(final Long companyId) {
		final Optional<String> uOptional = mainoverviewRepository.findHistoryByCompanyId(companyId);
		return uOptional.isPresent() ? uOptional.get() : "";
	}
	
	@Override
	@Transactional(readOnly = true)
	public TimelineForm readTimelineForm(final Long companyId) {
		final TimelineForm timelineForm = new TimelineForm();
		final List<Timeline> timesLine = timelineRepository.findAllTimeline(companyId);
		for (final Timeline timeline : timesLine) {
			timelineForm.getIdents().add(timeline.getId());
			timelineForm.getLinesDate().add(DateTimeFormat.forPattern("dd/MM/yyyy").print(timeline.getLineDate()));
			timelineForm.getTitles().add(timeline.getTitle());
			timelineForm.getDescriptions().add(timeline.getDescription());
		}
		timelineForm.setHistory(getHistory(companyId));
		timelineForm.setId(companyId);
		return timelineForm;
	}
	
	@Transactional
	private final Mainoverview updateHistory(final Long companyId, final String history) {
		final Optional<Mainoverview> uOptional = mainoverviewRepository.findById(companyId);
		final Mainoverview mainoverview = uOptional.isPresent() ? uOptional.get() : new Mainoverview(companyId);
		mainoverview.setHistory(history);
		return mainoverviewRepository.save(mainoverview);
	}
	
	@Transactional
	private final Timeline updateTimelineItem(final Long idItem, final DateTime lineDate, final String title, final String description) {
		final Timeline timeline = timelineRepository.findById(idItem).get();
		timeline.setLineDate(lineDate);
		timeline.setTitle(title);
		timeline.setDescription(description);
		return timelineRepository.save(timeline);
	}
	
	@Transactional
	private final void updateTimelineItems(final TimelineForm timelineForm) {
		final List<Timeline> timesLine = new ArrayList<Timeline>();
		for(int i = 0; i < timelineForm.getIdents().size(); i++) {
			if(timelineForm.getIdents().get(i) == -1) {
				if(timelineForm.getUpdated().get(i) == -1) {
					final Timeline timeline = new Timeline();
					timeline.setCompanyId(timelineForm.getId());
					timeline.setLineDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(timelineForm.getLinesDate().get(i)));
					timeline.setTitle(timelineForm.getTitles().get(i));
					timeline.setDescription(timelineForm.getDescriptions().get(i));
					timesLine.add(timeline);
				} else {
					updateTimelineItem(timelineForm.getUpdated().get(i), 
							DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(timelineForm.getLinesDate().get(i)), 
							timelineForm.getTitles().get(i), timelineForm.getDescriptions().get(i));
				}
			}
		}
		if(!timesLine.isEmpty()) {
			timelineRepository.saveAll(timesLine);
		}
	}
	
	@Override
	@Transactional
	public void updateTimeline(final TimelineForm timelineForm) {
		updateHistory(timelineForm.getId(), timelineForm.getHistory());
		if(timelineForm.isUpdateTimeline()) {
			if(!timelineForm.getTrashed().isEmpty()) {
				timelineRepository.deleteLinesTimeline(timelineForm.getTrashed());
			}
			if(!timelineForm.getTitles().isEmpty()) {
				updateTimelineItems(timelineForm);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public MainaboutForm readMainaboutForm(final Long companyId) {
		final MainaboutForm mainaboutForm = new MainaboutForm();
		final Optional<Mainabout> uOptional = mainaboutRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Mainabout mainabout = uOptional.get();
			mainaboutForm.setName(mainabout.getName());
			mainaboutForm.setFunction(mainabout.getFunction());
			mainaboutForm.setWord(mainabout.getWord());
			mainaboutForm.setSize(mainabout.getSize());
			mainaboutForm.setHasAvatar(mainabout.getHasCover());
		} else {
			mainaboutForm.setHasAvatar(false);
			mainaboutForm.setSize(2);
		}
		mainaboutForm.setId(companyId);
		mainaboutForm.setUrlAvatar(mainaboutForm.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.about
				: "/static/picts/avatars/about-min.jpg");
		return mainaboutForm;
	}
	
	@Override
	@Transactional
	public Mainabout updateMainabout(final MainaboutForm mainaboutForm) {
		final Optional<Mainabout> uOptional = mainaboutRepository.findById(mainaboutForm.getId());
		final Mainabout mainabout = uOptional.isPresent() ? uOptional.get() : new Mainabout(mainaboutForm.getId());
		if(mainaboutForm.isHasFileChanged()) {
			if(mainaboutForm.isHasAvatar()) {
				avatarService.postOrUpdate(mainaboutForm.getFile(), mainaboutForm.getId(), AvatarType.about);
			} else {
				avatarService.deleteAvatar(mainaboutForm.getId(), AvatarType.about);
			}
		}
		mainabout.setName(mainaboutForm.getName());
		mainabout.setFunction(mainaboutForm.getFunction());
		mainabout.setWord(mainaboutForm.getWord());
		mainabout.setSize(mainaboutForm.getSize());
		mainabout.setHasCover(mainaboutForm.isHasAvatar());
		return mainaboutRepository.save(mainabout);
	}
	
	@Override
	@Transactional(readOnly = true)
	public MainthinkForm readMainthinkForm(final Long companyId) {
		final MainthinkForm mainthinkForm = new MainthinkForm();
		final Optional<Mainthink> uOptional = mainthinkRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Mainthink mainthink = uOptional.get();
			final List<ThinkItem> items = new ArrayList<ThinkItem>(mainthink.getItems());
			for (final ThinkItem item : items) {
				mainthinkForm.getIdents().add(item.getId());
				mainthinkForm.getThinks().add(item.getThink());
				mainthinkForm.getTitles().add(item.getTitle());
				mainthinkForm.getDescriptions().add(item.getDescription());
				mainthinkForm.getPhotosUUID().add(item.getPhotoUUID().toString());
			}
		}
		mainthinkForm.setId(companyId);
		return mainthinkForm;
	}
	
	@Transactional
	private final void clearThinkItems(final List<Long> lines, final List<String> photosUUID) {
		if(!lines.isEmpty()) {
			thinkItemRepository.deleteLinesThinkItem(lines);
		}
		if(!photosUUID.isEmpty()) {
			photoService.deleteAllPhoto(photosUUID);
		}
	}
	
	@Transactional
	private final Mainthink createOrUpdateMainthink(final Long companyId) {
		final Optional<Mainthink> uOptional = mainthinkRepository.findById(companyId);
		if(uOptional.isPresent()) {
			return uOptional.get();
		}
		final Mainthink mainthink = new Mainthink(companyId);
		return mainthinkRepository.save(mainthink);
	}
	
	@Transactional
	private final ThinkItem updateThinkItem(final Long idItem, final String think, final String title, final String description) {
		final ThinkItem thinkItem = thinkItemRepository.findById(idItem).get();
		thinkItem.setThink(think);
		thinkItem.setTitle(title);
		thinkItem.setDescription(!StringUtils.isEmpty(description) ? description : null);
		return thinkItemRepository.save(thinkItem);
	}
	
	@Transactional
	private final void updateThinkItems(final Mainthink mainthink, final MainthinkForm mainthinkForm) {
		int j = 0;
		final List<ThinkItem> thinksItem = new ArrayList<ThinkItem>();
		for(int i = 0; i < mainthinkForm.getIdents().size(); i++) {
			if(mainthinkForm.getIdents().get(i) == -1) {
				if(mainthinkForm.getUpdated().get(i) == -1) {
					final String description = mainthinkForm.getDescriptions().get(i);
					final Photo photo = photoService.addPhoto(mainthinkForm.getFiles()[j++], mainthinkForm.getId(), PhotoType.think);
					final ThinkItem thinkItem = new ThinkItem();
					thinkItem.setThink(mainthinkForm.getThinks().get(i));
					thinkItem.setTitle(mainthinkForm.getTitles().get(i));
					thinkItem.setDescription(!StringUtils.isEmpty(description) ? description : null);
					thinkItem.setPhotoUUID(photo.getId());
					thinkItem.setMainthink(mainthink);
					thinksItem.add(thinkItem);
				} else {
					final ThinkItem thinkItem = updateThinkItem(mainthinkForm.getUpdated().get(i), mainthinkForm.getThinks().get(i), 
							mainthinkForm.getTitles().get(i), mainthinkForm.getDescriptions().get(i));
					photoService.updatePhoto(thinkItem.getPhotoUUID(), mainthinkForm.getFiles()[j++]);
				}
			} else if(mainthinkForm.getIdents().get(i) == 0) {
				updateThinkItem(mainthinkForm.getUpdated().get(i), mainthinkForm.getThinks().get(i), mainthinkForm.getTitles().get(i), 
						mainthinkForm.getDescriptions().get(i));
			}
		}
		if(!thinksItem.isEmpty()) {
			thinkItemRepository.saveAll(thinksItem);
		}
	}
	
	@Override
	@Transactional
	public Mainthink updateMainthink(final MainthinkForm mainthinkForm) {
		if(mainthinkForm.isUpdateThink()) {
			clearThinkItems(mainthinkForm.getTrashed(), mainthinkForm.getPhotosUUID());
		}
		if(mainthinkForm.getThinks().isEmpty()) {
			if(mainthinkRepository.existsById(mainthinkForm.getId())) {
				mainthinkRepository.deleteById(mainthinkForm.getId());
			}
			return null;
		}
		final Mainthink mainthink = createOrUpdateMainthink(mainthinkForm.getId());
		if(mainthinkForm.isUpdateThink()) {
			updateThinkItems(mainthink, mainthinkForm);
		}
		return mainthink;
	}
	
}
