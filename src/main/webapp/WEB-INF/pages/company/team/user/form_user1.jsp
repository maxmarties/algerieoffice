<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.team.agent1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.team2.1.1"/></p>
<hr class="my-4">
<div class="form-container">
	<form:form name="teamguestForm" action="/" method="POST" modelAttribute="teamguest" enctype="utf8" novalidate="novalidate">
		<spring:bind path="guestmail">
			<div id="guestmailForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="guestmail">
					<spring:message code="lbl.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.team2.1" /></span>
				</form:label>
				<div class="col-md-8 col-lg-9">
					<form:input class="form-control" type="email" path="guestmail" />
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<spring:bind path="guestrole">
			<div id="guestroleForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="guestrole">
					<spring:message code="tabs.role" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<div class="col-md-4 col-lg-4">
					<c:set var="providers" value="visit,autor,edit,manager,admin" scope="page"></c:set>
					<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
					<form:select class="form-select2-simple" path="guestrole" data-placeholder="${pageScope.faholder}">
						<option></option>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<option value="${state.count}"><spring:message code="chose.role.${pageScope.provider}" /></option>
						</c:forEach>
					</form:select>
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
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
	<hr class="my-2">
	<h3 class="h-header h-header5 i-primary font-normal"><spring:message code="wizard.team.agent3"/></h3>
	<p class="font-small m-t-10"><spring:message code="txt.company.team2.1.3"/></p>
	<div class="form-group m-t-20">
		<ul class="font-small">
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li class="m-b-10">
					<span class="font-gras"><spring:message code="chose.role.${pageScope.provider}" /></span> :
					<spring:message code="overview.role${state.count}" />
				</li>
			</c:forEach>
		</ul>
	</div>
</div>