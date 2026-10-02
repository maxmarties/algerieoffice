<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="home-loader explorer-loading p-position">
	<div class="logo-load">
		<img height="74" src="<c:url value="${explorerCompany.header.urlLogo}"/>" alt="<c:out value="${explorerCompany.profile.tradename}"/>" />
		<c:import url="/WEB-INF/basics/loading_span.jsp"/>
	</div>
</div>