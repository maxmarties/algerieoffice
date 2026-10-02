<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="isAnonymous()"><c:import url="/WEB-INF/fields/modals/modal_login.jsp"/></sec:authorize>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')"><c:import url="/WEB-INF/fields/modals/modal_easylist.jsp"/></sec:authorize>