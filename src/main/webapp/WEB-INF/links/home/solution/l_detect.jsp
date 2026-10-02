<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.solution.detect"/>">
<meta property="og:description" content="<spring:message code="seo.solution.detect"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/detect-min.jpg" />">
<title><spring:message code="title.solution.detect"/></title>
<meta name="description" content="<spring:message code="seo.solution.detect"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/aos/aos.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeSolution" value="4" scope="request"></c:set>