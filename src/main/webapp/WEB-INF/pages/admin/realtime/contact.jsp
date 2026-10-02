<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/realtime/companies"/>" class="lien lien-black">
			<i class="cmsms-icon-flag m-r-5"></i><spring:message code="sidebar.admin.dashboard9"/></a></li>
		<li><a href="<c:url value="/admin/realtime/contacts"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard9.6"/></a></li>
		<li class="active"><spring:message code="btn.viewmore" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="btn.viewmore"/></h1>
		<p><spring:message code="txt.admin.realtime6.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/realtime/contacts" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<div class="form-group row">
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="txt.help.explorer3.7" />
				<span class="help-text"><spring:message code="txt.help.contact1.1" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<div class="table-responsive">
					<table class="table table-page table-panel">
						<thead><tr><th style="width:30%;"></th><th style="width:70%;"></th></tr></thead>
						<tbody class="font-small">
							<c:if test="${!empty contact.userId}">
								<tr>
									<td><spring:message code="tabs.user"/></td>
									<td>
										<div class="form-identity">
											<img src="<c:url value="${contact.urlAvatar}"/>" class="pull-left img-circle" alt="<c:out value="${contact.username}" />">
											<div class="identity-brand">
												<a href="<c:url value="/membres?id=${contact.userId}" />" class="lien lien-table" target="_blank"><c:out value="${contact.username}" /></a>
												<span class="help-text"><c:out value="${contact.tradename}" /></span>
											</div>
											<span class="clearfix"></span>
										</div>
									</td>
								</tr>
							</c:if>
							<tr><td><spring:message code="lbl.sub.object"/></td><td class="font-bold"><spring:message code="chose.request${contact.object}" /></td></tr>
							<tr><td><spring:message code="lbl.firstname"/></td><td><c:out value="${contact.firstName}" /></td></tr>
							<tr><td><spring:message code="lbl.lastname"/></td><td><c:out value="${contact.lastName}" /></td></tr>
							<tr><td><spring:message code="tabs.phone"/></td><td><c:out value="${contact.phone}" /></td></tr>
							<tr><td><spring:message code="tabs.email"/></td><td><a href="mailto:${contact.email}" class="lien lien-primary lien-underline"><c:out value="${contact.email}" /></a></td></tr>
							<tr><td><spring:message code="explorer.home.title1"/></td><td><c:out value="${contact.company}" /></td></tr>
							<tr><td><spring:message code="tabs.requested"/></td><td><c:out value="${contact.postedDate}" /></td></tr>
							<tr><td><spring:message code="tabs.message"/></td><td><c:out value="${contact.message}" /></td></tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="form-group row m-b-30">
			<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
			<div class="col-md-8 col-lg-9">
				<a href="<c:url value="/admin/realtime/contacts"/>" class="btn btn-segond btn-add btn-left">
					<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-4 m-r-10"></i><spring:message code="btn.back"/></span>
				</a>
			</div>
		</div>
	</div>
</div>