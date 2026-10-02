<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<title><spring:message code="header.marketplace.campaign.${empty campaign.id ? 'new' : 'edit'}"/> | <c:out value="${currentCompany.tradename}"/></title>
<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/dashboard.min.css"/>'>
<c:set var="treeviewMarketplace" value="4" scope="request"></c:set>