<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="lien.explorer.delete"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewTools" value="1" scope="request"></c:set>