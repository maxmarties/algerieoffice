<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.4" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard6.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile4"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
		<form:form name="locationForm" action="/" method="POST" modelAttribute="location" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="row m-t-20">
				<label class="col-form-label col-md-4 col-lg-3" for="address">
					<spring:message code="lbl.companyadresse" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.location1" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="address">
						<div id="addressForm" class="form-group m-b-0">
							<form:input class="form-control" type="text" path="address" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<div class="row m-t-10">
						<spring:bind path="postal">
							<div id="postalForm" class="form-group col-md-4 m-b-10">
								<c:set var="faholder" scope="page"><spring:message code="lbl.postal" /></c:set>
								<form:input class="form-control" type="text" path="postal" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</spring:bind>
						<spring:bind path="wilaya">
							<div id="wilayaForm" class="form-group col-md-8 m-b-10">
								<c:set var="faholder" scope="page"><spring:message code="lbl.wilaya" /></c:set>
								<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}" >
									<option></option>
									<c:forEach var="i" begin="1" end="48" step="1">
										<option value="${i}" ${location.wilaya == i ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</spring:bind>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
			<spring:bind path="addrs"><form:input type="hidden" path="addrs" /></spring:bind>
			<spring:bind path="postals"><form:input type="hidden" path="postals" /></spring:bind>
			<spring:bind path="wilayas"><form:input type="hidden" path="wilayas" /></spring:bind>
			<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
			<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
			<spring:bind path="updateAddr"><form:input type="hidden" path="updateAddr" /></spring:bind>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.location1" />
					<span class="help-text"><spring:message code="txt.help.location2" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<table id="tableItems" class="table table-page ${!location.addrs.isEmpty() ? 'm-b-20' : ''}">
						<thead>
							<tr>
								<th style="width:26px;"></th><th style="width:calc(100% - 94px);"></th>
								<th style="width:34px;"></th><th style="width:34px;"></th>
							</tr>
						</thead>
						<tbody class="font-small">
							<c:forEach var="addr" items="${location.addrs}" varStatus="state">
								<tr id="lineItem${state.count}" data-ident="${location.idents.get(state.count - 1)}">
									<td><i class="cmsms-icon-building-filled i-help"></i></td>
									<td class="i-input">
										<c:out value="${addr}, ${location.postals.get(state.count - 1)}"/> 
										<span class="text-uppercase"><spring:message code="chose.wilaya${location.wilayas.get(state.count - 1)}" /></span>
									</td>
									<td class="btn-td">
										<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
											onclick="editLineItem('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
									</td>
									<td class="btn-td">
										<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
											onclick="deleteLineItem('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="hidden" id="countItems" value="${location.addrs.size()}" />
					<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${location.addrs.size() >= 10 ? 'disabled' : ''}">
						<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.location"/></span>
					</a>
					<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
					<c:set var="chosesWilaya" scope="page">
						<c:forEach var="i" begin="1" end="48" step="1"><spring:message code="chose.wilaya${i}" /><c:out value="${i < 48 ? ',' : ''}"/></c:forEach>
					</c:set>
					<input type="hidden" id="chosesWilaya" value="${pageScope.chosesWilaya}" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.location2" />
					<span class="help-text"><spring:message code="txt.help.location3" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="hasCarte">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="hasCarte" />
							<span class="input-span"></span><spring:message code="comp.location" />
						</label>
					</spring:bind>
					<div id="urlmapForm" class="form-group m-t-10">
						<label class="ui-radio ui-radio-segond font-small ${!location.hasCarte ? 'disabled' : ''}">
							<form:radiobutton value="${false}" path="hasEmpded" />
							<span class="input-span"></span><spring:message code="comp.location1" />
						</label>
						<c:set var="faholder" scope="page"><spring:message code="tool.ind.urlmap1" /></c:set>
						<form:input class="form-control" type="url" path="urlmap" placeholder="${pageScope.faholder}" disabled="${!location.hasCarte || location.hasEmpded}" />
						<span class="error"></span>
					</div>
					<div id="empdedForm" class="form-group m-t-10">
						<label class="ui-radio ui-radio-segond font-small ${!location.hasCarte ? 'disabled' : ''}">
							<form:radiobutton value="${true}" path="hasEmpded" />
							<span class="input-span"></span><spring:message code="comp.location2" />
						</label>
						<c:set var="faholder" scope="page"><spring:message code="tool.ind.urlmap2" /></c:set>
						<form:textarea class="form-control form-area" rows="3" path="empded" placeholder="${pageScope.faholder}" disabled="${!location.hasCarte || !location.hasEmpded}" />
						<span class="error"></span>
					</div>
					<span class="help-text"><spring:message code="txt.help.location4" /></span>
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
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile4"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header6 i-primary"><spring:message code="lbl.sub.location3.1"/></h3>
			<div class="form-group m-t-10">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:26px;"></th><th style="width:calc(100% - 26px);"></th></tr></thead>
					<tbody class="font-small">
						<tr>
							<td><i class="cmsms-icon-location-1 i-red"></i></td>
							<td id="addressOeriew">
								<c:out value="${location.address}, ${location.postal}"/> 
								<span class="text-uppercase"><spring:message code="chose.wilaya${location.wilaya}" /></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.sub.location3.2"/></h3>
			<div class="form-group m-t-10">
				<table id="tableOverview" class="table table-page table-panel">
					<thead><tr><th style="width:26px;"></th><th style="width:calc(100% - 26px);"></th></tr></thead>
					<tbody class="font-small">
						<c:choose>
							<c:when test="${!location.addrs.isEmpty()}">
								<c:forEach var="addr" items="${location.addrs}" varStatus="state">
									<tr id="lineOverview${state.count}">
										<td><i class="cmsms-icon-building-filled i-help"></i></td>
										<td>
											<c:out value="${addr}, ${location.postals.get(state.count - 1)}"/> 
											<span class="text-uppercase"><spring:message code="chose.wilaya${location.wilayas.get(state.count - 1)}" /></span>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise><tr class="empty-tr"><td colspan="2"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.sub.location2"/></h3>
			<div class="form-group m-t-10 m-b-20">
				<div id="carteOverview" class="map-outer" style="${!location.hasCarte ? 'display:none;' : ''}">
					<c:if test="${location.hasCarte}">
						<c:choose>
							<c:when test="${location.hasEmpded}"><c:out value="${location.empded}" escapeXml="false" /></c:when>
							<c:otherwise><iframe width="100%" height="200" src="${location.urlmap}"></iframe></c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<div id="emptyCarteOverview" class="form-group form-tr" style="${location.hasCarte ? 'display:none;' : ''}">
					<spring:message code="lbl.sub.location3.3"/>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>