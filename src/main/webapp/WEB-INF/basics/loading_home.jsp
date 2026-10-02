<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="home-loader p-position">
	<div class="logo-load">
		<img height="74" src="<c:url value="/static/icons/apple-home-min.png"/>" alt="<spring:message code="app.brand"/>" />
		<c:import url="/WEB-INF/basics/loading_span.jsp"/>
	</div>
</div>