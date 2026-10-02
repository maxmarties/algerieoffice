<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
			<div class="screen-column widget-column m-b-20" data-about="${line.companyId}" data-document="${line.id}">
				<div class="widget-title" data-dir="${line.language == 'ar' ? 'rtl' : 'ltr'}">
					<span class="item-provider p-right">
						<c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/>
					</span>
					<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc2 lien sh-black explorer-result"><c:out value="${line.title}" /></a>
				</div>
				<div class="widget-body">
					<div class="table-responsive">
						<table class="table table-widget">
							<thead><tr><th style="width:50%;"></th><th style="width:50%;"></th></tr></thead>
							<tbody class="font-small">
								<tr>
									<td><spring:message code="tabs.type" /> : <strong><spring:message code="chose.annonce${line.type}"/></strong></td>
									<td class="text-right"><spring:message code="tabs.explorer.dateOn" /> : <strong><c:out value="${line.startDate}"/></strong></td>
								</tr>
								<tr>
									<td><spring:message code="lbl.visibility" /> : <strong><spring:message code="overview.visibility${line.visibility}"/></strong></td>
									<td class="text-right">
										<spring:message code="tabs.explorer.dateOff" /> : <span class="i-red"><strong><c:out value="${line.endDate}"/></strong></span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="widget-description">
						<div class="text-${line.language == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}" /></div>
						<c:if test="${!empty line.keysword}">
							<ul class="list-keysword list-none m-t-10">
								<c:forEach var="keyword" items="${line.buildKeysword()}">
									<li>
										<i class="cmsms-icon-hash-1 i-segond i-tags m-r-5"></i>
										<a href="<c:url value="/marketplace/annonces?tag=${line.parsKey(keyword)}" />" 
											class="lien lien-keyword"><c:out value="${keyword}" /></a>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<div class="row row-mini">
						<div class="col-md-8 col-mini">
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
						<div class="col-md-4 col-mini">
							<div class="widget-button text-right">
								<a href="<c:url value="${line.identifyURL}?prospect=open" />" 
									class="btn btn-primary btn-simple btn-fixed"><span><spring:message code="btn.explorer.contact4"/></span></a>
								<div id="submitedFavorite${line.id}Form" class="form-submit">
									<a class="btn btn-segond btn-flat-favorite btn-simple btn-submit iFavorite ${line.favorite ? 'active' : ''}" 
										title="<spring:message code="tool.navigate.company2"/>"><span><i class="cmsms-icon-star-filled"></i></span></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="currFavorite${line.id}" value="${line.favorite}" />
			</div>
			<c:if test="${state.count == indexMarket && !empty blogMarket}"><c:import url="/WEB-INF/tempajax/screen/widget_blogmarket.jsp" /></c:if>
		</c:forEach>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult" value="${list.countResult}" />
<input type="hidden" id="countScreenSize" value="${list.lines.size()}" />
</compress:html>