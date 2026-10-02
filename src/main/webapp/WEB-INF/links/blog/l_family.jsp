<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:set var="ogtitle" scope="page"><spring:message code="title.blog.family"/> <spring:message code="chose.blog.family${category}"/> - <spring:message code="title.blog"/></c:set>
<c:set var="ogseo" scope="page"><spring:message code="seo.blog.family"/></c:set>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="website">
<meta property="og:title" content="${pageScope.ogtitle}">
<meta property="og:description" content="${pageScope.ogseo}">
<meta property="og:image" content="<c:url value="/static/picts/extras/blog-min.jpg" />">
<title>${pageScope.ogtitle}</title>
<meta name="description" content="${pageScope.ogseo}">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<c:set var="homeBlog" value="1" scope="request"></c:set>