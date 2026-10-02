<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="header.team.agent.${empty agent.id ? 'new' : 'edit'}"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/bootstrap/bootstrap-ui.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/cropper.min.css"/>'>
<c:set var="treeviewTeam" value="3" scope="request"></c:set>