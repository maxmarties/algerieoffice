package com.rinitec.algerieoffice.services.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.realtime.AdmTestimonialForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAdmRepportService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findTestimonialList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmTestimonialForm readTestimonialForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param testimonialForm
	 * @param adminId
	 * @return
	 */
	Testimonial addTestimonial(AdmTestimonialForm testimonialForm, Long adminId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param testimonialForm
	 * @return
	 */
	Testimonial updateTestimonial(AdmTestimonialForm testimonialForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Testimonial deleteTestimonial(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteTestimonials(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAssistList(String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Assist deleteAssist(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAssists(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findProblemList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Problem deleteProblem(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteProblems(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
