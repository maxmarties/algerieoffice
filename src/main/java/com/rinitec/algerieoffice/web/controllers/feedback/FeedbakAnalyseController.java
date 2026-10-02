package com.rinitec.algerieoffice.web.controllers.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.services.analytic.IAnalyseService;
import com.rinitec.algerieoffice.utils.ParseUtil;

@Controller
@RequestMapping(value = "/feedback/analyse")
public class FeedbakAnalyseController {

	private IAnalyseService analyseService;
	
	@Autowired
	public FeedbakAnalyseController(IAnalyseService analyseService) {
		this.analyseService = analyseService;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-type", method = RequestMethod.GET)
	public String readCompaniesTypeForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartType", analyseService.readChartCompaniesType(sector, wilaya));
		return "analyticCompaniesType";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-agent", method = RequestMethod.GET)
	public String readCompaniesAgentForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartAgent", analyseService.readChartCompaniesAgent(sector, wilaya));
		return "analyticCompaniesAgent";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-capital", method = RequestMethod.GET)
	public String readCompaniesCapitalForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartCapital", analyseService.readChartCompaniesCapital(sector, wilaya));
		return "analyticCompaniesCapital";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-build", method = RequestMethod.GET)
	public String readCompaniesBuildForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartBuild", analyseService.readChartCompaniesBuild(sector, wilaya));
		model.addAttribute("yearsBuild", ParseUtil.getLastYearsToString(5));
		return "analyticCompaniesBuild";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-briefcase", method = RequestMethod.GET)
	public String readCompaniesBriefcaseForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartBriefcase", analyseService.readChartCompaniesBriefcase(sector, wilaya));
		return "analyticCompaniesBriefcase";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-month", method = RequestMethod.GET)
	public String readCompaniesMonthForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartMonth", analyseService.readChartCompaniesMonth(sector, wilaya));
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		return "analyticCompaniesMonth";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-warehouse", method = RequestMethod.GET)
	public String readCompaniesWarehouseForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartWarehouse", analyseService.readChartCompaniesWarhouse(sector, wilaya));
		return "analyticCompaniesWarehouse";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-effectif", method = RequestMethod.GET)
	public String readCompaniesEffectifForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartEffectif", analyseService.readChartCompaniesEffectif(sector, wilaya));
		return "analyticCompaniesEffectif";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-strict", method = RequestMethod.GET)
	public String readCompaniesStrictForSector(final Model model, @RequestParam("sector") final Integer sector, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartStrict", analyseService.readChartCompaniesStrict(sector, wilaya));
		return "analyticCompaniesStrict";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param sector
	 * @return
	 */
	@RequestMapping(value = "/sectors/companies-region", method = RequestMethod.GET)
	public String readCompaniesRegionForSector(final Model model, @RequestParam("sector") final Integer sector) {
		model.addAttribute("chartRegion", analyseService.readChartCompaniesRegion(sector));
		return "analyticCompaniesRegion";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-type", method = RequestMethod.GET)
	public String readCompaniesTypeForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartType", analyseService.readChartCompaniesType(code, wilaya));
		return "analyticCompaniesType";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-agent", method = RequestMethod.GET)
	public String readCompaniesAgentForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartAgent", analyseService.readChartCompaniesAgent(code, wilaya));
		return "analyticCompaniesAgent";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-capital", method = RequestMethod.GET)
	public String readCompaniesCapitalForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartCapital", analyseService.readChartCompaniesCapital(code, wilaya));
		return "analyticCompaniesCapital";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-build", method = RequestMethod.GET)
	public String readCompaniesBuildForActivity(final Model model, @RequestParam("code") final String code,
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartBuild", analyseService.readChartCompaniesBuild(code, wilaya));
		model.addAttribute("yearsBuild", ParseUtil.getLastYearsToString(5));
		return "analyticCompaniesBuild";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-briefcase", method = RequestMethod.GET)
	public String readCompaniesBriefcaseForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartBriefcase", analyseService.readChartCompaniesBriefcase(code, wilaya));
		return "analyticCompaniesBriefcase";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-month", method = RequestMethod.GET)
	public String readCompaniesMonthForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartMonth", analyseService.readChartCompaniesMonth(code, wilaya));
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		return "analyticCompaniesMonth";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-warehouse", method = RequestMethod.GET)
	public String readCompaniesWarehouseForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartWarehouse", analyseService.readChartCompaniesWarhouse(code, wilaya));
		return "analyticCompaniesWarehouse";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-effectif", method = RequestMethod.GET)
	public String readCompaniesEffectifForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartEffectif", analyseService.readChartCompaniesEffectif(code, wilaya));
		return "analyticCompaniesEffectif";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-strict", method = RequestMethod.GET)
	public String readCompaniesStrictForActivity(final Model model, @RequestParam("code") final String code, 
			@RequestParam("wilaya") final Integer wilaya) {
		model.addAttribute("chartStrict", analyseService.readChartCompaniesStrict(code, wilaya));
		return "analyticCompaniesStrict";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/activities/companies-region", method = RequestMethod.GET)
	public String readCompaniesRegionForActivity(final Model model, @RequestParam("code") final String code) {
		model.addAttribute("chartRegion", analyseService.readChartCompaniesRegion(code));
		return "analyticCompaniesRegion";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/companies-detail", method = RequestMethod.GET)
	public String readCompaniesDetail(final Model model) {
		model.addAttribute("chartDetail", analyseService.readChartCompaniesDetail());
		return "analyticCompaniesDetail";
	}
	
}
