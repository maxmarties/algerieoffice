<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<a href="<c:url value="/user/dashboard"/>" class="brand-dashboard transition-color" title="<spring:message code="tooltip.dashboard1" />">
	<img src="<c:url value="${currentUser.getIconUrl()}"/>" class="pull-left img-circle" alt="<spring:message code="tooltip.avatar" />">
	<span class="brand-header">
		<span class="block text-truncate"><c:out value="${currentUser.getUsername()}" /></span>
		<span class="font-mini font-bold i-segond">
			<spring:message code="chose.role.${currentUser.getRoleName()}" />
			<i class="treeview-trigger cmsms-icon-${langage.clazz == 'ar' ? 'left' : 'right'}-big m-l-5 animated fadeInLeft"></i>
		</span>
	</span>
	<span class="clearfix"></span>
</a>