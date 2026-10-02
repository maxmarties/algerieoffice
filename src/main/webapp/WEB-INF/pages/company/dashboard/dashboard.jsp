<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.1" /></li>
	</ol>
</div>
<div class="page-wrapper page-dashboard">
	<div class="page-title m-b-20"><h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard1"/></h1></div>
	<c:if test="${currentSocial.promoted && !currentConfig.identityCollapse && empty currentCompany.url}">
		<c:set var="counter" value="${100 - countSkills}" scope="page"></c:set>
		<c:if test="${pageScope.counter > 0}">
			<div class="alert alert-warning m-b-20">
				<i class="cmsms-icon-gift-2 i-alert"></i>
				<ul class="navbar-nav nav-flex-icons">
					<li class="m-r-20">
						<span class="block font-big font-bold"><spring:message code="message.explorer.offer1"/></span>
						<p class="font-small m-t-10"><spring:message code="message.explorer.offer2.2"/> <spring:message code="message.explorer.offer3"/></p>
						<div class="m-t-20">
							<a href="<c:url value="/company/profile/identity"/>" class="btn btn-segond btn-fixed" style="min-width:190px;">
								<span><spring:message code="message.explorer.offer4.2" /></span></a>
							<span class="font-mini i-red m-l-20"><strong><c:out value="${pageScope.counter}"/></strong> <spring:message code="message.explorer.offer4.3" /></span>
						</div>
					</li>
					<li class="text-center ml-auto">
						<a id="iOffer" class="btn btn-simple btn-modal" data-dismiss="alert" title="<spring:message code="btn.close"/>"><i class="cmsms-icon-cancel-2"></i></a>
						<hr class="m-t-20">
						<p class="h-header i-red sh-black m-t-30" style="font-size:46px;"><c:out value="${pageScope.counter}"/></p>
					</li>
				</ul>
			</div>
		</c:if>
	</c:if>
	<div class="row">
		<div class="col-6">
			<p class="h-header font-large"><spring:message code="tool.dashboard.welcome1"/>, <c:out value="${currentUser.user.firstName}" /> !</p>
			<p class="font-mini i-help m-t-5">
				<span class="font-bold"><joda:format value="${toDay}" pattern="dd MMM yyyy"></joda:format></span>, <spring:message code="txt.help.dashboard1.1"/> 
			</p>
		</div>
		<div class="col-6 hidden-sm-down">
			<ul class="navbar-nav nav-flex-icons navcard-welcome font-small">
				<li class="ml-auto">
					<spring:message code="sidebar.company.dashboard8"/>
					<span class="help-text text-truncate"><c:out value="${currentCommunication.parseSum()}" /> <spring:message code="tool.dashboard.welcome1.1"/></span>
				</li>
				<li class="divider"></li>
				<li>
					<spring:message code="sidebar.company.dashboard9"/>
					<span class="help-text text-truncate"><c:out value="${currentProspect.parseSum()}" /> <spring:message code="tool.dashboard.welcome1.2"/></span>
				</li>
			</ul>
		</div>
	</div>
	<div class="row row-mini">
		<c:set var="providers" value="bag,pin-1,calendar-7,coffee,tree-3,user-2" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-6 col-md-4 col-lg-2 col-mini m-t-10">
				<c:set var="dataLine" value="${dataLines.get(state.count - 1)}" scope="page"></c:set>
				<div class="card-analytic card-data card-right ${state.count == 6 ? 'card-segond' : ''} h-100">
					<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-right"></i>
					<div class="card-brand">
						<span class="text-truncate font-small"><spring:message code="tool.dashboard.data${state.count}" /></span>
						<p class="h-header ${state.count == 6 ? 'i-white' : 'i-primary'}"><c:out value="${pageScope.dataLine.countAll}"/></p>
					</div>
					<div class="card-footer m-t-5">
						<c:set var="persent" value="${pageScope.dataLine.parsePesrsentPublished()}" scope="page"></c:set>
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
	<c:if test="${!empty begginer}">
		<div class="card-analytic card-welcome m-t-20">
			<ul class="navbar-nav nav-flex-icons">
				<li><h2 class="h-header h-header3 i-primary"><spring:message code="subheader.dashboard1.2"/></h2></li>
				<li class="ml-auto"><a id="iCloseWelcome" class="lien lien-help"><i class="cmsms-icon-dot-circled m-r-5"></i><spring:message code="btn.close"/></a></li>
			</ul>
			<p class="font-small m-t-5"><spring:message code="txt.help.dashboard1.2"/> :</p>
			<hr class="my-1">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard1.2.1"/></h3>
					<span class="help-text m-t-20"><spring:message code="txt.help.dashboard2.1.1"/></span>
					<div class="hidden-md-down m-t-20">
						<a id="iStarted" class="btn btn-primary btn-flat btn-fixed"><span><spring:message code="txt.help.dashboard2.1.2"/></span></a>
					</div>
					<div class="alert alert-info hidden-md-up m-t-10"><i class="cmsms-icon-info i-alert"></i><p class="p-alert"><spring:message code="txt.help.appeerance2"/></p></div>
					<div class="social-auth-hr font-small m-t-20 m-b-10"><span><spring:message code="lbl.or"/></span></div>
					<i class="cmsms-icon-explorer-hand i-22"></i><a href="<c:url value="/company/help/begginer"/>" 
						class="lien lien-table"><spring:message code="txt.help.case3.9.2"/></a>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="row">
						<div class="col-sm-6 m-b-20">
							<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard1.2.2"/></h3>
							<ul class="list-none list-block m-t-20">
								<c:set var="liens" value="overview/header,overview/presentation,profile/linked,overview/timeline,profile/identity" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="text-nowrap m-t-5">
										<i class="cmsms-icon-${begginer.tasks[0][state.count - 1] ? 'ok i-green' : 'explorer-angle i-primary'} i-12"></i>
										<a href="<c:url value="/company/${pageScope.lien}"/>" 
											class="lien lien-table"><spring:message code="txt.help.dashboard2.2.${state.count}"/></a>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="col-sm-6 m-b-20">
							<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard1.2.3"/></h3>
							<ul class="list-none list-block m-t-20">
								<c:set var="liens" value="posts/new,team/users/new,marketplace/ads/new,portfolio/actus/new,manage/appearance" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="text-nowrap m-t-5">
										<i class="cmsms-icon-${begginer.tasks[1][state.count - 1] ? 'ok i-green' : 'explorer-angle i-primary'} i-12"></i>
										<a href="<c:url value="/company/${pageScope.lien}"/>" 
											class="lien lien-table"><spring:message code="txt.help.dashboard2.3.${state.count}"/></a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="widget-more flexed m-t-10">
				<label class="ui-checkbox ui-checkbox-segond font-small"><input type="checkbox" name="checkCloseWelcome" />
					<span class="input-span"></span><spring:message code="txt.help.dashboard2.1.3" /> </label>
				<a id="iExitWelcome" class="lien lien-red lien-hover lien-small disabled m-l-5"><spring:message code="txt.help.dashboard2.1.4" /></a>.
			</div>
		</div>	
	</c:if>
	<div class="row row-mini m-t-10">
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard2.1"/></h3>
					<div id="cardChartTrafic" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/dashboard/analytic"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard3.1.3"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard2.2"/></h3>
					<div id="cardChartStatistic" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/posts/statistic"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard3.1.6"/></a>
				</div>
			</div>
		</div>
	</div>
	<div class="row row-mini m-t-10">
		<div class="col-md-4 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard3.1"/></h3>
					<div id="cardChartCommunication" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/communication/contacts"/>"
					class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.1.3"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard3.2"/></h3>
					<div id="cardChartProspect" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/prospects/quotes"/>"
					class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.1.3"/></a>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-mini m-t-10">
			<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
				<div>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard3.3"/></h3>
					<c:choose>
						<c:when test="${!evaluationLines.isEmpty()}">
							<div class="table-responsive">
								<table class="table table-page">
									<thead><tr><th style="width:calc(100% - 150px);"></th><th style="width:126px;"></th><th style="width:24px;"></th></tr></thead>
									<tbody class="font-small">
										<c:forEach var="evaluationLine" items="${evaluationLines}">
											<tr>
												<td class="td-brand">
													<img src="<c:url value="${evaluationLine.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${evaluationLine.username}"/>">
													<div class="brand-colspan">
														<a href="<c:url value="/membres?id=${evaluationLine.userId}" />" class="lien lien-black font-bold" target="_blank">
															<c:out value="${evaluationLine.username}" />
														</a>
														<span class="help-text"><c:out value="${evaluationLine.tradename}"/></span>
													</div>
													<span class="clearfix"></span>
												</td>
												<td class="text-center">
													<c:set var="countStar" value="${evaluationLine.note / 2}" scope="page"></c:set>
													<ul class="list-none list-evaluation">
														<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
														<c:if test="${evaluationLine.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
													</ul>
												</td>
												<td><i class="cmsms-icon-thumbs-${evaluationLine.liked ? 'up-alt i-green' : 'down-alt i-red'}"></i></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:when>
						<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard4.1.4"/></span></c:otherwise>
					</c:choose>
				</div>
				<div class="widget-more m-t-10">
					<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/communication/evaluations"/>"
						class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.1.3"/></a>
				</div>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${currentCompany.hasPremium()}">
			<div class="row row-mini m-t-10">
				<div class="col-md-6 col-mini m-t-10">
					<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
						<div>
							<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard4.1"/></h3>
							<c:choose>
								<c:when test="${!journalLines.isEmpty()}">
									<div class="table-responsive">
										<table class="table table-page">
											<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 170px);"></th><th style="width:130px;"></th></tr></thead>
											<tbody class="font-small">
												<c:forEach var="journalLine" items="${journalLines}">
													<tr>
														<td class="td-check td-image"><i class="cmsms-icon-${journalLine.icon} i-table"></i></td>
														<td>
															<spring:message code="txt.journal.company${journalLine.action}" />
															<c:if test="${!empty journalLine.element}"> : <c:out value="${journalLine.element}" /></c:if>
															<span class="help-text">
																<a href="<c:url value="/membres?id=${journalLine.userId}" />" class="lien lien-help" target="_blank">
																	<c:out value="${journalLine.username}" />
																</a>
															</span>
														</td>
														<td class="text-center"><joda:format value="${journalLine.postedDate}" pattern="dd MMM - HH:mm"></joda:format></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard4.2.1"/></span></c:otherwise>
							</c:choose>
						</div>
						<div class="widget-more m-t-10">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/dashboard/journal"/>"
								class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.2.2"/></a>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-mini m-t-10">
					<div class="card-analytic card-dashboard flexed flex-colone flex-jusitify h-100">
						<div>
							<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard4.2"/></h3>
							<c:choose>
								<c:when test="${!detectLines.isEmpty()}">
									<div class="table-responsive">
										<table class="table table-page">
											<thead><tr><th style="width:calc(100% - 180px);"></th><th style="width:146px;"></th><th style="width:34px;"></th></tr></thead>
											<tbody class="font-small">
												<c:forEach var="detectLine" items="${detectLines}">
													<tr>
														<td class="td-brand">
															<img src="<c:url value="${detectLine.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${detectLine.username}"/>">
															<div class="brand-colspan">
																<a href="<c:url value="/membres?id=${detectLine.userId}" />" class="lien lien-table"
																	title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
																	<c:out value="${detectLine.username}" />
																</a>
																<span class="help-text"><c:out value="${detectLine.tradename}"/></span>
															</div>
															<span class="clearfix"></span>
														</td>
														<td class="text-center"><joda:format value="${detectLine.accessDate}" pattern="dd MMM - HH:mm"></joda:format></td>
														<td class="btn-td">
															<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${detectLine.userId}" 
																data-avatar="${detectLine.urlAvatar}" data-name="${detectLine.username}"><i class="cmsms-icon-mail-alt"></i></a>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise><span class="help-text help-empty m-t-10"><spring:message code="txt.help.dashboard4.3.1"/></span></c:otherwise>
							</c:choose>
						</div>
						<div class="widget-more m-t-10">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/dashboard/detect"/>"
								class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.dashboard4.3.2"/></a>
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning m-t-20">
				<i class="cmsms-icon-dollar i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.dashboard"/> 
					<a href="<c:url value="/company/tools/subscribes/new" />" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="card-analytic card-dashboard card-charts m-t-20">
		<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin7.1"/></h3>
		<div id="cardChartMarket" class="card-load m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
	</div>
</div>
<div class="panel-dashboard hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.dashboard1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header6 i-primary"><spring:message code="subheader.dashboard1.1"/></h3>
			<div id="iExplorerCampaign" class="card-analytic card-campaign m-t-10"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			<hr class="my-1">
			<div class="card-analytic card-dashboard">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard1.3"/></h3>
				<div id="cardChartCompleted" class="card-load text-center m-t-20"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
			<div class="card-analytic card-dashboard m-t-10">
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.dashboard1.4"/></h3>
				<p class="font-small m-t-10"><span class="h-header font-large i-primary m-r-10"><c:out value="${countFavorite}"/></span><spring:message code="txt.help.dashboard1.3.3"/></p>
			</div>
			<hr class="my-1">
			<div class="card-analytic card-dashboard m-b-10">
				<h3 class="h-header h-header5 i-primary"><spring:message code="wizard.forums.explorer2"/></h3>
				<div id="cardChartTopics" class="card-load m-t-5"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>
<c:if test="${!empty begginer}"><c:import url="/WEB-INF/fields/help/started_company.jsp"/></c:if>