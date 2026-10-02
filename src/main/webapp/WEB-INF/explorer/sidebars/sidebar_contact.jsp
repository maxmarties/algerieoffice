<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="row">
	<div class="col-md-6 col-lg-12 m-b-10">
		<div class="explorer-column h-100">
			<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.contact3.1"/></h2></div>
			<div class="widget-body m-b-20">
				<table class="table table-explorer table-last">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<c:set var="openedState" value="${explorerCompany.shedule.openedState()}" scope="page"></c:set>
						<tr>
							<td><spring:message code="lbl.sub.shedule3.1" /></td>
							<td class="${pageScope.openedState ? 'i-green' : 'i-red'}">
								<spring:message code="lbl.sub.shedule3.1.${pageScope.openedState ? '1' : '2'}" /> 
								<c:if test="${pageScope.openedState}"><c:out value="${explorerCompany.shedule.closedClock()}"/></c:if>
							</td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.shedule3.2" /></td>
							<td>
								<c:set var="currday" value="${explorerCompany.shedule.currdayClock()}" scope="page"></c:set>
								<c:choose>
									<c:when test="${empty pageScope.currday}"><spring:message code="lbl.sub.shedule3.1.2"/></c:when>
									<c:otherwise><c:out value="${pageScope.currday}"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-12 m-b-10">
		<div class="explorer-column h-100">
			<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="lbl.sub.location2"/></h2></div>
			<c:choose>
				<c:when test="${!empty explorerPage.inbox.urlEmbded}">
					<div class="explorer-carte map-outer m-b-10">
						<c:choose>
							<c:when test="${explorerPage.inbox.hasEmbded}"><c:out value="${explorerPage.inbox.urlEmbded}" escapeXml="false" /></c:when>
							<c:otherwise><iframe width="100%" height="120" src="${explorerPage.inbox.urlEmbded}"></iframe></c:otherwise>
						</c:choose>
					</div>
				</c:when>
				<c:otherwise>
					<div class="widget-body m-b-20">
						<p class="explorer-alert"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.location"/></p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="col-md-6 col-lg-12 m-b-10">
		<div class="explorer-column explorer-contact h-100">
			<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.contact3.2"/></h2></div>
			<div class="explorer-body">
				<ul class="list-explorer-contact list-none list-block font-small">
					<li>
						<span class="text-border m-r-5"><spring:message code="lbl.sub.order2.4"/>:</span>
						<c:out value="${explorerCompany.profile.address}"/>, <c:out value="${explorerCompany.profile.postal}"/> 
						<span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></span>	
					</li>
					<li>
						<span class="text-border m-r-5"><spring:message code="tabs.email"/>:</span>
						<a href="mailto:${explorerCompany.profile.email}" class="lien lien-explorer-yellow iMail"><c:out value="${explorerCompany.profile.email}"/></a>
					</li>
					<li>
						<span class="text-border m-r-5"><spring:message code="tabs.phone"/>:</span>
						<a href="tel:+213${explorerCompany.profile.phone}" class="lien lien-explorer-yellow iPhone"><c:out value="${explorerCompany.profile.getFormattedPhone()}"/></a>
					</li>
					<c:if test="${!empty explorerCompany.shedule.fax}">
						<li>
							<span class="text-border m-r-5"><spring:message code="tabs.fax"/>:</span><c:out value="${explorerCompany.shedule.getFormattedFax()}"/>
						</li>
					</c:if>
					<c:if test="${!empty explorerCompany.shedule.mobile}">
						<li>
							<span class="text-border m-r-5"><spring:message code="tabs.mobile"/>:</span><c:out value="${explorerCompany.shedule.getFormattedMobile()}"/>
						</li>
					</c:if>
				</ul>
				<c:if test="${explorerPage.inbox.hasPresentSocialMedias()}">
					<hr class="my-2">
					<ul class="navbar-nav nav-flex-icons navbar-linked list-explorer-linked">
						<c:set var="providers" value="facebook,twitter,google,linkedin,youtube,instagram" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<c:if test="${!empty explorerPage.inbox.socialMedias[state.count - 1]}">
								<li>
									<a href="<c:url value="${explorerPage.inbox.socialMedias[state.count - 1]}" />" 
										class="btn icon-${pageScope.provider}" target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-12 m-b-10 ${explorerCompany.profile.addrs.isEmpty() ? 'hidden-md-up' : ''}">
		<div class="explorer-column h-100">
			<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="lbl.sub.location3.4"/></h2></div>
			<div class="explorer-body">
				<c:choose>
					<c:when test="${explorerCompany.profile.addrs.isEmpty()}"><p><spring:message code="tool.empty.location"/></p></c:when>
					<c:otherwise>
						<ul class="list-explorer-location list-none list-block font-small">
							<c:forEach var="addr" items="${explorerCompany.profile.addrs}" varStatus="state">
								<li>
									<i class="cmsms-icon-building-filled text-help pull-left"></i>
									<div class="explorer-brand">
										<c:out value="${addr}" />, <c:out value="${explorerCompany.profile.postals.get(state.count - 1)}" /> 
										<span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilayas.get(state.count - 1)}"/></span>
									</div> 
									<span class="clearfix"></span>
								</li>
							</c:forEach>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>