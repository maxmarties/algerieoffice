<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black">
			<i class="cmsms-icon-wrench m-r-5"></i><spring:message code="sidebar.company.dashboard10"/></a></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard10.1"/></a></li>
		<li class="active"><spring:message code="btn.delete" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="lien.explorer.delete"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.tools1.1"/></p>
	</div>
	<div class="form-container">
		<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
			<c:choose>
				<c:when test="${!empty deactivate}">
					<form:form name="deactivateForm" action="/" method="POST" modelAttribute="deactivate" enctype="utf8" novalidate="novalidate">
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<spring:bind path="reason">
							<div id="reasonForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="reason">
									<spring:message code="lbl.sub.report" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.setting5.1" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
									<form:select class="form-select2-simple" path="reason" data-placeholder="${pageScope.faholder}">
										<option></option>
										<c:forEach var="i" begin="1" end="4" step="1">
											<option value="${i}"><spring:message code="chose.deactivate.company${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="observation">
							<div id="observationForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="observation">
									<spring:message code="tabs.observation" />
									<span class="help-text"><spring:message code="txt.help.setting5.2" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<form:textarea class="form-control form-area" rows="4" path="observation" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="password">
							<div id="passwordForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="password">
									<spring:message code="lbl.sub.oldpassword"/> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.setting5.3" /></span>
								</form:label>
								<div class="col-md-8 col-lg-6">
									<form:input class="form-control" type="password" path="password" value="" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<div class="form-group row m-t-0">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-2">
								<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.setting5"/></h2>
								<ul class="list-none list-block font-small m-t-10">
									<li><a href="<c:url value="/company/manage/preferences"/>" class="lien lien-primary lien-underline">
											<spring:message code="txt.help.setting5.3.1"/></a></li>
									<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-primary lien-underline">
											<spring:message code="txt.help.setting5.3.2"/></a></li>
								</ul>
							</div>
						</div>
						<div class="form-group row m-t-10 m-b-20">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-4">
								<div id="submitForm" class="form-submit m-t-10">
									<button type="submit" class="btn btn-danger btn-submit btn-fixed"><span><spring:message code="btn.delete"/></span></button>
								</div>
								<span class="font-small i-help m-l-10"><spring:message code="txt.help.setting5.4" /></span>
							</div>
						</div>
					</form:form>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger m-t-10">
						<i class="cmsms-icon-attention-circled i-alert"></i>
						<p class="p-alert">
							<spring:message code="txt.company.tools1.2"/> 
							<a href="<c:url value="/company/manage/preferences" />" class="lien lien-primary lien-underline"><spring:message code="lien.help.preference"/></a>
						</p>
					</div>
				</c:otherwise>
			</c:choose>
		</sec:authorize>
	</div>
</div>