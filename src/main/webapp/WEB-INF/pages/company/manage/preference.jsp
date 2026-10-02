<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/manage/display"/>" class="lien lien-black">
			<i class="cmsms-icon-sliders m-r-5"></i><spring:message code="sidebar.company.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard5.5" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard5.5"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.manage5"/></p>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
			<form:form name="preferenceForm" action="/" method="POST" modelAttribute="preference" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.preference1"/></h2>
				<spring:bind path="hasActive">
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.sub.preference1.1" />
							<span class="help-text"><spring:message code="txt.help.preference1.1" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<ul class="nav">
								<li class="m-r-10"><spring:message code="lbl.sub.preference1.1.1" /></li>
								<li><label class="ui-switch ui-switch-action">
									<form:checkbox path="hasActive" /><span class="input-span"></span><span class="layer-span"></span>
								</label></li>
								<li class="m-l-10"><spring:message code="lbl.sub.preference1.1.2" /></li>
							</ul>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="language">
					<div id="languageForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="language">
							<spring:message code="lbl.lang" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.preference1.2" /></span>
						</form:label>
						<div class="col-md-4 col-lg-3">
							<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
							<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
							<form:select class="form-select2-simple" path="language">
								<option></option>
								<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
									<option value="${pageScope.choser}" ${preference.language == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="messengerId">
					<div id="messengerIdForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="messengerId">
							<spring:message code="lbl.sub.preference1.2" /> <small class="min"><spring:message code="lbl.requis" /></small><i class="cmsms-icon-dollar i-dollar m-l-5"></i>
							<span class="help-text"><spring:message code="txt.help.preference1.3" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:select class="form-select2" path="messengerId" disabled="${preference.disabledMessenger}">
								<option></option>
								<c:forEach var="choseUser" items="${choseUsers}" >
									<option value="${choseUser.userId}" ${choseUser.userId == preference.messengerId ? 'selected' : ''}><c:out value="${choseUser.email}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-t-10">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="disabledMessenger">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="disabledMessenger" />
								<span class="input-span"></span><spring:message code="comp.messenger" />
							</label>
						</spring:bind>
					</div>
				</div>
				<hr class="my-2">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.preference2"/></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="tooltip.popup.notifications" />
						<span class="help-text"><spring:message code="txt.help.preference2.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="table-responsive">
							<table class="table table-page table-console">
								<thead><tr><th style="width:calc(100% - 168px);"></th><th style="width:84px;"></th><th style="width:84px;"></th></tr></thead>
								<tbody class="font-small">
									<tr class="console-header i-primary">
										<td><spring:message code="tooltip.popup.clouds" /></td>
										<td class="btn-td"><i class="cmsms-icon-cloud"></i></td>
										<td class="btn-td"><spring:message code="tabs.email" /><i class="cmsms-icon-dollar i-dollar m-l-5"></i></td>
									</tr>
									<c:forEach var="i" begin="1" end="7">
										<tr>
											<td><spring:message code="lbl.sub.preference2.1.${i}" /></td>
											<td class="btn-td">
												<spring:bind path="communications[${i - 1}]">
													<label class="ui-checkbox ui-checkbox-segond font-small m-l-5">
														<form:checkbox path="communications[${i - 1}]" /><span class="input-span"></span>
													</label>
												</spring:bind>
											</td>
											<td class="btn-td">
												<c:if test="${i == 1}">
													<spring:bind path="mails[0]">
														<label class="ui-checkbox ui-checkbox-segond font-small">
															<form:checkbox path="mails[0]" /><span class="input-span"></span>
														</label>
													</spring:bind>	
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<tr class="console-header i-primary">
										<td><spring:message code="sidebar.company.dashboard9" /></td>
										<td class="btn-td"><i class="cmsms-icon-cloud"></i></td>
										<td class="btn-td"><spring:message code="tabs.email" /><i class="cmsms-icon-dollar i-dollar m-l-5"></i></td>
									</tr>
									<c:forEach var="i" begin="1" end="4">
										<tr>
											<td><spring:message code="lbl.sub.preference2.2.${i}" /></td>
											<td class="btn-td">
												<spring:bind path="perspects[${i - 1}]">
													<label class="ui-checkbox ui-checkbox-segond font-small m-l-5">
														<form:checkbox path="perspects[${i - 1}]" /><span class="input-span"></span>
													</label>
												</spring:bind>
											</td>
											<td class="btn-td">
												<spring:bind path="mails[${i}]">
													<label class="ui-checkbox ui-checkbox-segond font-small m-l-5">
														<form:checkbox path="mails[${i}]" /><span class="input-span"></span>
													</label>
												</spring:bind>
											</td>
										</tr>
									</c:forEach>
									<tr class="console-header i-primary">
										<td><spring:message code="lbl.sub.preference2.3" /></td>
										<td class="btn-td"><i class="cmsms-icon-cloud"></i></td>
										<td class="btn-td"><spring:message code="tabs.email" /><i class="cmsms-icon-dollar i-dollar m-l-5"></i></td>
									</tr>
									<c:forEach var="i" begin="1" end="2">
										<tr>
											<td><spring:message code="lbl.sub.preference2.3.${i}" /></td>
											<td class="btn-td">
												<spring:bind path="perspects[${i + 3}]">
													<label class="ui-checkbox ui-checkbox-segond font-small m-l-5">
														<form:checkbox path="perspects[${i + 3}]" /><span class="input-span"></span>
													</label>
												</spring:bind>
											</td>
											<td class="btn-td"></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<hr class="my-2">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.preference3"/></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.companymail" /><i class="cmsms-icon-dollar i-dollar m-l-5"></i>
						<span class="help-text"><spring:message code="txt.help.preference3.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<table class="table table-page m-b-10">
							<thead><tr><th style="width:calc(100% - 34px);"></th><th style="width:34px;"></th></tr></thead>
							<tbody class="font-small">
								<tr>
									<td><c:out value="${preference.email}"/></td>
									<td class="btn-td">
										<a href="<c:url value="/company/profile/contact"/>" class="btn btn-table btn-yellow" 
											title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
									</td>
								</tr>
							</tbody>
						</table>
						<p class="font-mini"><spring:message code="txt.help.preference3.2" /></p>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="tabs.phone" />
						<span class="help-text"><spring:message code="txt.help.preference3.3" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<table class="table table-page">
							<thead><tr><th style="width:calc(100% - 34px);"></th><th style="width:34px;"></th></tr></thead>
							<tbody class="font-small">
								<tr>
									<td><c:out value="${preference.phone}"/></td>
									<td class="btn-td">
										<a href="<c:url value="/company/profile/contact"/>" class="btn btn-table btn-yellow" 
											title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.account5" />
						<span class="help-text"><spring:message code="txt.help.preference3.4" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<c:choose>
							<c:when test="${preference.published}">
								<table class="table table-page">
									<thead><tr><th style="width:calc(100% - 68px);"></th><th style="width:34px;"></th><th style="width:34px;"></th></tr></thead>
									<tbody class="font-small">
										<tr>
											<td><input class="form-control disaload" type="text" id="pseudoCompany" value="${preference.companyURL}" disabled /></td>
											<td class="btn-td"><a id="pseudoCopy" class="btn btn-table btn-green" title="<spring:message code="btn.copy" />"><i class="cmsms-icon-clipboard"></i></a></td>
											<td class="btn-td"><a href="<c:url value="/company/profile/linked"/>" class="btn btn-table btn-yellow"
												title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a></td>
										</tr>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								<div class="alert alert-warning">
									<i class="cmsms-icon-attention i-alert"></i>
									<p class="p-alert">
										<spring:message code="txt.help.preference3.5"/> 
										<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group row m-b-30">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
						<p class="font-mini m-t-20"><i class="cmsms-icon-dollar i-dollar m-r-10"></i><spring:message code="txt.help.preference3.6"/></p>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>