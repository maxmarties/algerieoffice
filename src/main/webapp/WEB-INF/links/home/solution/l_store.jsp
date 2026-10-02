<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.solution.store"/>">
<meta property="og:description" content="<spring:message code="seo.solution.store"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/store-min.jpg" />">
<title><spring:message code="title.solution.store"/></title>
<meta name="description" content="<spring:message code="seo.solution.store"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/aos/aos.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeSolution" value="3" scope="request"></c:set>