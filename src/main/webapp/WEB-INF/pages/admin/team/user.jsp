<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/team/users"/>" class="lien lien-black">
			<i class="cmsms-icon-user-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard10"/></a></li>
		<li><a href="<c:url value="/admin/team/users"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard10.1"/></a></li>
		<li class="active"><spring:message code="btn.add" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.team.user.new"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.team1.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/team/users" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
		<div class="row">
			<div class="col-lg-9">
				<form:form name="admuserForm" action="/" method="POST" modelAttribute="admuser" enctype="utf8" novalidate="novalidate">
					<spring:bind path="firstname">
						<div id="firstnameForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="firstname">
								<spring:message code="lbl.firstname" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="text" path="firstname" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="lastname">
						<div id="lastnameForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="lastname">
								<spring:message code="lbl.lastname" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="text" path="lastname" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="email">
						<div id="emailForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="email">
								<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="email" path="email" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="password">
						<div id="passwordForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="password">
								<spring:message code="lbl.password"/> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.password" /></span>
							</form:label>
							<div class="col-md-8 col-lg-6">
								<form:input class="form-control" type="password" path="password" />
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
									<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
								</button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</sec:authorize>
</div>