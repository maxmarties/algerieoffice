<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/admin/marketplace/campaigns"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4.4"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.campaign.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.campaign"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/marketplace/campaigns" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<form:form name="campaignForm" action="/" method="POST" modelAttribute="campaign" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3"><spring:message code="subheader.post5" /></label>
						<div class="col-md-8 col-lg-9">
							<div class="table-responsive">
								<table class="table table-page table-panel">
									<thead><tr><th style="width:30%;"></th><th style="width:70%;"></th></tr></thead>
									<tbody class="font-small">
										<tr><td><spring:message code="tabs.campaign"/></td><td><c:out value="${campaign.title}" /></td></tr>
										<tr><td><spring:message code="tabs.type"/></td><td><spring:message code="wizard.screen.navbar${campaign.type}"/></td></tr>
										<tr><td><spring:message code="tabs.sector"/></td><td><spring:message code="chose.sector${campaign.sector == 0 ? '.all' : campaign.sector}"/></td></tr>
										<tr><td><spring:message code="tabs.lieu"/></td><td><spring:message code="chose.wilaya${campaign.wilaya == 0 ? '.all' : campaign.wilaya}"/></td></tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<spring:bind path="viewCount">
						<div id="viewCountForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="viewCount">
								<spring:message code="tabs.eye" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<form:input class="form-control" type="number" path="viewCount" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="clicCount">
						<div id="clicCountForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="clicCount">
								<spring:message code="tabs.clic" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<form:input class="form-control" type="number" path="clicCount" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="creditCount">
						<div id="creditCountForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="creditCount">
								<spring:message code="tabs.credit" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<form:input class="form-control" type="number" path="creditCount" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div class="form-group row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<spring:bind path="enabled">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="enabled" />
									<span class="input-span"></span><spring:message code="comp.admin.promote" /> 
								</label>
							</spring:bind>
						</div>
					</div>
					<div class="form-group row m-b-20">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-20">
								<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
									<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
								</button>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
		</div>
	</div>
</div>