<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:url" content="${mapsiteURL}">
<meta property="og:type" content="article">
<meta property="og:title" content="<c:out value="${inbox.title}"/>">
<meta property="og:description" content="<c:out value="${inbox.description}"/>">
<meta property="og:image" content="<c:url value="${inbox.photoURL}" />">
<title><c:out value="${inbox.title}"/></title>
<meta name="description" content="<c:out value="${inbox.description}"/>">
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="homeBlog" value="1" scope="request"></c:set>