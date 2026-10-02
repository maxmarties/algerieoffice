<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/posts/all"/>" class="lien lien-black">
			<i class="cmsms-icon-bag m-r-5"></i><spring:message code="sidebar.company.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard1.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard1.1"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.company.posts2"/>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
	<div class="row no-print m-t-10">
		<div class="col-6">
			<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
				<a href="<c:url value="/company/posts/new"/>" class="btn btn-success btn-simple btn-add btn-left">
					<span><i class="cmsms-icon-plus"></i><spring:message code="header.posts.post.new"/></span>
				</a>
			</sec:authorize>
		</div>
		<div class="col-6 text-right">
			<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
				<c:if test="${countTrashed > 0}">
					<a href="<c:url value="/company/tools/recycle/posts"/>" class="lien lien-red lien-hover lien-small m-r-10">
						(<c:out value="${countTrashed}"/>) <spring:message code="chose.state.promote8"/></a>
				</c:if>
			</sec:authorize>
			<a class="btn btn-warning btn-refresh" title="<spring:message code="btn.refresh"/>" 
				data-toggle="multiple-action" data-attribut="action-refresh"><span><i class="cmsms-icon-arrows-cw"></i></span></a>
		</div>
	</div>
	<hr class="no-print my-1">
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_select.jsp"/></sec:authorize>
	<div class="row">
		<div class="col-md-6">
			<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
				<c:set var="actionTrash" value="1" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/tables/table_action.jsp"/>
			</sec:authorize>
		</div>
		<div class="col-md-6">
			<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.post"/></c:set>
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
					<c:set var="faholder" scope="page"><spring:message code="tool.filter.post" /></c:set>
					<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
						<option></option>
						<option value="empty"><c:out value="-" /></option>
						<c:forEach var="choseCategorie" items="${choseCategories}" >
							<option value="${choseCategorie.uuid()}"><c:out value="${choseCategorie.name}" /></option>
						</c:forEach>
					</select>
				</li>
			</ul>
			<ul class="nav nav-table">
				<c:set var="choseSorters" value="title,autor,modifiedate" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
				<li class="divider"></li>
				<c:set var="maxColumns" value="8" scope="request"></c:set>
				<c:set var="choseColumns" value="title,type,category,keys,date,autor,published,posts" scope="request"></c:set>
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
							<c:set var="cols" value="22,8,14,16,8,12,6,10" scope="page"></c:set>
							<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
								<th class="column${state.count} ${state.count >= 7 ? 'sorter-false' : ''}" 
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