<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.6" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard6.6"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile6"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
		<form:form name="creditForm" action="/" method="POST" modelAttribute="credit" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.credit1" />
					<span class="help-text"><spring:message code="txt.help.credit1" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:set var="providers" value="cheque,versement,espece,carte,paypal" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<spring:bind path="${pageScope.provider}">
							<div class="m-b-10">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="${pageScope.provider}" data-name="${pageScope.provider}" />
									<span class="input-span"></span><spring:message code="chose.credit${state.count}" />
								</label>
							</div>
						</spring:bind>
					</c:forEach>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<spring:bind path="identsTaxe"><form:input type="hidden" path="identsTaxe" /></spring:bind>
			<spring:bind path="taxesname"><form:input type="hidden" path="taxesname" /></spring:bind>
			<spring:bind path="taxestaux"><form:input type="hidden" path="taxestaux" /></spring:bind>
			<spring:bind path="updatedTaxe"><form:input type="hidden" path="updatedTaxe" /></spring:bind>
			<spring:bind path="trashedTaxe"><form:input type="hidden" path="trashedTaxe" /></spring:bind>
			<spring:bind path="updateTaxe"><form:input type="hidden" path="updateTaxe" /></spring:bind>
			<div class="form-group row m-t-10">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.credit2" />
					<span class="help-text"><spring:message code="txt.help.credit2" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<table id="tableTaxes" class="table table-page ${!credit.taxesname.isEmpty() ? 'm-b-20' : ''}">
						<thead>
							<tr>
								<th style="width:26px;"></th><th style="width:calc(60% - 43px);"></th>
								<th style="width:calc(40% - 51px);"></th><th style="width:34px;"></th><th style="width:34px;"></th>
							</tr>
						</thead>
						<tbody class="font-small">
							<c:forEach var="taxename" items="${credit.taxesname}" varStatus="state">
								<tr id="lineTaxe${state.count}" data-ident="${credit.identsTaxe.get(state.count - 1)}">
									<td><i class="cmsms-icon-chart-pie-2 i-help"></i></td>
									<td class="i-input"><c:out value="${taxename}"/></td>
									<td><c:out value="${credit.taxestaux.get(state.count - 1)}%"/></td>
									<td class="btn-td">
										<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
											onclick="editLineTaxe('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
									</td>
									<td class="btn-td">
										<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
											onclick="deleteLineTaxe('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="hidden" id="countTaxes" value="${credit.taxesname.size()}" />
					<a id="insertTaxe" class="btn btn-success btn-simple btn-add btn-left">
						<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.taxe"/></span>
					</a>
					<div id="insertFormTaxe" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<spring:bind path="identsTruck"><form:input type="hidden" path="identsTruck" /></spring:bind>
			<spring:bind path="indstruck"><form:input type="hidden" path="indstruck" /></spring:bind>
			<spring:bind path="updatedTruck"><form:input type="hidden" path="updatedTruck" /></spring:bind>
			<spring:bind path="trashedTruck"><form:input type="hidden" path="trashedTruck" /></spring:bind>
			<spring:bind path="updateTruck"><form:input type="hidden" path="updateTruck" /></spring:bind>
			<div class="form-group row m-t-10">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.credit3" />
					<span class="help-text"><spring:message code="txt.help.credit3" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<table id="tableTrucks" class="table table-page ${!credit.indstruck.isEmpty() ? 'm-b-20' : ''}">
						<thead>
							<tr>
								<th style="width:26px;"></th><th style="width:calc(100% - 94px);"></th>
								<th style="width:34px;"></th><th style="width:34px;"></th>
							</tr>
						</thead>
						<tbody class="font-small">
							<c:forEach var="indtruck" items="${credit.indstruck}" varStatus="state">
								<tr id="lineTruck${state.count}" data-ident="${credit.identsTruck.get(state.count - 1)}">
									<td><i class="cmsms-icon-truck i-help"></i></td>
									<td class="i-input"><c:out value="${indtruck}" /></td>
									<td class="btn-td">
										<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
											onclick="editLineTruck('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
									</td>
									<td class="btn-td">
										<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
											onclick="deleteLineTruck('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="hidden" id="countTrucks" value="${credit.indstruck.size()}" />
					<a id="insertTruck" class="btn btn-success btn-simple btn-add btn-left">
						<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.truck"/></span>
					</a>
					<div id="insertFormTruck" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
				</div>
			</div>
			<div class="form-group row m-b-20">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4">
					<div id="submitForm" class="form-submit m-t-20">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile6"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header6 i-primary"><spring:message code="lbl.sub.credit1"/></h3>
			<div class="form-group m-t-10">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:100%;"></th></tr></thead>
					<tbody class="font-small single-tr">
						<tr id="creditOverview" style="${!credit.hasCredit() ? 'display:none;' : ''}">
							<td>
								<c:set var="providers" value="cheque,versement,espece,carte,paypal" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<span id="${pageScope.provider}Overview" class="tag-credit m-r-5 m-b-5" 
										style="${!credit.buildCredit(pageScope.provider) ? 'display:none;' : ''}">
										<spring:message code="overview.credit${state.count}" />
									</span>
								</c:forEach>
							</td>
						</tr>
						<tr id="emptyCreditOverview" class="empty-tr" style="${credit.hasCredit() ? 'display:none;' : ''}">
							<td><spring:message code="tool.empty.credit" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-10"><spring:message code="lbl.sub.credit2"/></h3>
			<div class="form-group m-t-10">
				<table id="tabelTaxeOverview" class="table table-page table-panel">
					<thead><tr><th style="width:26px;"></th><th style="width:calc(60% - 13px);"></th><th style="width:calc(40% - 13px);"></th></tr></thead>
					<tbody class="font-small">
						<c:choose>
							<c:when test="${!credit.taxesname.isEmpty()}">
								<c:forEach var="taxename" items="${credit.taxesname}" varStatus="state">
									<tr id="lineTaxeOverview${state.count}">
										<td><i class="cmsms-icon-chart-pie-2 i-help"></i></td>
										<td><c:out value="${taxename}"/></td>
										<td><c:out value="${credit.taxestaux.get(state.count - 1)}%"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise><tr class="empty-tr"><td colspan="3"><spring:message code="tool.empty.taxe" /></td></tr></c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-10"><spring:message code="lbl.sub.credit3"/></h3>
			<div class="form-group m-t-10 m-b-20">
				<table id="tabelTruckOverview" class="table table-page table-panel">
					<thead><tr><th style="width:26px;"></th><th style="width:calc(100% - 26px);"></th></tr></thead>
					<tbody class="font-small">
						<c:choose>
							<c:when test="${!credit.indstruck.isEmpty()}">
								<c:forEach var="indtruck" items="${credit.indstruck}" varStatus="state">
									<tr id="lineTruckOverview${state.count}">
										<td><i class="cmsms-icon-truck i-help"></i></td>
										<td><c:out value="${indtruck}"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise><tr class="empty-tr"><td colspan="2"><spring:message code="tool.empty.truck" /></td></tr></c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>