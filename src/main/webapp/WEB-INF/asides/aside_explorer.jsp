<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<aside class="home-sidebar ${empty requestScope.ignoreResponsive ? 'responsive-sidebar' : ''}">
	<div class="sidebar flexed flex-colone flex-jusitify">
		<div class="sidebar-body">
			<div class="sidebar-scroll">
				<ul class="list-none list-block list-sidebar h-header">
					<sec:authorize access="isAnonymous()">
						<li>
							<a href="<c:url value="/users/login"/>" class="transition-color"><i class="cmsms-icon-user-3 provider-trigger"></i><span><spring:message code="btn.partner5"/></span></a>
							<ul class="list-none list-block sublist-sidebar animated fadeFromLeft">
								<li class="sublist-header"><span><spring:message code="btn.partner5"/></span></li>
								<c:set var="liens" value="register/user,users/login,register/company" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li><a href="<c:url value="/${pageScope.lien}"/>" class="transition-color"><spring:message code="btn.partner${state.count}" /></a></li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')">
						<li><a href="<c:url value="/user/dashboard"/>" 
							class="transition-color"><i class="cmsms-icon-user-3 provider-trigger"></i><span><spring:message code="sidebar.admin.dashboard2"/></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
						<li><a href="<c:url value="/company/dashboard"/>" 
							class="transition-color"><i class="cmsms-icon-home-3 provider-trigger"></i><span><spring:message code="sidebar.admin.dashboard1"/></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
						<li><a href="<c:url value="/admin/dashboard"/>" 
							class="transition-color"><i class="cmsms-icon-home-3 provider-trigger"></i><span><spring:message code="sidebar.admin.dashboard1"/></span></a></li>
					</sec:authorize>
					<li class="${!empty requestScope.homeCompany ? 'active' : ''}">
						<a href="<c:url value="/entreprises"/>" 
							class="transition-color"><i class="cmsms-icon-building provider-trigger"></i><span><spring:message code="sidebar.home.dashboard1"/></span></a>
						<ul class="list-none list-block sublist-sidebar animated fadeFromLeft">
							<li class="sublist-header"><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.home.dashboard1"/></a></li>
							<c:set var="liens" value="secteurs,villes,recherche/entreprises,trouver-vite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/${pageScope.lien}"/>" class="transition-color"><spring:message code="sidebar.home.dashboard1.${state.count}" /></a></li>
								<c:if test="${state.count == 3}"><li class="dropdown-divider"></li></c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="${!empty requestScope.homeMarketplace ? 'active' : ''}">
						<a href="<c:url value="/marketplace"/>" 
							class="transition-color"><i class="cmsms-icon-attach-3 provider-trigger"></i><span><spring:message code="sidebar.home.dashboard2"/></span></a>
						<ul class="list-none list-block sublist-sidebar animated fadeFromLeft">
							<li class="sublist-header"><a href="<c:url value="/marketplace"/>" class="lien"><spring:message code="sidebar.home.dashboard2"/></a></li>
							<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi,actualites" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/marketplace/${pageScope.lien}"/>" class="transition-color"><spring:message code="sidebar.home.dashboard2.${state.count}" /></a></li>
								<c:if test="${state.count == 4}"><li class="dropdown-divider"></li></c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="${!empty requestScope.homeSolution ? 'active' : ''}">
						<a href="<c:url value="/solutions"/>" 
							class="transition-color"><i class="cmsms-icon-rocket-2 provider-trigger"></i><span><spring:message code="sidebar.home.dashboard3"/></span></a>
						<ul class="list-none list-block sublist-sidebar animated fadeFromLeft">
							<li class="sublist-header"><a href="<c:url value="/solutions"/>" class="lien"><spring:message code="sidebar.home.dashboard3"/></a></li>
							<c:set var="liens" value="presentation,visibilite,referencement,detect,easylist,publicite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><a href="<c:url value="/solutions/${pageScope.lien}"/>" class="transition-color"><spring:message code="sidebar.home.dashboard3.${state.count}" /></a></li>
								<c:if test="${state.count == 5}"><li class="dropdown-divider"></li></c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="${!empty requestScope.homeBlog ? 'active' : ''}">
						<a href="<c:url value="/blog"/>" class="transition-color"><i class="cmsms-icon-newspaper-2 provider-trigger"></i><span><spring:message code="sidebar.home.dashboard4"/></span></a>
					</li>
					<li class="${!empty requestScope.homeContact ? 'active' : ''}">
						<a href="<c:url value="/contacts"/>" class="transition-color"><i class="cmsms-icon-message provider-trigger"></i><span><spring:message code="sidebar.home.dashboard5"/></span></a>
					</li>
				</ul>
			</div>
		</div>
		<ul class="list-none list-block list-sidebar list-bottom h-header">
			<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
				<li><a href="<c:url value="/logout"/>" class="transition-color"><i class="cmsms-icon-off provider-trigger"></i><span><spring:message code="btn.signout"/></span></a></li>
			</sec:authorize>
		</ul>
	</div>
</aside>