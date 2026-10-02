<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<header class="home-menu fixed-top">
	<c:import url="/WEB-INF/fields/navbars/navbar_admin.jsp" />
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
			<li class="divider"></li>
			<c:import url="/WEB-INF/basics/navbar_search.jsp"/>
		</ul>
		<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
			<li class="divider"></li>
			<c:import url="/WEB-INF/fields/popups/popup_messages.jsp"/>
			<c:import url="/WEB-INF/fields/popups/popup_notifications.jsp"/>
			<c:import url="/WEB-INF/fields/popups/popup_supports.jsp"/>
			<li class="divider hidden-sm-down"></li>
			<c:import url="/WEB-INF/fields/popups/popup_project.jsp"/>
			<li class="divider"></li>
			<c:import url="/WEB-INF/fields/popups/popup_user.jsp"/>
		</ul>
	</nav>
</header>