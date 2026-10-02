<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" class="lien"><c:out value="${explorerCompany.profile.tradename}"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}/presentation"/>" class="lien"><spring:message code="explorer.mainmenu2"/></a></li>
			<li class="active"><span><spring:message code="explorer.mainmenu2.1"/></span></li>
		</ol>
		<div class="row">
			<div class="col-lg-3 m-t-10">
				<c:import url="/WEB-INF/explorer/sidebars/sidebar_presentation.jsp" />
				<c:if test="${explorerCompany.menu.hasSticky()}"><c:import url="/WEB-INF/explorer/widgets/widget_sticky.jsp" /></c:if>
			</div>
			<div class="col-lg-9 m-t-10">
				<div class="explorer-column">
					<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="explorer.mainmenu2.1"/></h1></div>
					<div class="explorer-body">
						<c:choose>
							<c:when test="${empty explorerPage.timeline.history && explorerPage.timeline.titles.isEmpty()}">
								<div class="alert alert-info">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.timeline"/><br>
										<a href="<c:url value="/company/overview/timeline"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.timeline"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/desktops/desktop_timeline.jsp" /></c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>