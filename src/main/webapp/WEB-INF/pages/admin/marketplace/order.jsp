<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/admin/marketplace/orders"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4.2"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.annonce.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.order"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/marketplace/orders" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
		<form:form name="orderForm" action="/" method="POST" modelAttribute="order" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="serial">
				<div id="serialForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="serial">
						<spring:message code="tabs.serial" />
						<span class="help-text"><spring:message code="txt.help.admin.promote2.1" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="serial" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="amount">
				<div id="amountForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="amount">
						<spring:message code="tabs.amount" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<form:input class="form-control" type="number" path="amount" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="orderDate">
				<div id="orderDateForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="orderDate">
						<spring:message code="tabs.date" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<div class="input-group-icon date">
							<form:input class="form-control" type="text" path="orderDate" />
							<span class="input-group-addon" style="display:none;"></span>
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="form-group row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="response">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="response" />
							<span class="input-span"></span><spring:message code="comp.admin.order" />
						</label>
					</spring:bind>
					<hr class="my-2">
					<p class="font-mini"><spring:message code="txt.help.admin.promote2.2"/></p>
				</div>
			</div>
			<div class="form-group row m-b-30">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4">
					<div id="submitForm" class="form-submit m-t-20">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
		<hr class="my-4">
		<p class="font-mini m-b-20"><spring:message code="txt.help.admin.promote2"/></p>
	</sec:authorize>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary"><spring:message code="wizard.marketplace.promote3"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.post1"/></h3>
			<div class="form-group m-t-20">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<tr><td><spring:message code="tabs.pack"/></td><td><c:out value="${promote.pack}" /></td></tr>
						<tr><td><spring:message code="tabs.amount"/></td><td><c:out value="${promote.amount}" /></td></tr>
						<tr>
							<td><spring:message code="tabs.filereader"/></td>
							<td>
								<a href="<c:url value="${promote.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
									<spring:message code="tool.view.file" />
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<hr class="my-4">
			<h3 class="h-header h-header5 i-primary m-t-10"><spring:message code="subheader.marketplace3.2"/></h3>
			<div class="banner-overview banner-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
				<div class="widget-promote banner-box m-auto">
					<c:if test="${!empty promote.urlAvatar}">
						<img class="img-responsive m-b-10" src="<c:url value="${promote.urlAvatar}"/>" alt="<c:out value="${promote.title}" />">
					</c:if>
					<h4 class="h-header h-headerAds i-primary"><c:out value="${promote.title}" /></h4>
					<p class="font-small i-help m-t-5"><c:out value="${promote.description}" /></p>
				</div>
			</div>
			<hr class="my-4">
			<a href="<c:url value="/admin/marketplace/promotes/edit?id=${promote.promoteId}" />" class="btn btn-warning btn-sm btn-simple btn-fixed">
				<span><i class="cmsms-icon-paper-plane-3 m-r-5"></i><spring:message code="btn.promote.edit"/></span>
			</a>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>