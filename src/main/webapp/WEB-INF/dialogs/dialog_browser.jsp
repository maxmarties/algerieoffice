<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/fields/modals/modal_login.jsp"/></sec:authorize>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<c:import url="/WEB-INF/fields/modals/modal_favorite.jsp"/>
	<c:import url="/WEB-INF/fields/modals/modal_evaluation.jsp"/>
	<c:import url="/WEB-INF/fields/modals/modal_notice.jsp"/>
	<c:import url="/WEB-INF/fields/modals/modal_appointment.jsp"/>
	<c:import url="/WEB-INF/fields/modals/modal_report.jsp"/>
	<c:if test="${!currentUser.hasCompany()}"><c:import url="/WEB-INF/fields/modals/modal_collaborate.jsp"/></c:if>
</sec:authorize>
