<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.4" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.company.detect"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.company.detect1"/>
			<c:if test="${currentCompany.premium > 1}">
				<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
			</c:if>
		</p>
		<span class="clearfix"></span>
	</div>
	<c:choose>
		<c:when test="${currentCompany.premium > 1}">
			<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_select.jsp"/></sec:authorize>
			<div class="row">
				<div class="col-md-6">
					<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></sec:authorize>
				</div>
				<div class="col-md-6">
					<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.user"/></c:set>
					<c:import url="/WEB-INF/fields/tables/table_find.jsp"/>
				</div>
			</div>
			<c:import url="/WEB-INF/fields/tables/table_result.jsp"/>
			<div class="table-container m-t-10">
				<div class="table-overlay"></div>
				<nav class="navbar">
					<ul class="nav nav-table">
						<c:import url="/WEB-INF/fields/tables/table_filter.jsp"/>
						<li class="form-filter">
							<c:set var="faholder" scope="page"><spring:message code="tool.filter.data" /></c:set>
							<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="6" step="1">
									<option value="${i}"><spring:message code="chose.date${i}"/></option>
								</c:forEach>
							</select>
						</li>
					</ul>
					<ul class="nav nav-table">
						<c:set var="choseSorters" value="date,user,type" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
						<li class="divider"></li>
						<c:set var="maxColumns" value="6" scope="request"></c:set>
						<c:set var="choseColumns" value="user,date,page,device,web,activities" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_config.jsp"/>
					</ul>
				</nav>
				<div class="table-content">
					<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
					<div class="table-responsive">
						<table id="tableList" class="table table-form">
							<thead>
								<tr>
									<th class="td-check sorter-false" style="width:4%;">
										<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
											<input type="checkbox" name="checkAllRowTable" />
											<span class="input-span"></span>
										</label>
									</th>
									<c:set var="cols" value="22,16,14,26,10,8" scope="page"></c:set>
									<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
										<th class="column${state.count} ${state.count >= 5 ? 'sorter-false' : ''}" 
											style="width:${pageScope.col}%;${currTable.column[state.count - 1] ? '' : 'display:none;'}">
											<spring:message code="tabs.${requestScope.choseColumns.split(',')[state.count - 1]}"/>
										</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<c:import url="/WEB-INF/fields/tables/table_pagination.jsp"/>
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning m-t-10">
				<i class="cmsms-icon-dollar i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.detect"/> 
					<a href="<c:url value="/company/tools/subscribes/new" />" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.detect1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<c:choose>
				<c:when test="${currentCompany.premium > 1}">
					<div id="panelLoad" class="panel-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</c:when>
				<c:otherwise>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.detect1.1"/></h3>
					<p class="font-small m-t-10"><spring:message code="txt.company.detect1.1"/></p>
					<ul class="font-small m-t-10">
						<c:forEach var="i" begin="1" end="3" step="1"><li><spring:message code="txt.company.detect1.1.${i}"/></li></c:forEach>
					</ul>
					<hr class="my-2">
					<a href="<c:url value="/company/tools/subscribes/new" />" class="btn btn-success btn-simple btn-add btn-left">
						<span><i class="cmsms-icon-dollar"></i><spring:message code="lien.help.upgard"/></span>
					</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>