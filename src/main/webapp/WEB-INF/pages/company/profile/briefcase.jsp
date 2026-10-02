<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.2" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard6.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile2"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
		<form:form name="briefcaseForm" action="/" method="POST" modelAttribute="brief" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="briefcase">
				<div id="briefcaseForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="briefcase">
						<spring:message code="lbl.sub.briefcase1" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<c:set var="faholder" scope="page"><spring:message code="chose.briefcase1" /></c:set>
						<form:select class="form-select2-simple" path="briefcase" data-placeholder="${pageScope.faholder}">
							<option></option>
							<c:forEach var="i" begin="1" end="10" step="1">
								<option value="${i}" ${brief.briefcase == i ? 'selected' : ''}><spring:message code="chose.briefcase1.${i}" /></option>
							</c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="warehouse">
				<div id="warehouseForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="warehouse">
						<spring:message code="lbl.sub.briefcase2" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<label class="ui-radio ui-radio-segond font-small m-r-10">
							<form:radiobutton value="${false}" path="warehouse" />
							<span class="input-span"></span><spring:message code="chose.briefcase2.1" />
						</label>
						<label class="ui-radio ui-radio-segond font-small">
							<form:radiobutton value="${true}" path="warehouse" />
							<span class="input-span"></span><spring:message code="chose.briefcase2.2" />
						</label>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="capital">
				<div id="capitalForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="capital">
						<spring:message code="lbl.sub.briefcase3" />
						<span class="help-text"><spring:message code="txt.help.capital" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<div class="input-group-phone input-group-right">
							<span class="input-icon"><spring:message code="tool.ind.capital" /></span>
							<form:input class="form-control" type="text" path="capital" />
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="type">
				<div id="typeForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="type">
						<spring:message code="lbl.sub.briefcase4.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<c:set var="faholder" scope="page"><spring:message code="chose.briefcase3" /></c:set>
						<form:select class="form-select2-simple" path="type" data-placeholder="${pageScope.faholder}">
							<option></option>
							<c:forEach var="i" begin="1" end="4" step="1">
								<option value="${i}" ${brief.type == i ? 'selected' : ''}><spring:message code="chose.briefcase3.${i}" /></option>
							</c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="nrc">
				<div id="nrcForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="nrc">
						<spring:message code="lbl.sub.briefcase5" />
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="nrc" data-inputmask="'mask': '99/99-9999999.a.99'" data-toggle="mask" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="nif">
				<div id="nifForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="nif">
						<spring:message code="lbl.sub.briefcase6" />
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="nif" data-inputmask="'mask': '999.999.999.999.999'" data-toggle="mask" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="nis">
				<div id="nisForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="nis">
						<spring:message code="lbl.sub.briefcase7" />
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="nis" data-inputmask="'mask': '999.999.999.999.999'" data-toggle="mask" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
			<spring:bind path="labels"><form:input type="hidden" path="labels" /></spring:bind>
			<spring:bind path="infos"><form:input type="hidden" path="infos" /></spring:bind>
			<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
			<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
			<spring:bind path="updateInfo"><form:input type="hidden" path="updateInfo" /></spring:bind>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.briefcase8" />
					<span class="help-text"><spring:message code="txt.help.briefcase" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<table id="tableItems" class="table table-page ${!brief.labels.isEmpty() ? 'm-b-20' : ''}">
						<thead>
							<tr>
								<th style="width:calc(40% - 34px);"></th><th style="width:calc(60% - 34px);"></th>
								<th style="width:34px;"></th><th style="width:34px;"></th>
							</tr>
						</thead>
						<tbody class="font-small">
							<c:forEach var="label" items="${brief.labels}" varStatus="state">
								<tr id="lineItem${state.count}" data-ident="${brief.idents.get(state.count - 1)}">
									<td class="i-input"><c:out value="${label}" /></td>
									<td><c:out value="${brief.infos.get(state.count - 1)}" /></td>
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
					<input type="hidden" id="countItems" value="${brief.labels.size()}" />
					<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left">
						<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.briefcase"/></span>
					</a>
					<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
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
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile2"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<div class="form-group">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<tr>
							<td><spring:message code="lbl.sub.briefcase1" /></td>
							<td id="briefcaseOverview">
								<c:choose>
									<c:when test="${!empty brief.briefcase}"><spring:message code="overview.briefcase1.${brief.briefcase}" /></c:when>
									<c:otherwise><c:out value="-"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase2" /></td>
							<td id="warehouseOverview"><spring:message code="chose.briefcase2.${!brief.warehouse ? '1' : '2'}" /></td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase3" /></td>
							<td id="capitalOverview">
								<c:choose>
									<c:when test="${!empty brief.capital}">
										<c:out value="${brief.getFormattedCapital()}"/> <spring:message code="tool.ind.capital" />
									</c:when>
									<c:otherwise><c:out value="-"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase4.2" /></td>
							<td id="typeOverview">
								<c:choose>
									<c:when test="${!empty brief.type}"><spring:message code="overview.briefcase3.${brief.type}" /></c:when>
									<c:otherwise><c:out value="-"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase5" /></td>
							<td id="nrcOverview"><c:out value="${!empty brief.nrc ? brief.nrc : '-'}"/></td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase6" /></td>
							<td id="nifOverview"><c:out value="${!empty brief.nif ? brief.nif : '-'}"/></td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.briefcase7" /></td>
							<td id="nisOverview"><c:out value="${!empty brief.nis ? brief.nis : '-'}"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.sub.briefcase8"/></h3>
			<div class="form-group m-t-10 m-b-20">
				<table id="tableOverview" class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<c:choose>
							<c:when test="${!brief.labels.isEmpty()}">
								<c:forEach var="label" items="${brief.labels}" varStatus="state">
									<tr id="lineOverview${state.count}">
										<td><c:out value="${label}" /></td>
										<td><c:out value="${brief.infos.get(state.count - 1)}" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise><tr class="empty-tr"><td colspan="2"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>