<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/team/users"/>" class="lien lien-black">
			<i class="cmsms-icon-user-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard10"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard10.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard10.3"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.admin.team3"/>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
	<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
		<div class="row m-t-10">
			<div class="col-6">
				<a href="<c:url value="/admin/team/managers/new"/>" class="btn btn-success btn-simple btn-add btn-left">
					<span><i class="cmsms-icon-plus"></i><spring:message code="header.team.manager.new"/></span>
				</a>
			</div>
			<div class="col-6 text-right">
				<a class="btn btn-warning btn-refresh" title="<spring:message code="btn.refresh"/>" 
					data-toggle="multiple-action" data-attribut="action-refresh"><span><i class="cmsms-icon-arrows-cw"></i></span></a>
			</div>
		</div>
		<hr class="my-1">
		<c:import url="/WEB-INF/fields/tables/table_select.jsp"/>
		<div class="row">
			<div class="col-md-6"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></div>
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
						<c:set var="providers" value="autor,manager,admin" scope="page"></c:set>
						<c:set var="faholder" scope="page"><spring:message code="tool.filter.user" /></c:set>
						<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
							<option></option>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<option value="${state.count}"><spring:message code="chose.role.${pageScope.provider}" /></option>
							</c:forEach>
						</select>
					</li>
				</ul>
				<ul class="nav nav-table">
					<c:set var="choseSorters" value="user,createdate" scope="request"></c:set>
					<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
					<li class="divider"></li>
					<c:set var="maxColumns" value="9" scope="request"></c:set>
					<c:set var="choseColumns" value="photo,username,role,email,buildate,login,visit,locked,users" scope="request"></c:set>
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
								<c:set var="cols" value="3,14,12,16,12,12,10,5,12" scope="page"></c:set>
								<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
									<th class="${state.count == 1 || state.count >= 7 ? 'sorter-false' : ''}" style="width:${pageScope.col}%;">
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
	</sec:authorize>
</div>