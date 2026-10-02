<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black">
			<i class="cmsms-icon-wrench m-r-5"></i><spring:message code="sidebar.company.dashboard10"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard10.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard10.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.tools2"/></p>
	</div>
	<div class="wizard wizard-user">
		<div class="wizard-tabbed">
			<ul class="nav nav-tabs nav-tabs4" role="tablist">
				<c:set var="liens" value="posts,promotes,ads" scope="page"></c:set>
				<c:set var="providers" value="bag,megaphone-1,pin-1" scope="page"></c:set>
				<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
					<li>
						<a href="<c:url value="/company/tools/recycle/${pageScope.lien}"/>" class="lien" 
							title="<spring:message code="wizard.tools.recycle${state.count}"/>">
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} i-34 transition-35"></i>
							<span class="help-tab"><spring:message code="wizard.tools.recycle${state.count}"/></span>
						</a>
					</li>	
				</c:forEach>
				<li>
					<a class="lien active" title="<spring:message code="wizard.tools.recycle4"/>">
						<i class="cmsms-icon-graduation-cap i-34 transition-35"></i>
						<span class="help-tab"><spring:message code="wizard.tools.recycle4"/></span>
					</a>
				</li>
			</ul>
			<div class="wizard-content">
				<div class="wizard-body">
					<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.tools.recycle4"/></h2>
					<p class="font-small m-t-5">
						<spring:message code="txt.company.tools2.4"/>
						<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
						<span class="clearfix"></span>
					</p>
					<hr class="my-4">
					<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
						<c:import url="/WEB-INF/fields/tables/table_select.jsp"/>
						<div class="row">
							<div class="col-md-6"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></div>
							<div class="col-md-6">
								<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.employe"/></c:set>
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
										<c:set var="faholder" scope="page"><spring:message code="tool.filter.contract" /></c:set>
										<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
											<option></option>
											<c:forEach var="i" begin="1" end="5" step="1">
												<option value="${i}"><spring:message code="chose.contract${i}" /></option>
											</c:forEach>
										</select>
									</li>
								</ul>
								<ul class="nav nav-table">
									<c:set var="choseSorters" value="date,title" scope="request"></c:set>
									<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
									<li class="divider"></li>
									<c:set var="maxColumns" value="6" scope="request"></c:set>
									<c:set var="choseColumns" value="title,type,expiredate,date,autor,employes" scope="request"></c:set>
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
												<c:set var="cols" value="32,10,14,14,16,10" scope="page"></c:set>
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
					</sec:authorize>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="no-print"><hr class="my-4"><p class="font-mini m-b-20"><spring:message code="txt.company.tools2.5"/></p></div>
</div>