<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/fields/modals/modal_login.jsp"/></sec:authorize>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<c:if test="${!empty rate}">
		<c:import url="/WEB-INF/fields/modals/modal_lock.jsp"/>
		<c:import url="/WEB-INF/fields/modals/modal_rate.jsp"/>
	</c:if>
</sec:authorize>