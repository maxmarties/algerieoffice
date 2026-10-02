<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/data/activities" />" class="lien lien-black">
			<i class="cmsms-icon-folder m-r-5"></i><spring:message code="sidebar.admin.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard6.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard6.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.social"/></p>
	</div>
	<div class="row">
		<div class="col-lg-9">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<form:form name="socialForm" action="/" method="POST" modelAttribute="social" enctype="utf8" novalidate="novalidate">
					<c:set var="providers" value="facebook,twitter,linkedin,youtube" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<spring:bind path="${pageScope.provider}">
							<div id="${pageScope.provider}Form" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="${pageScope.provider}">
									<span class="text-capitalize"><c:out value="${pageScope.provider}"/></span>
									<c:if test="${state.count == 1}"><span class="help-text"><spring:message code="txt.help.admin.social" /></span></c:if>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<form:input class="form-control" type="number" path="${pageScope.provider}" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
					</c:forEach>
					<spring:bind path="begginer">
						<div class="form-group row">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<label class="ui-checkbox ui-checkbox-segond font-small">
				            		<form:checkbox path="begginer" />
									<span class="input-span"></span><spring:message code="comp.admin.begginer" />
								</label>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="promoted">
						<div class="form-group row">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<label class="ui-checkbox ui-checkbox-segond font-small">
				            		<form:checkbox path="promoted" />
									<span class="input-span"></span><spring:message code="comp.admin.promoted" />
								</label>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="socialForm">
						<div id="socialFormForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="socialForm">
								<spring:message code="tabs.descrptif" /> (EMBED VIDEO)
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:textarea class="form-control form-area" rows="2" path="socialForm" />
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