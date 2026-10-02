<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-down h-header">
	<div class="container">
		<div class="row">
			<div class="col-lg-4 m-t-10 m-b-10">
				<p class="font-mini">&copy; <span class="yearsApp">2021</span> <spring:message code="app.copyright"/></p>
			</div>
			<div class="col-lg-8 m-t-10 m-b-10">
			 	<ul class="list-none list-inline list-footer-more font-mini text-right">
			 		<li><a href="<c:url value="/blog"/>" class="lien"><spring:message code="sidebar.admin.dashboard5"/></a></li>
			 		<c:set var="liens" value="faq,cgu,politique-confidentialite,mentions-legales,plan-du-site,credits" scope="page"></c:set>
					<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
						<li><a href="<c:url value="/infos/${pageScope.lien}"/>" class="lien"><spring:message code="explorer.mainmenu7.${state.count}"/></a></li>
			 		</c:forEach>
			 		<li><a href="<c:url value="/contacts"/>" class="lien"><spring:message code="sidebar.user.dashboard8.4"/></a></li>
			 	</ul>
			</div>
		</div>
	</div>
</div>