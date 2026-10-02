<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/prospect/quotes"/>" class="lien lien-black">
			<i class="cmsms-icon-quote-right m-r-5"></i><spring:message code="sidebar.company.dashboard9"/></a></li>
		<li><a href="<c:url value="/company/prospect/infos"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard9.3"/></a></li>
		<li class="active"><spring:message code="btn.viewmore" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.prospect.infos.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.prospect3.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/prospect/infos" scope="request"></c:set>
	<c:set var="backwordPage" value="17" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<c:choose>
			<c:when test="${currentCompany.hasPremium()}">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="txt.help.infos1" />
						<span class="help-text"><spring:message code="txt.help.infos1.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="table-responsive">
							<table class="table table-page table-panel">
								<thead><tr><th style="width:30%;"></th><th style="width:70%;"></th></tr></thead>
								<tbody class="font-small">
									<tr>
										<td><spring:message code="tabs.info"/></td>
										<td class="i-primary">
											<c:choose>
												<c:when test="${prospectDetail.hasPublished}">
													<a href="<c:url value="${prospectDetail.identifyURL}" />" class="lien lien-table" target="_blank">
														<c:out value="${prospectDetail.title}" />
													</a>
												</c:when>
												<c:otherwise><c:out value="${prospectDetail.title}" /></c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td><spring:message code="tabs.date"/></td><td><c:out value="${prospectDetail.postedDate}" /></td></tr>
									<tr><td><spring:message code="tabs.username"/></td><td><c:out value="${prospectDetail.username}" /></td></tr>
									<tr><td><spring:message code="tabs.email"/></td>
										<td><a href="mailto:${prospectDetail.email}" class="lien lien-primary lien-underline"><c:out value="${prospectDetail.email}" /></a></td></tr>
									<tr><td><spring:message code="tabs.phone"/></td><td><c:out value="${prospectDetail.phone}" /></td></tr>
									<tr>
										<td><spring:message code="tabs.filereader"/></td>
										<td>
											<c:choose>
												<c:when test="${!empty prospectDetail.fileUrl}">
													<a href="<c:url value="${prospectDetail.fileUrl}" />" class="lien lien-hover lien-primary" target="_blank">
														<spring:message code="tool.view.file" />
													</a>
												</c:when>
												<c:otherwise><c:out value="-" /></c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td><spring:message code="tabs.message"/></td><td><c:out value="${prospectDetail.message}" /></td></tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="form-group row m-b-30">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<a href="<c:url value="/company/prospect/infos"/>" class="btn btn-segond btn-add btn-left">
							<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-4 m-r-10"></i><spring:message code="btn.back"/></span>
						</a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<i class="cmsms-icon-dollar i-alert"></i>
					<p class="p-alert">
						<spring:message code="message.premium.communication"/> 
						<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>