<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/data/activities"/>" class="lien lien-black">
			<i class="cmsms-icon-folder m-r-5"></i><spring:message code="sidebar.admin.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard6.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard6.2"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.admin.identities"/>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
	<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_select.jsp"/></sec:authorize>
	<div class="row">
		<div class="col-md-6">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></sec:authorize>
		</div>
		<div class="col-md-6">
			<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.company"/></c:set>
			<c:import url="/WEB-INF/fields/tables/table_find.jsp"/>
		</div>
	</div>
	<c:import url="/WEB-INF/fields/tables/table_result.jsp"/>
	<div class="table-container m-t-10">
		<div class="table-overlay"></div>
		<nav class="navbar">
			<ul class="nav nav-table">
				<c:import url="/WEB-INF/fields/tables/table_filter.jsp"/>
				<li class="form-filter"></li>
			</ul>
			<ul class="nav nav-table">
				<c:set var="choseSorters" value="buildate,subscribe,company" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
				<li class="divider"></li>
				<c:set var="maxColumns" value="7" scope="request"></c:set>
				<c:set var="choseColumns" value="logo,company,requested,subscribe,filereader,consulted,identities" scope="request"></c:set>
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
							<c:set var="cols" value="4,24,14,14,12,8,14" scope="page"></c:set>
							<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
								<th class="${state.count == 1 || state.count >= 5 ? 'sorter-false' : ''}" style="width:${pageScope.col}%;">
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
</div>