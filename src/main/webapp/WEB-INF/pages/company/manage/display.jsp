<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/manage/display"/>" class="lien lien-black">
			<i class="cmsms-icon-sliders m-r-5"></i><spring:message code="sidebar.company.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard5.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard5.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.manage1"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
		<div class="wizard wizard-user">
			<form:form name="maindisplayForm" action="/" method="POST" modelAttribute="maindisplay" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<div class="wizard-tabbed">
					<ul class="nav nav-tabs nav-tabs3" role="tablist">
						<c:set var="providers" value="list,puzzle,box-2" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<li>
								<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
									title="<spring:message code="wizard.manage.display${state.count}"/>">
									<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
									<span class="help-tab"><spring:message code="wizard.manage.display${state.count}"/></span>
								</a>
							</li>
						</c:forEach>
					</ul>
					<div class="wizard-content">
						<div class="wizard-body">
							<c:forEach var="i" begin="1" end="3" step="1">
								<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
									<c:import url="/WEB-INF/pages/company/manage/display/form_display${i}.jsp"/>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="form-group row m-b-20">
					<div class="col-md-4 hidden-sm-down"></div>
					<div class="col-md-8">
						<div id="submitForm" class="form-submit m-t-10">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span></button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</sec:authorize>
</div>