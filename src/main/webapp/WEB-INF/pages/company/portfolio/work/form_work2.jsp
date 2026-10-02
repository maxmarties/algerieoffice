<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.portfolio.work2"/></h2>
<spring:bind path="description">
	<div id="descriptionForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="description">
			<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.portfolio1.4" /></span>
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
		<span class="help-text"><spring:message code="txt.help.portfolio1.5" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<nav class="navbar navbar-editor">
			<ul class="nav">
				<li>
					<button id="resetEditor" class="btn btn-editor btn-simple ${empty work.detail ? 'disabled' : ''}" 
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
		<div id="editor"><c:if test="${!empty work.id}"><c:out value="${work.detail}" escapeXml="false" /></c:if></div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="partner">
	<div id="partnerForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="partner">
			<spring:message code="tabs.partner" />
			<span class="help-text"><spring:message code="txt.help.portfolio1.6" /></span>
		</form:label>
		<div class="col-md-6 col-lg-4">
			<form:select class="form-select2" path="partner">
				<option value="-" ${empty work.partner ? 'selected' : ''}><c:out value="-" /></option>
				<c:forEach var="chosePartner" items="${chosePartners}" >
					<option value="${chosePartner.uuid()}" ${chosePartner.uuid() == work.partner ? 'selected' : ''}><c:out value="${chosePartner.name}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>