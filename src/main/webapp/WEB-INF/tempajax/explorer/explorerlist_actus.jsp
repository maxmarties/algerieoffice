<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="${hasView ? 'col-12' : 'col-md-6'} m-b-20">
					<div class="explorer-column ${hasView ? 'explorer-row' : 'h-100'}">
						<c:choose>
							<c:when test="${hasView}"><div class="explorer-header background-container" style="background-image: url('${line.photoURL}');"></div></c:when>
							<c:otherwise><div class="explorer-header"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div></c:otherwise>
						</c:choose>
						<div class="explorer-content">
							<div class="widget-title">
								<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
								<a href="<c:url value="/marketplace/actualites/${line.id}" />" class="h-doc h-doc4 lien lien-explorer-title sh-black explorer-resultFind"><c:out value="${line.title}" /></a>
							</div>
							<div class="widget-body">
								<div class="widget-date font-small">
									<i class="cmsms-icon-calendar-empty text-segond m-r-10"></i><span class="m-r-5"><spring:message code="tabs.actu"/> :</span>
									<span class="text-help"><joda:format value="${line.actuDate}" pattern="dd/MM/yyyy"></joda:format></span>
								</div>
								<p class="m-t-10 m-b-10"><c:out value="${line.description}" /></p>
								<c:if test="${!empty line.urlExtern}">
									<a href="<c:url value="${line.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" 
										target="_blank"><c:out value="${line.urlExtern}" /></a>
								</c:if>
								<div class="widget-more m-t-10">
									<ul class="navbar-nav nav-flex-icons font-small">
										<li>
											<a class="link-like transition-35 iLike ${line.liked ? 'active' : ''} ${!empty hasPreview ? 'disabled' : ''}" 
												data-actu="${line.id}"><i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
												<span class="iLikeCount animated m-l-10"><c:out value="${line.likeCount}" /></span>
											</a>
										</li>
										<li class="link-comment"><i class="cmsms-icon-comment m-r-10"></i><c:out value="${line.commentCount}" /></li>
										<li class="ml-auto">
											<a class="icon-facebook transition-35 iFollow ${!empty hasPreview ? 'disabled' : ''}" 
												title="<spring:message code="tool.navigate.company5.3" arguments="Facebook" />"
												href="<c:url value="https://www.facebook.com/sharer/sharer.php?url=${line.mapnewsURL}" />"><i class="cmsms-icon-facebook"></i></a>
										</li>
										<li class="m-l-5">
											<a class="icon-twitter transition-35 iFollow ${!empty hasPreview ? 'disabled' : ''}" 
												title="<spring:message code="tool.navigate.company5.3" arguments="Twitter" />"
												href="<c:url value="https://twitter.com/intent/tweet?url=${line.mapnewsURL}" />"><i class="cmsms-icon-twitter"></i></a>
										</li>
										<li class="m-l-5">
											<a class="icon-google transition-35 iFollow ${!empty hasPreview ? 'disabled' : ''}" 
												title="<spring:message code="tool.navigate.company5.3" arguments="Google" />"
												href="<c:url value="https://plus.google.com/share?url=${line.mapnewsURL}" />"><i class="cmsms-icon-google"></i></a>
										</li>
										<li class="m-l-5">
											<a class="icon-linkedin transition-35 iFollow ${!empty hasPreview ? 'disabled' : ''}" 
												title="<spring:message code="tool.navigate.company5.3" arguments="Linkedin" />"
												href="<c:url value="https://www.linkedin.com/shareArticle?url=${line.mapnewsURL}" />"><i class="cmsms-icon-linkedin"></i></a>
										</li>
										<li class="m-l-5">
											<a class="icon-viadeo transition-35 iFollow ${!empty hasPreview ? 'disabled' : ''}" 
												title="<spring:message code="tool.navigate.company5.3" arguments="Viadeo" />"
												href="<c:url value="https://www.viadeo.com/shareit/share/?url=${line.mapnewsURL}" />"><i class="cmsms-icon-viadeo"></i></a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countDesktopResult" value="${explorerList.count}" />
<input type="hidden" id="countDesktopSize" value="${explorerList.lines.size()}" />
</compress:html>