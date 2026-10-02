<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:set var="ogtitle" scope="page"><spring:message code="title.wilaya"/> <spring:message code="chose.wilaya${wilaya}"/>.</c:set>
<c:set var="ogseo" scope="page"><spring:message code="seo.wilaya"/> <spring:message code="chose.wilaya${wilaya}"/></c:set>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="${pageScope.ogtitle}">
<meta property="og:description" content="${pageScope.ogseo}">
<meta property="og:image" content="<c:url value="/static/picts/extras/villes-min.jpg" />">
<title>${pageScope.ogtitle}</title>
<meta name="description" content="${pageScope.ogseo}">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeCompany" value="2" scope="request"></c:set>