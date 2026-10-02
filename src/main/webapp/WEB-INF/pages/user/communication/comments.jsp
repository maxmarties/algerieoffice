<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/communication/notices"/>" class="lien lien-black">
			<i class="cmsms-icon-chat m-r-5"></i><spring:message code="sidebar.user.dashboard3"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard3.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard3.4"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.user.communication4"/>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
	<c:import url="/WEB-INF/fields/tables/table_select.jsp"/>
	<div class="row">
		<div class="col-md-6"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></div>
		<div class="col-md-6">
			<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.actu"/></c:set>
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
				<c:set var="choseSorters" value="date,title" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
				<li class="divider"></li>
				<c:set var="maxColumns" value="6" scope="request"></c:set>
				<c:set var="choseColumns" value="company,title,message,requested,like,comments" scope="request"></c:set>
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
							<c:set var="cols" value="18,22,30,12,8,6" scope="page"></c:set>
							<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
								<th class="column${state.count} ${state.count == 6 ? 'sorter-false' : ''}" 
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
	<div class="no-print"><hr class="my-4"><p class="font-mini m-b-20"><spring:message code="txt.user.communication4.1"/></p></div>
</div>