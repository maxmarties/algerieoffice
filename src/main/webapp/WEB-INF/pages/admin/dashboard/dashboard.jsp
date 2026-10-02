<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.1" /></li>
	</ol>
</div>
<div class="page-wrapper page-dashboard">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard1"/></h1>
		<p class="m-t-5"><spring:message code="tool.dashboard.welcome1"/>, <c:out value="${currentUser.user.firstName}" /> !</p>
	</div>
	<div class="row row-mini">
		<c:set var="providers" value="user-male,lock-5,isight,adult,building-filled,globe" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-6 col-md-4 col-lg-2 col-mini m-t-10">
				<div class="card-analytic card-refering ${state.count == 1 ? 'card-segond' : ''} h-100">
					<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
					<div class="card-brand text-right">
						<span class="text-truncate font-small ${state.count == 1 ? 'i-gray' : 'i-help'}"><spring:message code="tool.dashboard.admin1.${state.count}" /></span>
						<p class="h-header ${state.count == 1 ? 'i-white' : 'i-primary'}"><c:out value="${dashboardData[state.count - 1][0]}"/></p>
					</div>
					<span class="clearfix"></span>
					<div class="card-footer m-t-5">
						<p class="font-mini">
							<spring:message code="tool.dashboard.admin1.${state.count}.1" /><span class="font-small pull-right"><c:out value="${dashboardData[state.count - 1][1]}"/></span>
						</p>
						<p class="font-mini m-t-5">
							<spring:message code="tool.dashboard.admin1.${state.count}.2" /><span class="font-small pull-right"><c:out value="${dashboardData[state.count - 1][2]}"/></span>
						</p>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.admin.dashboard4"/></h3><hr>
					<div id="dataMarketplace" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/marketplace"/>" target="_blank" 
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.solution.office2.5"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.admin.dashboard7"/></h3><hr>
					<div id="dataFeedback" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</div>
			<div class="card-analytic card-dashboard m-t-10">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.company.dashboard3"/></h3><hr>
					<div id="dataContent" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</div>
		</div>
	</div>
	<hr class="m-t-10 m-b-0">
	<div class="row row-mini">
		<c:set var="providers" value="basket,box,briefcase,comment,ajust,roadblock" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-6 col-sm-4 col-lg-2 col-mini m-t-10">
				<div class="card-analytic card-social">
					<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
					<div class="card-brand">
						<span class="text-truncate font-mini i-help"><spring:message code="tool.dashboard.admin3.3.${state.count}" /></span>
						<p class="h-header font-large"><c:out value="${dashboardDatakey[state.count - 1]}"/></p>
					</div>
					<span class="clearfix"></span>
				</div>
			</div>
		</c:forEach>
	</div>
	<hr class="m-t-10 m-b-0">
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<label class="col-form-label">
				<spring:message code="sidebar.admin.dashboard9"/>
				<span class="help-text m-t-5"><spring:message code="subheader.screen.sector3.3" /></span>
			</label>
			<div class="row row-mini">
				<c:set var="providers" value="chat,mail-alt,bell-alt,lifebuoy-2,cloud" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<div class="col-sm-4 col-mini m-t-10">
						<div class="card-analytic card-refering card-left h-100">
							<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
							<div class="card-brand text-right">
								<span class="text-truncate font-small i-help"><spring:message code="tool.dashboard.admin4.1.${state.count}" /></span>
								<p class="h-header i-primary"><c:out value="${dashboardInbox.parseInbox(state.count)}"/></p>
							</div>
							<span class="clearfix"></span>
							<div class="card-footer m-t-5">
								<c:set var="persent" value="${dashboardInbox.getPesrsentInbox(state.count)}" scope="page"></c:set>
								<i class="cmsms-icon-${pageScope.persent >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-10"></i><c:out value="${pageScope.persent}%"/>
							</div>
						</div>
					</div>
				</c:forEach>
				<div class="col-sm-4 col-mini m-t-10">
					<div class="card-analytic card-refering card-left card-primary h-100">
						<i class="cmsms-icon-award card-trigger pull-left"></i>
						<div class="card-brand text-right">
							<span class="text-truncate font-small i-gray"><spring:message code="tool.dashboard.admin4.1.6" /></span>
							<p class="h-header i-white"><c:out value="${dashboardInbox.parseSum()}"/></p>
						</div>
						<span class="clearfix"></span>
						<div class="card-footer m-t-5">
							<i class="cmsms-icon-up-1 i-green m-r-10"></i><c:out value="100%"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.company.dashboard6.5"/></h3><hr>
					<div id="dataLinked" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</div>
		</div>
	</div>
	<hr class="m-t-10 m-b-0">
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin5.1"/></h3><hr>
					<div id="dataAdmin" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin5.2"/></h3><hr>
					<div id="dataFavorite" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="panel-dashboard hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.dashboard1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header6 i-primary">
				<spring:message code="overview.subscribe.order1"/><span class="font-bold font-mini pull-right"><c:out value="${dashboardPremium[0]}"/></span>
			</h3>
			<div class="row row-mini">
				<c:forEach var="i" begin="1" end="4">
					<div class="col-6 col-mini m-t-10">
						<div class="card-analytic card-data card-right h-100" style="padding:10px 14px;">
							<i class="cmsms-icon-bat${i} card-trigger card-trigger${i} pull-right"></i>
							<div class="card-brand">
								<span class="text-truncate font-small"><spring:message code="overview.subscribe.order${i + 1}" /></span>
								<p class="h-header i-primary"><c:out value="${dashboardPremium[i]}"/></p>
							</div>
							<span class="clearfix"></span>
						</div>
					</div>
				</c:forEach>
			</div>
			<hr class="my-1">
			<div class="card-analytic card-dashboard m-t-10" style="padding:14px 20px;">
				<div>
					<h3 class="h-header h-header5 i-primary"><i class="cmsms-icon-database-3 m-r-10"></i><spring:message code="lbl.sub.display2.7"/></h3><hr>
					<table class="table table-page table-panel">
						<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
						<tbody class="font-small">
							<c:forEach var="i" begin="1" end="7" step="1">
								<tr>
									<td><spring:message code="tool.dashboard.admin3.2.${i}" /></td>
									<td class="font-bold text-right"><c:out value="${dashboardMedia[i - 1]}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card-analytic card-dashboard m-t-10" style="padding:14px 20px;">
				<div>
					<h3 class="h-header h-header5 i-primary"><i class="cmsms-icon-bell-1 m-r-10"></i><spring:message code="tabs.alert"/></h3><hr>
					<table class="table table-page table-panel">
						<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
						<tbody class="font-small">
							<c:forEach var="i" begin="1" end="2" step="1">
								<tr>
									<td><spring:message code="tool.dashboard.admin2.1.${i == 1 ? '2' : '5'}" /></td>
									<td class="font-bold text-right"><c:out value="${dashboardAlert[i - 1]}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card-analytic card-dashboard m-t-10 m-b-10" style="padding:14px 20px;">
				<div>
					<h3 class="h-header h-header5 i-primary"><i class="cmsms-icon-list-alt m-r-10"></i><spring:message code="sidebar.admin.dashboard1.3"/></h3><hr>
					<table class="table table-page table-panel">
						<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
						<tbody class="font-small">
							<c:forEach var="i" begin="1" end="2" step="1">
								<tr>
									<td><spring:message code="tool.dashboard.admin1.${i == 1 ? '5' : '1'}" /></td>
									<td class="font-bold text-right"><c:out value="${dashboardJournal[i - 1]}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>