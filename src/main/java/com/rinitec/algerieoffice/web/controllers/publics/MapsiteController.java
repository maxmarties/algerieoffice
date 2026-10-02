package com.rinitec.algerieoffice.web.controllers.publics;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.services.mapsite.IMapsiteService;

@Controller
@RequestMapping(value = "/mapsite")
public class MapsiteController {

	private IMapsiteService mapsiteService;
	
	@Autowired
	public MapsiteController(IMapsiteService mapsiteService) {
		this.mapsiteService = mapsiteService;
	}
	
	private final void parseMapsiteInResponse(final String mapsite, final HttpServletResponse response) throws IOException {
		if(!StringUtils.isEmpty(mapsite)) {
			response.setContentType(MediaType.APPLICATION_XML_VALUE);
			response.getWriter().print(mapsite);
			response.getWriter().flush();
		}
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/static.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getStaticMapsite(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateStaticMapsite();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/entreprises.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getCompaniesIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateCompaniesIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/produits.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getPostsIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generatePostsIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/annonces.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getAnnoncesIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateAnnoncesIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/evenements.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getEventsIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateEventsIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getEmployesIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateEmployesIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/actualites.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getActualitiesIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateActualitiesIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * >> PARSE IN WEBMASTER CONSOLE
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/blogs.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getBlogsIndex(final HttpServletResponse response) {
		try {
			final String mapsite = mapsiteService.generateBlogIndex();
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/entreprises/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getCompaniesMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateCompaniesMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/produits/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getPostsMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generatePostsMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/annonces/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getAnnoncesMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateAnnoncesMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/evenements/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getEventsMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateEventsMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getEmployesMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateEmployesMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/actualites/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getActualitiesMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateActualitiesMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/blogs/{page}/index.xml", produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public String getBlogsMapsite(final HttpServletResponse response, @PathVariable("page") final Integer page) {
		try {
			final String mapsite = mapsiteService.generateBlogMapsite(page);
			parseMapsiteInResponse(mapsite, response);
		} catch (IOException e) {}
		return null;
	}
	
}
