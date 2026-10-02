<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="nav-item dropdown langage-menu">
	<a class="nav-link lien" data-toggle="dropdown" title="<spring:message code="tooltip.langage"/>">
		<img height="14" src="<c:url value="/static/vectors/${langage.lang}-min.png" />" alt="${langage.lang}" class="m-r-5">
		<c:out value="${langage.lang == 'fr' ? 'Français' : langage.lang == 'en' ? 'English' : 'العربية'}" />
		<i class="cmsms-icon-down-dir i-8 m-l-10"></i>
	</a>
	<ul class="dropdown-menu" role="menu">
		<c:set var="providers" value="${langage.lang == 'fr' ? 'en,ar' : langage.lang == 'ar' ? 'fr,en' : 'fr,ar'}" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}">
			<li>
				<a class="dropdown-item transition-35" data-toggle="language" data-language="${pageScope.provider}">
					<img height="14" src="<c:url value="/static/vectors/${pageScope.provider}-min.png" />" alt="${pageScope.provider}" class="m-r-5">
					<c:out value="${pageScope.provider == 'fr' ? 'Français' : pageScope.provider == 'en' ? 'English' : 'العربية'}" />
				</a>
			</li>
		</c:forEach>
	</ul>
</li>