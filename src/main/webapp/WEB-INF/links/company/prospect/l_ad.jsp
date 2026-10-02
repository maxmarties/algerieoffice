<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="header.prospect.ads.edit"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewProspect" value="2" scope="request"></c:set>