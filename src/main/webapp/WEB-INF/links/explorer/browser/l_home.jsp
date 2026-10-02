<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:title" content="<spring:message code="explorer.home.title${explorerPage.briefcase.stateBriefcase()}"/> <c:out value="${explorerCompany.profile.tradename}"/>">
<title><spring:message code="explorer.home.title${explorerPage.briefcase.stateBriefcase()}"/> <c:out value="${explorerCompany.profile.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/lib/editor/froala-style.min.css"/>'>
<c:set var="explorerMainmenu" value="1" scope="request"></c:set>