<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.2" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard1.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.analytic1"/></p>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary ignore-border"><spring:message code="txt.solution.office3.4.4"/></h3>
				<span class="help-text"><spring:message code="chose.date1"/>, <joda:format value="${toDay}" pattern="dd MMMM yyyy"></joda:format></span>
				<hr class="m-t-10 m-b-20">
				<ul class="list-none list-inline">
					<li class="m-r-40">
						<span class="h-header font-large i-primary"><c:out value="${analyticLogin.parseCount()}"/></span>
						<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="sidebar.admin.dashboard10" /></span>
					</li>
					<li class="m-r-40">
						<span class="h-header font-large i-primary"><c:out value="${analyticLogin.parseCounts(1)}"/></span>
						<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tool.dashboard.admin1.1.1" /></span>
					</li>
					<li class="m-r-40">
						<span class="h-header font-large i-primary"><c:out value="${analyticLogin.parseCounts(2)}"/></span>
						<span class="help-text"><i class="cmsms-icon-circle i-yellow m-r-5"></i><spring:message code="tool.dashboard.admin1.1.2" /></span>
					</li>
					<li>
						<span class="h-header font-large i-primary"><c:out value="${analyticLogin.parseLocked()}"/></span>
						<span class="help-text"><i class="cmsms-icon-circle i-red m-r-5"></i><spring:message code="overview.alert2" /></span>
					</li>
				</ul>
				<hr class="m-t-20 m-b-20">
				<div class="row">
					<div class="col-sm-6">
						<c:set var="persentLogin" value="${analyticLogin.getPersentLogin()}" scope="page"></c:set>
						<div id="circleChartLogin" class="circle-3d circle-globe m-auto" data-counter="${pageScope.persentLogin}">
							<div class="circle-content text-center">
								<span class="h-header i-blue"><c:out value="${pageScope.persentLogin}%" /></span>
								<p class="font-small"><spring:message code="sidebar.admin.dashboard10"/></p>
							</div>
							<div id="circleCanvasLogin" class="circle-canvas"></div>
						</div>
					</div>
					<div class="col-sm-6">
						<p class="h-header font-value i-primary m-t-40"><c:out value="${analyticLogin.parseLogin()}"/></p>
						<span class="help-text m-t-10"><spring:message code="chose.visibility2"/></span>
					</div>
				</div>
				<hr class="m-t-20">
				<div class="row">
					<c:set var="providers" value="commerical-building,user-6" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<div class="col-6">
							<div class="card-data card-left">
								<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-left"></i>
								<div class="card-brand text-right">
									<span class="text-truncate font-small"><spring:message code="tool.dashboard.admin1.1.${state.count}" /></span>
									<p class="h-header i-primary"><c:out value="${analyticLogin.parseLogins(state.count)}"/></p>
								</div>
								<div class="card-footer m-t-5">
									<c:set var="persentCount" value="${analyticLogin.getPersentLogins(state.count)}" scope="page"></c:set>
									<div class="progress">
										<div class="progress-bar progress-data${state.count} animated onne progress-animated" style="width:${pageScope.persentCount}%" role="progressbar"></div>
									</div>
									<p class="font-mini i-help m-t-5">
										<spring:message code="tool.dashboard.admin1.${state.count == 1 ? '5' : '1'}"/>
										<span class="pull-right"><c:out value="${pageScope.persentCount}%"/></span>
									</p>
								</div>
								<span class="clearfix"></span>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin6.1"/></h3>
				<div id="cardChartAccess" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin6.2"/></h3>
				<div id="cardChartPremium" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin6.3"/></h3>
				<div id="cardChartVisit" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div id="cardChartMarket" class="card-analytic card-charts m-t-10">
		<div class="card-header">
			<div class="card-overlay"></div>
			<div class="form-group row m-t-0">
				<div class="col-md-6 col-lg-8">
					<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.admin.dashboard4"/></h3>
					<span class="help-text"><spring:message code="tool.dashboard.admin6.4" /></span>
				</div>
				<div class="col-md-6 col-lg-4">
					<select class="form-select2-simple" id="filterChartMarket" name="filterChartMarket">
						<option></option>
						<c:forEach var="i" begin="1" end="7" step="1">
							<option value="${i}" ${i == 7 ? 'selected' : ''}><spring:message code="chose.date${i}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="card-content">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div class="card-load"></div>
		</div>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="lbl.sub.search2"/></h3>
				<ul class="list-none list-inline m-t-10">
					<li>
						<span class="h-header font-large i-primary"><c:out value="${analyticCompany.getFormattedCountAll()}"/></span>
						<span class="help-text"><i class="cmsms-icon-circle i-help m-r-5"></i><spring:message code="tool.order.total" /></span>
					</li>
				</ul>
				<div class="card-body">
					<div class="row row-mini">
						<div class="col-sm-6 col-md-12 col-lg-6 m-b-10">
							<div class="card-evaluation">
								<c:set var="persentCompany" value="${analyticCompany.getPersentAttribut(1)}" scope="page"></c:set>
								<div id="circleCompanyPublished" class="circle-3d circle-evaluation pull-left" data-counter="${pageScope.persentCompany}">
									<div class="circle-content text-center">
										<span class="h-header font-bold i-blue"><c:out value="${pageScope.persentCompany}%" /></span>
									</div>
									<div id="circleCanvasPublished" class="circle-canvas"></div>
								</div>
								<div class="card-brand">
									<p class="font-small i-help"><spring:message code="tool.dashboard.admin1.3"/></p>
									<p class="h-header font-large i-primary"><c:out value="${analyticCompany.getFormattedAttribut(1)}"/></p>
								</div>
								<span class="clearfix"></span>
							</div>
						</div>
						<div class="col-sm-6 col-md-12 col-lg-6 m-b-10">
							<div class="card-evaluation">
								<c:set var="persentCompleted" value="${analyticCompany.getPersentAttribut(2)}" scope="page"></c:set>
								<div id="circleCompanyCompleted" class="circle-3d circle-evaluation pull-left" data-counter="${pageScope.persentCompleted}">
									<div class="circle-content text-center">
										<span class="h-header font-bold i-green"><c:out value="${pageScope.persentCompleted}%" /></span>
									</div>
									<div id="circleCanvasCompleted" class="circle-canvas"></div>
								</div>
								<div class="card-brand">
									<p class="font-small i-help"><spring:message code="lbl.sub.account3.1"/></p>
									<p class="h-header font-large i-primary"><c:out value="${analyticCompany.getFormattedAttribut(2)}"/></p>
								</div>
								<span class="clearfix"></span>
							</div>
						</div>
					</div>
				</div>
				<div class="card-footer m-t-10">
					<div class="row">
						<c:set var="providers" value="home-1,warehouse,commerical-building,town-hall" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<div class="col-sm-6 m-t-10">
								<div class="card-data card-right" style="padding:0;">
									<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-right"></i>
									<div class="card-brand">
										<span class="text-truncate font-small"><spring:message code="overview.briefcase3.${state.count}" /></span>
										<p class="h-header i-primary"><c:out value="${analyticCompany.getFormattedType(state.count)}"/></p>
									</div>
									<div class="m-t-5">
										<c:set var="persent" value="${analyticCompany.getPersentType(state.count)}" scope="page"></c:set>
										<div class="progress">
											<div class="progress-bar progress-data${state.count} animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div>
										</div>
										<p class="font-mini ${state.count != 6 ? 'i-help' : ''} m-t-5">
											<spring:message code="tool.dashboard.welcome1.3.${state.count == 6 ? '2' : '1'}"/>
											<span class="pull-right"><c:out value="${pageScope.persent}%"/></span>
										</p>
									</div>
									<span class="clearfix"></span>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin6.5"/></h3>
				<div id="cardChartAnalyse" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="sidebar.admin.dashboard1.3"/></h3>
				<div id="cardChartJournal" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-charts card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin6.6"/></h3>
				<div id="cardChartGuest" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.dashboard1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<div class="card-analytic card-charts card-dashboard">
				<h3 class="h-header h-header5 i-primary"><i class="cmsms-icon-user-2 m-r-10"></i><spring:message code="sidebar.admin.dashboard10"/></h3><hr>
				<div class="m-t-10">
					<ul class="list-none list-inline">
						<li>
							<span class="h-header font-large i-primary"><c:out value="${analyticUser.getFormattedCountAll()}"/></span>
							<span class="help-text"><i class="cmsms-icon-circle i-help m-r-5"></i><spring:message code="tool.order.total" /></span>
						</li>
					</ul>
					<div class="card-body">
						<canvas id="canvaUserPro" style="height:220px;max-width:100%;"></canvas>
						<input type="hidden" id="userProValue" value="${analyticUser.countProToString()}" />
					</div>
				</div>
				<div class="card-footer m-t-10">
					<c:set var="persentProfile" value="${analyticUser.getPersentProfile()}" scope="page"></c:set>
					<div class="row">
						<div class="col-6"><p class="h-header font-big i-primary m-t-5"><c:out value="${pageScope.persentProfile}%"/></p></div>
						<div class="col-6 text-right">
							<span class="help-text"><spring:message code="tool.dashboard.admin1.3.1"/></span>
							<p class="h-header font-small"><c:out value="${analyticUser.getFormattedCountSexe()}"/></p>
						</div>
					</div>
					<div class="progress">
						<div class="progress-bar progress-primary animated onne progress-animated" style="width:${pageScope.persentProfile}%" role="progressbar"></div>
					</div>
					<div class="row m-t-10">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div class="col-6">
								<c:set var="persentSexe" value="${analyticUser.getPersentSexe(i)}" scope="page"></c:set>
								<div class="row">
									<div class="col-6"><p class="h-header font-big m-t-5"><c:out value="${pageScope.persentSexe}%"/></p></div>
									<div class="col-6 text-right">
										<span class="help-text"><spring:message code="tool.dashboard.admin1.4.${i}"/></span>
										<p class="h-header font-small"><c:out value="${analyticUser.getFormattedSexe(i)}"/></p>
									</div>
								</div>
								<div class="progress">
									<div class="progress-bar progress-access${i} animated onne progress-animated" style="width:${pageScope.persentSexe}%" role="progressbar"></div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="card-analytic card-charts card-dashboard m-t-10">
				<h3 class="h-header h-header5 i-primary"><i class="cmsms-icon-user-female m-r-10"></i><spring:message code="tool.dashboard.admin1.4"/></h3><hr>
				<div class="m-t-10">
					<ul class="list-none list-inline">
						<li>
							<span class="h-header font-large i-primary"><c:out value="${analyticUser.getFormattedCountAgent()}"/></span>
							<span class="help-text"><i class="cmsms-icon-circle i-help m-r-5"></i><spring:message code="tool.order.total" /></span>
						</li>
					</ul>
					<div class="card-body">
						<canvas id="canvaUserAgent" style="height:220px;max-width:100%;"></canvas>
						<input type="hidden" id="userAgentValue" value="${analyticUser.countAgentToString()}" />
					</div>
				</div>
				<div class="card-footer m-t-10">
					<c:set var="persentPingled" value="${analyticUser.getPersentPingled()}" scope="page"></c:set>
					<div class="row">
						<div class="col-6"><p class="h-header font-big i-primary m-t-5"><c:out value="${pageScope.persentPingled}%"/></p></div>
						<div class="col-6 text-right">
							<span class="help-text"><spring:message code="tabs.pin"/></span>
							<p class="h-header font-small"><c:out value="${analyticUser.getFormattedCountPingled()}"/></p>
						</div>
					</div>
					<div class="progress">
						<div class="progress-bar progress-segoond animated onne progress-animated" style="width:${pageScope.persentPingled}%" role="progressbar"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>