<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:set var="ogtitle" scope="page"><spring:message code="explorer.desktop.grid2" /> - <c:out value="${inbox.title}"/></c:set>
<c:set var="ogseo" scope="page"><c:out value="${inbox.description}"/></c:set>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="article">
<meta property="og:title" content="${pageScope.ogtitle}">
<meta property="og:description" content="${pageScope.ogseo}">
<meta property="og:image" content="<c:url value="${inbox.photoURL}" />">
<title>${pageScope.ogtitle}</title>
<meta name="description" content="${pageScope.ogseo}">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeMarketplace" value="5" scope="request"></c:set>