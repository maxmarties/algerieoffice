<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<header class="home-menu fixed-top">
	<c:import url="/WEB-INF/fields/navbars/navbar_top.jsp" />
	<div class="navbar-container transition-height">
		<nav class="navbar navbar-pc navbar-expand-lg transition-3d">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand hidden-xs-down" href="<c:url value="/"/>">
						<img class="transition-height" src="<c:url value="/static/icons/branded-${currentConfig.defaultStyle == 3 ? 'white' : 'logo'}.svg"/>" alt="<spring:message code="app.brand"/>" />
					</a>
					<a class="navbar-brand hidden-xs-up" href="<c:url value="/"/>">
						<img class="transition-height" src="<c:url value="/static/icons/apple-${currentConfig.defaultStyle == 3 ? 'white' : 'black'}-min.png"/>" alt="<spring:message code="app.brand"/>" />
					</a>
				</div>
				<ul class="navbar-nav navbar-screen hidden-md-down">
					<li><a href="<c:url value="/"/>" class="lien ${!empty requestScope.homeWebsite ? 'active' : ''}"><spring:message code="explorer.mainmenu1"/></a></li>
					<li class="dropdown">
						<a href="<c:url value="/entreprises"/>" 
							class="lien ${!empty requestScope.homeCompany ? 'active' : ''}"><spring:message code="sidebar.admin.dashboard3"/><i class="cmsms-icon-angle-down i-11 m-l-10"></i></a>
						<ul class="dropdown-menu animated fadeInDown" role="menu">
							<c:set var="liens" value="secteurs,villes,recherche/entreprises,trouver-vite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/${pageScope.lien}"/>" class="lien ${requestScope.homeCompany == state.count ? 'active' : ''}">
									<spring:message code="explorer.home.mainmenu2.${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
					<li class="dropdown">
						<a href="<c:url value="/marketplace"/>" 
							class="lien ${!empty requestScope.homeMarketplace ? 'active' : ''}"><spring:message code="sidebar.admin.dashboard4"/><i class="cmsms-icon-angle-down i-11 m-l-10"></i></a>
						<ul class="dropdown-menu animated fadeInDown" role="menu">
							<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi,actualites" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/marketplace/${pageScope.lien}"/>" class="lien ${requestScope.homeMarketplace == state.count ? 'active' : ''}">
									<spring:message code="wizard.screen.navbar${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
					<li class="dropdown">
						<a href="<c:url value="/solutions"/>" 
							class="lien ${!empty requestScope.homeSolution ? 'active' : ''}"><spring:message code="sidebar.home.dashboard6"/><i class="cmsms-icon-angle-down i-11 m-l-10"></i></a>
						<ul class="dropdown-menu animated fadeInDown" role="menu">
							<c:set var="liens" value="presentation,visibilite,referencement,detect,easylist,publicite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/solutions/${pageScope.lien}"/>" class="lien ${requestScope.homeSolution == state.count ? 'active' : ''}">
									<spring:message code="explorer.home.mainmenu4.${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
					<li><a href="<c:url value="/blog"/>" class="lien ${!empty requestScope.homeBlog ? 'active' : ''}"><spring:message code="sidebar.admin.dashboard5"/></a></li>
					<li><a href="<c:url value="/contacts"/>" class="lien ${!empty requestScope.homeContact ? 'active' : ''}"><spring:message code="sidebar.admin.dashboard9.6"/></a></li>
				</ul>
				<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
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
					<li class="divider hidden-md-down"></li>
					<li class="nav-push hidden-md-down"><a data-toggle="push-search" title="<spring:message code="tool.find.company"/>"><i class="cmsms-icon-search-1"></i></a></li>
				</ul>
			</div>
		</nav>
		<nav class="navbar navbar-backword navbar-expand-lg transition-3d">
			<div class="container">
				<a class="close-backword transition-color m-r-10" data-toggle="push-search" title="<spring:message code="btn.close"/>"><i class="cmsms-icon-cancel-2"></i></a>
				<form name="searchMainForm" class="form-main" action="/" novalidate="novalidate">
					<div class="row row-mini">
						<div class="form-group col-lg-6 col-mini m-b-0">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.token"/></c:set>
							<div class="input-group-token">
								<span class="indSearch"><spring:message code="tool.view.token" /></span>
								<input class="form-control" type="search" id="findmain" name="findmain" placeholder="${pageScope.placeholderFind}"/>
							</div>
						</div>
						<div class="form-group col-lg-4 col-mini m-b-0">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.location"/></c:set>
							<div class="input-group-location">
								<span class="indSearch"><spring:message code="tool.view.location" /></span><i class="cmsms-icon-location i-red trigger-location"></i>
								<select class="form-select2" id="locationmain" name="locationmain" data-placeholder="${pageScope.placeholderFind}" >
									<option></option>
									<option value="0"><spring:message code="comp.target" /></option>
									<c:forEach var="i" begin="1" end="48" step="1">
										<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group col-lg-2 col-mini m-b-0">
							<button type="submit" class="btn btn-segond btn-simple btn-block">
								<span><i class="cmsms-icon-search-1 m-r-10"></i><spring:message code="btn.find" /></span>
							</button>
						</div>
					</div>
				</form>
			</div>
		</nav>
	</div>
</header>