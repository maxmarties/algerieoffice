<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/blog/all"/>" class="lien lien-black">
			<i class="cmsms-icon-quote-right m-r-5"></i><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li><a href="<c:url value="/admin/blog/autors"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5.4"/></a></li>
		<li class="active"><spring:message code="btn.${empty autor.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.blog.autor.${!empty autor.id ? 'edit' : 'new'}"/></h1>
		<p><spring:message code="txt.admin.autor"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/blog/autors" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<form:form name="autorForm" action="/" method="POST" modelAttribute="autor" enctype="multipart/form-data" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
				<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.avatar" />
						<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="avatar-content avatar-account">
							<div class="avatar-view">
								<img id="avatarImg" class="img-responsive transition-35"
									src="<c:url value="${autor.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
								<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
							</div>
							<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
								style="${!autor.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i></a>
						</div>
						<span class="error"></span>
					</div>
				</div>
				<spring:bind path="autorname">
					<div id="autornameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="autorname">
							<spring:message code="tabs.username" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:input class="form-control" type="text" path="autorname" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="identify">
					<div id="identifyForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="identify">
							<spring:message code="lbl.autorurl" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.url" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<div class="input-group">
								<div class="input-group-lien"><c:out value="${urlAutors}"/></div>
								<div class="input-group-icon input-group-check" data-input="">
									<form:input class="form-control" type="text" path="identify" />
								</div>
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="checkedIdentify"><form:input type="hidden" path="checkedIdentify" /></spring:bind>
				<spring:bind path="function">
					<div id="functionForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="function">
							<spring:message code="tabs.function" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.team3.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
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
						<div class="col-md-8 col-lg-6">
							<form:textarea class="form-control form-area" rows="3" path="biography" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div id="emailForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="email"><spring:message code="tabs.email" /></form:label>
						<div class="col-md-8 col-lg-6">
							<div class="input-group-contact">
								<i class="cmsms-icon-mail-alt icon-contact"></i>
								<form:input class="form-control" type="email" path="email" />
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<c:set var="providers" value="facebook,twitter,linkedin" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<spring:bind path="${pageScope.provider}">
						<div id="${pageScope.provider}Form" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="${pageScope.provider}">
								<span class="text-capitalize"><c:out value="${pageScope.provider}"/></span>
							</form:label>
							<div class="col-md-8 col-lg-6">
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
				<div class="form-group row m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty autor.id ? 'save' : 'update'}"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>