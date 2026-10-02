<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<form:form name="partneruserForm" action="/" method="POST" modelAttribute="partneruser" enctype="multipart/form-data" novalidate="novalidate">
	<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
	<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
	<div id="fileForm" class="form-group row">
		<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
		<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="lbl.brand" />
			<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<div class="avatar-content avatar-company">
				<div class="avatar-view">
					<img id="avatarImg" class="img-responsive transition-35"
						src="<c:url value="${partneruser.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
					<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
				</div>
				<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
					style="${!partneruser.hasAvatar ? 'display:none;' : ''}">
					<i class="cmsms-icon-trash-7"></i>
				</a>
			</div>
			<span class="error"></span>
		</div>
	</div>
	<spring:bind path="name">
		<div id="nameForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="name">
				<spring:message code="tabs.tradename" /> <small class="min"><spring:message code="lbl.requis" /></small>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:input class="form-control" type="text" path="name" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="biography">
		<div id="biographyForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="biography">
				<spring:message code="tabs.biography" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.portfolio5.2" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:textarea class="form-control form-area" rows="3" path="biography" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="url">
		<div id="urlForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="url">
				<spring:message code="tabs.url" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.portfolio5.3" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<c:set var="faholder" scope="page"><spring:message code="tool.ind.partner" /></c:set>
				<form:input class="form-control" type="url" path="url" placeholder="${pageScope.faholder}" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<div class="form-group row">
		<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
		<div class="col-md-8 col-lg-9">
			<spring:bind path="hasPingled">
				<label class="ui-checkbox ui-checkbox-segond font-small">
					<form:checkbox path="hasPingled" />
					<span class="input-span"></span><spring:message code="comp.partenaire" /> 
				</label>
			</spring:bind>
		</div>
	</div>
	<div class="form-group row">
		<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
		<div class="col-md-8 col-lg-9">
			<hr class="my-4">
			<div id="submituserForm" class="form-submit m-t-20">
				<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
					<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty partneruser.id ? 'save' : 'update'}"/></span>
				</button>
			</div>
		</div>
	</div>
</form:form>