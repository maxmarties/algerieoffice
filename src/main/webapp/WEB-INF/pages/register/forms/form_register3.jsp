<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">3. <spring:message code="subheader.register2.3"/></h2>
<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
<div id="fileForm" class="form-group row">
	<label class="col-form-label col-md-4">
		<spring:message code="lbl.companylogo" />
		<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
	</label>
	<div class="col-md-8">
		<div class="avatar-content avatar-company">
			<div class="avatar-view">
				<img id="avatarImg" class="img-responsive transition-35"
					src="<c:url value="/static/picts/avatars/company-min.jpg"/>" alt="<spring:message code="tooltip.avatar" />">
				<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
			</div>
			<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" style="display:none;"><i class="cmsms-icon-trash-7"></i></a>
		</div>
		<span class="error"></span>
	</div>
</div>
<div class="row m-t-20">
	<label class="col-form-label col-md-4" for="address">
		<spring:message code="lbl.companyadresse" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.companyaddress" /></span>
	</label>
	<div class="col-md-8">
		<spring:bind path="address">
			<div id="addressForm" class="form-group m-b-0">
				<form:input class="form-control" type="text" path="address" />
				<span class="error"></span>
			</div>
		</spring:bind>
		<div class="row m-t-10">
			<spring:bind path="postal">
				<div id="postalForm" class="form-group col-md-4 m-b-10">
					<c:set var="faholder" scope="page"><spring:message code="lbl.postal" /></c:set>
					<form:input class="form-control" type="text" path="postal" placeholder="${pageScope.faholder}" />
					<span class="error"></span>
				</div>
			</spring:bind>
			<spring:bind path="wilaya">
				<div id="wilayaForm" class="form-group col-md-8 m-b-10">
					<c:set var="faholder" scope="page"><spring:message code="lbl.wilaya" /></c:set>
					<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}" >
						<option></option>
						<c:forEach var="i" begin="1" end="48" step="1">
							<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
						</c:forEach>
					</form:select>
					<span class="error"></span>
				</div>
			</spring:bind>
		</div>
	</div>
</div>
<spring:bind path="phone">
	<div id="phoneForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="phone">
			<spring:message code="tabs.phone" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.phone" /></span>
		</form:label>
		<div class="col-md-8">
			<div class="input-group-phone">
				<c:set var="faholder" scope="page"><spring:message code="tool.ind.phone" /></c:set>
				<span class="input-icon"><c:out value="+213"/></span>
				<form:input class="form-control" type="tel" path="phone" placeholder="${pageScope.faholder}" />
			</div>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<spring:bind path="companymail">
	<div id="companymailForm" class="form-group row">
		<form:label class="col-form-label col-md-4" path="companymail">
			<spring:message code="lbl.companymail" />
			<span class="help-text"><spring:message code="txt.help.companymail" /></span>
		</form:label>
		<div class="col-md-8">
			<form:input class="form-control" type="email" path="companymail" />
			<span class="error"></span>
		</div>
	</div>
</spring:bind>