<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="subheader.register2.2"/></h2>
<spring:bind path="denomination">
	<div id="denominationForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="denomination">
			<spring:message code="lbl.denomination" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.denomination1" /></span>
		</form:label>
		<div class="col-md-8">
			<form:input class="form-control" type="text" path="denomination" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="tradename">
	<div id="tradenameForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="tradename">
			<spring:message code="tabs.tradename" />
			<span class="help-text"><spring:message code="txt.help.tradename1" /></span>
		</form:label>
		<div class="col-md-8">
			<form:input class="form-control" type="text" path="tradename" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="activity">
	<div id="activityForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="activity">
			<spring:message code="lbl.sub.activity1" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text">
				<spring:message code="txt.help.activity1" />
				<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.activity" />"></i>
			</span>
		</form:label>
		<div class="col-md-8">
			<form:select class="form-select2" path="activity">
				<option></option>
				<c:forEach var="choseActivity" items="${choseActivities}" varStatus="state">
					<optgroup label="<spring:message code="chose.sector${state.count}" />">
						<c:forEach var="codeActivity" items="${choseActivity}">
							<option value="${codeActivity}"><c:out value="${codeActivity}"/> - <spring:message code="chose.activity.${codeActivity}" /></option>
						</c:forEach>
					</optgroup>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="description">
	<div id="descriptionForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="description">
			<spring:message code="lbl.description" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text">
				<spring:message code="txt.help.description" />
				<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.description" />"></i>
			</span>
		</form:label>
		<div class="col-md-8">
			<form:textarea class="form-control form-area" rows="3" path="description" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="lang">
	<div id="langForm" class="form-group row m-b-10">
		<form:label class="col-form-label col-md-4" path="lang">
			<spring:message code="lbl.lang" /> <small class="min"><spring:message code="lbl.requis" /></small>
		</form:label>
		<div class="col-md-4">
			<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
			<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
			<form:select class="form-select2-simple" path="lang">
				<option></option>
				<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
					<option value="${pageScope.choser}" ${company.lang == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>