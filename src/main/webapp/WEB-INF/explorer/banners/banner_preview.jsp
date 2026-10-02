<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<ul class="list-none list-block bnn-follow p-left">
	<li>
		<a class="icon-preview transition-35" data-toggle="tooltip" data-placement="${explorerCompany.profile.language == 'ar' ? 'left' : 'right'}" 
			title="<spring:message code="message.explorer.preview1" />"><i class="cmsms-icon-eye"></i></a>
	</li>
	<li>
		<a href="<c:url value="/company/manage/display"/>" class="icon-preview transition-35" data-toggle="tooltip" 
			data-placement="${explorerCompany.profile.language == 'ar' ? 'left' : 'right'}" 
			title="<spring:message code="message.explorer.preview2" />"><i class="cmsms-icon-cog"></i></a>
	</li>
	<li>
		<a href="<c:url value="/company/manage/appearance"/>" class="icon-preview transition-35" data-toggle="tooltip" 
			data-placement="${explorerCompany.profile.language == 'ar' ? 'left' : 'right'}" 
			title="<spring:message code="message.explorer.preview3" />"><i class="cmsms-icon-brush-2"></i></a>
	</li>
</ul>
<c:if test="${hasLoading && !explorerCompany.profile.enabled}">
	<div class="alert alert-explorer">
		<div class="container">
			<ul class="navbar-nav nav-flex-icons">
				<li class="font-big m-r-20"><spring:message code="message.explorer.preview" /></li>
				<li class="ml-auto"><a href="<c:url value="/company/profile/identity"/>" class="btn btn-primary btn-fixed"><span><spring:message code="lien.help.validate" /></span></a></li>
				<li class="m-l-5"><button type="button" class="btn btn-danger" data-dismiss="alert" title="<spring:message code="btn.close"/>"><span><i class="cmsms-icon-cancel-2"></i></span></button></li>
			</ul>
		</div>
	</div>
</c:if>