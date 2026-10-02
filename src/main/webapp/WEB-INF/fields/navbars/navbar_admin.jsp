<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<nav class="navbar navbar-top navbar-expand-lg">
	<ul class="navbar-nav">
		<li class="nav-item nav-control m-r-5">
			<a id="collapseMenu" class="transition-35 hidden-md-down" title="<spring:message code="tooltip.sidebar.${currentConfig.menuCollapse ? 'close' : 'open'}" />"><i class="cmsms-icon-menu-3"></i></a>
			<a class="transition-35 hidden-md-up" data-toggle="push-menu" title="<spring:message code="tooltip.sidebar.menu" />"><i class="cmsms-icon-menu-3"></i></a>
		</li>
		<c:import url="/WEB-INF/fields/popups/popup_langage.jsp" />
		<li class="nav-text hidden-md-down">
			<a href="<c:url value="https://analytics.google.com/analytics/web/?authuser=1#/p271609230/reports/defaulthome"/>" class="nav-link lien" target="_blank">
				<i class="cmsms-icon-chart-bar-2 m-r-5"></i><spring:message code="tooltip.analytic" />
			</a>
		</li>
		<c:import url="/WEB-INF/fields/popups/popup_market.jsp" />
	</ul>
	<ul class="navbar-nav ml-auto"><c:import url="/WEB-INF/fields/popups/popup_account.jsp"/></ul>
</nav>