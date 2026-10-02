<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:set var="faTitle" scope="page"><spring:message code="explorer.desktop.grid5" /> - <c:out value="${explorerPage.inbox.title}"/> | <c:out value="${explorerCompany.profile.tradename}"/></c:set>
<meta property="og:title" content="${pageScope.faTitle}">
<title><c:out value="${pageScope.faTitle}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="explorerMainmenu" value="3" scope="request"></c:set>