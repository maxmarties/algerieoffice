<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.solution.home"/>">
<meta property="og:description" content="<spring:message code="seo.solution.home"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/solutions-min.jpg" />">
<title><spring:message code="title.solution.home"/></title>
<meta name="description" content="<spring:message code="seo.solution.home"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/aos/aos.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeSolution" value="0" scope="request"></c:set>