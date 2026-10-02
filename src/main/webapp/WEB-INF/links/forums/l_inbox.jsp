<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><c:out value="${inbox.title}"/> | <spring:message code="app.forums"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/forums.min.css"/>'>