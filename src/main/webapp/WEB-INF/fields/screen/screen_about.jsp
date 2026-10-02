<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="col-lg-3 m-t-10 m-b-10 screen-noprint">
	<div class="screen-sidebar screen-about">
		<a class="sidebar-header bn-primary">
			<i class="cmsms-icon-building m-r-20"></i><spring:message code="wizard.screen.autor${requestScope.screenAbout}"/>
			<i class="breadview-trigger cmsms-icon-angle-down i-segond"></i>
		</a>
		<div class="sidebar-body">
			<i class="cmsms-icon-location-5 i-segond i-24 pull-left"></i>
			<div class="bnn-brand">
				<a href="<c:url value="${company.companyURL}" />" class="lien lien-company sh-black"><c:out value="${company.tradename}" /></a>
				<span class="help-text m-t-10">
					<c:out value="${company.address}," /><br>
					<c:out value="${company.postal}" />  <span class="text-uppercase"><spring:message code="chose.wilaya${company.wilaya}"/></span>
				</span>
			</div>
			<span class="clearfix"></span>
			<hr class="my-2">
			<ul class="bnn-list list-none">
				<li class="m-b-5">
					<a href="tel:+213${company.phone}" class="btn btn-more btn-banner btn-simple btn-block iPhone">
						<span>
							<i class="cmsms-icon-call-out transition-35 i-12"></i>
							<spring:message code="tool.navigate.company4.1"/> <c:out value="${company.getFormattedPhone()}"></c:out>
						</span>
					</a>
				</li>
				<li class="m-b-5">
					<a href="mailto:${company.mail}" class="btn btn-more btn-banner btn-simple btn-block iMail">
						<span><i class="cmsms-icon-mail-1 transition-35 i-12"></i><spring:message code="tool.navigate.company4.2"/></span>
					</a>
				</li>
				<li>
					<a href="<c:url value="${company.companyURL}/contact"/>" class="btn btn-segond btn-banner btn-simple btn-block">
						<span><i class="cmsms-icon-chat-empty i-12"></i><spring:message code="tool.navigate.company4.3"/></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="screen-sticky hidden-md-down">
		<div class="screen-column m-t-10">
			<div id="iExplorerSpn" class="explorer-spn"><p class="font-small i-help"><spring:message code="txt.help.explorer4.2"/></p></div>
		</div>
	</div>
</div>