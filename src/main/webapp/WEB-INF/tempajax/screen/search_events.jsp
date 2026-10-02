<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${list.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="col-md-6 m-b-20">
					<div class="screen-column widget-column flexed flex-colone flex-jusitify h-100" data-about="${line.companyId}" data-document="${line.id}">
						<div class="widget-header">
							<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
							<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
							<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
						</div>
						<div class="widget-flexed flexed flex-colone flex-jusitify">
							<div class="widget-title" data-dir="${line.language == 'ar' ? 'rtl' : 'ltr'}">
								<span class="item-provider p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
								<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien sh-black explorer-result"><c:out value="${line.title}" /></a>
							</div>
							<div class="widget-body">
								<div class="table-responsive">
									<table class="table table-widget">
										<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
										<tbody class="font-small">
											<tr><td><spring:message code="lbl.sub.explorer3.1" /></td><td class="text-right"><c:out value="${line.eventDate}"/></td></tr>
											<tr><td><spring:message code="lbl.sub.explorer3.5" /></td><td class="text-right"><c:out value="${line.eventClock}"/></td></tr>
											<tr><td><spring:message code="lbl.sub.explorer3.2" /></td><td class="text-right"><spring:message code="chose.wilaya${line.wilaya}"/></td></tr>
										</tbody>
									</table>
								</div>
								<div class="widget-description">
									<p class="text-${line.language == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}" /></p>
									<c:if test="${!empty line.keysword}">
										<ul class="list-keysword list-none m-t-10">
											<c:forEach var="keyword" items="${line.buildKeysword()}">
												<li>
													<i class="cmsms-icon-hash-1 i-segond i-tags m-r-5"></i>
													<a href="<c:url value="/marketplace/evenements?tag=${line.parsKey(keyword)}" />" 
														class="lien lien-keyword"><c:out value="${keyword}" /></a>
												</li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
								<div class="row row-mini">
									<div class="col-10 col-mini">
										<div class="widget-about">
											<ul class="navbar-nav nav-flex-icons">
												<li class="widget-icon"><img class="img-circle" src="<c:url value="${line.urlAvatar}"/>" alt="<c:out value="${line.tradename}" />"></li>
												<li class="widget-abouter abouter-min">
													<a href="<c:url value="${line.companyURL}" />" class="lien lien-black lien-underline"><c:out value="${line.tradename}" /></a>
													<span> - <c:out value="${line.address}"/></span>
												</li>
											</ul>
										</div>
									</div>
									<div class="col-2 col-mini">
										<div class="widget-button text-right">
											<div id="submitedFavorite${line.id}Form" class="form-submit">
												<a class="btn btn-segond btn-flat-favorite btn-simple iFavorite ${line.favorite ? 'active' : ''}" 
													title="<spring:message code="tool.navigate.company2"/>"><span><i class="cmsms-icon-star-filled"></i></span></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<input type="hidden" id="currFavorite${line.id}" value="${line.favorite}" />
				</div>
				<c:if test="${state.count == indexMarket && !empty blogMarket}">
					<div class="col-12">
						<c:import url="/WEB-INF/tempajax/screen/widget_blogmarket.jsp" />
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult" value="${list.countResult}" />
<input type="hidden" id="countScreenSize" value="${list.lines.size()}" />
</compress:html>