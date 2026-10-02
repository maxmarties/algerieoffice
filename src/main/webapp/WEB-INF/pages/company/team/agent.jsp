<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/team/users"/>" class="lien lien-black">
			<i class="cmsms-icon-user-2 m-r-5"></i><spring:message code="sidebar.company.dashboard7"/></a></li>
		<li><a href="<c:url value="/company/team/agents"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard7.3"/></a></li>
		<li class="active"><spring:message code="${empty agent.id ? 'sidebar.company.dashboard1.2' : 'btn.edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.team.agent.${empty agent.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.team3.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/team/agents" scope="request"></c:set>
	<c:set var="backwordPage" value="12" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty agent.id && hasMaxAgent}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.agent"/> : 
					<a href="<c:url value="/company/tools/subscribes"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.premium"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
				<form:form name="agentForm" action="/" method="POST" modelAttribute="agent" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<div class="row">
						<div class="col-lg-8">
							<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.agent1"/></h2>
							<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
							<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
							<div id="fileForm" class="form-group row">
								<label class="col-form-label col-md-4 col-lg-3">
									<spring:message code="lbl.agentavatar" />
									<span class="help-text"><spring:message code="txt.help.team3.1" /></span>
								</label>
								<div class="col-md-8 col-lg-9">
									<div class="avatar-content avatar-agent">
										<div class="avatar-view">
											<img id="avatarImg" class="img-responsive transition-35"
												src="<c:url value="${agent.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
											<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
										</div>
										<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
											style="${!agent.hasAvatar ? 'display:none;' : ''}">
											<i class="cmsms-icon-trash-7"></i>
										</a>
									</div>
									<span class="error"></span>
								</div>
							</div>
							<spring:bind path="sexe">
								<div id="sexeForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="sexe">
										<spring:message code="tabs.sexe" /> <small class="min"><spring:message code="lbl.requis" /></small>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<label class="ui-radio ui-radio-segond font-small m-r-10">
											<form:radiobutton value="${true}" path="sexe" />
											<span class="input-span"></span><spring:message code="chose.sexe1" />
										</label>
										<label class="ui-radio ui-radio-segond font-small">
											<form:radiobutton value="${false}" path="sexe" />
											<span class="input-span"></span><spring:message code="chose.sexe2" />
										</label>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
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
							<spring:bind path="function">
								<div id="functionForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="function">
										<spring:message code="tabs.function" /> <small class="min"><spring:message code="lbl.requis" /></small>
										<span class="help-text"><spring:message code="txt.help.team3.2" /></span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<form:input class="form-control" type="text" path="function" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="biography">
								<div id="biographyForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="biography">
										<spring:message code="tabs.biography" />
										<span class="help-text"><spring:message code="txt.help.team3.3" /></span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<form:textarea class="form-control form-area" rows="3" path="biography" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="userId">
								<div id="userIdForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="userId">
										<spring:message code="tabs.user" />
										<span class="help-text"><spring:message code="txt.help.team3.4" /></span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<form:select class="form-select2" path="userId">
											<option value="-1"><c:out value="-" /></option>
											<c:forEach var="choseUser" items="${choseUsers}" >
												<option value="${choseUser.userId}" ${agent.userId == choseUser.userId ? 'selected' : ''}><c:out value="${choseUser.email}" /></option>
											</c:forEach>
										</form:select>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
						</div>
						<div class="col-lg-4">
							<hr class="my-4 hidden-md-up m-b-20">
							<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.agent2"/></h2>
							<div class="row m-t-5">
								<div class="col-md-4 col-lg-12">
									<span class="help-text"><spring:message code="txt.help.team3.5" /></span>
								</div>
							</div>
							<spring:bind path="email">
								<div id="emailForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-12" path="email">
										<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
									</form:label>
									<div class="col-md-8 col-lg-12">
										<div class="input-group-contact">
											<i class="cmsms-icon-mail-alt icon-contact"></i>
											<form:input class="form-control" type="email" path="email" />
										</div>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="phone">
								<div id="phoneForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-12" path="phone">
										<spring:message code="tabs.phone" /> <small class="min"><spring:message code="lbl.requis" /></small>
									</form:label>
									<div class="col-md-8 col-lg-12">
										<div class="input-group-contact">
											<i class="cmsms-icon-phone-3 icon-contact"></i>
											<form:input class="form-control" type="tel" path="phone" />
										</div>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="lbl.sub.linked3"/></h2>
							<div class="row m-t-5">
								<div class="col-md-4 col-lg-12">
									<span class="help-text"><spring:message code="txt.help.linked3" /></span>
								</div>
							</div>
							<c:set var="providers" value="facebook,twitter,linkedin" scope="page"></c:set>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<spring:bind path="${pageScope.provider}">
									<div id="${pageScope.provider}Form" class="form-group row">
										<form:label class="col-form-label col-md-4 col-lg-12" path="${pageScope.provider}">
											<span class="text-capitalize"><c:out value="${pageScope.provider}"/></span>
										</form:label>
										<div class="col-md-8 col-lg-12">
											<c:set var="faholder" scope="page"><spring:message code="tool.ind.agent${state.count}" /></c:set>
											<div class="input-group-contact">
												<i class="cmsms-icon-${pageScope.provider} icon-contact"></i>
												<form:input class="form-control" type="url" path="${pageScope.provider}" 
													placeholder="${pageScope.faholder}" />
											</div>
											<span class="error"></span>
										</div>
									</div>
								</spring:bind>
							</c:forEach>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-lg-8">
							<div class="row">
								<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
								<div class="col-md-8 col-lg-9">
									<spring:bind path="hasPingled">
										<label class="ui-checkbox ui-checkbox-segond font-small">
											<form:checkbox path="hasPingled" />
											<span class="input-span"></span><spring:message code="comp.agent" /> 
										</label>
									</spring:bind>
									<hr class="my-4">
									<p class="font-mini"><spring:message code="txt.company.team3.2"/></p>
								</div>
							</div>
						</div>
					</div>
					<hr class="my-4 m-t-30">
					<div class="form-group row m-b-30">
						<div class="col-lg-8">
							<div class="row">
								<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
								<div class="col-md-8 col-lg-9">
									<div id="submitForm" class="form-submit m-t-10">
										<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
											<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty agent.id ? 'save' : 'update'}"/></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
		</c:otherwise>
	</c:choose>
</div>