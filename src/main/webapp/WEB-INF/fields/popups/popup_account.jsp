<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<li class="nav-item dropdown partner-menu partner-account">
	<c:set var="fatitle" scope="page"><spring:message code="tooltip.welcome" /> <c:out value="${currentUser.getUsername()}" /></c:set>
	<a class="nav-link lien text-truncate" data-toggle="dropdown" title="${pageScope.fatitle}">
		<spring:message code="tooltip.welcome" /> 
		<span class="font-gras i-white"><c:out value="${currentUser.getUsername()}" /></span><i class="cmsms-icon-down-dir i-8 m-l-10 i-position"></i>
	</a>
	<ul class="dropdown-menu" role="menu">
		<c:set var="providers" value="home-2,user-1" scope="page"></c:set>
		<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="liens" value="user/dashboard,user/account/profile" scope="page"></c:set></sec:authorize>
		<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')"><c:set var="liens" value="company/dashboard,user/dashboard" scope="page"></c:set></sec:authorize>
		<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="liens" value="admin/dashboard,user/dashboard" scope="page"></c:set></sec:authorize>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<li>
				<a href="<c:url value="/${pageScope.lien}"/>" class="dropdown-item transition-35">
					<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} m-r-10"></i>
					<spring:message code="sidebar.admin.dashboard${state.count}" />
				</a>
			</li>
		</c:forEach>
		<li class="dropdown-divider"></li>
		<li><a href="<c:url value="/logout"/>" class="dropdown-item transition-35"><i class="cmsms-icon-off m-r-10"></i><spring:message code="btn.logout" /></a></li>
	</ul>
</li>