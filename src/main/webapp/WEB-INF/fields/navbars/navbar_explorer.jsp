<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<nav class="navbar navbar-top navbar-expand-lg">
	<div class="container">
		<ul class="navbar-nav">
			<li class="nav-item nav-control m-r-5">
				<a class="transition-35" data-toggle="push-menu" title="<spring:message code="tooltip.sidebar.menu" />"><i class="cmsms-icon-menu-3"></i></a>
			</li>
			<li class="nav-item nav-control nav-explorerlang">
				<a class="nav-link lien" title="<spring:message code="tooltip.companylang"/>">
					<img height="14" src="<c:url value="/static/vectors/${explorerCompany.profile.language}-min.png" />" 
						alt="${explorerCompany.profile.language}" class="m-r-5">
					<c:out value="${explorerCompany.profile.language == 'fr' ? 'Français' : explorerCompany.profile.language == 'en' ? 'English' : 'العربية'}" />
				</a>
			</li>
			<c:if test="${explorerCurrent.hasPreview}">
				<li class="nav-text">
					<a href="<c:url value="/company/dashboard"/>" class="nav-link lien" title="<spring:message code="tooltip.company.preview" />">
						<i class="cmsms-icon-${explorerCompany.profile.language == 'ar' ? 'right' : 'left'}-open i-8 m-r-5"></i>
						<span class="hidden-sm-down"><spring:message code="tooltip.company.preview" /></span>
					</a>
				</li>
			</c:if>
		</ul>
		<ul class="navbar-nav ml-auto">
			<c:if test="${explorerCurrent.premium != 0}">
				<li class="nav-item nav-premium hidden-sm-down">
					<ul class="list-none list-premium btn-warning">
						<li class="m-r-5"><spring:message code="lbl.premium" /></li>
						<c:forEach var="i" begin="1" end="${explorerCurrent.premium}" step="1">
							<li><i class="cmsms-icon-plus-circled-1"></i></li>
						</c:forEach>
					</ul>
				</li>
			</c:if>
			<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/fields/popups/popup_partner.jsp"/></sec:authorize>
			<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')"><c:import url="/WEB-INF/fields/popups/popup_account.jsp"/></sec:authorize>
		</ul>
	</div>
</nav>