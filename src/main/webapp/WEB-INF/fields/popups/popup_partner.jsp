<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="nav-item dropdown partner-menu">
	<a class="nav-link lien" data-toggle="dropdown" title="<spring:message code="btn.partner" />">
		<i class="cmsms-icon-lock-5 m-r-5"></i><span class="hidden-xs-down"><spring:message code="btn.partner" /></span><i class="cmsms-icon-down-dir i-8 m-l-10"></i>
	</a>
	<ul class="dropdown-menu" role="menu">
		<c:set var="liens" value="register/user,users/login,register/company" scope="page"></c:set>
		<c:set var="providers" value="user-male,login,building-filled" scope="page"></c:set>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<li>
				<a href="<c:url value="/${pageScope.lien}"/>" class="dropdown-item transition-35">
					<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} m-r-10"></i><spring:message code="btn.partner${state.count}" />
				</a>
			</li>
			<c:if test="${state.count == 2}"><li class="dropdown-divider"></li></c:if>
		</c:forEach>
	</ul>
</li>