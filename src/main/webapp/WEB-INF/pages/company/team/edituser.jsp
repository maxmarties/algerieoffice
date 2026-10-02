<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/team/users"/>" class="lien lien-black">
			<i class="cmsms-icon-user-2 m-r-5"></i><spring:message code="sidebar.company.dashboard7"/></a></li>
		<li><a href="<c:url value="/company/team/users"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard7.1"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.team.user.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.team2.2"/></p>
	</div>
	<c:set var="backwordURL" value="/company/team/users" scope="request"></c:set>
	<c:set var="backwordPage" value="11" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="form-container">
		<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
			<form:form name="teamuserForm" action="/" method="POST" modelAttribute="teamuser" enctype="multipart/form-data" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
					<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
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
							<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
								style="${!teamuser.hasAvatar ? 'display:none;' : ''}">
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
							<span class="help-text"><spring:message code="txt.help.team2.3" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="email" path="email" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
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
									<option value="${state.count}" ${teamuser.role == state.count ? 'selected' : ''}><spring:message code="chose.role.${pageScope.provider}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>