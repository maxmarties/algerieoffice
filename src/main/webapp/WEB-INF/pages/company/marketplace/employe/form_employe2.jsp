<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.marketplace.employe2"/></h2>
<spring:bind path="description">
	<div id="descriptionForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="description">
			<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace3.6" /></span>
		</form:label>
		<div class="col-md-8 col-lg-9">
			<form:textarea class="form-control form-area" rows="2" path="description" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
<div id="editorForm" class="form-group form-editor row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.marketplace3.7" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<nav class="navbar navbar-editor">
			<ul class="nav">
				<li>
					<button id="resetEditor" class="btn btn-editor btn-simple ${empty employe.detail ? 'disabled' : ''}" 
						type="button" title="<spring:message code="tooltip.reset"/>">
						<i class="cmsms-icon-arrows-cw i-red"></i>
					</button>
				</li>
			</ul>
			<ul class="nav ml-auto">
				<li class="m-r-5">
					<button id="previewEditor" class="btn btn-editor btn-simple" type="button" title="<spring:message code="btn.preview"/>">
						<i class="cmsms-icon-search-6"></i>
					</button>
				</li>
				<li>
					<button id="editEditor" class="btn btn-editor btn-simple disabled" type="button" title="<spring:message code="btn.editor"/>">
						<i class="cmsms-icon-edit-1"></i>
					</button>
				</li>
			</ul>
		</nav>
		<div id="editor"><c:if test="${!empty employe.id}"><c:out value="${employe.detail}" escapeXml="false" /></c:if></div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="discoverType">
	<div id="discoverTypeForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="discoverType">
			<spring:message code="lbl.contract.discover" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace3.8" /></span>
		</form:label>
		<div class="col-md-6 col-lg-4">
			<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
			<form:select class="form-select2-simple" path="discoverType" data-placeholder="${pageScope.faholder}">
				<option></option>
				<c:forEach var="i" begin="1" end="3" step="1">
					<option value="${i}" ${employe.discoverType == i ? 'selected' : ''}><spring:message code="chose.discover${i}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="discoverValue">
	<div id="discoverValueForm" class="form-group row m-t-10">
		<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
		<div class="col-md-6 col-lg-4">
			<div class="input-group-phone input-group-right">
				<span class="input-icon"><spring:message code="tool.ind.capital" /></span>
				<form:input class="form-control" type="text" path="discoverValue" disabled="${employe.discoverType != 3}" />
			</div>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="updateLocations"><form:input type="hidden" path="updateLocations" /></spring:bind>
<spring:bind path="locations">
	<div id="locationsForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="discoverType">
			<spring:message code="lbl.contract.location" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace3.9" /></span>
		</form:label>
		<div class="col-md-8 col-lg-9">
			<form:select class="form-select2" path="locations" multiple="multiple">
				<option></option>
				<c:forEach var="i" begin="1" end="48" step="1">
					<option value="${i}" ${employe.inLocations(i) ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>