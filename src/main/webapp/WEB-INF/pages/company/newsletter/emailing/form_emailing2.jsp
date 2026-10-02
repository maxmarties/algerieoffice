<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.newsletter.emailing2"/></h2>
<spring:bind path="items"><form:input type="hidden" path="items" /></spring:bind>
<div id="itemsForm" class="form-group row animated onne fadeIn" style="display:none;">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.sub.newsletter1.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.newsletter3.2.1" /></span>
	</label>
	<div class="col-md-8 col-lg-6">
		<div class="campaign-load">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div id="itemsLoad"></div>
		</div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
<div id="editorForm" class="form-group form-editor row animated onne fadeIn" style="display:none;">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.newsletter3.2.2" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<nav class="navbar navbar-editor">
			<ul class="nav">
				<li>
					<button id="resetEditor" class="btn btn-editor btn-simple disabled" type="button" title="<spring:message code="tooltip.reset"/>">
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
		<div id="editor"></div>
		<span class="error"></span>
		<hr class="my-4">
		<p class="font-mini m-t-10"><spring:message code="txt.company.newsletter3.2"/></p>
	</div>
</div>