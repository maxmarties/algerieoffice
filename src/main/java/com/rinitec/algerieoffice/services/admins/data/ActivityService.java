package com.rinitec.algerieoffice.services.admins.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.admins.datas.ActivityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

@Service
public class ActivityService implements IActivityService {

	private ActivityRepository activityRepository;
	
	@Autowired
	public ActivityService(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsById(final Long id) {
		return activityRepository.existsById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByURL(final String url) {
		return activityRepository.existsByUrl(url);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ActivityForm readActivity(final Long id) {
		final Activity activity = activityRepository.findById(id).get();
		return new ActivityForm(activity);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<List<String>> findAllChoseActivity() {
		final List<List<String>> choseActivities = activityRepository.findAllChoseActivityCriteria();
		return choseActivities;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findActivityList(final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = activityRepository.countAllActivityCriteria(filter, search);
		final List<Activity> lines = countResult == 0L ? new ArrayList<Activity>() 
				: activityRepository.findAllActivityCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional
	private final Activity saveActivity(final Activity activity, final ActivityForm activityForm) {
		activity.setCode(activityForm.getCode());
		activity.setSector(activityForm.getSector());
		activity.setUrl(activityForm.getUrl());
		return activityRepository.save(activity);
	}
	
	@Override
	@Transactional
	public Activity addActivity(final ActivityForm activityForm) {
		if(activityRepository.existsByCode(activityForm.getCode())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(activityRepository.existsByUrl(activityForm.getUrl())) {
			throw new UrlUnavailableException("message.error.url");
		}
		return saveActivity(new Activity(), activityForm);
	}
	
	@Override
	@Transactional
	public Activity updateActivity(final ActivityForm activityForm) {
		final Activity activity = activityRepository.findById(activityForm.getId()).get();
		if(!activity.getCode().equals(activityForm.getCode()) 
				&& activityRepository.existsByCode(activityForm.getCode())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(!activity.getUrl().equalsIgnoreCase(activityForm.getUrl()) 
				&& activityRepository.existsByUrl(activityForm.getUrl())) {
			throw new UrlUnavailableException("message.error.url");
		}
		return saveActivity(activity, activityForm);
	}
	
	@Override
	@Transactional
	public Activity deleteActivity(final Long id) {
		final Optional<Activity> uOptional = activityRepository.findById(id);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Activity activity = uOptional.get();
		//TODO TRASH COMPANY ACTIVITY
		activityRepository.delete(activity);
		return activity;
	}
	
	@Override
	@Transactional
	public void deleteActivities(final List<Long> lines) {
		if(lines.isEmpty() || lines.size() != activityRepository.countActivities(lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		//TODO TRASH ALL COMPANY ACTIVITY
		activityRepository.deleteActivities(lines);
	}
	
}
