<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${staticURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:message code="title.home"/>">
<meta property="og:description" content="<spring:message code="seo.home"/>">
<meta property="og:image" content="<c:url value="/static/picts/extras/home-min.jpg" />">
<title><spring:message code="title.home"/></title>
<meta name="description" content="<spring:message code="seo.home"/>">

<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/aos/aos.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeWebsite" value="1" scope="request"></c:set>
<c:set var="parseBlog" value="1" scope="request"></c:set>