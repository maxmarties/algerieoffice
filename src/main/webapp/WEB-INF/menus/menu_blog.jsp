<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<header class="home-menu fixed-top">
	<c:import url="/WEB-INF/fields/navbars/navbar_blog.jsp" />
	<nav class="navbar navbar-pc navbar-blog navbar-expand-lg transition-height">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand hidden-xs-down" href="<c:url value="/"/>">
					<img class="transition-height" src="<c:url value="/static/icons/branded-${currentConfig.defaultStyle == 3 ? 'white' : 'logo'}.svg"/>" alt="<spring:message code="app.brand"/>" />
				</a>
				<a class="navbar-brand hidden-xs-up" href="<c:url value="/"/>">
					<img class="transition-height" src="<c:url value="/static/icons/apple-${currentConfig.defaultStyle == 3 ? 'white' : 'black'}-min.png"/>" alt="<spring:message code="app.brand"/>" />
				</a>
			</div>
			<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
				<li class="nav-search hidden-sm-down">
					<form name="searchArticleForm" action="/" novalidate="novalidate">
						<div class="form-group m-b-0">
							<div class="input-group-find">
								<i class="cmsms-icon-search-1 icon-find"></i>
								<input class="form-control" type="search" id="searcharticle" name="searcharticle" placeholder="<spring:message code="tool.find.blog" />" value="${search}" />
							</div>
						</div>
					</form>
				</li>
				<li class="divider"></li>
				<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/basics/navbar_social.jsp"/></sec:authorize>
				<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
					<c:import url="/WEB-INF/fields/popups/popup_messages.jsp"/>
					<c:import url="/WEB-INF/fields/popups/popup_notifications.jsp"/>
				</sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
					<c:import url="/WEB-INF/fields/popups/popup_clouds.jsp"/>
				</sec:authorize>
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
					<c:import url="/WEB-INF/fields/popups/popup_supports.jsp"/>
				</sec:authorize>
			</ul>
		</div>
	</nav>
</header>