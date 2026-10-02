<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="subheader.case2.1"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewHelp" value="1" scope="request"></c:set>