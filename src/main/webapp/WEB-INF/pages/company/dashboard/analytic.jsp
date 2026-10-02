<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard1.2" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.company.analytic"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.analytic1"/></p>
	</div>
	<c:choose>
		<c:when test="${currentCompany.hasPremium()}">
			<label class="col-form-label">
				<spring:message code="subheader.analytic1.1"/>
				<span class="help-text"><spring:message code="txt.help.analytic1.1" /></span>
			</label>
			<div class="row row-mini">
				<c:set var="providers" value="target-4,search-1,filter,hash-1,fire,award" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<div class="col-6 col-sm-4 col-lg-2 col-mini m-t-10">
						<div class="card-analytic card-refering card-left ${state.count == 6 ? 'card-primary' : ''} h-100">
							<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
							<div class="card-brand text-right">
								<span class="text-truncate font-small ${state.count == 6 ? 'i-gray' : 'i-help'}"><spring:message code="tool.analytic.refering${state.count}" /></span>
								<p class="h-header ${state.count == 6 ? 'i-white' : 'i-primary'}"><c:out value="${refering.getReferingValue(state.count)}"/></p>
							</div>
							<span class="clearfix"></span>
							<div class="card-footer m-t-5">
								<c:set var="persent" value="${refering.getReferingPersent(state.count)}" scope="page"></c:set>
								<i class="cmsms-icon-${pageScope.persent >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-10"></i><c:out value="${pageScope.persent}%"/>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<hr class="my-1">
			<div class="row row-mini">
				<div class="col-md-4 col-mini">
					<div class="row row-mini h-100">
						<div class="col-sm-6 col-md-12 col-mini m-b-10">
							<div id="cardAnalyticFavorite" class="card-analytic card-charts h-100">
								<div class="card-header">
									<div class="card-overlay"></div>
									<div class="form-group row m-t-0">
										<label class="col-form-label col-lg-4"><spring:message code="subheader.analytic2.1"/></label>
										<div class="col-lg-8">
											<select class="form-select2-simple" id="filterAnalyticFavorite" name="filterAnalyticFavorite">
												<option></option>
												<c:forEach var="i" begin="1" end="7" step="1">
													<option value="${i}" ${i == 7 ? 'selected' : ''}><spring:message code="chose.date${i}"/></option>
												</c:forEach>
											</select>
										</div>
									</div>
									<span class="help-text m-t-10"><spring:message code="txt.help.analytic2.1" /></span>
								</div>
								<div class="card-content">
									<c:import url="/WEB-INF/basics/loading_span.jsp"/>
									<div class="card-load"></div>
								</div>
							</div>		
						</div>
						<div class="col-sm-6 col-md-12 col-mini m-b-10">
							<div id="cardAnalyticDocument" class="card-analytic card-charts h-100">
								<div class="card-header">
									<div class="card-overlay"></div>
									<div class="form-group row m-t-0">
										<label class="col-form-label col-lg-4"><spring:message code="subheader.analytic2.3"/></label>
										<div class="col-lg-8">
											<select class="form-select2-simple" id="filterAnalyticDocument" name="filterAnalyticDocument">
												<option></option>
												<c:forEach var="i" begin="1" end="7" step="1">
													<option value="${i}" ${i == 7 ? 'selected' : ''}><spring:message code="chose.date${i}"/></option>
												</c:forEach>
											</select>
										</div>
									</div>
									<span class="help-text m-t-10"><spring:message code="txt.help.analytic2.6" /></span>
								</div>
								<div class="card-content">
									<c:import url="/WEB-INF/basics/loading_span.jsp"/>
									<div class="card-load"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 col-mini">
					<div id="cardAnalyticAccess" class="card-analytic card-charts m-b-10">
						<div class="card-header">
							<div class="card-overlay"></div>
							<div class="form-group row m-t-0">
								<label class="col-form-label col-md-6 col-lg-8"><spring:message code="subheader.analytic2.2"/></label>
								<div class="col-md-6 col-lg-4">
									<select class="form-select2-simple" id="filterAnalyticAccess" name="filterAnalyticAccess">
										<option></option>
										<c:forEach var="i" begin="1" end="7" step="1">
											<option value="${i}" ${i == 7 ? 'selected' : ''}><spring:message code="chose.date${i}"/></option>
										</c:forEach>
									</select>
								</div>
							</div>
							<span class="help-text m-t-10"><spring:message code="txt.help.analytic2.4" /></span>
						</div>
						<div class="card-content">
							<c:import url="/WEB-INF/basics/loading_span.jsp"/>
							<div class="card-load"></div>
						</div>
					</div>
					<div class="card-analytic card-charts card-segond m-b-10">
						<label class="col-form-label i-white">
							<spring:message code="subheader.analytic2.4"/> <small class="min">(<spring:message code="txt.help.analytic3.1" />)</small>
						</label>
						<span class="help-text i-gray"><spring:message code="txt.help.analytic3.2" /></span>
						<div class="card-footer m-t-10">
							<div class="row">
								<c:set var="providers" value="glasses-1,call-out,mail-3" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<c:set var="persent" value="${refering.parsePersentClick(state.count)}" scope="page"></c:set>
									<div class="col-sm-4 m-t-10">
										<div class="card-click">
											<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
											<div class="card-brand">
												<span class="font-big i-gray"><spring:message code="txt.help.analytic3.2.${state.count}" /></span>
												<span class="h-header i-white m-l-5"><c:out value="${refering.getFormattedClick(state.count)}"/></span>
												<p class="font-mini i-gray">
													<i class="cmsms-icon-${pageScope.persent >= 50 ? 'up-1' : 'down-1'} m-r-5"></i><c:out value="${pageScope.persent}%"/>
												</p>
												<hr class="i-light">
												<span class="font-mini i-gray"><spring:message code="txt.help.analytic3.3.${state.count}" /></span>
											</div>
											<span class="clearfix"></span>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			<hr class="m-t-0 m-b-10">
			<div class="row row-mini">
				<div class="col-md-6 col-mini m-b-10">
					<div id="cardAnalyticActivity" class="card-analytic card-charts h-100">
						<div class="card-header">
							<label class="col-form-label">
								<spring:message code="subheader.analytic2.5"/>
								<span class="help-text"><spring:message code="txt.help.analytic4.1" /></span>
							</label>
						</div>
						<div class="card-content">
							<c:import url="/WEB-INF/basics/loading_span.jsp"/>
							<div class="card-load"></div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-mini m-b-10">
					<div id="cardAnalyticEvaluation" class="card-analytic card-charts">
						<div class="card-header">
							<div class="card-overlay"></div>
							<div class="form-group row m-t-0">
								<label class="col-form-label col-md-6"><spring:message code="subheader.analytic2.6"/></label>
								<div class="col-md-6">
									<select class="form-select2-simple" id="filterAnalyticEvaluation" name="filterAnalyticEvaluation">
										<option></option>
										<c:forEach var="i" begin="1" end="7" step="1">
											<option value="${i}" ${i == 7 ? 'selected' : ''}><spring:message code="chose.date${i}"/></option>
										</c:forEach>
									</select>
								</div>
							</div>
							<span class="help-text m-t-10"><spring:message code="txt.help.analytic4.2" /></span>
						</div>
						<div class="card-content">
							<c:import url="/WEB-INF/basics/loading_span.jsp"/>
							<div class="card-load"></div>
						</div>
					</div>
					<div class="card-analytic card-charts m-t-10">
						<label class="col-form-label"><spring:message code="subheader.analytic3.1"/></label>
						<span class="help-text"><spring:message code="txt.help.analytic4.5" /></span>
						<div class="card-footer m-t-10">
							<div class="row row-mini">
								<c:set var="providers" value="comment-5,calendar-7,briefcase-3" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<div class="col-sm-4 col-mini m-t-5">
										<div class="card-upwork">
											<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-left"></i>
											<div class="card-brand">
												<span class="font-small i-help"><spring:message code="sidebar.user.dashboard3.${state.count == 3 ? '5' : state.count}"/></span>
												<p class="h-header font-large i-primary"><c:out value="${refering.getFormattedUpwork(state.count)}"/></p>
											</div>
											<span class="clearfix"></span>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			<hr class="m-t-0 m-b-10">
			<label class="col-form-label">
				<spring:message code="subheader.analytic3.2"/>
				<span class="help-text"><spring:message code="txt.help.analytic4.6" /></span>
			</label>
			<div class="row row-mini">
				<c:set var="providers" value="facebook,twitter,google,linkedin,viadeo" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<div class="col-6 col-sm-4 col-lg-2 col-mini m-t-10">
						<div class="card-analytic card-social btn-${pageScope.provider}">
							<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
							<div class="card-brand">
								<span class="text-capitalize text-truncate font-small i-gray"><c:out value="${pageScope.provider}"/></span>
								<p class="h-header font-large"><c:out value="${refering.getFormattedFollow(state.count)}"/></p>
							</div>
							<span class="clearfix"></span>
						</div>
					</div>
				</c:forEach>
				<div class="col-6 col-sm-4 col-lg-2 col-mini m-t-10">
					<div class="card-analytic card-social card-primary">
						<i class="cmsms-icon-share card-trigger pull-left i-segond"></i>
						<div class="card-brand">
							<span class="text-capitalize text-truncate font-small i-gray"><spring:message code="txt.help.analytic4.7" /></span>
							<p class="h-header font-large i-white"><c:out value="${refering.getFormattedSumFollow()}"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
			<hr class="my-1">
			<c:choose>
				<c:when test="${currentCompany.premium > 1}">
					<div class="row row-mini">
						<div class="col-md-6 col-mini">
							<div id="cardAnalyticContact" class="card-analytic card-charts h-100">
								<div class="card-header">
									<label class="col-form-label">
										<spring:message code="subheader.analytic4.1"/>
										<span class="help-text"><spring:message code="txt.help.analytic5.1" /></span>
									</label>
								</div>
								<div class="card-content">
									<c:import url="/WEB-INF/basics/loading_span.jsp"/>
									<div class="card-load"></div>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-mini">
							<div id="cardAnalyticGuest" class="card-analytic card-charts h-100">
								<div class="card-header">
									<label class="col-form-label">
										<spring:message code="subheader.analytic4.2"/>
										<span class="help-text"><spring:message code="txt.help.analytic5.2" /></span>
									</label>
								</div>
								<div class="card-content">
									<c:import url="/WEB-INF/basics/loading_span.jsp"/>
									<div class="card-load"></div>
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-warning m-t-10">
						<i class="cmsms-icon-dollar i-alert"></i>
						<p class="p-alert">
							<spring:message code="message.premium.analyse"/> 
							<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
						</p>
					</div>
				</c:otherwise>
			</c:choose>
			<hr class="my-1">
			<div class="card-analytic card-dashboard card-charts m-t-10">
				<c:set var="chosesMonth" scope="page">
					<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
				</c:set>
				<input type="hidden" id="legendsAccessing" value="${pageScope.chosesMonth}" />
				<h3 class="h-header h-header5 i-primary"><spring:message code="tool.dashboard.admin7.2"/></h3>
				<div class="row m-t-10">
					<div class="col-sm-6 col-lg-4 m-t-10">
						<div class="flexed flex-colone flex-jusitify h-100">
							<div>
								<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin7.2.1"/></h4>
								<div class="card-body">
									<canvas id="canvaAccessRefering" style="height:220px;max-width:100%;"></canvas>
									<input type="hidden" id="referingAccessValue" value="${refering.countSearchToString()}" />
								</div>
							</div>
							<div class="card-footer m-t-20">
								<div class="card-refering card-left" style="padding:0;">
									<i class="cmsms-icon-flashlight card-trigger pull-left"></i>
									<div class="card-brand text-right">
										<span class="text-truncate font-mini i-help"><spring:message code="tool.order.total" /></span>
										<p class="h-header i-primary"><c:out value="${refering.getFormattedSumSearch()}"/></p>
									</div>
									<span class="clearfix"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-4 m-t-10">
						<div class="flexed flex-colone flex-jusitify h-100">
							<div>
								<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin7.2.2"/></h4>
								<div class="card-body">
									<canvas id="canvaAccessVisit" style="height:220px;max-width:100%;"></canvas>
									<input type="hidden" id="visitAccessValue" value="${accessing.countProToString()}" />
								</div>
							</div>
							<div class="card-footer m-t-20">
								<div class="card-refering card-left" style="padding:0;">
									<i class="cmsms-icon-eye-2 card-trigger pull-left"></i>
									<div class="card-brand text-right">
										<span class="text-truncate font-mini i-help"><spring:message code="tool.order.total" /></span>
										<p class="h-header i-primary"><c:out value="${accessing.getFormattedSumPro()}"/></p>
									</div>
									<span class="clearfix"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-4 m-t-10">
						<div class="flexed flex-colone flex-jusitify h-100">
							<div>
								<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin7.2.3"/></h4>
								<div class="card-body">
									<canvas id="canvaAccessFavorite" style="height:220px;max-width:100%;"></canvas>
									<input type="hidden" id="favoriteAccessValue" value="${accessing.countIndividualyToString()}" />
								</div>
							</div>
							<div class="card-footer m-t-20">
								<div class="card-refering card-left" style="padding:0;">
									<i class="cmsms-icon-star-5 card-trigger pull-left"></i>
									<div class="card-brand text-right">
										<span class="text-truncate font-mini i-help"><spring:message code="tool.order.total" /></span>
										<p class="h-header i-primary"><c:out value="${accessing.getFormattedSumIndividualy()}"/></p>
									</div>
									<span class="clearfix"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning m-t-10">
				<i class="cmsms-icon-dollar i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.analytic"/> 
					<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.analytic1"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<c:choose>
				<c:when test="${currentCompany.premium > 1}">
					<div id="panelLoad" class="panel-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</c:when>
				<c:otherwise>
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.detect1.1"/></h3>
					<p class="font-small m-t-10"><spring:message code="txt.company.detect1.1"/></p>
					<ul class="font-small m-t-10">
						<c:forEach var="i" begin="1" end="3" step="1">
							<li><spring:message code="txt.company.analytic1.1.${i}"/></li>
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