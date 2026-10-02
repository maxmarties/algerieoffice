<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<nav class="navbar navbar-top nav-forum navbar-expand-lg">
	<div class="container">
		<ul class="navbar-nav">
			<li class="nav-item nav-control m-r-5">
				<a href="<c:url value="/"/>" class="transition-35" title="<spring:message code="tooltip.forums.menu" />"><i class="cmsms-icon-explorer-back"></i></a>
			</li>
			<c:choose>
				<c:when test="${empty inbox}"><c:import url="/WEB-INF/fields/popups/popup_langage.jsp" /></c:when>
				<c:otherwise>
					<li class="nav-item nav-control nav-forumslang">
						<a class="nav-link lien" title="<spring:message code="tooltip.topiclang"/>">
							<img height="14" src="<c:url value="/static/vectors/${inbox.language}-min.png" />" alt="${inbox.language}" class="m-r-5">
							<c:out value="${inbox.language == 'fr' ? 'Français' : inbox.language == 'en' ? 'English' : 'العربية'}" />
						</a>
					</li>
				</c:otherwise>
			</c:choose>
			<li class="nav-text nav-address hidden-md-down"><i class="cmsms-icon-location-5 i-white m-r-10"></i><span><spring:message code="app.address" /></span></li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-text hidden-md-down m-r-5">
				<a href="mailto:<spring:message code="app.contact" />" class="nav-link lien" title="<spring:message code="tooltip.contact"/>">
					<i class="cmsms-icon-mail-alt m-r-5"></i><spring:message code="app.contact" /></a>
			</li>
			<c:import url="/WEB-INF/fields/popups/popup_account.jsp"/>
		</ul>
	</div>
</nav>