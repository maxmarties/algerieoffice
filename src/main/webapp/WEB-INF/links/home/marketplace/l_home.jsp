<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.marketplace.home"/>">
<meta property="og:description" content="<spring:message code="seo.marketplace.home"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/aomrk-min.jpg" />">
<title><spring:message code="title.marketplace.home"/></title>
<meta name="description" content="<spring:message code="seo.marketplace.home"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeMarketplace" value="0" scope="request"></c:set>