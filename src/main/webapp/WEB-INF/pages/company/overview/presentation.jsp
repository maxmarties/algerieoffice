<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview2"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<form:form name="presentationForm" action="/" method="POST" modelAttribute="presentation" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
			<div id="editorForm" class="form-group form-editor row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text">
						<spring:message code="txt.help.companydetail" />
						<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.companydetail" />"></i>
					</span>
				</label>
				<div class="col-md-8 col-lg-9">
					<nav class="navbar navbar-editor">
						<ul class="nav">
							<li>
								<button id="resetEditor" class="btn btn-editor btn-simple ${empty presentation.detail ? 'disabled' : ''}" 
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
					<div id="editor"><c:if test="${!empty presentation.detail}"><c:out value="${presentation.detail}" escapeXml="false" /></c:if></div>
					<span class="error"></span>
					<p class="font-mini m-t-10"><spring:message code="txt.company.overview2.1"/></p>
					<hr class="my-4">
				</div>
			</div>
			<div id="fileForm" class="form-group row">
				<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
				<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.extrawid" />
					<span class="help-text">
						<spring:message code="txt.help.companyextra" />
						<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.companyextra" />"></i>
					</span>
				</label>
				<div class="col-md-8 col-lg-9">
					<div class="avatar-content avatar-extrawid">
						<div class="avatar-view">
							<img id="avatarImg" class="img-responsive transition-35"
								src="<c:url value="${presentation.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
							<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
						</div>
						<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
							style="${!presentation.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i>
						</a>
					</div>
					<span class="error"></span>
				</div>
			</div>
			<div class="form-group row m-b-30">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4">
					<div id="submitForm" class="form-submit m-t-10">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>