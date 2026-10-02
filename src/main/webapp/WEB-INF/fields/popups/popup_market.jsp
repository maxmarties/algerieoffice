<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="nav-text dropdown market-menu hidden-md-down">
	<a class="nav-link transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.market"/>">
		<i class="cmsms-icon-th-large"></i>
	</a>
	<ul class="dropdown-menu" role="menu">
		<li class="dropdown">
			<a href="<c:url value="/entreprises"/>" class="dropdown-item transition-35" target="_blank">
				<spring:message code="sidebar.admin.dashboard3"/><i class="cmsms-icon-explorer-right treeview-trigger i-8"></i>
			</a>
			<ul class="dropdown-menu" role="menu">
				<c:set var="liens" value="secteurs,villes,recherche/entreprises" scope="page"></c:set>
				<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
					<li><a href="<c:url value="/${pageScope.lien}"/>" class="dropdown-item transition-35" target="_blank">
						<spring:message code="explorer.home.mainmenu2.${state.count}" /><i class="cmsms-icon-link-ext treeview-trigger i-8"></i></a></li>
				</c:forEach>
			</ul>
		</li>
		<li class="dropdown">
			<a href="<c:url value="/marketplace"/>" class="dropdown-item transition-35" target="_blank">
				<spring:message code="sidebar.admin.dashboard4"/><i class="cmsms-icon-explorer-right treeview-trigger i-8"></i>
			</a>
			<ul class="dropdown-menu" role="menu">
				<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi,actualites" scope="page"></c:set>
				<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
					<li><a href="<c:url value="/marketplace/${pageScope.lien}"/>" class="dropdown-item transition-35" target="_blank">
						<spring:message code="wizard.screen.navbar${state.count}" /><i class="cmsms-icon-link-ext treeview-trigger i-8"></i></a></li>
					<c:if test="${state.count == 4}"><li class="dropdown-divider"></li></c:if>
				</c:forEach>
			</ul>
		</li>
		<li>
			<a href="<c:url value="/blog"/>" class="dropdown-item transition-35" target="_blank">
				<spring:message code="sidebar.admin.dashboard5"/><i class="cmsms-icon-link-ext treeview-trigger i-8"></i>
			</a>
		</li>
		<li class="dropdown-divider"></li>
		<li>
			<a href="<c:url value="/forums"/>" class="dropdown-item transition-35" target="_blank">
				<spring:message code="sidebar.home.mainfooter1.12"/><i class="cmsms-icon-paper-plane-3 treeview-trigger i-primary i-8"></i>
			</a>
		</li>
	</ul>
</li>