<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="dropdown brand-menu brand-fixed">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.table.filter"/>">
		<i class="cmsms-icon-filter"></i>
	</a>
	<ul class="dropdown-menu animated slideInY" role="menu">
		<li><a id="clearFilter" class="dropdown-item disabled"><spring:message code="btn.clear.filter"/></a></li>
	</ul>
</li>