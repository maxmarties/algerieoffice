<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" class="lien"><c:out value="${explorerCompany.profile.tradename}"/></a></li>
			<li class="active"><spring:message code="sidebar.company.dashboard2.3"/></li>
		</ol>
		<div class="row">
			<div class="col-lg-3 m-t-10">
				<c:import url="/WEB-INF/explorer/sidebars/sidebar_jobs.jsp" />
				<c:if test="${explorerCompany.menu.hasSticky()}"><c:import url="/WEB-INF/explorer/widgets/widget_sticky.jsp" /></c:if>
			</div>
			<div class="col-lg-9 m-t-10">
				<div id="desktopElements">
					<c:set var="choseSortersDesktop" value="expire,domaine,date" scope="request"></c:set>
					<c:import url="/WEB-INF/explorer/sidebars/sidebar_elements.jsp" />
					<div class="explorer-loader m-t-20">
						<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
						<div id="desktopLoad"></div>
					</div>
					<c:import url="/WEB-INF/explorer/sidebars/sidebar_pagination.jsp" />
				</div>
			</div>
		</div>
	</div>
</div>