<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-sidebar h-header">
	<a href="<c:url value="${explorerCurrent.companyURL}/presentation"/>" class="sidebar-header bn-explorer-primary"><spring:message code="explorer.mainmenu2"/></a>
	<ul class="sidebar-mainmenu list-none">
		<c:set var="mainsidebar" value="${explorerCompany.menu.sidebar()}" scope="page"></c:set>
		<c:set var="liens" value="historique,actualites,evenements,realisations,faqs,partenaires" scope="page"></c:set>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<c:if test="${pageScope.mainsidebar[state.count - 1]}">
				<li class="${requestScope.explorerMainsibdebar == state.count ? 'active' : ''}">
					<a href="<c:url value="${explorerCurrent.companyURL}/${pageScope.lien}"/>" class="transition-color"><spring:message code="explorer.mainmenu2.${state.count}"/></a>
				</li>
			</c:if>
		</c:forEach>
	</ul>
</div>