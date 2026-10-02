<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.portfolio.event1"/></h2>
<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
<div id="fileForm" class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.photo" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.portfolio2.1" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<div class="avatar-content avatar-actu">
			<div class="avatar-view">
				<img id="avatarImg" class="img-responsive transition-35"
					src="<c:url value="${event.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
				<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
			</div>
			<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" style="${!event.hasAvatar ? 'display:none;' : ''}">
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
			<span class="help-text"><spring:message code="txt.help.portfolio3.1" /></span>
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
					<c:out value="${!empty currentCompany.url ? currentCompany.url : '@'}"/><c:out value="${urlEvents}"/>
				</div>
				<form:input class="form-control" type="text" path="identify" />
			</div>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<input type="hidden" id="maxkeysword" value="${premium.maxKeywords}" />
<spring:bind path="keysword">
	<div id="keyswordForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="keysword">
			<spring:message code="tabs.keys" />
			<span class="help-text"><spring:message code="txt.help.keys" arguments="${premium.maxKeywords}" /></span>
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
			<span class="help-text"><spring:message code="txt.help.portfolio3.2" /></span>
		</form:label>
		<div class="col-md-8 col-lg-9">
			<c:set var="faholder" scope="page"><spring:message code="tool.ind.event" /></c:set>
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