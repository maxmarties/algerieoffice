<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="i" begin="1" end="${list.size()}" step="1">
	<c:set var="line" value="${list.get(list.size() - i)}" scope="page"></c:set>
	<li class="comment-item" data-comment="${line.id}" data-parent="${line.parentId}" data-username="${line.username}">
		<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}" />">
		<ul class="navbar-nav pull-right">
			<li class="dropdown brand-menu brand-fixed">
				<a class="btn btn-dropdown btn-flat-favorite btn-simple" data-toggle="dropdown"><span><i class="cmsms-icon-ellipsis-vert"></i></span></a>
				<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
					<li>
						<c:choose>
							<c:when test="${currUserId == line.userId}"><a class="dropdown-item iDeleteComment"><i class="i-12"></i><spring:message code="btn.delete"/></a></c:when>
							<c:otherwise><a class="dropdown-item iRateComment"><i class="i-12"></i><spring:message code="explorer.popup.topic2"/></a></c:otherwise>
						</c:choose>
					</li>
				</ul>
			</li>
		</ul>
		<div class="comment-brand">
			<a href="<c:url value="/forums/user/${line.userId}"/>" class="lien lien-black font-bold"><c:out value="${line.username}" /></a>
			<span class="help-text"><joda:format value="${line.postedDate}" pattern="${line.formatDate}"></joda:format></span>
		</div>
		<div class="widget-detail subcomment-detail font-small m-t-10">
			<div class="fr-view fr-explorer"><c:out value="${line.message}" escapeXml="false" /></div>
			<ul class="list-none list-inline list-footer-more text-left">
				<li><a class="lien iLikeComment animated ${line.liked ? 'active' : ''}"><spring:message code="tool.view.like"/></a></li>
				<li><a class="lien iReplyComment"><spring:message code="btn.reply"/></a></li>
				<c:if test="${currUserId != line.userId}">
					<li>
						<a class="lien iMessageComment" data-id="${line.userId}" data-avatar="${line.urlAvatar}" data-name="${line.username}">
							<spring:message code="btn.contact"/></a>
					</li>
				</c:if>
				<li class="iViewLiked" style="${line.likeCount == 0 ? 'display:none;'  : ''}">
					<i class="cmsms-icon-thumbs-up-2 i-blue m-r-5"></i><span class="iLikeSubCommentCount"><c:out value="${line.likeCount}"/></span>
				</li>
			</ul>
		</div>
		<div class="clearfix"></div>
		<input type="hidden" id="iLikeCount${line.id}" value="${line.likeCount}" />
	</li>
</c:forEach>
</compress:html>