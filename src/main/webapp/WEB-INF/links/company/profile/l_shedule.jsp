<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="sidebar.company.dashboard6.3"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewProfil" value="3" scope="request"></c:set>
<c:set var="applyFooter" value="1" scope="request"></c:set>