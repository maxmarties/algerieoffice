<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<nav class="navbar navbar-top navbar-expand-lg">
	<ul class="navbar-nav">
		<li class="nav-item nav-control m-r-5">
			<a id="collapseMenu" class="transition-35 hidden-md-down" title="<spring:message code="tooltip.sidebar.${currentConfig.menuCollapse ? 'close' : 'open'}" />"><i class="cmsms-icon-menu-3"></i></a>
			<a class="transition-35 hidden-md-up" data-toggle="push-menu" title="<spring:message code="tooltip.sidebar.menu" />"><i class="cmsms-icon-menu-3"></i></a>
		</li>
		<c:import url="/WEB-INF/fields/popups/popup_langage.jsp" />
		<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
			<li class="nav-text">
				<a href="<c:url value="/explorer/preview"/>" class="nav-link lien" target="_blank" title="<spring:message code="tooltip.company.overiew" />">
					<i class="cmsms-icon-paper-plane-3 m-r-5"></i><span class="hidden-sm-down"><spring:message code="tooltip.company.overiew" /></span>
				</a>
			</li>
			<c:import url="/WEB-INF/fields/popups/popup_market.jsp" />
		</sec:authorize>
	</ul>
	<ul class="navbar-nav ml-auto">
		<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
			<c:if test="${!currentCompany.hasPremium() && currentCompany.hasIgnoreWelcome()}">
				<li class="nav-premium hidden-sm-down"><a href="<c:url value="/company/tools/subscribes/new"/>" class="btn btn-warning"><span><spring:message code="lien.premium" /></span></a></li>
			</c:if>
		</sec:authorize>
		<c:import url="/WEB-INF/fields/popups/popup_account.jsp"/>
	</ul>
</nav>