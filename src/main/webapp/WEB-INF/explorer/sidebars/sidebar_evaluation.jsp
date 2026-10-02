<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<ul class="navbar-nav nav-flex-icons shared-navigate ml-auto">
	<li><a class="transition-color iRdv ${explorerCurrent.hasPreview ? 'disabled' : ''}">
			<i class="cmsms-icon-calendar-empty m-r-10"></i><spring:message code="explorer.popup.home1"/></a></li>
	<li class="dropdown">
		<a class="transition-color" data-toggle="dropdown" title="<spring:message code="tooltip.explorer.evaluation" />">
			<i class="cmsms-icon-thumbs-up m-r-10"></i><spring:message code="explorer.popup.home2"/><i class="cmsms-icon-angle-down i-6 m-l-10"></i>
		</a>
		<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
			<li><a class="dropdown-item lien iRate ${explorerCurrent.hasPreview ? 'disabled' : ''}"><spring:message code="explorer.popup.home2.1"/></a></li>
			<li><a class="dropdown-item lien iNotice ${explorerCurrent.hasPreview ? 'disabled' : ''}"><spring:message code="explorer.popup.home2.2"/></a></li>
			<li class="dropdown-divider"></li>
			<li><a class="dropdown-item lien iReport ${explorerCurrent.hasPreview ? 'disabled' : ''}"><spring:message code="explorer.popup.home2.3"/></a></li>
		</ul>
	</li>
</ul>