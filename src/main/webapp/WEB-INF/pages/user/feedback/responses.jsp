<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/feedback/messages"/>" class="lien lien-black">
			<i class="cmsms-icon-megaphone-1 m-r-5"></i><spring:message code="sidebar.user.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard5.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard5.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.feedback4"/></p>
	</div>
	<div class="wizard wizard-user">
		<div class="wizard-tabbed">
			<ul class="nav nav-tabs nav-tabs2" role="tablist">
				<li>
					<a href="<c:url value="/admin-user/feedback/supports"/>" class="lien" title="<spring:message code="wizard.feddback.user1"/>">
						<i class="cmsms-icon-reply i-34 transition-35"></i>
						<span class="help-tab"><spring:message code="wizard.feddback.user1"/></span>
					</a>
				</li>
				<li>
					<a class="lien active" title="<spring:message code="wizard.feddback.user2"/>">
						<i class="cmsms-icon-share-2 i-34 transition-35"></i>
						<span class="help-tab"><spring:message code="wizard.feddback.user2"/></span>
					</a>
				</li>
			</ul>
			<div class="wizard-content">
				<div class="wizard-body">
					<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.feddback.user2"/></h2>
					<p class="font-small m-t-10">
						<spring:message code="txt.user.feedback4.2"/>
						<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
						<span class="clearfix"></span>
					</p>
					<hr class="my-4">
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_select.jsp"/></sec:authorize>
					<div class="row">
						<div class="col-md-6">
							<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')"><c:import url="/WEB-INF/fields/tables/table_action.jsp"/></sec:authorize>
						</div>
						<div class="col-md-6">
							<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.message"/></c:set>
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
									<c:set var="faholder" scope="page"><spring:message code="tool.filter.member" /></c:set>
									<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
										<option></option>
										<c:forEach var="choseUser" items="${choseUsers}" >
											<option value="${choseUser.userId}"><c:out value="${choseUser.email}" /></option>
										</c:forEach>
									</select>
								</li>
							</ul>
							<ul class="nav nav-table">
								<c:set var="choseSorters" value="date,user" scope="request"></c:set>
								<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
								<li class="divider"></li>
								<c:set var="maxColumns" value="7" scope="request"></c:set>
								<c:set var="choseColumns" value="illustr,photo,username,message,date,autor,messages" scope="request"></c:set>
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
											<c:set var="cols" value="2,4,14,40,12,14,10" scope="page"></c:set>
											<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
												<th class="column${state.count} ${state.count <= 2 || state.count == 7 ? 'sorter-false' : ''}" style="width:${pageScope.col}%;">
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
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>