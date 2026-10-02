<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="sidebar-footer">
	<c:import url="/WEB-INF/basics/sidebar_social.jsp"/>
	<div class="sidebar-footer-body font-mini i-border">
		<spring:message code="txt.help.support"/> 
		<a href="mailto:<spring:message code="app.support"/>" class="lien lien-hover lien-segond"><spring:message code="app.support"/></a>
	</div>
</div>
<div class="sidebar-logout">
	<a href="<c:url value="/logout"/>" class="transition-35" title="<spring:message code="btn.logout" />"><i class="cmsms-icon-off m-r-10"></i></a>
</div>