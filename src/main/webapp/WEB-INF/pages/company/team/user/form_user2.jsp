<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.team.agent2"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.team2.1.2"/></p>
<hr class="my-4">
<div class="form-container">
	<form:form name="teamuserForm" action="/" method="POST" modelAttribute="teamuser" enctype="multipart/form-data" novalidate="novalidate">
		<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
		<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
		<div id="fileForm" class="form-group row">
			<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="lbl.avatar" />
				<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<div class="avatar-content avatar-account">
					<div class="avatar-view">
						<img id="avatarImg" class="img-responsive transition-35"
							src="<c:url value="${teamuser.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
						<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
					</div>
					<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" style="display:none;">
						<i class="cmsms-icon-trash-7"></i>
					</a>
				</div>
				<span class="error"></span>
			</div>
		</div>
		<spring:bind path="firstname">
			<div id="firstnameForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="firstname">
					<spring:message code="lbl.firstname" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<div class="col-md-8 col-lg-9">
					<form:input class="form-control" type="text" path="firstname" />
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<spring:bind path="lastname">
			<div id="lastnameForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="lastname">
					<spring:message code="lbl.lastname" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<div class="col-md-8 col-lg-9">
					<form:input class="form-control" type="text" path="lastname" />
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<spring:bind path="email">
			<div id="emailForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="email">
					<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.team2.2" /></span>
				</form:label>
				<div class="col-md-8 col-lg-9">
					<form:input class="form-control" type="email" path="email" />
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<spring:bind path="password">
			<div id="passwordForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="password">
					<spring:message code="lbl.password" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<div class="col-md-8 col-lg-9">
					<form:input class="form-control" type="password" path="password" />
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<div class="form-group row m-t-10">
			<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
			<div class="col-md-8 col-lg-9">
				<spring:bind path="hasRandomPassword">
					<label class="ui-checkbox ui-checkbox-segond font-small">
						<form:checkbox path="hasRandomPassword" />
						<span class="input-span"></span><spring:message code="comp.teamuser" />
					</label>
				</spring:bind>
			</div>
		</div>
		<spring:bind path="role">
			<div id="roleForm" class="form-group row">
				<form:label class="col-form-label col-md-4 col-lg-3" path="role">
					<spring:message code="tabs.role" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<div class="col-md-4 col-lg-4">
					<c:set var="providers" value="visit,autor,edit,manager,admin" scope="page"></c:set>
					<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
					<form:select class="form-select2-simple" path="role" data-placeholder="${pageScope.faholder}">
						<option></option>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<option value="${state.count}"><spring:message code="chose.role.${pageScope.provider}" /></option>
						</c:forEach>
					</form:select>
					<span class="error"></span>
				</div>
			</div>
		</spring:bind>
		<div class="form-group row">
			<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
			<div class="col-md-8 col-lg-9">
				<hr class="my-4">
				<div id="submituserForm" class="form-submit m-t-20">
					<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
						<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
					</button>
				</div>
			</div>
		</div>
	</form:form>
</div>