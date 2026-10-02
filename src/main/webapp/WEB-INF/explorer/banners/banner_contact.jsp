<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-sidebar">
	<div class="sidebar-header bn-explorer-primary h-header"><i class="cmsms-icon-building m-r-20"></i><spring:message code="btn.explorer.contact6"/></div>
	<div class="sidebar-body">
		<i class="cmsms-icon-location-5 text-segond i-24 pull-left"></i>
		<div class="bnn-brand">
			<h2 class="h-header h-header4 text-segond1"><c:out value="${explorerCompany.profile.tradename}"/></h2>
			<p class="font-mini text-help m-t-10">
				<c:out value="${explorerCompany.profile.address}," /><br>
				<c:out value="${explorerCompany.profile.postal}" />  <span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></span>
			</p>
		</div>
		<span class="clearfix"></span>
		<hr class="my-1">
		<ul class="bnn-list list-none">
			<li class="m-b-5">
				<a href="tel:+213${explorerCompany.profile.phone}" 
					class="btn btn-more btn-banner btn-explorer-treen btn-simple btn-block iPhone ${explorerCurrent.hasPreview ? 'disabled' : ''}">
					<span>
						<i class="cmsms-icon-call-out transition-35 i-12"></i><spring:message code="tool.navigate.company4.1"/> 
						<c:out value="${explorerCompany.profile.getFormattedPhone()}"></c:out>
					</span>
				</a>
			</li>
			<li class="m-b-5">
				<a href="mailto:${explorerCompany.profile.email}" 
					class="btn btn-more btn-banner btn-explorer-treen btn-simple btn-block iMail ${explorerCurrent.hasPreview ? 'disabled' : ''}">
					<span><i class="cmsms-icon-mail-1 transition-35 i-12"></i><spring:message code="tool.navigate.company4.2"/></span>
				</a>
			</li>
			<li>
				<a href="<c:url value="${explorerCurrent.companyURL}/contact"/>" class="btn btn-banner btn-explorer-segond btn-simple btn-block">
					<span><i class="cmsms-icon-chat-empty i-12"></i><spring:message code="tool.navigate.company4.3"/></span>
				</a>
			</li>
		</ul>
	</div>
</div>