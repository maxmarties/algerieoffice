<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="explorer.desktop.grid6" /> - <c:out value="${explorerPage.inbox.title}"/> | <c:out value="${explorerCompany.profile.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="explorerMainmenu" value="4" scope="request"></c:set>