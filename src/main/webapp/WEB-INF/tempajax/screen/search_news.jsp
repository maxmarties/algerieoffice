<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
			<div class="screen-column widget-column m-b-20" data-document="${line.uuid}">
				<div class="row row-mini">
					<div class="col-md-4 col-mini">
						<div class="widget-window">
							<div class="widget-header">
								<a href="<c:url value="/marketplace/actualites/${line.uuid}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
								<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
								<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
							</div>
						</div>
					</div>
					<div class="col-md-8 col-mini">
						<div class="widget-title" data-dir="${line.language == 'ar' ? 'rtl' : 'ltr'}">
							<span class="item-provider p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
							<a href="<c:url value="/marketplace/actualites/${line.uuid}" />" class="h-doc h-doc4 lien sh-black explorer-result"><c:out value="${line.title}" /></a>
						</div>
						<div class="widget-category font-small">
							<p class="text-${line.language == 'ar' ? 'ar' : 'fr'}">
								<i class="cmsms-icon-calendar-7 i-segond m-r-10"></i><span class="i-help"><joda:format value="${line.actuDate}" pattern="dd MMM yyyy"></joda:format></span>
							</p>
						</div>
						<div class="widget-descriptif"><p class="text-${line.language == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}"/></p></div>
						<ul class="navbar-nav nav-flex-icons nav-screenlike font-small">
							<li>
								<a class="link-like transition-35 iLike ${line.liked ? 'active' : ''}">
									<i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
									<span class="iLikeCount animated m-l-10"><c:out value="${line.likeCount}" /></span>
								</a>
							</li>
							<li class="link-comment">
								<a href="<c:url value="/marketplace/actualites/${line.uuid}" />" class="lien lien-black" 
									title="<spring:message code="tool.view.comment"/>"><i class="cmsms-icon-comment m-r-10"></i><c:out value="${line.commentCount}" /></a>
							</li>
							<li class="ml-auto">
								<a class="icon-facebook transition-35 iFollow" title="<spring:message code="tool.navigate.company5.3" arguments="Facebook" />"
									href="<c:url value="https://www.facebook.com/sharer/sharer.php?url=${line.mapnewsURL}" />"><i class="cmsms-icon-facebook"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-twitter transition-35 iFollow" title="<spring:message code="tool.navigate.company5.3" arguments="Twitter" />"
									href="<c:url value="https://twitter.com/intent/tweet?url=${line.mapnewsURL}" />"><i class="cmsms-icon-twitter"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-google transition-35 iFollow" title="<spring:message code="tool.navigate.company5.3" arguments="Google" />"
									href="<c:url value="https://plus.google.com/share?url=${line.mapnewsURL}" />"><i class="cmsms-icon-google"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-linkedin transition-35 iFollow" title="<spring:message code="tool.navigate.company5.3" arguments="Linkedin" />"
									href="<c:url value="https://www.linkedin.com/shareArticle?url=${line.mapnewsURL}" />"><i class="cmsms-icon-linkedin"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-viadeo transition-35 iFollow" title="<spring:message code="tool.navigate.company5.3" arguments="Viadeo" />"
									href="<c:url value="https://www.viadeo.com/shareit/share/?url=${line.mapnewsURL}" />"><i class="cmsms-icon-viadeo"></i></a>
							</li>
						</ul>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-about-post">
						<div class="row row-mini">
							<div class="col-md-${!empty line.urlExtern ? '8' : '12'} col-mini">
								<div class="widget-about">
									<ul class="navbar-nav nav-flex-icons">
										<li class="widget-icon"><img class="img-circle" src="<c:url value="${line.urlAvatar}"/>" alt="<c:out value="${line.tradename}" />"></li>
										<li class="widget-abouter">
											<a href="<c:url value="${line.companyURL}" />" class="lien lien-black lien-underline"><c:out value="${line.tradename}" /></a>
											<span> - <c:out value="${line.address}"/></span>
										</li>
									</ul>
								</div>
							</div>
							<c:if test="${!empty line.urlExtern}">
								<div class="col-md-4 col-mini">
									<div class="widget-button-more text-right">
										<a href="<c:url value="${line.urlExtern}" />" class="lien lien-primary lien-underline" target="_blank">
											<spring:message code="lien.news"/></a><i class="cmsms-icon-paper-plane-3 m-l-10"></i>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${state.count == indexMarket && !empty blogMarket}"><c:import url="/WEB-INF/tempajax/screen/widget_blogmarket.jsp" /></c:if>
		</c:forEach>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult${currentPage}" value="${list.countResult}" />
<input type="hidden" id="countScreenSize${currentPage}" value="${list.lines.size()}" />
</compress:html>