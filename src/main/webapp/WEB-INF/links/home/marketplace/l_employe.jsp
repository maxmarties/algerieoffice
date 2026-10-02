<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:set var="ogtitle" scope="page"><spring:message code="explorer.desktop.grid7" /> - <c:out value="${inbox.title}"/></c:set>
<c:set var="ogseo" scope="page"><c:out value="${inbox.description}"/></c:set>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="article">
<meta property="og:title" content="${pageScope.ogtitle}">
<meta property="og:description" content="${pageScope.ogseo}">
<meta property="og:image" content="<c:url value="/static/picts/extras/employes-min.jpg" />">
<title>${pageScope.ogtitle}</title>
<meta name="description" content="${pageScope.ogseo}">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="homeMarketplace" value="4" scope="request"></c:set>