package com.rinitec.algerieoffice.services.publics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.sectors.ActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorLink;

@Service
public class SectorService implements ISectorService {

	private CompanyRepository companyRepository;
	private ActivityRepository activityRepository;
	
	@Autowired
	public SectorService(CompanyRepository companyRepository, ActivityRepository activityRepository) {
		this.companyRepository = companyRepository;
		this.activityRepository = activityRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ActivityLink> findActivityLink() {
		return activityRepository.findActivityLinkCriteria();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SectorLink> findSectorLinkList(final int sector) {
		return activityRepository.findSectorLinkCriteria(sector);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Activity findActivityByURL(String url) {
		return activityRepository.findByUrl(url);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SectorActivityLink> findWilayaLinkList() {
		final List<SectorActivityLink> lines = new ArrayList<SectorActivityLink>();
		for (int i = 1; i <= 48; i++) {
			final String wilayaURL = ConstraintesURL.getWilayaLinkURL(i);
			final long count = companyRepository.countCompaniesForSector(null, i);
			lines.add(new SectorActivityLink(wilayaURL, count));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SectorActivityLink> findSectorActivityLinkList(final Activity activity) {
		final List<SectorActivityLink> lines = new ArrayList<SectorActivityLink>();
		for (int i = 1; i <= 48; i++) {
			final String wilayaURL = ConstraintesURL.getSectorActivityWilayaURL(activity.getUrl(), i);
			final long count = companyRepository.countCompaniesForActivity(activity.getCode(), i);
			lines.add(new SectorActivityLink(wilayaURL, count));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SectorActivityLink> findWilayaSectorLinkList(final int wilaya) {
		final List<SectorActivityLink> lines = new ArrayList<SectorActivityLink>();
		for (int i = 1; i <= ConstraintesForm.COUNT_SECTOR_ACTIITY; i++) {
			final String sectorURL = ConstraintesURL.getWilayaSectorURL(wilaya, i);
			final long count = companyRepository.countCompaniesForSector(i, wilaya);
			lines.add(new SectorActivityLink(sectorURL, count));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SectorLink> findWilayaActivityLinkList(final int wilaya, final int sector) {
		final List<SectorLink> lines = new ArrayList<SectorLink>();
		final String sectorURL = ConstraintesURL.getWilayaSectorURL(wilaya, sector);
		final List<Tuple> tuples = activityRepository.findWilayaActivityLinkCriteria(wilaya, sector);
		for (final Tuple tuple : tuples) {
			lines.add(new SectorLink((String) tuple.get(0), (String) tuple.get(1), (Long) tuple.get(2), sectorURL));
		}
		return lines;
	}
	
}
