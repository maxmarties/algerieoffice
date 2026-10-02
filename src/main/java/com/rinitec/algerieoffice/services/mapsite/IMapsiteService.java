package com.rinitec.algerieoffice.services.mapsite;

import java.net.MalformedURLException;

public interface IMapsiteService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateStaticMapsite() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateCompaniesIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateCompaniesMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generatePostsIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generatePostsMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateAnnoncesIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateAnnoncesMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateEventsIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateEventsMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateEmployesIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateEmployesMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateActualitiesIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateActualitiesMapsite(int page) throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws MalformedURLException
	 */
	String generateBlogIndex() throws MalformedURLException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	String generateBlogMapsite(int page) throws MalformedURLException;
	
}
