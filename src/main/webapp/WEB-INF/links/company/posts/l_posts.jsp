<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="sidebar.company.dashboard1.1"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewPosts" value="1" scope="request"></c:set>