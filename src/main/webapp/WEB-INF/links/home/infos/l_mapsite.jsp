<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="article">
<meta property="og:title" content="<spring:message code="title.infos.mapsite"/>">
<meta property="og:description" content="<spring:message code="seo.infos.mapsite"/>">
<title><spring:message code="title.infos.mapsite"/></title>
<meta name="description" content="<spring:message code="seo.infos.mapsite"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeInfos" value="1" scope="request"></c:set>