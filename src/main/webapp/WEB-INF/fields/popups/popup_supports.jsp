<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li id="iSupports" class="dropdown icons-menu">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.popup.supports"/>">
		<i class="cmsms-icon-box"></i><span class="dropdown-indicator"></span>
	</a>
	<ul class="dropdown-menu dropdown-menu-right" role="menu">
		<li class="dropdown-load"><c:import url="/WEB-INF/basics/loading_span.jsp"/></li>
	</ul>
</li>