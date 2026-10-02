package com.rinitec.algerieoffice.services.user.feedback;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.user.repport.CommentForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface ILikeService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param blogId
	 * @return
	 */
	boolean hasLikeBlog(Long userId, String blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param blogId
	 * @throws NotFoundException
	 * @return
	 */
	Blog postOrRemoveBlogLike(Long userId, String blogId) throws NotFoundException;

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actualityId
	 * @return
	 */
	boolean hasLikeActu(Long userId, String actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actualityId
	 * @return
	 * @throws NotFoundException
	 */
	Actuality postOrRemoveActualityLike(Long userId, String actualityId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @return
	 * @throws NotFoundException
	 */
	Object[] postOrRemoveCommentLike(Long userId, String commentId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param commentForm
	 * @return
	 * @throws NotFoundException
	 */
	Object[] addActualityComment(CommentForm commentForm) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actuId
	 * @param page
	 * @param rows
	 * @return
	 */
	ElementsList findActualityCommentList(Long userId, String actuId, int page, int rows);
	
}
