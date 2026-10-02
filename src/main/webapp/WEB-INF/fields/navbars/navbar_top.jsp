<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<nav class="navbar navbar-top navbar-expand-lg">
	<div class="container">
		<ul class="navbar-nav">
			<li class="nav-item nav-control m-r-5 hidden-md-up" style="width:34px;">
				<a class="transition-color" data-toggle="push-menu" title="<spring:message code="tooltip.sidebar.menu" />"><i class="cmsms-icon-menu-3"></i></a>
			</li>
			<c:import url="/WEB-INF/fields/popups/popup_langage.jsp" />
			<li class="nav-text nav-address hidden-md-down"><i class="cmsms-icon-location-5 i-white m-r-10"></i><span><spring:message code="app.address" /></span></li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-text hidden-md-down m-r-5">
				<a href="mailto:<spring:message code="app.contact" />" class="nav-link lien" title="<spring:message code="tooltip.contact"/>">
					<i class="cmsms-icon-mail-alt i-white m-r-5"></i><spring:message code="app.contact" /></a>
			</li>
			<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/fields/popups/popup_partner.jsp"/></sec:authorize>
			<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')"><c:import url="/WEB-INF/fields/popups/popup_account.jsp"/></sec:authorize>
		</ul>
	</div>
</nav>