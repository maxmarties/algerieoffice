<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" class="lien"><c:out value="${explorerCompany.profile.tradename}"/></a></li>
			<li class="active"><spring:message code="explorer.mainmenu5"/></li>
		</ol>
		<div class="row row-mini">
			<div class="col-lg-4 col-mini m-t-10"><c:import url="/WEB-INF/explorer/sidebars/sidebar_contact.jsp" /></div>
			<div class="col-lg-8 col-mini m-t-10 m-b-10">
				<div class="explorer-column h-100">
					<div class="explorer-title">
						<h1 class="h-doc h-explorer h-explorer1">
							<spring:message code="explorer.subheader.contact${explorerPage.inbox.stateBriefcase()}"/> 
							<c:out value="${explorerCompany.profile.tradename}"/>
						</h1>
					</div>
					<div class="explorer-body"><c:import url="/WEB-INF/explorer/desktops/desktop_contact.jsp" /></div>
				</div>
			</div>
		</div>
		<c:if test="${!explorerPage.agents.isEmpty()}"><c:import url="/WEB-INF/explorer/widgets/widget_contact.jsp" /></c:if>
	</div>
</div>