<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.marketplace.employe1"/></h2>
<spring:bind path="contract">
	<div id="contractForm" class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="tabs.contract" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace3.1" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<div>
				<c:forEach var="i" begin="1" end="4" step="1">
					<label class="ui-radio ui-radio-segond font-small m-r-20">
						<form:radiobutton value="${i}" path="contract" />
						<span class="input-span"></span><spring:message code="chose.contract${i}" />
					</label>
				</c:forEach>
			</div>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<div id="createForm" class="animated onne fadeIn m-b-0" style="${empty employe.id ? 'display:none;' : ''}">
	<spring:bind path="title">
		<div id="titleForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="title">
				<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.marketplace3.2" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:input class="form-control" type="text" path="title" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="identify">
		<div id="identifyForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="identify">
				<spring:message code="lbl.identify" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text">
					<spring:message code="txt.help.url" />
					<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.identify" />"></i>
				</span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<div class="input-group">
					<div class="input-group-lien">
						<c:out value="${!empty currentCompany.url ? currentCompany.url : '@'}"/><c:out value="${urlEmploye}"/>
					</div>
					<form:input class="form-control" type="text" path="identify" />
				</div>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="domaine">
		<div id="domaineForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="domaine">
				<spring:message code="lbl.contract.domaine" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.marketplace3.3" /></span>
			</form:label>
			<div class="col-md-6 col-lg-4">
				<c:set var="faholder" scope="page"><spring:message code="chose.domaine" /></c:set>
				<form:select class="form-select2-simple" path="domaine" data-placeholder="${pageScope.faholder}">
					<option></option>
					<c:forEach var="i" begin="1" end="11" step="1">
						<option value="${i}" ${employe.domaine == i ? 'selected' : ''}><spring:message code="chose.domaine${i}" /></option>
					</c:forEach>
				</form:select>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="expiredDate">
		<div id="expiredDateForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="expiredDate">
				<spring:message code="tabs.expire" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.marketplace3.4" /></span>
			</form:label>
			<div class="col-md-6 col-lg-4">
				<div class="input-group-icon date">
					<form:input class="form-control" type="text" path="expiredDate" />
					<span class="input-group-addon" style="display:none;"></span>
				</div>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<input type="hidden" id="maxkeysword" value="${maxkeysword}" />
	<spring:bind path="keysword">
		<div id="keyswordForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="keysword">
				<spring:message code="tabs.keys" />
				<span class="help-text">
					<spring:message code="txt.help.keys" arguments="${maxkeysword}" />
					<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.keys" />"></i>
				</span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:input class="form-control" type="text" path="keysword" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="urlExtern">
		<div id="urlExternForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="urlExtern">
				<spring:message code="lbl.externurl" />
				<span class="help-text"><spring:message code="txt.help.marketplace3.5" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<c:set var="faholder" scope="page"><spring:message code="tool.ind.contract" /></c:set>
				<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="hasPublished">
		<div id="hasPublishedForm" class="form-group row m-b-20">
			<form:label class="col-form-label col-md-4 col-lg-3" path="hasPublished">
				<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
			</form:label>
			<div class="col-md-4 col-lg-3">
				<label class="ui-radio ui-radio-segond font-small m-r-10">
					<form:radiobutton value="${true}" path="hasPublished" />
					<span class="input-span"></span><spring:message code="chose.published1" />
				</label>
				<label class="ui-radio ui-radio-segond font-small">
					<form:radiobutton value="${false}" path="hasPublished" />
					<span class="input-span"></span><spring:message code="chose.published2" />
				</label>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
</div>