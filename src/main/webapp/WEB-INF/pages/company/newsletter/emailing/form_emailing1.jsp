<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.newsletter.emailing1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.newsletter3.1"/></p>
<hr class="my-4">
<spring:bind path="type">
	<div id="typeForm" class="form-group row" data-toggle="buttons">
		<form:label class="col-form-label col-md-4 col-lg-3" path="type">
			<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.newsletter3.1" /></span>
		</form:label>
		<div class="col-md-8 col-lg-6">
			<ul class="nav nav-campaign m-b-10">
				<c:set var="providers" value="tree-3,calendar-7,pin-1,coffee,bag,code" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<li class="${state.count > 3 ? 'm-t-20' : ''}">
						<label class="btn btn-campaign btn-simple m-auto" style="min-height:120px;">
							<i class="cmsms-icon-${pageScope.provider} i-24"></i>
							<span class="help-text m-t-10"><spring:message code="wizard.newsletter.emailing1.${state.count}"/></span>
							<form:radiobutton class="hidden" value="${state.count}" path="type"/>
						</label>
					</li>
				</c:forEach>
			</ul>
			<span class="error"></span>
			<hr class="my-4">
		</div>
	</div>
</spring:bind>
<div id="createForm" class="animated onne fadeIn m-b-0" style="display:none;">
	<spring:bind path="subject">
		<div id="subjectForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="subject">
				<spring:message code="tabs.subject" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.newsletter3.1.1" /></span>
			</form:label>
			<div class="col-md-8 col-lg-6">
				<form:input class="form-control" type="text" path="subject" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="title">
		<div id="titleForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="title">
				<spring:message code="tabs.title" />
				<span class="help-text"><spring:message code="txt.help.newsletter3.1.2" /></span>
			</form:label>
			<div class="col-md-8 col-lg-6">
				<form:input class="form-control" type="text" path="title" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
</div>