<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/premium/subscribes"/>" class="lien lien-black">
			<i class="cmsms-icon-bookmark m-r-5"></i><spring:message code="sidebar.admin.dashboard8"/></a></li>
		<li><a href="<c:url value="/admin/premium/emailings"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard8.4"/></a></li>
		<li class="active"><spring:message code="header.tools.subscribe" /></li>
	</ol>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.tools.subscribe"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.premium4.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/premium/emailings" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.admin.subscribe1" /></h2>
	<div class="form-group m-t-20">
		<table class="table table-page">
			<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
			<tbody class="font-small">
				<tr>
					<td class="i-primary font-bold"><spring:message code="tabs.company" /></td>
					<td class="td-brand">
						<img src="<c:url value="${order.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${order.tradename}"/>">
						<div class="brand-colspan">
							<c:choose>
								<c:when test="${!empty order.companyURL}">
									<a href="<c:url value="${order.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
										<c:out value="${order.tradename}" /></a>
								</c:when>
								<c:otherwise><c:out value="${order.tradename}" /></c:otherwise>
							</c:choose>
							<span class="help-text"><spring:message code="chose.activity.${order.activity}"/></span>
						</div>
						<span class="clearfix"></span>
					</td>
				</tr>
				<tr><td class="i-primary font-bold"><spring:message code="tabs.pack" /></td><td><spring:message code="chose.subscribe.order${order.pack + 1}" /></td></tr>
				<tr><td class="i-primary font-bold"><spring:message code="tabs.date" /></td><td><c:out value="${order.orderDate}" /></td></tr>
				<tr>
					<td class="i-primary font-bold"><spring:message code="tabs.filereader" /></td>
					<td>
						<a href="<c:url value="${order.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
							<spring:message code="tool.view.file" />
						</a>
					</td>
				</tr>
				<tr><td class="i-primary font-bold"><spring:message code="tool.newsletter.budget1.1" /></td><td><c:out value="${order.parseEmails()}" /></td></tr>
				<tr><td class="i-primary font-bold"><spring:message code="tool.newsletter.budget1.2" /></td><td><c:out value="${order.parseSendings()}" /></td></tr>
				<tr><td class="i-primary font-bold"><spring:message code="tabs.amount" /></td><td><c:out value="${order.parseAmount()}" /></td></tr>
			</tbody>
		</table>
	</div>
	<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.admin.subscribe2" /></h2>
	<div class="table-container m-t-20">
		<div class="table-responsive">
			<table class="table table-form">
				<thead>
					<tr>
						<c:set var="cols" value="30,24,24,18" scope="page"></c:set>
						<c:set var="providers" value="company,email,potential,subscribes" scope="page"></c:set>
						<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
							<th style="width:${pageScope.col}%;">
								<spring:message code="tabs.${pageScope.providers.split(',')[state.count - 1]}"/>
							</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody class="font-small">
					<tr class="tr-info">
						<td><c:out value="${order.tradename}" /></td>
						<td class="text-center"><c:out value="${currBudget.getFormattedBudget(1)}" /></td>
						<td class="text-center"><c:out value="${currBudget.getFormattedBudget(2)}" /></td>
						<td></td>
					</tr>
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
				<form:form name="subscribeForm" action="/" method="POST" modelAttribute="subscribe" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<spring:bind path="serial">
						<div id="serialForm" class="form-group m-t-20">
							<form:label class="col-form-label" path="serial">
								<spring:message code="tabs.serial" />
								<span class="help-text"><spring:message code="txt.help.admin.promote2.1" /></span>
							</form:label>
							<form:input class="form-control" type="text" path="serial" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="amount">
						<div id="amountForm" class="form-group">
							<form:label class="col-form-label" path="amount">
								<spring:message code="tabs.amount" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<form:input class="form-control" type="number" path="amount" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="orderDate">
						<div id="orderDateForm" class="form-group">
							<form:label class="col-form-label" path="orderDate">
								<spring:message code="tabs.date" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="input-group-icon date">
								<form:input class="form-control" type="text" path="orderDate" />
								<span class="input-group-addon" style="display:none;"></span>
							</div>
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="response">
						<div class="form-group m-t-10">
				        	<label class="ui-checkbox ui-checkbox-segond font-small">
				            	<form:checkbox path="response" />
								<span class="input-span"></span><spring:message code="comp.admin.order" />
							</label>
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
					<hr class="my-4">
					<p class="font-mini m-b-20"><spring:message code="txt.help.admin.promote2"/></p>
				</form:form>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>