package com.rinitec.algerieoffice.services.analytic;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesCapital;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesDetail;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesType;

@Service
public class AnalyseService implements IAnalyseService {

	private AgentRepository agentRepository;
	private CompanyRepository companyRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private CompanySheduleRepository companySheduleRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private CompanyLocationRepository companyLocationRepository;
	
	@Autowired
	public AnalyseService(AgentRepository agentRepository, CompanyRepository companyRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository, CompanySheduleRepository companySheduleRepository, 
			CompanyLinkedRepository companyLinkedRepository, CompanyLocationRepository companyLocationRepository) {
		this.agentRepository = agentRepository;
		this.companyRepository = companyRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.companyLocationRepository = companyLocationRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesType(final Integer sector, final Integer wilaya) {
		final Long[] countTypes = new Long[4];
		for (int i = 0; i < 4; i++) {
			countTypes[i] = companyBriefcaseRepository.countByTypeForSector(sector, wilaya, i + 1);
		}
		return new ChartCompaniesType(countTypes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesAgent(final Integer sector, final Integer wilaya) {
		final Long[] countSexes = new Long[2];
		for (int i = 0; i < 2; i++) {
			countSexes[i] = agentRepository.countAgentsForSector(sector, wilaya, i == 0);
		}
		return new ChartCompaniesType(countSexes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesCapital readChartCompaniesCapital(final Integer sector, final Integer wilaya) {
		return companyBriefcaseRepository.readChartCompaniesCapital(sector, wilaya);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesBuild(final Integer sector, final Integer wilaya) {
		int year = ParseUtil.getCurrYear() - 4;
		final Long[] countBuilds = new Long[5];
		for (int i = 0; i < 5; i++) {
			final DateTime begin = ParseUtil.getBeginYearDate(year);
			final DateTime end = ParseUtil.getEndYearDate(year++);
			countBuilds[i] = companyRepository.countCompaniesByBuildForSector(sector, wilaya, begin, end);
		}
		return new ChartCompaniesType(countBuilds);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesBriefcase(final Integer sector, final Integer wilaya) {
		final Long[] countBriefcases = new Long[10];
		final long countCompanies = companyRepository.countCompaniesForSector(sector, wilaya);
		if(countCompanies > 0L) {
			for (int i = 0; i < 10; i++) {
				countBriefcases[i] = companyBriefcaseRepository.countByBriefcaseForSector(sector, wilaya, i + 1);
			}
		}
		return new ChartCompaniesType(countBriefcases);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesMonth(final Integer sector, final Integer wilaya) {
		final Long[] countMonth = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countMonth[i] = companyRepository.countCompaniesByBuildForSector(sector, wilaya, begin, end);
		}
		return new ChartCompaniesType(countMonth);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesWarhouse(final Integer sector, final Integer wilaya) {
		final Long[] countWarhouses = new Long[2];
		for (int i = 0; i < 2; i++) {
			countWarhouses[i] = companyBriefcaseRepository.countByWarehouseForSector(sector, wilaya, i != 0);
		}
		return new ChartCompaniesType(countWarhouses);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesEffectif(final Integer sector, final Integer wilaya) {
		final Long[] countEffectifs = new Long[2];
		for (int i = 0; i < 2; i++) {
			countEffectifs[i] = agentRepository.countAgentsForSector(sector, wilaya, i == 0);
		}
		return new ChartCompaniesType(countEffectifs);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesStrict(final Integer sector, final Integer wilaya) {
		final Long[] countStrict = new Long[3];
		countStrict[0] = companyRepository.countCompaniesForSector(sector, wilaya);
		if(countStrict[0] > 0L) {
			countStrict[2] = companyBriefcaseRepository.countByWarehouseForSector(sector, wilaya, true);
			countStrict[1] = countStrict[0] - countStrict[2];
		}
		return new ChartCompaniesType(countStrict);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesRegion(final Integer sector) {
		final Long[] countRegions = new Long[4];
		for (int i = 0; i < 4; i++) {
			countRegions[i] = companyRepository.countCompaniesByRegionForSector(sector, i + 1);
		}
		return new ChartCompaniesType(countRegions);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesType(final String code, final Integer wilaya) {
		final Long[] countTypes = new Long[4];
		for (int i = 0; i < 4; i++) {
			countTypes[i] = companyBriefcaseRepository.countByTypeForActivity(code, wilaya, i + 1);
		}
		return new ChartCompaniesType(countTypes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesAgent(final String code, final Integer wilaya) {
		final Long[] countSexes = new Long[2];
		for (int i = 0; i < 2; i++) {
			countSexes[i] = agentRepository.countAgentsForActivity(code, wilaya, i == 0);
		}
		return new ChartCompaniesType(countSexes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesCapital readChartCompaniesCapital(final String code, final Integer wilaya) {
		return companyBriefcaseRepository.readChartCompaniesCapital(code, wilaya);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesBuild(final String code, final Integer wilaya) {
		int year = ParseUtil.getCurrYear() - 4;
		final Long[] countBuilds = new Long[5];
		for (int i = 0; i < 5; i++) {
			final DateTime begin = ParseUtil.getBeginYearDate(year);
			final DateTime end = ParseUtil.getEndYearDate(year++);
			countBuilds[i] = companyRepository.countCompaniesByBuildForActivity(code, wilaya, begin, end);
		}
		return new ChartCompaniesType(countBuilds);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesBriefcase(final String code, final Integer wilaya) {
		final Long[] countBriefcases = new Long[10];
		final long countCompanies = companyRepository.countCompaniesForActivity(code, wilaya);
		if(countCompanies > 0L) {
			for (int i = 0; i < 10; i++) {
				countBriefcases[i] = companyBriefcaseRepository.countByBriefcaseForActivity(code, wilaya, i + 1);
			}
		}
		return new ChartCompaniesType(countBriefcases);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesMonth(final String code, final Integer wilaya) {
		final Long[] countMonth = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countMonth[i] = companyRepository.countCompaniesByBuildForActivity(code, wilaya, begin, end);
		}
		return new ChartCompaniesType(countMonth);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesWarhouse(final String code, final Integer wilaya) {
		final Long[] countWarhouses = new Long[2];
		for (int i = 0; i < 2; i++) {
			countWarhouses[i] = companyBriefcaseRepository.countByWarehouseForActivity(code, wilaya, i != 0);
		}
		return new ChartCompaniesType(countWarhouses);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesEffectif(final String code, final Integer wilaya) {
		final Long[] countEffectifs = new Long[2];
		for (int i = 0; i < 2; i++) {
			countEffectifs[i] = agentRepository.countAgentsForActivity(code, wilaya, i == 0);
		}
		return new ChartCompaniesType(countEffectifs);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesStrict(final String code, final Integer wilaya) {
		final Long[] countStrict = new Long[3];
		countStrict[0] = companyRepository.countCompaniesForActivity(code, wilaya);
		if(countStrict[0] > 0L) {
			countStrict[2] = companyBriefcaseRepository.countByWarehouseForActivity(code, wilaya, true);
			countStrict[1] = countStrict[0] - countStrict[2];
		}
		return new ChartCompaniesType(countStrict);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesType readChartCompaniesRegion(final String code) {
		final Long[] countRegions = new Long[4];
		for (int i = 0; i < 4; i++) {
			countRegions[i] = companyRepository.countCompaniesByRegionForActivity(code, i + 1);
		}
		return new ChartCompaniesType(countRegions);
	}
	
	@Transactional(readOnly = true)
	private final Long[] readCountContact() {
		final Long[] countContact = new Long[4];
		final Long countComapnies = companyRepository.countCompaniesForSector(null, null);
		countContact[0] = countComapnies;
		countContact[1] = countComapnies;
		countContact[2] = companySheduleRepository.countMobile();
		countContact[3] = companySheduleRepository.countFax();
		return countContact;
	}
	
	@Transactional(readOnly = true)
	private final Long[] readCountDigital() {
		final Long[] countDigital = new Long[2];
		countDigital[0] = companyLinkedRepository.countWebsite();
		countDigital[1] = companyLocationRepository.countCarte();
		return countDigital;
	}
	
	@Transactional(readOnly = true)
	private final Long[] readCountSocial() {
		final Long[] countSocial = new Long[6];
		final String[] providers = {"facebook", "twitter", "linkedin", "youtube", "google", "instagram"};
		for (int i = 0; i < 6; i++) {
			countSocial[i] = companyLinkedRepository.countSocial(providers[i]);
		}
		return countSocial;
	}
	
	@Transactional(readOnly = true)
	private final Long[] readCountBuild() {
		final Long[] countBuild = new Long[10];
		int year = ParseUtil.getCurrYear() - 9;
		for (int i = 0; i < 10; i++) {
			final DateTime begin = ParseUtil.getBeginYearDate(year);
			final DateTime end = ParseUtil.getEndYearDate(year++);
			countBuild[i] = companyRepository.countCompaniesByBuildForActivity(null, null, begin, end);
		}
		return countBuild;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ChartCompaniesDetail readChartCompaniesDetail() {
		final Long[] countContact = readCountContact();
		final Long[] countDigital = readCountDigital();
		final Long[] countSocial = readCountSocial();
		final Long[] countBuild = readCountBuild();
		return new ChartCompaniesDetail(countContact, countDigital, countSocial, countBuild);
	}
	
}
