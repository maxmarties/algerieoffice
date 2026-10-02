<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><c:out value="${explorerCompany.profile.tradename}"/> | <spring:message code="explorer.mainmenu2.1"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<c:import url="/WEB-INF/tags/comps/init_appearance.jsp"/>
<c:set var="explorerMainmenu" value="2" scope="request"></c:set>
<c:set var="explorerMainsibdebar" value="1" scope="request"></c:set>