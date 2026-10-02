<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/prospect/quotes"/>" class="lien lien-black">
			<i class="cmsms-icon-quote-right m-r-5"></i><spring:message code="sidebar.company.dashboard9"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard9.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard9.4"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.company.prospect4"/>
			<c:if test="${currentCompany.hasPremium()}">
				<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
			</c:if>
		</p>
		<span class="clearfix"></span>
	</div>
	<c:choose>
		<c:when test="${currentCompany.hasPremium()}">
			<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_select.jsp"/></sec:authorize>
			<div class="row">
				<div class="col-md-6">
					<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></sec:authorize>
				</div>
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
						<c:set var="choseSorters" value="date,title,user,state" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
						<li class="divider"></li>
						<c:set var="maxColumns" value="8" scope="request"></c:set>
						<c:set var="choseColumns" value="job,username,email,requested,filereader,consulted,consultedBy,prospects" scope="request"></c:set>
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
									<c:set var="cols" value="20,12,16,12,8,6,12,8" scope="page"></c:set>
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
			<div class="alert alert-warning">
				<i class="cmsms-icon-dollar i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.communication"/> 
					<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>