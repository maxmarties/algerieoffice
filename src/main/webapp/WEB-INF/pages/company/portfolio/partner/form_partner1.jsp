<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.portfolio.partner1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.portfolio5.1.1"/></p>
<hr class="my-4">
<form:form name="partnerguestForm" action="/" method="POST" modelAttribute="partnerguest" enctype="utf8" novalidate="novalidate">
	<spring:bind path="email">
		<div id="emailForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="email">
				<spring:message code="lbl.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.portfolio5.1" /></span>
			</form:label>
			<div class="col-md-8 col-lg-6">
				<form:input class="form-control" type="email" path="email" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<div class="form-group row">
		<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
		<div class="col-md-8 col-lg-9">
			<spring:bind path="hasGuestpingled">
				<label class="ui-checkbox ui-checkbox-segond font-small">
					<form:checkbox path="hasGuestpingled" />
					<span class="input-span"></span><spring:message code="comp.partenaire" />
				</label>
			</spring:bind>
		</div>
	</div>
	<div class="form-group row m-b-20">
		<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
		<div class="col-md-8 col-lg-9">
			<hr class="my-4">
			<div id="submitguestForm" class="form-submit m-t-20">
				<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
					<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.guest"/></span>
				</button>
			</div>
		</div>
	</div>
</form:form>