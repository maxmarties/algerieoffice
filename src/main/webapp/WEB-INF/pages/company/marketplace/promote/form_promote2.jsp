<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.marketplace.promote2"/></h2>
<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
<div id="fileForm" class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.image" />
		<span class="help-text"><spring:message code="txt.help.marketplace1.1" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<div class="avatar-content avatar-promote">
			<div class="avatar-view">
				<img id="avatarImg" class="img-responsive transition-35"
					src="<c:url value="${promote.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
				<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
			</div>
			<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
				style="${promote.hasAvatar ? '' : 'display:none;'}">
				<i class="cmsms-icon-trash-7"></i>
			</a>
		</div>
		<span class="error"></span>
	</div>
</div>
<spring:bind path="title">
	<div id="titleForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="title">
			<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace1.2" /></span>
		</form:label>
		<div class="col-md-8 col-lg-9">
			<form:input class="form-control" type="text" path="title" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="description">
	<div id="descriptionForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="description">
			<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace1.3" /></span>
		</form:label>
		<div class="col-md-8 col-lg-9">
			<form:textarea class="form-control form-area" rows="2" path="description" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="label">
	<div id="labelForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="label">
			<spring:message code="lbl.sub.marketplace1.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace1.4" /></span>
		</form:label>
		<div class="col-md-6 col-lg-4">
			<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
			<form:select class="form-select2-simple" path="label" data-placeholder="${pageScope.faholder}">
				<option></option>
				<c:forEach var="i" begin="1" end="3" step="1">
					<option value="${i}" ${promote.label == i ? 'selected' : ''}><spring:message code="chose.label${i}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<div id="urlForm" class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="tabs.url" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.marketplace1.5" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<div>
			<label class="ui-radio ui-radio-segond font-small">
				<form:radiobutton value="${true}" path="hasURL" />
				<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.2" />
			</label>
			<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked" /></c:set>
			<spring:bind path="url">
				<form:input class="form-control" type="url" path="url" placeholder="${pageScope.faholder}" 
				disabled="${!promote.hasURL}" />
				<span class="error"></span>
			</spring:bind>
		</div>
		<div class="m-t-10">
			<label class="ui-radio ui-radio-segond font-small">
				<form:radiobutton value="${false}" path="hasURL" />
				<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.3" />
			</label>
		</div>
	</div>
</div>
<div class="form-group row">
	<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
	<div class="col-md-8 col-lg-9">
		<spring:bind path="hasPageonly">
			<label class="ui-checkbox ui-checkbox-segond font-small">
				<form:checkbox path="hasPageonly" />
				<span class="input-span"></span><spring:message code="comp.promote" /> 
			</label>
		</spring:bind>
		<hr class="my-2">
		<p class="font-mini"><spring:message code="txt.help.marketplace1.6"/></p>
	</div>
</div>