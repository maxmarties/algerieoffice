<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/mailing/newsletter" />" class="lien lien-black">
			<i class="cmsms-icon-email m-r-5"></i><spring:message code="sidebar.admin.dashboard12"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard12.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard12.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.mailing2"/></p>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
			<form:form name="marketplaceForm" action="/" method="POST" modelAttribute="marketplace" enctype="utf8" novalidate="novalidate">
				<div class="row">
					<div class="col-md-6 m-b-10">
						<spring:bind path="todays">
							<div id="todaysForm" class="form-group row">
								<form:label class="col-form-label col-md-6" path="todays">
									<spring:message code="lbl.sub.explorer7.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-6">
									<form:input class="form-control" type="number" path="todays" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<hr class="my-4">
						<h3 class="h-header h-header4 i-primary"><spring:message code="subheader.mailing1.1"/></h3>
						<p class="font-small m-t-5"><spring:message code="txt.admin.mailing1.1"/></p>
						<hr class="my-4">
						<spring:bind path="users"><form:input type="hidden" path="users" /></spring:bind>
						<div id="formTargetForm" class="form-group">
							<div class="table-scroll m-t-5">
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
										<c:forEach var="user" items="${marketplace.users}">
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
					<div class="col-md-6 m-b-10">
						<spring:bind path="offers">
							<div id="offersForm" class="form-group row">
								<form:label class="col-form-label col-md-6" path="offers">
									<spring:message code="lbl.sub.explorer7.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-6">
									<form:input class="form-control" type="number" path="offers" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<hr class="my-4">
						<h3 class="h-header h-header4 i-primary"><spring:message code="subheader.mailing1.2"/></h3>
						<p class="font-small m-t-5"><spring:message code="txt.admin.mailing2.1"/></p>
						<hr class="my-4">
						<spring:bind path="news"><form:input type="hidden" path="news" /></spring:bind>
						<div id="formAnnonceForm" class="form-group">
							<div class="table-scroll m-t-5">
								<table class="table table-target">
									<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
									<tbody class="font-small">
										<tr class="header">
											<td class="td-check">
												<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
													<input type="checkbox" name="checkAllRowAnnonce"/>
													<span class="input-span"></span>
												</label>
											</td>
											<td><spring:message code="sidebar.admin.dashboard5.1"/></td>
										</tr>
										<c:forEach var="annonce" items="${marketplace.annonces}">
											<tr>
												<td class="td-check">
													<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
														<input type="checkbox" name="checkRowAnnonce" data-row="${annonce.uuid}"/>
														<span class="input-span"></span>
													</label>
												</td>
												<td class="td-result"><c:out value="${annonce.title}"/><span class="help-text"><spring:message code="chose.annonce${annonce.type}"/></span></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<input type="hidden" id="countAnnonces" value="0" />
							</div>
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
</div>