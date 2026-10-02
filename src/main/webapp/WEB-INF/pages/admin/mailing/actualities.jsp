<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/mailing/newsletter" />" class="lien lien-black">
			<i class="cmsms-icon-email m-r-5"></i><spring:message code="sidebar.admin.dashboard12"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard12.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard12.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.blog.newsletter3"/></p>
	</div>
	<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
		<form:form name="mailingForm" action="/" method="POST" modelAttribute="mailing" enctype="utf8" novalidate="novalidate">
			<div class="row m-t-20">
				<div class="col-md-6 col-lg-4 m-b-10">
					<h3 class="h-header h-header4 i-primary"><spring:message code="subheader.mailing1.1"/></h3>
					<p class="font-small m-t-5"><spring:message code="txt.admin.mailing1.1"/></p>
					<hr class="my-4">
					<spring:bind path="users"><form:input type="hidden" path="users" /></spring:bind>
					<div id="formTargetForm" class="form-group">
						<div class="table-scroll m-t-5" style="min-height:500px;">
							<table class="table table-target">
								<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
								<tbody class="font-small">
									<tr class="header">
										<td class="td-check">
											<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
												<input type="checkbox" name="checkAllRowTarget"/>
												<span class="input-span"></span>
											</label>
										</td>
										<td><spring:message code="chose.members.all"/></td>
									</tr>
									<c:forEach var="user" items="${mailing.users}">
										<tr>
											<td class="td-check">
												<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
													<input type="checkbox" name="checkRowTarget" data-row="${user}"/>
													<span class="input-span"></span>
												</label>
											</td>
											<td class="td-result"><c:out value="${user}"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="hidden" id="countSectors" value="0" />
						</div>
						<span class="error"></span>
					</div>
				</div>
				<div class="col-md-6 col-lg-8 m-b-10">
					<spring:bind path="title">
						<div id="titleForm" class="form-group">
							<label class="col-form-label">
								<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</label>
							<form:input class="form-control" type="text" path="title" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
					<div id="editorForm" class="form-group form-editor">
						<label class="col-form-label"><spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small></label>
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
					</div>
				</div>
			</div>
			<div class="form-group m-b-20">
				<hr class="my-4">
				<div id="submitForm" class="form-submit m-t-20">
					<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
						<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
					</button>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>