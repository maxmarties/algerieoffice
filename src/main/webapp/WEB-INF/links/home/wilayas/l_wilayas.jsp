<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.wilayas"/>">
<meta property="og:description" content="<spring:message code="seo.wilayas"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/villes-min.jpg" />">
<title><spring:message code="title.wilayas"/></title>
<meta name="description" content="<spring:message code="seo.wilayas"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeCompany" value="2" scope="request"></c:set>