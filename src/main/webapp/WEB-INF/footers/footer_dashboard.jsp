<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<footer class="dashboard-footer">
	<div class="text-center">
		<ul class="list-none list-inline list-footer-more font-mini text-left">
			<li><a href="<c:url value="/blog"/>" class="lien" target="_blank"><spring:message code="sidebar.admin.dashboard5"/></a></li>
			<c:set var="liens" value="faq,cgu,politique-confidentialite,mentions-legales,plan-du-site,credits" scope="page"></c:set>
			<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
				<li><a href="<c:url value="/infos/${pageScope.lien}"/>" class="lien" target="_blank"><spring:message code="explorer.mainmenu7.${state.count}"/></a></li>
			</c:forEach>
			<li><a href="<c:url value="/contacts"/>" class="lien" target="_blank"><spring:message code="sidebar.user.dashboard8.4"/></a></li>
		</ul>
	</div>
	<p class="font-mini text-center i-help">&copy;<span class="yearsApp">2021</span> <spring:message code="app.copyright"/></p>
	<div class="only-forprint m-t-20">
		<img height="32" src="<c:url value="/static/icons/logo-icon-min.jpg"/>"/><span class="font-mini">&copy;<span class="yearsApp">2021</span> <spring:message code="app.copyright"/></span>
	</div>
</footer>