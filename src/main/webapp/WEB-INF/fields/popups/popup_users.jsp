<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li id="iUsers" class="dropdown icons-menu favorites-menu">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.popup.users"/>">
		<i class="cmsms-icon-group"></i>
	</a>
	<ul class="dropdown-menu" role="menu">
		<li class="dropdown-header"><span class="font-bold i-primary"><spring:message code="tooltip.popup.users"/></span></li>
		<li class="dropdown-form">
			<form name="iUsersForm" action="/" novalidate="novalidate">
				<div class="form-group m-b-0">
					<div class="input-group-icon">
						<input class="form-control" type="search" id="iSearchUsers" name="iSearchUsers" placeholder="<spring:message code="tool.find.preview" />" />
						<button type="submit" class="btn btn-transparent btn-simple" title="<spring:message code="btn.find" />">
							<span><i class="cmsms-icon-search-1"></i></span></button>
					</div>
				</div>
			</form>
		</li>
		<li class="dropdown-body"><ul class="menu-scroll list-none"><li class="dropdown-load"><c:import url="/WEB-INF/basics/loading_span.jsp"/></li></ul></li>
		<li class="dropdown-footer text-center">
			<a href="<c:url value="/company/team/users"/>" class="lien lien-hover lien-primary lien-small"><spring:message code="tool.inobx.viewall"/></a>
		</li>
	</ul>
</li>