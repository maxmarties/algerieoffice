<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/companies/all"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li><a href="<c:url value="/admin/companies/features"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3.3"/></a></li>
		<li class="active"><spring:message code="header.tools.subscribe" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard8.5"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.premium5.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/companies/features" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
				<form:form name="budgetForm" action="/" method="POST" modelAttribute="budget" enctype="utf8" novalidate="novalidate">
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<spring:bind path="emails">
						<div id="emailsForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="emails">
								<spring:message code="tabs.credit" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="number" path="emails" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="sendings">
						<div id="sendingsForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="sendings">
								<spring:message code="tabs.potential" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="number" path="sendings" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
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