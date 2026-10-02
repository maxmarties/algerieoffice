<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/companies/all"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li><a href="<c:url value="/admin/companies/features"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3.3"/></a></li>
		<li class="active"><spring:message code="header.tools.subscribe" /></li>
	</ol>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.tools.subscribe"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.premium2.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/companies/features" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.register2.2" /></h2>
	<div class="form-group m-t-20">
		<table class="table table-page">
			<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
			<tbody class="font-small">
				<tr>
					<td class="i-primary font-bold"><spring:message code="tabs.company" /></td>
					<td class="td-brand">
						<img src="<c:url value="${company.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${company.tradename}"/>">
						<div class="brand-colspan">
							<c:choose>
								<c:when test="${!empty company.companyURL}">
									<a href="<c:url value="${company.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
										<c:out value="${company.tradename}" /></a>
								</c:when>
								<c:otherwise><c:out value="${company.tradename}" /></c:otherwise>
							</c:choose>
							<span class="help-text"><spring:message code="chose.activity.${company.activity}"/></span>
						</div>
						<span class="clearfix"></span>
					</td>
				</tr>
				<tr><td class="i-primary font-bold"><spring:message code="lbl.companyadresse" /></td><td><c:out value="${company.address}" /></td></tr>
				<tr><td class="i-primary font-bold"><spring:message code="lbl.postal" /></td><td><c:out value="${company.postal}" /></td></tr>
				<tr><td class="i-primary font-bold"><spring:message code="lbl.wilaya" /></td><td><spring:message code="chose.wilaya${company.wilaya}"/></td></tr>
			</tbody>
		</table>
	</div>
	<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.admin.subscribe2" /></h2>
	<div class="table-container m-t-20">
		<div class="table-responsive">
			<table class="table table-form">
				<thead>
					<tr>
						<c:set var="cols" value="30,20,20,8,18" scope="page"></c:set>
						<c:set var="providers" value="pack,subscribe,expire,active,subscribes" scope="page"></c:set>
						<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
							<th style="width:${pageScope.col}%;">
								<spring:message code="tabs.${pageScope.providers.split(',')[state.count - 1]}"/>
							</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody class="font-small">
					<c:choose>
						<c:when test="${premiums.isEmpty()}"><tr class="empty-tr"><td colspan="5"><spring:message code="tool.empty.table" /></td></tr></c:when>
						<c:otherwise>
							<c:forEach var="premium" items="${premiums}" varStatus="state">
								<tr class="tr-info">
									<td><spring:message code="chose.subscribe.order${premium.pack + 1}" /></td>
									<td class="text-center"><c:out value="${premium.createDate}" /></td>
									<td class="text-center"><c:out value="${premium.expiryDate}" /></td>
									<td class="text-center"><i class="cmsms-icon-${premium.enabled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
									<td></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary"><spring:message code="subheader.admin.identity3"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<p class="font-small"><spring:message code="txt.help.admin.premium2.1"/></p>
				<hr class="my-4">
				<form:form name="featureForm" action="/" method="POST" modelAttribute="feature" enctype="utf8" novalidate="novalidate">
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<spring:bind path="pass">
						<div id="passForm" class="form-group m-t-20">
							<form:label class="col-form-label" path="pass">
								<spring:message code="tabs.pack" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
							<form:select class="form-select2-simple" path="pass" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="4" step="1">
									<option value="${i}"><spring:message code="chose.subscribe.order${i + 1}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="createDate">
						<div id="createDateForm" class="form-group">
							<form:label class="col-form-label" path="createDate">
								<spring:message code="tabs.subscribe" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="input-group-icon date">
								<form:input class="form-control" type="text" path="createDate" />
								<span class="input-group-addon" style="display:none;"></span>
							</div>
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="expiryDate">
						<div id="expiryDateForm" class="form-group">
							<form:label class="col-form-label" path="expiryDate">
								<spring:message code="tabs.expire" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="input-group-icon date">
								<form:input class="form-control" type="text" path="expiryDate" />
								<span class="input-group-addon" style="display:none;"></span>
							</div>
							<span class="error"></span>
						</div>
					</spring:bind>
					<hr class="my-4">
					<div class="form-group m-t-20 m-b-20">
						<div id="submitForm" class="form-submit">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
							</button>
						</div>
					</div>
					<span class="help-text"><spring:message code="txt.help.admin.premium2.2" /></span>
				</form:form>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>