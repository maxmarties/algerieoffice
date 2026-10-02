<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li id="iMessages" class="dropdown icons-menu">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.popup.messages"/>">
		<i class="cmsms-icon-mail-alt"></i><span class="dropdown-indicator"></span>
	</a>
	<ul class="dropdown-menu dropdown-menu-right" role="menu">
		<li class="dropdown-load"><c:import url="/WEB-INF/basics/loading_span.jsp"/></li>
	</ul>
</li>