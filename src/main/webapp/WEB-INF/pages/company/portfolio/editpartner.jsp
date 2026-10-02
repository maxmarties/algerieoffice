<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/portfolio/works"/>" class="lien lien-black">
			<i class="cmsms-icon-book m-r-5"></i><spring:message code="sidebar.company.dashboard3"/></a></li>
		<li><a href="<c:url value="/company/portfolio/partners"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard3.5"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.portfolio.partner.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.portfolio5.2"/></p>
	</div>
	<c:set var="backwordURL" value="/company/portfolio/partners" scope="request"></c:set>
	<c:set var="backwordPage" value="10" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
			<c:import url="/WEB-INF/pages/company/portfolio/partner/form_partner.jsp"/>
		</sec:authorize>
	</div>
</div>