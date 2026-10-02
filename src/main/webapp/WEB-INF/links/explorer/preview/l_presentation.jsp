<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><c:out value="${explorerCompany.profile.tradename}"/> | <spring:message code="sidebar.company.dashboard4.2"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="explorerMainmenu" value="2" scope="request"></c:set>