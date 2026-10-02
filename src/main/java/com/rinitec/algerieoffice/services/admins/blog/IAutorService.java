package com.rinitec.algerieoffice.services.admins.blog;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.admins.blog.AutorForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAutorService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	boolean existsByIdentify(String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AutorForm readAutorForm(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param autorForm
	 * @return
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws SocialExistException
	 */
	Autor addAutor(AutorForm autorForm) throws UrlUnavailableException, AlreadyExistException, SocialExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param autorForm
	 * @return
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws SocialExistException
	 */
	Autor updateAutor(AutorForm autorForm) throws UrlUnavailableException, AlreadyExistException, SocialExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAutorsList(String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws AccessLeaderException
	 */
	Autor deleteAutor(Long id) throws NotFoundException, AccessLeaderException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws AccessLeaderException
	 */
	void deleteAutors(List<Long> lines) throws NotFoundException, AccessLeaderException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<UserMini> findAllAutors();
	
}
