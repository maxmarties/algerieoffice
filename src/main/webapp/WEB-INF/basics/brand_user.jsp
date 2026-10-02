<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
	<a href="<c:url value="/admin/dashboard"/>" class="brand-dashboard transition-color" title="<spring:message code="tooltip.dashboard3" />">
		<img src="<c:url value="/static/icons/icon-white-min.jpg"/>" class="pull-left" alt="<spring:message code="app.brand" />">
		<span class="brand-header">
			<span class="block text-truncate"><spring:message code="app.brand" /></span>
			<span class="font-mini font-bold i-segond">
				<spring:message code="tooltip.dashboard4" />
				<i class="treeview-trigger cmsms-icon-${langage.clazz == 'ar' ? 'right' : 'left'}-big m-l-5 animated fadeInRight"></i>
			</span>
		</span>
		<span class="clearfix"></span>
	</a>
</sec:authorize>
<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
	<a href="<c:url value="/company/dashboard"/>" class="brand-dashboard transition-color" title="<spring:message code="tooltip.dashboard2" />">
		<span class="i-indicator ${currentCompany.published ? 'active' : ''}"></span>
		<img src="<c:url value="${currentCompany.iconurl}"/>" class="pull-left" alt="<spring:message code="tooltip.avatar" />">
		<span class="brand-header">
			<span class="block text-truncate"><c:out value="${currentCompany.tradename}" /></span>
			<span class="font-mini font-bold i-segond">
				<spring:message code="tooltip.company.backword" />
				<i class="treeview-trigger cmsms-icon-${langage.clazz == 'ar' ? 'right' : 'left'}-big m-l-5 animated fadeInRight"></i>
			</span>
		</span>
		<span class="clearfix"></span>
	</a>
</sec:authorize>
<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')">
	<div class="brand-user">
		<a href="<c:url value="/guest/add-company"/>" class="btn btn-segond btn-brand btn-block" title="<spring:message code="btn.partner3" />">
			<span><i class="cmsms-icon-plus"></i><span class="brand-text m-l-5"><spring:message code="btn.partner3" /></span></span>
		</a>
	</div>
</sec:authorize>