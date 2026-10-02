package com.rinitec.algerieoffice.web.form;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;

public class ConstraintesURL {
	public static final String MAPSITE_DATE_APP = "09/05/2021";
	
	public static final String URL_FACEBOOK = "https://www.facebook.com/algerieoffice";
	public static final String URL_TWITTER = "https://twitter.com/AlgerieOffice";
	public static final String URL_LINKEDIN = "https://www.linkedin.com/company/algerieoffice";
	public static final String URL_YOUTUBE = "https://www.youtube.com/channel/UCVtW1WLbMT63ny6X1lhehAw";
	public static final String URL_GOOGLE = "https://www.plus.google.com/algerieoffice";
	
	public static final String URL_APPLICATION = "https://www.algerieoffice.net";
	public static final String EMAIL_ADMINISTRATOR = "medreda.bensaad@gmail.com";
	
	public static final String URL_AVATARS = "/media/avt";
	public static final String URL_PHOTOS = "/media/photo";
	public static final String URL_IMAGES = "/media/image";
	public static final String URL_AOBNN = "/media/aobnn";
	public static final String URL_FILES = "/media/file";
	public static final String URL_ENVELOPES = "/envelope/file";
	public static final String URL_IDENTITY = "/envelope/avatar";
	public static final String URL_SCREENSHOT = "/proxy/screenshot";
	
	public static final String URL_DOCUMENT_ADSBUB = "/document/bub";
	
	public static final String URL_BLOG = "/blog";
	public static final String URL_AUTORS = URL_BLOG.concat("/auteurs");
	public static final String URL_FAMILIES = URL_BLOG.concat("/categorie");
	public static final String URL_TAGS = URL_BLOG.concat("/tag");

	public static final String URL_SEARCH = "/recherche";
	public static final String URL_PROFILES = "/membres";
	public static final String URL_COMPANIES = "/entreprises";
	public static final String URL_COMPANY = "/entreprise";
	public static final String URL_ANNONCES = "/annonces";
	public static final String URL_CATEGORIES = "/produits-et-services?categorie=";
	public static final String URL_POSTS = "/produits-et-services";
	public static final String URL_MARKETPLACE = "/marketplace";
	public static final String URL_EMPLOYE = "/offres-emploi";
	public static final String URL_EVENTS = "/evenements";
	public static final String URL_WORKS = "/realisations";
	public static final String URL_NEWS = "/actualites";
	public static final String URL_QUICKLY = "/trouver-vite";
	
	public static final String URL_PREVIEW = "/explorer/preview";
	public static final String URL_SOLUTIONS = "/solutions";
	public static final String URL_INFOS = "/infos";
	public static final String URL_CONTACT = "/contacts";
	
	public static final String URL_SECTOR = "/secteurs";
	public static final String URL_SECTOR_ACTIVITY = URL_SECTOR.concat("/activite");
	
	public static final String URL_WILAYA = "/villes";
	
	public static final String URL_POLITIC = "/infos/politique-confidentialite";
	public static final String URL_CGU = "/infos/cgu";
	
	public static final String URL_CONFIRM_MAIL = "/website/confirmation?token=";
	public static final String URL_CHANGE_PASSWORD = "/website/update-password?id=";
	
	
	public static final String[] URL_SECTORS = {"administration-fonction-publique", "agriculture-peche", "agroalimentaire-tabacs-et-allumettes", 
			"artisanat-d-art", "associations", "banques-assurances-services-financiers", "chimie-plastique-conditionnement", 
			"commerce-de-detail-grande-distribution", "communication-marketing-information", "construction-batiment-travaux-publics", 
			"culture-sports-loisirs", "energie", "enseignement-formation", "environnement-recuperation-tri-recyclage-traitement-des-dechets", 
			"equipement-materiel-pour-activites-professionnelles", "fabrication-commerce-de-gros-articles-destines-a-la-vente", 
			"gestion-administration-des-entreprises", "hotellerie-restauration-tourisme", "immobilier", "import-et-export", 
			"industrie-textile", "industries-de-cuirs-chaussures", "industries-diverses", "informatique", "logistique-transports", 
			"materiaux-de-construction-ceramique-verre", "minerais-mineraux-siderurgie", "mines-carrieres", "professions-juridiques", 
			"sante-action-sociale", "services-aux-particuliers-collectivites-entreprises"};
	
	public static final String[] URL_WILAYAS = {"adrar", "chlef", "laghouat", "oum-el-bouaghi", "batna", "bejaia", "biskra", "bechar", "blida", 
			"bouira", "tamanrasset", "tebessa", "tlemcen", "tiaret", "tizi-ouzou", "alger", "djelfa", "jijel", "setif", "saida", "skikda", 
			"sidi-bel-abbes", "annaba", "guelma", "constantine", "medea", "mostaganem", "m-sila", "mascara", "ouargla", "oran", "el-bayadh", 
			"illizi", "bordj-bou-arreridj", "boumerdes", "el-tarf", "tindouf", "tissemsilt", "el-oued", "khenchela", "souk-ahras", 
			"tipaza", "mila", "ain-defla", "Naama", "ain-temouchent", "ghardaia", "relizane"};
	
	public static final String[] URL_FAMILY_BLOG = {"communication", "data", "developpement-commercial", "entreprendre", "finance", "juridique", 
			"marketing", "on-en-parle", "productivite", "social-media"};
	
	public static final String[] URL_LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; 

	
	public static final String getExplorerBlogURL() {
		return URL_APPLICATION.concat(URL_BLOG).concat("/");
	}
	
	public static final String getExplorerBlogAutorURL() {
		return URL_APPLICATION.concat(URL_AUTORS).concat("/");
	}
	
	public static final String getBrowserBlogURL(final String identify) {
		return URL_BLOG.concat("/").concat(identify);
	}
	
	public static final String getBrowserBlogAutorURL(final String identify) {
		return URL_AUTORS.concat("/").concat(identify);
	}
	
	public static final String getBrowserBlogFamilyURL(final int category) {
		return URL_FAMILIES.concat("/").concat(URL_FAMILY_BLOG[category - 1]);
	}
	
	public static final int getIndexFamily(final String familyURL) {
		return Arrays.asList(URL_FAMILY_BLOG).indexOf(familyURL) + 1;
	}
	
	public static final int getIndexLetter(final String letterURL) {
		return Arrays.asList(URL_LETTERS).indexOf(letterURL.toUpperCase()) + 1;
	}
	
	public static final String getMapsiteBlogURL() {
		return URL_APPLICATION.concat(URL_BLOG);
	}
	
	public static final String getMapsiteBlogFamilyURL(final String familyURL) {
		return URL_APPLICATION.concat(URL_FAMILIES).concat("/").concat(familyURL);
	}
	
	public static final String getMapsiteBlogAutorURL(final String autorURL) {
		return URL_APPLICATION.concat(URL_AUTORS).concat("/").concat(autorURL);
	}
	
	public static final String getMapsiteBlogSearchURL(final String token) {
		return URL_APPLICATION.concat(URL_BLOG).concat(URL_SEARCH).concat("/").concat(token);
	}
	
	public static final String getMapsiteBlogKeywordURL(final String keyword) {
		return URL_APPLICATION.concat(URL_TAGS).concat("/").concat(keyword);
	}
	
	public static final String getMapsiteBlogArticelURL(final String articleURL) {
		return URL_APPLICATION.concat(URL_BLOG).concat("/").concat(articleURL);
	}

	public static final String getCompanyExplorerURL(final String companyURL) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "");
	}
	
	public static final String getPostPreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_POSTS).concat("/").concat(identify);
	}
	
	public static final String getCategoryPreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_CATEGORIES).concat(identify);
	}
	
	public static final String getEventPreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_EVENTS).concat("/").concat(identify);
	}
	
	public static final String getWorkPreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_WORKS).concat("/").concat(identify);
	}
	
	public static final String getMarketplacePreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_MARKETPLACE).concat("/").concat(identify);
	}
	
	public static final String getEmployePreviewURL(final String identify) {
		return URL_PREVIEW.concat(URL_EMPLOYE).concat("/").concat(identify);
	}
	
	public static final String getAlerteMarketplaceURL(final DocumentType type) {
		switch(type) {
		case post: return URL_MARKETPLACE.concat(URL_POSTS);
		case annonce: return URL_MARKETPLACE.concat(URL_ANNONCES);
		case event: return URL_MARKETPLACE.concat(URL_EVENTS);
		default: return URL_MARKETPLACE.concat(URL_EMPLOYE);
		}
	}
	
	public static final String getPostFavoriteURL(final String companyURL, final String identify) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_POSTS).concat("/").concat(identify);
	}
	
	public static final String getMarketplaceFavoriteURL(final String companyURL, final String identify) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_MARKETPLACE).concat("/").concat(identify);
	}
	
	public static final String getEventFavoriteURL(final String companyURL, final String identify) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_EVENTS).concat("/").concat(identify);
	}
	
	public static final String getEmployeFavoriteURL(final String companyURL, final String identify) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_EMPLOYE).concat("/").concat(identify);
	}
	
	public static final String getWorkFavoriteURL(final String companyURL, final String identify) {
		return URL_COMPANIES.concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_WORKS).concat("/").concat(identify);
	}
	
	public static final String getActualiteFavoriteURL(final String uuid) {
		return URL_MARKETPLACE.concat(URL_NEWS).concat("/").concat(uuid);
	}
	
	public static final String getCompanyLinkedURL(final String companyURL) {
		return URL_APPLICATION.concat(URL_COMPANIES).concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@");
	}
	
	public static final String getPostLinkedURL(final String companyURL, final String identify) {
		return URL_APPLICATION.concat(URL_COMPANIES).concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_POSTS).concat("/").concat(identify);
	}
	
	public static final String getMarketplaceLinkedURL(final String companyURL, final String identify) {
		return URL_APPLICATION.concat(URL_COMPANIES).concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "@")
				.concat(URL_MARKETPLACE).concat("/").concat(identify);
	}
	
	public static final String getCompanyMapsiteURL(final String companyURL) {
		return URL_APPLICATION.concat(URL_COMPANIES).concat("/").concat(!StringUtils.isEmpty(companyURL) ? companyURL : "");
	}
	
	public static final String getCompanyNewsletterURL(final String companyURL, final String url) {
		return URL_APPLICATION.concat(URL_COMPANIES).concat("/").concat(companyURL).concat(url);
	}
	
	public static final String getPostMapsiteURL(final String companyURI, final String identify) {
		return URL_APPLICATION.concat(!StringUtils.isEmpty(companyURI) ? companyURI : "").concat(URL_POSTS).concat("/").concat(identify);
	}
	
	public static final String getEventMapsiteURL(final String companyURI, final String identify) {
		return URL_APPLICATION.concat(!StringUtils.isEmpty(companyURI) ? companyURI : "").concat(URL_EVENTS).concat("/").concat(identify);
	}
	
	public static final String getWorkMapsiteURL(final String companyURI, final String identify) {
		return URL_APPLICATION.concat(!StringUtils.isEmpty(companyURI) ? companyURI : "").concat(URL_WORKS).concat("/").concat(identify);
	}
	
	public static final String getAnnonceMapsiteURL(final String companyURI, final String identify) {
		return URL_APPLICATION.concat(!StringUtils.isEmpty(companyURI) ? companyURI : "").concat(URL_MARKETPLACE).concat("/").concat(identify);
	}
	
	public static final String getEmployeMapsiteURL(final String companyURI, final String identify) {
		return URL_APPLICATION.concat(!StringUtils.isEmpty(companyURI) ? companyURI : "").concat(URL_EMPLOYE).concat("/").concat(identify);
	}
	
	public static final int getIndexSector(final String sectorURL) {
		return Arrays.asList(URL_SECTORS).indexOf(sectorURL) + 1;
	}
	
	public static final int getIndexWilaya(final String wilayaURL) {
		return Arrays.asList(URL_WILAYAS).indexOf(wilayaURL) + 1;
	}
	
	public static final String getSectorURL(final int indexSector) {
		return URL_SECTOR.concat("/").concat(URL_SECTORS[indexSector - 1]);
	}
	
	public static final String getWilayaURL(final String wilayaURL) {
		return URL_WILAYA.concat("/").concat(wilayaURL);
	}
	
	public static final String getWilayaSectorURL(final String wilayaURL, final String sectorURL) {
		return URL_WILAYA.concat("/").concat(wilayaURL).concat("/").concat(sectorURL);
	}
	
	public static final String getSectorActivityURL(String activityURL) {
		return URL_SECTOR_ACTIVITY.concat("/").concat(activityURL);
	}
	
	public static final String getWilayaLinkURL(final int indexWilaya) {
		return URL_WILAYA.concat("/").concat(URL_WILAYAS[indexWilaya - 1]);
	}
	
	public static final String getSectorActivityWilayaURL(String activityURL, int indexWilaya) {
		return URL_SECTOR_ACTIVITY.concat("/").concat(activityURL).concat("/").concat(URL_WILAYAS[indexWilaya - 1]);
	}
	
	public static final String getWilayaSectorURL(final int indexWilaya, final int indexSector) {
		return URL_WILAYA.concat("/").concat(URL_WILAYAS[indexWilaya - 1]).concat("/").concat(URL_SECTORS[indexSector - 1]);
	}
	
	public static final String getMarketplacePostURL(final String postURL, final String companyURL) {
		return URL_MARKETPLACE.concat(URL_POSTS).concat("/").concat(companyURL).concat("/").concat(postURL);
	}
	
	public static final String getMarketplaceAnnonceURL(final String annonceURL, final String companyURL) {
		return URL_MARKETPLACE.concat(URL_ANNONCES).concat("/").concat(companyURL).concat("/").concat(annonceURL);
	}
	
	public static final String getMarketplaceEventURL(final String eventURL, final String companyURL) {
		return URL_MARKETPLACE.concat(URL_EVENTS).concat("/").concat(companyURL).concat("/").concat(eventURL);
	}
	
	public static final String getMarketplaceEmployeURL(final String employeURL, final String companyURL) {
		return URL_MARKETPLACE.concat(URL_EMPLOYE).concat("/").concat(companyURL).concat("/").concat(employeURL);
	}
	
	public static final String getSectorsMapsiteURL() {
		return URL_APPLICATION.concat(URL_SECTOR);
	}
	
	public static final String getSectorMapsiteURL(final String sectorURL) {
		return URL_APPLICATION.concat(URL_SECTOR).concat("/").concat(sectorURL);
	}
	
	public static final String getSectorActivityMapsiteURL(final String activityURL) {
		return URL_APPLICATION.concat(URL_SECTOR_ACTIVITY).concat("/").concat(activityURL);
	}
	
	public static final String getSectorWilayaMapsiteURL(final String activityURL, final String wilayaURL) {
		return URL_APPLICATION.concat(URL_SECTOR_ACTIVITY).concat("/").concat(activityURL).concat("/").concat(wilayaURL);
	}
	
	public static final String getWilayasMapsiteURL() {
		return URL_APPLICATION.concat(URL_WILAYA);
	}
	
	public static final String getWilayaMapsiteURL(final String wilayaURL) {
		return URL_APPLICATION.concat(URL_WILAYA).concat("/").concat(wilayaURL);
	}
	
	public static final String getWilayaSectorMapsiteURL(final String wilayaURL, final String sectorURL) {
		return URL_APPLICATION.concat(URL_WILAYA).concat("/").concat(wilayaURL).concat("/").concat(sectorURL);
	}
	
	public static final String getWilayaActivityMapsiteURL(final String wilayaURL, final String sectorURL, final String activityURL) {
		return URL_APPLICATION.concat(URL_WILAYA).concat("/").concat(wilayaURL).concat("/").concat(sectorURL).concat("/").concat(activityURL);
	}
	
	public static final String getCompaniesMapsiteURL() {
		return URL_APPLICATION.concat(URL_COMPANIES);
	}
	
	public static final String getSearchCompaniesMapsiteURL() {
		return URL_APPLICATION.concat(URL_SEARCH).concat(URL_COMPANIES);
	}
	
	public static final String getSearchCompanyMapsiteURL(final String token, final Integer wilaya) {
		return URL_APPLICATION.concat(URL_SEARCH).concat(URL_COMPANY).concat("?token=").concat(token).concat(wilaya != null 
				? "&location=".concat(URL_WILAYAS[wilaya - 1]) : "");
	}
	
	public static final String getSearchQuicklyMapsiteURL() {
		return URL_APPLICATION.concat(URL_QUICKLY);
	}
	
	public static final String getMarketplaceMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE);
	}
	
	public static final String getMarketplacePostsMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_POSTS);
	}
	
	public static final String getMarketplacePostMapsiteURL(final String companyURL, final String postURL) {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_POSTS).concat("/").concat(companyURL).concat("/").concat(postURL);
	}
	
	public static final String getMarketplaceAnnoncesMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_ANNONCES);
	}
	
	public static final String getMarketplaceAnnonceMapsiteURL(final String companyURL, final String annonceURL) {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_ANNONCES).concat("/").concat(companyURL).concat("/").concat(annonceURL);
	}
	
	public static final String getMarketplaceEmployesMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_EMPLOYE);
	}
	
	public static final String getMarketplaceEmployeMapsiteURL(final String companyURL, final String employeURL) {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_EMPLOYE).concat("/").concat(companyURL).concat("/").concat(employeURL);
	}
	
	public static final String getMarketplaceEventsMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_EVENTS);
	}
	
	public static final String getMarketplaceEventMapsiteURL(final String companyURL, final String eventURL) {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_EVENTS).concat("/").concat(companyURL).concat("/").concat(eventURL);
	}
	
	public static final String getMarketplaceNewsMapsiteURL() {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_NEWS);
	}
	
	public static final String getMarketplaceNewMapsiteURL(final String uuid) {
		return URL_APPLICATION.concat(URL_MARKETPLACE).concat(URL_NEWS).concat("/").concat(uuid);
	}
	
	public static final String getMembersMapsiteURL() {
		return URL_APPLICATION.concat(URL_PROFILES);
	}
	
	public static final String getMemberMapsiteURL(final String pseudoURL) {
		return URL_APPLICATION.concat(URL_PROFILES).concat("/").concat(pseudoURL);
	}
	
	public static final String getSolutionsMapsiteURL() {
		return URL_APPLICATION.concat(URL_SOLUTIONS);
	}
	
	public static final String getSolutionMapsiteURL(final String pageURL) {
		return URL_APPLICATION.concat(URL_SOLUTIONS).concat("/").concat(pageURL);
	}
	
	public static final String getInfosMapsiteURL(final String pageURL) {
		return URL_APPLICATION.concat(URL_INFOS).concat("/").concat(pageURL);
	}
	
	public static final String getContactsMapsiteURL() {
		return URL_APPLICATION.concat(URL_CONTACT);
	}
	
	public static final String getProxyImageMapsiteURL(final String imageId) {
		return URL_APPLICATION.concat(URL_IMAGES).concat("?uuid=").concat(imageId);
	}
	
	public static final String getProxyFileMapsiteURL(final String fileId) {
		return URL_APPLICATION.concat(URL_FILES).concat("?fileId=").concat(fileId);
	}
	
}
