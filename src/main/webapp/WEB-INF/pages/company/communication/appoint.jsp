<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/communication/notices"/>" class="lien lien-black">
			<i class="cmsms-icon-cloud m-r-5"></i><spring:message code="sidebar.company.dashboard8"/></a></li>
		<li><a href="<c:url value="/company/communication/appointments"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard8.4"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.communication.appoint.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.communication4.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/communication/appointments" scope="request"></c:set>
	<c:set var="backwordPage" value="13" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<c:choose>
			<c:when test="${currentCompany.hasPremium()}">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.appoint1"/></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="txt.help.explorer3.4" />
						<span class="help-text"><spring:message code="txt.help.appoint1.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="table-responsive">
							<table class="table table-page table-panel">
								<thead><tr><th style="width:30%;"></th><th style="width:70%;"></th></tr></thead>
								<tbody class="font-small">
									<tr>
										<td><spring:message code="tabs.user"/></td>
										<td>
											<div class="form-identity">
												<img src="<c:url value="${appointDetail.userMini.urlAvatar}"/>" class="pull-left img-circle"
													alt="<c:out value="${appointDetail.userMini.username}" />">
												<div class="identity-brand">
													<a href="<c:url value="${appointDetail.userMini.pseudoURL}" />" class="lien lien-table" 
														title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
														<c:out value="${appointDetail.userMini.username}" />
													</a><span class="i-certificated i-certificated${appointDetail.userMini.verified}"></span>
													<span class="help-text"><spring:message code="lbl.sub.pro${appointDetail.userMini.hasPro ? 1 : 2}" /></span>
												</div>
												<span class="clearfix"></span>
											</div>
										</td>
									</tr>
									<tr><td><spring:message code="tabs.requested"/></td><td><c:out value="${appointDetail.postedDate}" /></td></tr>
									<tr><td><spring:message code="tabs.message"/></td><td><c:out value="${appointDetail.motif}" /></td></tr>
									<tr>
										<td><spring:message code="lbl.sub.appoint3"/></td>
										<td>
											<c:choose>
												<c:when test="${empty appointDetail.forDate}"><spring:message code="txt.help.appoint1.2" /></c:when>
												<c:otherwise>
													<spring:message code="lbl.date.for" /> <c:out value="${appointDetail.forDate}" />
													<c:if test="${!empty appointDetail.toDate}">
														- <spring:message code="lbl.date.to" /> <c:out value="${appointDetail.toDate}" />
													</c:if>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td><spring:message code="lbl.sub.appoint4"/></td>
										<td>
											<c:choose>
												<c:when test="${empty appointDetail.period}"><spring:message code="txt.help.appoint1.2" /></c:when>
												<c:otherwise><spring:message code="chose.appoint${appointDetail.period}" /></c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr><td><spring:message code="lbl.sub.appoint2"/></td><td><spring:message code="chose.degree${appointDetail.degree}" /></td></tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<hr class="my-2 m-t-20">
				<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.appoint2"/></h2>
				<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
					<form:form name="appointForm" action="/" method="POST" modelAttribute="appoint" enctype="utf8" novalidate="novalidate">
						<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
						<spring:bind path="appointDate">
							<div id="appointDateForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="appointDate">
									<spring:message code="tabs.appointed" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<div class="input-group-icon date">
										<form:input class="form-control" type="text" path="appointDate" />
										<span class="input-group-addon" style="display:none;"></span>
									</div>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="clock">
							<div id="clockForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="clock">
									<spring:message code="tabs.clock" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<form:select class="form-select2-simple" path="clock">
										<option></option>
										<c:forEach var="i" begin="8" end="23" step="1">
											<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}:00"/></option>
										</c:forEach>
										<option value="24"><c:out value="00:00"/></option>
									</form:select>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<div class="form-group row m-b-30">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-4">
								<div id="submitForm" class="form-submit m-t-10">
									<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
										<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.validate"/></span>
									</button>
								</div>
							</div>
						</div>
					</form:form>
				</sec:authorize>
				<hr class="my-4">
				<p class="font-mini m-b-20"><spring:message code="txt.help.appoint1.3"/></p>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<i class="cmsms-icon-dollar i-alert"></i>
					<p class="p-alert">
						<spring:message code="message.premium.communication"/> 
						<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
					</p>
				</div>
			</c:otherwise>	
		</c:choose>
	</div>
</div>