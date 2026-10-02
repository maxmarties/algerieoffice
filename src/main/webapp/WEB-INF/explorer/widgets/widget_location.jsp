<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column explorer-contact h-100">
	<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.contact3.2"/></h2></div>
	<div class="explorer-body">
		<ul class="list-explorer-contact list-none list-block font-small">
			<li>
				<span class="text-border m-r-5"><spring:message code="lbl.sub.order2.4"/>:</span>
				<c:out value="${explorerCompany.profile.address}"/><br><c:out value="${explorerCompany.profile.postal}"/>,  
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
			<li>
				<span class="text-border m-r-5"><spring:message code="tabs.fax"/>:</span>
				<c:choose>
					<c:when test="${!empty explorerCompany.shedule.fax}"><c:out value="${explorerCompany.shedule.getFormattedFax()}"/></c:when>
					<c:otherwise><c:out value="--"/></c:otherwise>
				</c:choose>
			</li>
			<li>
				<span class="text-border m-r-5"><spring:message code="tabs.mobile"/>:</span>
				<c:choose>
					<c:when test="${!empty explorerCompany.shedule.mobile}"><c:out value="${explorerCompany.shedule.getFormattedMobile()}"/></c:when>
					<c:otherwise><c:out value="--"/></c:otherwise>
				</c:choose>
			</li>
		</ul>
		<c:if test="${!explorerPage.linked.isEmptySocialMedia()}">
			<hr class="my-2">
			<ul class="navbar-nav nav-flex-icons navbar-linked list-explorer-linked">
				<c:set var="providers" value="facebook,twitter,google,linkedin,youtube,instagram" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<c:if test="${!empty explorerPage.linked.socialMedias[state.count - 1]}">
						<li>
							<a href="<c:url value="${explorerPage.linked.socialMedias[state.count - 1]}" />" 
								class="btn icon-${pageScope.provider}" target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</c:if>
	</div>
</div>