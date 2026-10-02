<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.marketplace.news"/>">
<meta property="og:description" content="<spring:message code="seo.marketplace.news"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/aonw-min.jpg" />">
<title><spring:message code="title.marketplace.news"/></title>
<meta name="description" content="<spring:message code="seo.marketplace.news"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeMarketplace" value="5" scope="request"></c:set>