<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.1" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.company.identity"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile1.${!currentCompany.enabled ? '1' : '2'}"/></p>
	</div>
	<div id="identityResult">
		<c:if test="${!currentCompany.enabled && identityState.exists}">
			<c:choose>
				<c:when test="${!identityState.consulted}">
					<div class="alert alert-info m-t-20">
						<i class="cmsms-icon-info-circled-3 i-alert"></i>
						<p class="p-alert">
							<spring:message code="txt.company.profile1.3.3"/>
							<span class="help-text m-t-5">
								<i class="cmsms-icon-calendar-7 i-segond m-r-5"></i>
								<span class="font-mini"><spring:message code="tool.ind.sent"/> : 
								<span class="font-bold"><c:out value="${identityState.requestedDate}" /></span></span>
							</span>
						</p>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger m-t-20">
						<i class="cmsms-icon-attention-circled i-alert"></i>
						<p class="p-alert"><spring:message code="txt.company.profile1.3.4"/></p>
					</div>
					<hr class="my-4">
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${currentCompany.enabled || !identityState.exists || identityState.consulted}">
			<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
				<form:form name="identityForm" action="/" method="POST" modelAttribute="identity" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="denomination">
						<div id="denominationForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="denomination">
								<spring:message code="lbl.denomination" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.denomination2" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:input class="form-control" type="text" path="denomination" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="tradename">
						<div id="tradenameForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="tradename">
								<spring:message code="tabs.tradename" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.tradename2" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:input class="form-control" type="text" path="tradename" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="buildDate">
						<div id="buildDateForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="buildDate">
								<spring:message code="tabs.buildate" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<div class="input-group-icon date">
									<form:input class="form-control" type="text" path="buildDate" />
									<span class="input-group-addon" style="display:none;"></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="activity">
						<div id="activityForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="activity">
								<spring:message code="lbl.sub.activity1" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.activity1" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:select class="form-select2" path="activity" >
									<option></option>
									<c:forEach var="choseActivity" items="${choseActivities}" varStatus="state">
										<optgroup label="<spring:message code="chose.sector${state.count}" />">
											<c:forEach var="codeActivity" items="${choseActivity}">
												<option value="${codeActivity}" ${identity.activity == codeActivity ? 'selected' : ''}><c:out value="${codeActivity}"/> - <spring:message code="chose.activity.${codeActivity}" /></option>
											</c:forEach>
										</optgroup>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div class="row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
					</div>
					<spring:bind path="activities"><form:input type="hidden" path="activities" /></spring:bind>
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.sub.activity3" />
							<span class="help-text"><spring:message code="txt.help.activity2" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<table id="tableActivities" class="table table-page ${!identity.activities.isEmpty() ? 'm-b-20' : ''}">
								<thead><tr><th style="width:calc(100% - 34px);"></th><th style="width:34px;"></th></tr></thead>
								<tbody class="font-small">
									<c:forEach var="codeActivity" items="${identity.activities}" varStatus="state">
										<tr id="lineActivity${state.count}">
											<td class="i-input"><spring:message code="chose.activity.${codeActivity}" /></td>
											<td class="btn-td">
												<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
													onclick="deleteLineActivity('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="hidden" id="countActivities" value="${identity.activities.size()}" />
							<a id="insertActivity" class="btn btn-success btn-simple btn-add btn-left">
								<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.activity"/></span>
							</a>
							<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;">
								<label class="col-form-label m-t-10">
									<spring:message code="lbl.sub.activity2" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</label>
								<select class="form-select2" id="insert" name="insert" >
									<option></option>
									<c:forEach var="choseActivity" items="${choseActivities}" varStatus="state">
										<optgroup label="<spring:message code="chose.sector${state.count}" />">
											<c:forEach var="codeActivity" items="${choseActivity}">
												<option value="${codeActivity}"><c:out value="${codeActivity}"/> - <spring:message code="chose.activity.${codeActivity}" /></option>
											</c:forEach>
										</optgroup>
									</c:forEach>
								</select>
								<span class="error"></span>
								<div class="m-t-20 text-right">
									<a id="addActivity" class="btn btn-primary btn-fixed m-b-10"><span><spring:message code="btn.add"/></span></a>
									<a id="closeActivity" class="btn btn-segond btn-fixed m-l-10 m-b-10"><span><spring:message code="btn.cancel"/></span></a>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
					</div>
					<div id="inputFileForm" class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.building" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.building" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.building" />"></i>
							</span>
						</label>
						<div class="col-md-8 col-lg-9">
							<div class="file-input">
								<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
									<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="image/*">
									<span><spring:message code="btn.file"/></span>
								</label>
								<label id="resultFile"><spring:message code="tooltip.file" /></label>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<c:if test="${!currentCompany.enabled}">
						<spring:bind path="url">
							<div id="urlForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="url">
									<spring:message code="lbl.companyurl" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text">
										<spring:message code="txt.help.url" />
										<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.companyurl" />"></i>
									</span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<div class="input-group">
										<div class="input-group-lien"><c:out value="${staticURL}"/><c:out value="${urlCompanies}"/></div>
										<div class="input-group-icon input-group-check" data-input="">
											<form:input class="form-control" type="text" path="url" />
										</div>
									</div>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="checkedURL"><form:input type="hidden" path="checkedURL" /></spring:bind>
					</c:if>
					<input type="hidden" id="companyEnabled" value="${currentCompany.enabled}" />
					<div class="form-group row m-b-20">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-20 m-b-20">
								<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
				            		<span><i class="cmsms-icon-${!currentCompany.enabled ? 'paper-plane-3' : 'floppy'}"></i>
				            			<spring:message code="btn.${!currentCompany.enabled ? 'send' : 'update'}"/></span>
				            	</button>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
			<hr class="my-4">
			<p class="font-mini m-t-10"><spring:message code="txt.company.profile1.4.${!currentCompany.enabled ? '1' : '2'}"/></p>
		</c:if>
	</div>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<c:choose>
				<c:when test="${!currentCompany.enabled}">
					<div class="alert alert-warning">
						<i class="cmsms-icon-attention i-alert"></i>
						<p class="p-alert"><spring:message code="txt.company.profile1.3.1"/></p>
					</div>
					<hr class="my-4">
					<p class="font-mini m-t-10"><spring:message code="txt.company.profile1.3.2"/> 
						<a href="<c:url value="/infos/faq?sect=1"/>" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.more"/></a></p>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<table class="table table-page table-panel">
							<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
							<tbody class="font-small">
								<c:set var="providers" value="lbl.denomination,tabs.tradename,tabs.buildate" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<tr>
										<td><spring:message code="${pageScope.provider}" /></td>
										<td><c:out value="${identity.getInfoLine(state.count)}"/></td>
									</tr>
								</c:forEach>
								<tr>
									<td><spring:message code="lbl.sub.activity1" /></td>
									<td><spring:message code="chose.activity.${identity.activity}" /></td>
								</tr>
							</tbody>
						</table>
					</div>
					<c:if test="${!identity.activities.isEmpty()}">
						<h3 class="h-header h-header6 i-primary m-t-20"><c:out value="${identity.activities.size()}" /> <spring:message code="lbl.sub.activity3" /></h3>
						<div class="form-group m-t-10 m-b-20">
							<ul class="list-none font-small">
								<c:forEach var="code" items="${identity.activities}">
									<li><spring:message code="chose.activity.${code}" /></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>