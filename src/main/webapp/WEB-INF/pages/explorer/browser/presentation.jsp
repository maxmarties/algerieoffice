<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" class="lien"><c:out value="${explorerCompany.profile.tradename}"/></a></li>
			<li class="active"><span><spring:message code="explorer.mainmenu2"/></span></li>
		</ol>
		<div class="row">
			<div class="col-lg-3 m-t-10">
				<c:import url="/WEB-INF/explorer/sidebars/sidebar_presentation.jsp" />
				<c:if test="${explorerCompany.menu.hasSticky()}"><c:import url="/WEB-INF/explorer/widgets/widget_sticky.jsp" /></c:if>
			</div>
			<div class="col-lg-9 m-t-10">
				<c:if test="${explorerCompany.menu.hasAbout() && !empty explorerPage.about.urlCover}">
					<div class="m-b-10"><c:import url="/WEB-INF/explorer/widgets/widget_about.jsp" /></div>
				</c:if>
				<div class="explorer-column">
					<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.2"/></h1></div>
					<div class="explorer-body">
						<c:choose>
							<c:when test="${empty explorerPage.presentation}">
								<p class="explorer-alert"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.presentation"/></p>
							</c:when>
							<c:otherwise><div class="fr-view fr-explorer"><c:out value="${explorerPage.presentation}" escapeXml="false" /></div></c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${explorerCompany.menu.hasCatalog() && !explorerPage.catalog.titles.isEmpty()}">
					<div class="explorer-column m-t-10">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.3"/></h1></div>
						<div class="explorer-body"><c:import url="/WEB-INF/explorer/widgets/widget_album.jsp" /></div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>