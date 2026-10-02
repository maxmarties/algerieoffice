<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="sidebar-langage hidden">
	<ul class="navbar-nav nav-flex-icons">
		<c:set var="providers" value="fr,en,ar" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}">
			<li>
				<a class="${langage.lang == pageScope.provider ? 'active' : ''}" data-toggle="language" data-language="${pageScope.provider}" 
					title="<c:out value="${pageScope.provider == 'fr' ? 'Français' : pageScope.provider == 'en' ? 'English' : 'العربية'}" />">
					<img height="18" src="<c:url value="/static/vectors/${pageScope.provider}-min.png" />" alt="${pageScope.provider}">
				</a>
			</li>
		</c:forEach>
	</ul>
	<a href="<c:url value="/logout"/>" class="lien lien-logout"><i class="cmsms-icon-off i-segond i-22"></i><spring:message code="btn.signout" /></a>
</div>