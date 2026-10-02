<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<li class="comment-item hidden">
	<c:if test="${currPage == 1}"><input type="hidden" id="countCommentResult" value="${list.countResult}" /></c:if>
	<input type="hidden" id="countCommentSize${currPage}" value="${list.lines.size()}" />
</li>
<c:choose>
	<c:when test="${list.lines.isEmpty()}">
		<c:if test="${currPage == 1}"><li class="comment-item comment-empty i-help"><spring:message code="tool.empty.comments" /></li></c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="i" begin="1" end="${list.lines.size()}" step="1">
			<c:set var="line" value="${list.lines.get(list.lines.size() - i)}" scope="page"></c:set>
			<li class="comment-item m-b-20" data-comment="${line.commentId}">
				<span class="img-circle img-container pull-left ${line.online ? 'ind-login' : ''}">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<spring:message code="tooltip.avatar" />">
				</span>
				<div class="comment-brand">
					<a href="<c:url value="/membres?id=${line.userId}"/>" class="lien lien-black font-bold" target="_blank"><c:out value="${line.username}" /></a>
					<c:if test="${!empty line.companyname}"><span class="font-mini i-help"> - <c:out value="${line.companyname}"/></span></c:if>
					<span class="font-mini i-help pull-right"><joda:format value="${line.postedDate}" pattern="${line.formatDate}"></joda:format></span>
					<p class="font-normal m-t-5"><c:out value="${line.message}"/></p>
					<ul class="list-none list-inline list-footer-more text-left">
						<li><a class="lien iLike animated ${line.liked ? 'active' : ''}"><spring:message code="tool.view.like"/></a></li>
						<li><a class="lien iReply"><spring:message code="btn.reply"/></a></li>
						<c:if test="${currUserId != line.userId}">
							<li>
								<a class="lien iMessage" data-id="${line.userId}" data-avatar="${line.urlAvatar}" data-name="${line.username}" 
									data-login="${line.online}" ><spring:message code="btn.contact"/></a>
							</li>
						</c:if>
						<c:if test="${line.likeCount > 0}">
							<li class="iViewLiked">
								<i class="cmsms-icon-thumbs-up-2 i-blue m-r-5"></i><span class="iLikeCount"><c:out value="${line.likeCount}"/></span>
							</li>
						</c:if>
					</ul>
				</div>
				<span class="clearfix"></span>
				<input type="hidden" id="iLikeCount${line.commentId}" value="${line.likeCount}" />
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>