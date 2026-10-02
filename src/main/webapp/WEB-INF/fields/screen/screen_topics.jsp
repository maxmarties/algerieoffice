<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
	<div class="screen-column screen-topics m-t-10">
		<div class="widget-title"><h2 class="h-header h-header5 i-primary"><spring:message code="wizard.forums.explorer2"/></h2><hr class="my-1"></div>
		<div id="loadScreenTopics"></div>
	</div>
</sec:authorize>