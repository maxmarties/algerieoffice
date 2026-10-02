<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/settings/general"/>" class="lien lien-black">
			<i class="cmsms-icon-cog-5 m-r-5"></i><spring:message code="sidebar.user.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard7.5" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard10.3"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.user.setting1.5"/>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
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
				<li class="form-filter"></li>
			</ul>
			<ul class="nav nav-table">
				<c:set var="choseSorters" value="date,user" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
				<li class="divider"></li>
				<c:set var="maxColumns" value="4" scope="request"></c:set>
				<c:set var="choseColumns" value="user,motif,lockedate,lockeds" scope="request"></c:set>
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
							<c:set var="cols" value="24,40,18,12" scope="page"></c:set>
							<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
								<th class="column${state.count} ${state.count == 4 ? 'sorter-false' : ''}" 
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
</div>