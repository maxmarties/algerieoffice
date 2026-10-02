<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black">
			<i class="cmsms-icon-wrench m-r-5"></i><spring:message code="sidebar.company.dashboard10"/></a></li>
		<li><a href="<c:url value="/company/tools/black-list"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard10.3"/></a></li>
		<li class="active"><spring:message code="header.tools.blacklist" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.tools.blacklist"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.tools3.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/tools/black-list" scope="request"></c:set>
	<c:set var="backwordPage" value="21" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="form-container">
		<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
			<form:form name="blacklistForm" action="/" method="POST" modelAttribute="blacklist" enctype="utf8" novalidate="novalidate">
				<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
				<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
				<spring:bind path="username"><form:input type="hidden" path="username" /></spring:bind>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="tabs.user" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.blacklist1.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<input class="form-control disaload" type="text" value="${blacklist.username}" disabled />
					</div>
				</div>
				<spring:bind path="reason">
					<div id="reasonForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="reason">
							<spring:message code="tabs.motif" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.blacklist1.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="4" path="reason" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<hr class="my-4 m-t-30">
				<div class="form-group row m-b-30">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<div id="submitForm" class="form-submit m-t-10">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>