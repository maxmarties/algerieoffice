<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.1" /></li>
	</ol>
</div>
<div class="page-wrapper page-dashboard">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard1"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.dashboard1"/></p>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-20">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary ignore-border"><spring:message code="subheader.dashboard5.1.1"/></h3>
					<span class="help-text"><spring:message code="chose.date1"/>, <joda:format value="${toDay}" pattern="dd MMMM yyyy"></joda:format></span>
					<hr class="m-t-10 m-b-20">
					<div class="row">
						<div class="col-sm-5">
							<c:set var="persentGlobe" value="${dataLines.getPersentGlobeCompany()}" scope="page"></c:set>
							<div id="circleChartGlobe" class="circle-3d circle-globe m-auto" data-counter="${pageScope.persentGlobe}">
								<div class="circle-content text-center">
									<span class="h-header i-blue"><c:out value="${pageScope.persentGlobe}%" /></span>
									<p class="font-small"><spring:message code="sidebar.admin.dashboard3"/></p>
								</div>
								<div id="circleCanvasGlobe" class="circle-canvas"></div>
							</div>
						</div>
						<div class="col-sm-7">
							<ul class="list-none list-inline list-globe-info m-t-20">
								<c:forEach var="i" begin="1" end="2" step="1">
									<li class="${i == 1 ? 'm-r-20' : ''}">
										<i class="cmsms-icon-circle i-${i == 1 ? 'blue' : 'border'} pull-left"></i>
										<div class="card-brand">
											<span class="h-header font-value i-primary"><c:out value="${dataLines.getFormattedGlobe(i)}"/></span>
											<span class="help-text"><spring:message code="subheader.globe${i}" /></span>
										</div>
										<span class="clearfix"></span>
									</li>
								</c:forEach>
							</ul>
							<p class="font-small i-help m-t-10"><span class="font-bold"><c:out value="${currentUser.user.firstName}" /></span>, <spring:message code="txt.help.dashboard5.1.1"/></p>
						</div>
					</div>
				</div>
				<div class="widget-more m-t-20">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/user/globe/companies"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard5.1.3"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-20">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary ignore-border"><spring:message code="subheader.dashboard5.1.2"/></h3>
					<span class="help-text"><spring:message code="txt.help.dashboard5.1.2"/></span>
					<hr class="m-t-10 m-b-10">
					<div class="row">
						<c:set var="providers" value="bag,pin-1,calendar-7,coffee" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<div class="col-6">
								<div class="card-data card-left">
									<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-left"></i>
									<div class="card-brand text-right">
										<span class="text-truncate font-small"><spring:message code="sidebar.user.dashboard2.${state.count}" /></span>
										<p class="h-header i-primary"><c:out value="${dataLines.getFormattedFavorite(state.count)}"/></p>
									</div>
									<div class="card-footer m-t-5">
										<c:set var="persentFavorite" value="${dataLines.getPersentFavorite(state.count)}" scope="page"></c:set>
										<div class="progress">
											<div class="progress-bar progress-data${state.count} animated onne progress-animated" style="width:${pageScope.persentFavorite}%" role="progressbar"></div>
										</div>
										<p class="font-mini i-help m-t-5">
											<spring:message code="tool.dashboard.welcome1.3.3"/>
											<span class="pull-right"><c:out value="${pageScope.persentFavorite}%"/></span>
										</p>
									</div>
									<span class="clearfix"></span>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/user/favorite/posts"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard5.1.4"/></a>
				</div>
			</div>
		</div>
	</div>
	<div class="row row-mini">
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-6 col-md-3 col-mini m-t-10">
				<div class="card-analytic card-refering card-right h-100">
					<i class="cmsms-icon-${pageScope.provider} card-trigger pull-right"></i>
					<div class="card-brand">
						<span class="text-truncate font-small i-help"><spring:message code="sidebar.user.dashboard4.${state.count}" /></span>
						<p class="h-header i-primary"><c:out value="${dataLines.getFormattedAlert(state.count)}"/></p>
					</div>
					<span class="clearfix"></span>
					<div class="card-footer font-mini i-help m-t-5">
						<spring:message code="tool.dashboard.welcome1.3.4"/>
						<span class="pull-right"><c:out value="${dataLines.countAlertPotentiel(state.count)}"/></span>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard6.1"/></h3>
					<c:choose>
						<c:when test="${!historyLines.isEmpty()}">
							<div class="table-responsive">
								<table class="table table-page">
									<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 170px);"></th><th style="width:130px;"></th></tr></thead>
									<tbody class="font-small">
										<c:forEach var="historyLine" items="${historyLines}">
											<tr>
												<td class="td-check td-image"><i class="cmsms-icon-${historyLine.icon} i-table i-table-blue"></i></td>
												<td><spring:message code="txt.journal.user${historyLine.action}" /></td>
												<td class="text-center"><joda:format value="${historyLine.postedDate}" pattern="dd MMM - HH:mm"></joda:format></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:when>
						<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard6.2.1"/></span></c:otherwise>
					</c:choose>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/user/dashboard/history"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard6.2.2"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard h-100">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard6.2"/></h3>
				<div id="cardChartAnalytic" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div class="row row-mini">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard6.3"/></h3>
					<c:choose>
						<c:when test="${!messageLines.isEmpty()}">
							<div class="table-responsive">
								<table class="table table-page">
									<thead><tr><th style="width:calc(100% - 160px);"></th><th style="width:126px;"></th><th style="width:34px;"></th></tr></thead>
									<tbody class="font-small">
										<c:forEach var="messageLine" items="${messageLines}">
											<tr>
												<td class="td-brand">
													<img src="<c:url value="${messageLine.avatarURL}"/>" class="img-circle pull-left" alt="<c:out value="${messageLine.username}"/>">
													<div class="brand-colspan">
														<a href="<c:url value="/membres?id=${messageLine.userId}" />" class="lien lien-table"
															title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
															<c:out value="${messageLine.username}" />
														</a>
														<span class="help-text">
															<c:if test="${messageLine.senderId == currentUser.getUserId()}"><span class="font-bold"><spring:message code="tool.view.me" />: </span></c:if>
															<c:choose>
																<c:when test="${messageLine.emojis}"><img height="16" src="<c:url value="/static/vectors/emoticons/${messageLine.message}.png"/>"></c:when>
																<c:otherwise><c:out value="${messageLine.message}"/></c:otherwise>
															</c:choose>
														</span>
													</div>
													<span class="clearfix"></span>
												</td>
												<td class="text-center"><joda:format value="${messageLine.postedDate}" pattern="dd MMM - HH:mm"></joda:format></td>
												<td class="btn-td">
													<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${messageLine.userId}" 
														data-avatar="${messageLine.avatarURL}" data-name="${messageLine.username}"><i class="cmsms-icon-mail-alt"></i></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:when>
						<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard6.4.1"/></span></c:otherwise>
					</c:choose>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/user/feedback/messages"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.1.3"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard6.4"/></h3>
					<c:choose>
						<c:when test="${!commentLines.isEmpty()}">
							<div class="table-responsive">
								<table class="table table-page">
									<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 200px);"></th><th style="width:126px;"></th><th style="width:34px;"></th></tr></thead>
									<tbody class="font-small">
										<c:forEach var="commentLine" items="${commentLines}">
											<tr>
												<td class="td-check td-image"><i class="cmsms-icon-comment i-table i-table-yellow"></i></td>
												<td><c:out value="${commentLine.message}"/></td>
												<td class="text-center"><joda:format value="${commentLine.postedDate}" pattern="dd MMM - HH:mm"></joda:format></td>
												<td class="btn-td">
													<a href="<c:url value="${commentLine.actualityURL}" />" class="btn btn-table btn-green" 
														title="<spring:message code="btn.view" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:when>
						<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard6.4.2"/></span></c:otherwise>
					</c:choose>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/user/communication/comments"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.1.3"/></a>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="panel-dashboard hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.dashboard1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<div class="card-analytic card-dashboard">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard5.2.1"/></h3>
				<div id="cardChartCompleted" class="card-load text-center m-t-20"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
			<div class="card-analytic card-dashboard m-t-10">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard5.2.2"/></h3>
				<p class="font-small m-t-10">
					<span class="h-header font-large i-primary m-r-10"><c:out value="${dataLines.getFormattedPopularity()}"/></span><spring:message code="txt.help.dashboard6.1.3"/>
				</p>
			</div>
			<div class="card-analytic card-dashboard m-t-10">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard5.2.3"/></h3>
				<div id="cardChartPerform" class="card-load m-t-20"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
			<hr class="my-1">
			<div class="card-analytic card-dashboard m-b-10">
				<h3 class="h-header h-header5 i-primary"><spring:message code="explorer.subheader.blog"/></h3>
				<div id="cardChartBlog" class="card-load m-t-5"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>
<c:if test="${currentConfig.started}"><c:import url="/WEB-INF/fields/help/started_user.jsp"/></c:if>