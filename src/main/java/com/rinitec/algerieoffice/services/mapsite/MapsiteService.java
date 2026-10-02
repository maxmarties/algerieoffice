package com.rinitec.algerieoffice.services.mapsite;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.SitemapIndexUrl;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.mapsite.ActualityMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.AnnonceMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.BlogMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.CompanyMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.DocumentMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.EmployeMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.EventMapsite;
import com.rinitec.algerieoffice.web.modal.mapsite.PostMapsite;

@Service
public class MapsiteService implements IMapsiteService {
	private static final String[] STATICS_URL = {"entreprises", "marketplace", "solutions", "secteurs", "villes", "blog", "membres", "contacts"};
	private static final String[] SOLUTIONS_URL = {"presentation", "visibilite", "referencement", "detect", "easylist", "publicite"};
	private static final String[] INFOS_URL = {"faq", "cgu", "politique-confidentialite", "mentions-legales", "plan-du-site", "charte-bonnes-pratiques", "credits", "temoignages-clients"};
	private static final String[] COMPANIES_URL = {"presentation", "historique", "actualites", "evenements", "realisations", "faqs", "partenaires", "produits-et-services", 
			"marketplace", "offres-emploi", "contact"};
	
	private HttpServletRequest request;
	private CompanyRepository companyRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private ActualityRepository actualityRepository;
	private BlogRepository blogRepository;
	
	@Autowired
	public MapsiteService(HttpServletRequest request, CompanyRepository companyRepository, PostRepository postRepository, AnnonceRepository annonceRepository, 
			EventRepository eventRepository, EmployeRepository employeRepository, ActualityRepository actualityRepository, BlogRepository blogRepository) {
		this.request = request;
		this.companyRepository = companyRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.actualityRepository = actualityRepository;
		this.blogRepository = blogRepository;
	}
	
	@Override
	public String generateStaticMapsite() throws MalformedURLException {
		final Date beginDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(ConstraintesURL.MAPSITE_DATE_APP).toDate();
		final WebSitemapGenerator sitemap = new WebSitemapGenerator(ConstraintesURL.URL_APPLICATION);
		final WebSitemapUrl appURL = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION).lastMod(beginDate).priority(1.0).changeFreq(ChangeFreq.YEARLY).build();
		sitemap.addUrl(appURL);
		for (final String staticURL : STATICS_URL) {
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat("/").concat(staticURL)).lastMod(beginDate)
					.priority(0.6).changeFreq(ChangeFreq.YEARLY).build();
			sitemap.addUrl(url);
		}
		for (final String solutionURL : SOLUTIONS_URL) {
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat(ConstraintesURL.URL_SOLUTIONS).concat("/").concat(solutionURL))
					.lastMod(beginDate).priority(0.6).changeFreq(ChangeFreq.YEARLY).build();
			sitemap.addUrl(url);
		}
		for (final String infoURL : INFOS_URL) {
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat(ConstraintesURL.URL_INFOS).concat("/").concat(infoURL))
					.lastMod(beginDate).priority(0.6).changeFreq(ChangeFreq.YEARLY).build();
			sitemap.addUrl(url);
		}
		for (final String sectorURL : ConstraintesURL.URL_SECTORS) {
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat(ConstraintesURL.URL_SECTOR).concat("/").concat(sectorURL))
					.lastMod(beginDate).priority(0.6).changeFreq(ChangeFreq.MONTHLY).build();
			sitemap.addUrl(url);
		}
		for (final String wilayaURL : ConstraintesURL.URL_WILAYAS) {
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat(ConstraintesURL.URL_WILAYA).concat("/").concat(wilayaURL))
					.lastMod(beginDate).priority(0.6).changeFreq(ChangeFreq.MONTHLY).build();
			sitemap.addUrl(url);
		}
		return String.join("", sitemap.writeAsStrings());
	}
	
	private final int parseCountPagination(final Long count) {
		return count == 0L ? 0 : (int) ((count / ConstraintesForm.LIMIT_MAPSITE) + (count % ConstraintesForm.LIMIT_MAPSITE != 0 ? 1 : 0));
	}
	
	private final File parseOutFile(final String filename) {
		return new File(request.getServletContext().getRealPath(FilesUtil.MAPSITE_DIR).concat(File.separator).concat(filename).concat("_index.xml"));
	}
	
	private final String parseMapsiteIndex(final int pagination, final String filename, final String url) throws MalformedURLException {
		if(pagination == 0) return null;
		final Date nowDate = new DateTime(Date.from(Instant.now())).toDate();
		final SitemapIndexGenerator sitemap = new SitemapIndexGenerator(ConstraintesURL.URL_APPLICATION, parseOutFile(filename));
		for (int i = 0; i < pagination; i++) {
			final String indexURL = ConstraintesURL.URL_APPLICATION.concat(url).concat(String.valueOf(i + 1)).concat("/index.xml");
			final SitemapIndexUrl sitemapIndexUrl = new SitemapIndexUrl(indexURL, nowDate);
			sitemap.addUrl(sitemapIndexUrl);
		}
		return String.join("", sitemap.writeAsString());
	}
	
	private final String parseDocumentMapsite(final List<?> lines, final double priority, final ChangeFreq frequency) throws MalformedURLException {
		final WebSitemapGenerator sitemap = new WebSitemapGenerator(ConstraintesURL.URL_APPLICATION);
		for (final Object line : lines) {
			final DocumentMapsite document = (DocumentMapsite) line;
			final WebSitemapUrl url = new WebSitemapUrl.Options(ConstraintesURL.URL_APPLICATION.concat(document.getDocumentURL()))
					.lastMod(document.getModifiedDate().toDate()).priority(priority).changeFreq(frequency).build();
			sitemap.addUrl(url);
		}
		return String.join("", sitemap.writeAsStrings());
	}
	
	@Transactional(readOnly = true)
	private final long countAllCompany() {
		return companyRepository.countAllCompanyForMapsite();
	}
	
	@Override
	public String generateCompaniesIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllCompany());
		if(pagination == 1) return generateCompaniesMapsite(1);
		return parseMapsiteIndex(pagination, "companies", "/mapsite/entreprises/");
	}
	
	@Transactional(readOnly = true)
	private final List<CompanyMapsite> readCompanyMapsite(final int page) {
		return companyRepository.findAllCompanyMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateCompaniesMapsite(final int page) throws MalformedURLException {
		final List<CompanyMapsite> lines = readCompanyMapsite(page);
		final WebSitemapGenerator sitemap = new WebSitemapGenerator(ConstraintesURL.URL_APPLICATION);
		for (final CompanyMapsite line : lines) {
			final String url = ConstraintesURL.URL_APPLICATION.concat(line.getDocumentURL());
			sitemap.addUrl(new WebSitemapUrl.Options(url).lastMod(line.getModifiedDate().toDate()).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build());
			for (final String companyURL : COMPANIES_URL) {
				final String subURL = url.concat("/").concat(companyURL);
				sitemap.addUrl(new WebSitemapUrl.Options(subURL).lastMod(line.getModifiedDate().toDate()).priority(0.6).changeFreq(ChangeFreq.MONTHLY).build());
			}
		}
		return String.join("", sitemap.writeAsStrings());
	}
	
	@Transactional(readOnly = true)
	private final long countAllPost() {
		return postRepository.countAllActivePost();
	}
	
	@Override
	public String generatePostsIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllPost());
		if(pagination == 1) return generatePostsMapsite(1);
		return parseMapsiteIndex(pagination, "posts", "/mapsite/produits/");
	}
	
	@Transactional(readOnly = true)
	private final List<PostMapsite> readPostMapsite(final int page) {
		return postRepository.findAllPostMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generatePostsMapsite(final int page) throws MalformedURLException {
		final List<PostMapsite> lines = readPostMapsite(page);
		return parseDocumentMapsite(lines, 0.9, ChangeFreq.MONTHLY);
	}
	
	@Transactional(readOnly = true)
	private final long countAllAnnonce() {
		return annonceRepository.countAllActiveAnnonce();
	}
	
	@Override
	public String generateAnnoncesIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllAnnonce());
		if(pagination == 1) return generateAnnoncesMapsite(1);
		return parseMapsiteIndex(pagination, "annonces", "/mapsite/annonces/");
	}
	
	@Transactional(readOnly = true)
	private final List<AnnonceMapsite> readAnnonceMapsite(final int page) {
		return annonceRepository.findAllPostMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateAnnoncesMapsite(final int page) throws MalformedURLException {
		final List<AnnonceMapsite> lines = readAnnonceMapsite(page);
		return parseDocumentMapsite(lines, 0.9, ChangeFreq.MONTHLY);
	}
	
	@Transactional(readOnly = true)
	private final long countAllEvent() {
		return eventRepository.countAllActiveEvent();
	}
	
	@Override
	public String generateEventsIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllEvent());
		if(pagination == 1) return generateEventsMapsite(1);
		return parseMapsiteIndex(pagination, "events", "/mapsite/evenements/");
	}
	
	@Transactional(readOnly = true)
	private final List<EventMapsite> readEventMapsite(final int page) {
		return eventRepository.findAllPostMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateEventsMapsite(final int page) throws MalformedURLException {
		final List<EventMapsite> lines = readEventMapsite(page);
		return parseDocumentMapsite(lines, 0.9, ChangeFreq.MONTHLY);
	}
	
	@Transactional(readOnly = true)
	private final long countAllEmploye() {
		return employeRepository.countAllActiveEmploye();
	}
	
	@Override
	public String generateEmployesIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllEmploye());
		if(pagination == 1) return generateEmployesMapsite(1);
		return parseMapsiteIndex(pagination, "employes", "/mapsite/offres-emploi/");
	}
	
	@Transactional(readOnly = true)
	private final List<EmployeMapsite> readEmployeMapsite(final int page) {
		return employeRepository.findAllPostMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateEmployesMapsite(final int page) throws MalformedURLException {
		final List<EmployeMapsite> lines = readEmployeMapsite(page);
		return parseDocumentMapsite(lines, 0.9, ChangeFreq.MONTHLY);
	}
	
	@Transactional(readOnly = true)
	private final long countAllActualities() {
		return actualityRepository.countAllActyalityMapsite();
	}
	
	@Override
	public String generateActualitiesIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllActualities());
		if(pagination == 1) return generateActualitiesMapsite(1);
		return parseMapsiteIndex(pagination, "actus", "/mapsite/actualites/");
	}
	
	@Transactional(readOnly = true)
	private final List<ActualityMapsite> readActualityMapsite(final int page) {
		return actualityRepository.findAllActualityMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateActualitiesMapsite(final int page) throws MalformedURLException {
		final List<ActualityMapsite> lines = readActualityMapsite(page);
		return parseDocumentMapsite(lines, 0.9, ChangeFreq.MONTHLY);
	}
	
	@Transactional(readOnly = true)
	private final long countAllBlog() {
		return blogRepository.countAllBlogMapsite();
	}
	
	@Override
	public String generateBlogIndex() throws MalformedURLException {
		final int pagination = parseCountPagination(countAllBlog());
		if(pagination == 1) return generateBlogMapsite(1);
		return parseMapsiteIndex(pagination, "blogs", "/mapsite/blogs/");
	}
	
	@Transactional(readOnly = true)
	private final List<BlogMapsite> readBlogMapsite(final int page) {
		return blogRepository.findAllBlogMapsite(page, ConstraintesForm.LIMIT_MAPSITE);
	}
	
	@Override
	public String generateBlogMapsite(final int page) throws MalformedURLException {
		final List<BlogMapsite> lines = readBlogMapsite(page);
		return parseDocumentMapsite(lines, 0.8, ChangeFreq.YEARLY);
	}
	
}
