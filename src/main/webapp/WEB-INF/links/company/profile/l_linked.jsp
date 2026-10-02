<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="sidebar.company.dashboard6.5"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/bootstrap/bootstrap-ui.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/cropper.min.css"/>'>
<c:set var="treeviewProfil" value="5" scope="request"></c:set>
<c:set var="applyFooter" value="1" scope="request"></c:set>