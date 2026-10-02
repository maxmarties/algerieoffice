<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<meta property="og:title" content="<c:out value="${explorerCompany.profile.tradename}"/> | <spring:message code="explorer.mainmenu2.4"/>">
<title><c:out value="${explorerCompany.profile.tradename}"/> | <spring:message code="explorer.mainmenu2.4"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<c:set var="explorerMainmenu" value="2" scope="request"></c:set>
<c:set var="explorerMainsibdebar" value="4" scope="request"></c:set>