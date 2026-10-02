<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<nav class="navbar">
		<ul class="nav nav-screen">
			<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi" scope="page"></c:set>
			<c:set var="providers" value="bag,pin-1,calendar-7,coffee" scope="page"></c:set>
			<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
				<li class="h-header">
					<a href="<c:url value="/marketplace/${pageScope.lien}"/>" class="transition-35 ${state.count == requestScope.treeviewNav ? 'active' : ''}" 
						title="<spring:message code="wizard.screen.navbar${state.count}"/>">
						<i class="breadview-trigger cmsms-icon-down-open"></i>
						<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} i-20 m-r-10"></i>
						<span class="hidden-sm-down"><spring:message code="wizard.screen.navbar${state.count}"/></span>
					</a>
				</li>
			</c:forEach>
		</ul>
	</nav>
</div>