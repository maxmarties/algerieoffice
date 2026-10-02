<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/data/activities"/>" class="lien lien-black">
			<i class="cmsms-icon-folder m-r-5"></i><spring:message code="sidebar.admin.dashboard6"/></a></li>
			<li><a href="<c:url value="/admin/data/identities"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard6.2"/></a></li>
		<li class="active"><spring:message code="header.company.identity" /></li>
	</ol>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.company.identity"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.identity"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/data/identities" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.admin.identity1" /></h2>
	<div class="form-group m-t-20">
		<table class="table table-page">
			<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
			<tbody class="font-small">
				<c:set var="providers" value="tabs.subscribe,tabs.requested,lbl.denomination,tabs.tradename,tabs.buildate,lbl.identify" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<tr>
						<td class="i-primary font-bold"><spring:message code="${pageScope.provider}" /></td>
						<td><c:out value="${detail.getInfoLine(state.count)}"/></td>
					</tr>
				</c:forEach>
				<tr>
					<td class="i-primary font-bold"><spring:message code="lbl.sub.briefcase9" /></td>
					<td>
						<ul class="list-none">
							<c:forEach var="code" items="${detail.activities}">
								<li><span class="font-bold"><c:out value="${code}"/></span> - <spring:message code="chose.activity.${code}" /></li>
							</c:forEach>
						</ul>
					</td>
				</tr>
				<tr>
					<td class="i-primary font-bold"><spring:message code="tabs.filereader" /></td>
					<td>
						<a href="<c:url value="${detail.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
							<spring:message code="tool.view.file" />
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.admin.identity2" /></h2>
	<div class="table-container m-t-20">
		<div class="table-responsive">
			<table class="table table-form">
				<thead>
					<tr>
						<c:set var="cols" value="26,24,8,42" scope="page"></c:set>
						<c:set var="providers" value="autor,date,active,message" scope="page"></c:set>
						<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
								<th style="width:${pageScope.col}%;">
									<spring:message code="tabs.${pageScope.providers.split(',')[state.count - 1]}"/>
								</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody class="font-small">
					<c:choose>
						<c:when test="${detail.historiesDate.isEmpty()}"><tr class="empty-tr"><td colspan="4"><spring:message code="tool.empty.table" /></td></tr></c:when>
						<c:otherwise>
							<c:forEach var="history" items="${detail.historiesDate}" varStatus="state">
								<tr class="tr-info">
									<td><c:out value="${detail.validatesBy.get(state.count - 1)}" /></td>
									<td class="text-center"><c:out value="${history}" /></td>
									<td class="text-center"><i class="cmsms-icon-${detail.responses.get(state.count - 1) ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
									<td><c:out value="${detail.messages.get(state.count - 1)}" /></td>
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
				<p class="font-small"><spring:message code="txt.help.admin.identity2"/></p>
				<hr class="my-4">
				<form:form name="identityForm" action="/" method="POST" modelAttribute="identity" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="message">
						<div id="messageForm" class="form-group m-t-20">
							<form:label class="col-form-label" path="message">
								<spring:message code="tabs.message" />
								<span class="help-text"><spring:message code="txt.help.admin.identity1" /></span>
							</form:label>
							<form:textarea class="form-control form-area" rows="3" path="message" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="response">
						<div class="form-group m-t-10">
				        	<label class="ui-checkbox ui-checkbox-segond font-small">
				            	<form:checkbox path="response" />
								<span class="input-span"></span><spring:message code="comp.admin.identity" />
							</label>
	            		</div>
					</spring:bind>
					<hr class="my-4">
					<div class="form-group m-t-20">
						<div id="submitForm" class="form-submit">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
							</button>
						</div>
					</div>
				</form:form>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>