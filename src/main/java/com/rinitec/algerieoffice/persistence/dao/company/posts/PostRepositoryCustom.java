package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.posts.PostLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePostLine;
import com.rinitec.algerieoffice.web.modal.mapsite.PostMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostSimilarScreen;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostWidgetMini;

public interface PostRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPostCriteria(Long companyId, String filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<PostLine> findAllPostCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<PostMini> findAllPublishedPost(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllExplorerPostCriteria(Long companyId, String filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<Object[]> findAllExplorerPostCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @return
	 */
	Long countAllPostWidget(SearchPostForm searchPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @return
	 */
	List<PostWidgetMini> findPostWidgetList(SearchPostForm searchPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @return
	 */
	List<UUID> findAllPostEasylist(SearchPostForm searchPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param post
	 * @param sectors
	 * @param wilayas
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<PostSimilarScreen> findPostProxisList(Post post, List<Integer> sectors, List<Integer> wilayas, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<PostSimilarScreen> findPostSourcesList(UUID postId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllPostCampaignMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllRecyclePostCriteria(Long companyId, String filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<RecyclePostLine> findAllRecyclePostCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param service
	 * @return
	 */
	Object[] countStatsPostCriteria(Long companyId, boolean service);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @param update
	 * @return
	 */
	Long countAnalyticPostActivity(Long companyId, DateTime begin, DateTime end, boolean update);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	Long countPublishedPost(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * USED FOR MARKETPLACE AND MAPSITE
	 * @return
	 */
	Long countAllActivePost();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<PostMapsite> findAllPostMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param published
	 * @return
	 */
	Long countAllPost(Integer filter, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, List<UUID> lines);
	
}
