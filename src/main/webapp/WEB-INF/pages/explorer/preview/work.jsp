<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" class="lien"><c:out value="${explorerCompany.profile.tradename}"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}/presentation"/>" class="lien"><spring:message code="explorer.mainmenu2"/></a></li>
			<li><a href="<c:url value="${explorerCurrent.companyURL}/realisations"/>" class="lien"><spring:message code="explorer.mainmenu2.4"/></a></li>
			<li class="active"><c:out value="${explorerPage.inbox.title}"/></li>
		</ol>
		<c:set var="hrefBackword" value="${explorerCurrent.companyURL}/realisations" scope="request"></c:set>
		<c:set var="explorerBackword" value="4" scope="request"></c:set>
		<c:import url="/WEB-INF/explorer/sidebars/sidebar_backword.jsp" />
		<div class="row">
			<div class="col-lg-3 m-t-20 hidden-md-down"><c:import url="/WEB-INF/explorer/sidebars/sidebar_element.jsp" /></div>
			<div class="col-lg-9 m-t-20"><c:import url="/WEB-INF/explorer/desktops/desktop_work.jsp" /></div>
		</div>
	</div>
</div>