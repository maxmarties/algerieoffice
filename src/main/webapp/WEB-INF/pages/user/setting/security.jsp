<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/settings/general"/>" class="lien lien-black">
			<i class="cmsms-icon-cog-5 m-r-5"></i><spring:message code="sidebar.user.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard7.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard7.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.setting1.3"/></p>
	</div>
	<div class="page-container">
		<form:form name="securityForm" action="/" method="POST" modelAttribute="security" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="analytic">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting8.1" />
						<span class="help-text">
							<spring:message code="txt.help.setting9.1" /> <a href="<c:url value="/infos/politique-confidentialite"/>" class="lien lien-primary lien-hover" 
								target="_blank"><spring:message code="explorer.mainmenu7.3" /></a>.
						</span>
					</label>
					<div class="col-md-8 col-lg-9">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="analytic" /><span class="input-span"></span><spring:message code="comp.setting7.1" />
						</label>
					</div>
				</div>
			</spring:bind>
			<div id="codageForm" class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.setting8.2" />
					<span class="help-text"><spring:message code="txt.help.setting9.2" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="secured">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="secured" /><span class="input-span"></span><spring:message code="comp.setting7.2" />
						</label>
					</spring:bind>
					<spring:bind path="codage">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div class="m-t-5">
								<label class="ui-radio ui-radio-segond ui-radio-codage font-small ${!security.secured ? 'disabled' : ''}">
									<form:radiobutton value="${i}" path="codage" />
									<span class="input-span"></span><spring:message code="comp.setting7.2.${i}" />
								</label>
							</div>
						</c:forEach>
					</spring:bind>
					<span class="error"></span>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="logouted">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="logouted" /><span class="input-span"></span><spring:message code="comp.setting7.3" />
						</label>
					</spring:bind>
				</div>
			</div>
			<div class="form-group row m-t-10 m-b-30">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-1">
					<div id="submitForm" class="form-submit m-t-10">
						<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>