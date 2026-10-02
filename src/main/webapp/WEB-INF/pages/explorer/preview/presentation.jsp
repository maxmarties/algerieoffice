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
				<c:if test="${explorerCompany.menu.hasAbout()}">
					<c:choose>
						<c:when test="${empty explorerPage.about.urlCover}">
							<div class="explorer-column m-b-10">
								<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.6"/></h1></div>
								<div class="explorer-body">
									<div class="alert alert-info">
										<i class="cmsms-icon-info-circled-3 i-alert"></i>
										<p class="p-alert">
											<spring:message code="message.explorer.about"/><br>
											<a href="<c:url value="/company/overview/about"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.about"/></a>
										</p>
									</div>		
								</div>
							</div>
						</c:when>
						<c:otherwise><div class="m-b-10"><c:import url="/WEB-INF/explorer/widgets/widget_about.jsp" /></div></c:otherwise>
					</c:choose>
				</c:if>
				<div class="explorer-column">
					<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.2"/></h1></div>
					<div class="explorer-body">
						<c:choose>
							<c:when test="${empty explorerPage.presentation}">
								<div class="alert alert-info">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.presentation"/><br>
										<a href="<c:url value="/company/overview/presentation"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.presentation"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><div class="fr-view fr-explorer"><c:out value="${explorerPage.presentation}" escapeXml="false" /></div></c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${explorerCompany.menu.hasCatalog()}">
					<div class="explorer-column m-t-10">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.3"/></h1></div>
						<div class="explorer-body">
							<c:choose>
								<c:when test="${explorerPage.catalog.titles.isEmpty()}">
									<div class="alert alert-info">
										<i class="cmsms-icon-info-circled-3 i-alert"></i>
										<p class="p-alert">
											<spring:message code="message.explorer.catalog"/><br>
											<a href="<c:url value="/company/overview/catalog"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.catalog"/></a> 
										</p>
									</div>
								</c:when>
								<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_album.jsp" /></c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>