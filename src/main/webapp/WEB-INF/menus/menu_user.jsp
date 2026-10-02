<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<header class="home-menu fixed-top">
	<c:import url="/WEB-INF/fields/navbars/navbar_user.jsp" />
	<nav class="navbar navbar-pc navbar-dashboard navbar-expand-lg">
		<div class="navbar-header">
			<a class="navbar-brand hidden-md-down" href="<c:url value="/"/>">
				<img src="<c:url value="/static/icons/branded-${currentConfig.defaultStyle == 3 ? 'white' : 'logo'}.svg"/>" alt="<spring:message code="app.brand"/>" />
			</a>
			<a class="navbar-brand hidden-md-up" href="<c:url value="/"/>">
				<img src="<c:url value="/static/icons/apple-${currentConfig.defaultStyle == 3 ? 'white' : 'black'}-min.png"/>" alt="<spring:message code="app.brand"/>" />
			</a>
		</div>
		<ul class="navbar-nav nav-flex-icons navbar-icons hidden-xs-down">
			<li class="divider"></li>
			<c:import url="/WEB-INF/fields/popups/popup_companies.jsp"/>
			<c:import url="/WEB-INF/fields/popups/popup_members.jsp"/>
			<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
				<li class="divider"></li>
				<c:import url="/WEB-INF/fields/popups/popup_users.jsp"/>
			</sec:authorize>
			<li class="divider"></li>
			<c:import url="/WEB-INF/basics/navbar_search.jsp"/>
		</ul>
		<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
			<sec:authorize access="!hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
				<c:import url="/WEB-INF/fields/popups/popup_support.jsp"/>
			</sec:authorize>
			<li class="divider"></li>
			<c:import url="/WEB-INF/fields/popups/popup_messages.jsp"/>
			<c:import url="/WEB-INF/fields/popups/popup_notifications.jsp"/>
			<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
				<c:import url="/WEB-INF/fields/popups/popup_clouds.jsp"/>
			</sec:authorize>
			<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
				<c:import url="/WEB-INF/fields/popups/popup_supports.jsp"/>
			</sec:authorize>
			<li class="divider"></li>
			<c:import url="/WEB-INF/fields/popups/popup_project.jsp"/>
			<li class="divider hidden-md-down"></li>
			<c:import url="/WEB-INF/fields/popups/popup_user.jsp"/>
		</ul>
	</nav>
</header>