<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">3. <spring:message code="wizard.marketplace.annonce3"/></h2>
<spring:bind path="description">
	<div id="descriptionForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="description">
			<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace2.5" /></span>
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
		<span class="help-text"><spring:message code="txt.help.marketplace2.6" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<nav class="navbar navbar-editor">
			<ul class="nav">
				<li>
					<button id="resetEditor" class="btn btn-editor btn-simple ${empty annonce.detail ? 'disabled' : ''}" 
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
		<div id="editor"><c:if test="${!empty annonce.id}"><c:out value="${annonce.detail}" escapeXml="false" /></c:if></div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="visibility">
	<div id="visibilityForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="visibility">
			<spring:message code="lbl.visibility" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace2.7" /></span>
		</form:label>
		<div class="col-md-6 col-lg-4">
			<c:set var="faholder" scope="page"><spring:message code="chose.visibility" /></c:set>
			<form:select class="form-select2-simple" path="visibility" data-placeholder="${pageScope.faholder}">
				<option></option>
				<c:forEach var="i" begin="1" end="5" step="1">
					<option value="${i}" ${annonce.visibility == i ? 'selected' : ''}><spring:message code="chose.visibility${i}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="hasFile"><form:input type="hidden" path="hasFile" /></spring:bind>
<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
<div id="inputFileForm" class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.file.detail" />
		<span class="help-text"><spring:message code="txt.help.marketplace2.8" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<div class="file-input">
			<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
				<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="application/pdf">
				<span><spring:message code="btn.file"/></span>
			</label>
			<label id="resultFile">
				<c:choose>
					<c:when test="${annonce.hasFile}"><c:out value="${annonce.filename}" /></c:when>
					<c:otherwise><spring:message code="tooltip.file" /></c:otherwise>
				</c:choose>
			</label>
			<a id="clearFile" class="btn btn-table btn-red" style="${annonce.hasFile ? '' : 'display:none;'}" 
				title="<spring:message code="btn.delete.file"/>">
				<i class="cmsms-icon-trash-7"></i>
			</a>
		</div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="filename"><form:input type="hidden" path="filename" /></spring:bind>