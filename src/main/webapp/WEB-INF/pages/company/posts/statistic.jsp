<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/posts/all"/>" class="lien lien-black">
			<i class="cmsms-icon-bag m-r-5"></i><spring:message code="sidebar.company.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard1.4" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard1.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.posts4"/></p>
	</div>
	<c:choose>
		<c:when test="${currentCompany.hasPremium()}">
			<div class="row row-mini no-print">
				<div class="col-md-6 col-mini">
					<div id="cardStatisticPost" class="card-analytic card-charts m-b-10">
						<div class="card-header">
							<label class="col-form-label">
								<spring:message code="subheader.post6.2"/>
								<span class="help-text"><spring:message code="txt.help.statistic1.1" /></span>
							</label>
						</div>
						<div class="card-content">
							<c:import url="/WEB-INF/basics/loading_span.jsp"/>
							<div class="card-load"></div>
						</div>
					</div>
					<div class="card-analytic card-charts">
						<div class="card-header"><label class="col-form-label"><spring:message code="subheader.post6.4"/></label></div>
						<div class="row">
							<c:forEach var="i" begin="1" end="2">
								<div class="col-sm-6 m-t-20">
									<c:set var="insertPersent" value="${refering.getPesrsentInsert(i)}" scope="page"></c:set>
									<div class="row">
										<div class="col-6"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.insertPersent}%"/></p></div>
										<div class="col-6 text-right">
											<span class="help-text"><spring:message code="tool.statistic.post${i}" /></span>
											<c:set var="publishedPersent" value="${refering.getPesrsentPublished(i)}" scope="page"></c:set>
											<i class="cmsms-icon-${pageScope.publishedPersent >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-5"></i><c:out value="${pageScope.publishedPersent}%"/>
										</div>
									</div>
									<div class="progress m-t-10">
										<div class="progress-bar progress-access${i + 2} animated onne progress-animated" style="width:${pageScope.insertPersent}%" role="progressbar"></div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-mini">
					<div class="row row-mini">
						<c:set var="providers" value="basket,box,bag" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<div class="col-${state.count == 3 ? '12' : '6'} col-sm-4 col-mini m-b-10">
								<div class="card-analytic card-refering ${state.count == 3 ? 'card-primary' : ''} h-100">
									<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
									<div class="card-brand text-right">
										<span class="text-truncate font-small ${state.count == 3 ? 'i-gray' : 'i-help'}"><spring:message code="tool.statistic.post${state.count}" /></span>
										<p class="h-header ${state.count == 3 ? 'i-white' : 'i-primary'}"><c:out value="${refering.getFormattedTotal(state.count)}"/></p>
									</div>
									<span class="clearfix"></span>
									<div class="card-footer m-t-5">
										<p class="font-mini">
											<i class="cmsms-icon-upload i-blue m-r-10"></i><spring:message code="chose.published1" />
											<span class="font-small pull-right"><c:out value="${refering.getFormattedPublished(state.count)}"/></span>
										</p>
										<p class="font-mini m-t-5">
											<i class="cmsms-icon-trash-7 i-red m-r-10"></i><spring:message code="tabs.trashed" />
											<span class="font-small pull-right"><c:out value="${refering.getFormattedTrashed(state.count)}"/></span>
										</p>
										<p class="font-mini m-t-5">
											<i class="cmsms-icon-ok-5 i-green m-r-10"></i><spring:message code="tabs.published" />
											<span class="font-small pull-right"><c:out value="${refering.getFormattedCloud(state.count)}"/></span>
										</p>
										<span class="clearfix"></span>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<div id="cardStatisticActivity" class="card-analytic card-charts">
						<div class="card-header">
							<label class="col-form-label">
								<spring:message code="subheader.post6.3"/>
								<span class="help-text"><spring:message code="txt.help.statistic2.1" /></span>
							</label>
						</div>
						<div class="card-content">
							<c:import url="/WEB-INF/basics/loading_span.jsp"/>
							<div class="card-load"></div>
						</div>
					</div>
				</div>
			</div>
			<hr class="no-print my-1">
			<div class="row no-print">
				<div class="col-md-6">
					<label class="col-form-label">
						<spring:message code="subheader.post6.5"/>
						<span class="help-text"><spring:message code="txt.help.statistic2.2" /></span>
					</label>
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
							<c:set var="faholder" scope="page"><spring:message code="tool.filter.type" /></c:set>
							<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="2" step="1">
									<option value="${i == 2}"><spring:message code="chose.post.type${i}"/></option>
								</c:forEach>
							</select>
						</li>
					</ul>
					<ul class="nav nav-table">
						<c:set var="choseSorters" value="visit,modifiedate,title" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
						<li class="divider"></li>
						<c:set var="maxColumns" value="8" scope="request"></c:set>
						<c:set var="choseColumns" value="title,date,token,filter,keys,simultude,access,statistic" scope="request"></c:set>
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
									<c:set var="cols" value="22,14,10,10,10,10,10,10" scope="page"></c:set>
									<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
										<th class="column${state.count} ${state.count == 8 ? 'sorter-false' : ''}" 
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
					<spring:message code="message.premium.statistic"/> 
					<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.post6.1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<c:choose>
				<c:when test="${currentCompany.hasPremium()}">
					<div id="panelLoad" class="panel-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</c:when>
				<c:otherwise>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.post6.7"/></h3>
					<p class="font-small m-t-10"><spring:message code="txt.company.journal1.1"/></p>
					<ul class="font-small m-t-10">
						<c:forEach var="i" begin="1" end="3" step="1">
							<li><spring:message code="txt.company.posts4.1.${i}"/></li>
						</c:forEach>
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