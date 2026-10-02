<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="header.forums.${empty topic.id ? 'new' : 'edit'}"/> | <spring:message code="app.forums"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/forums.min.css"/>'>