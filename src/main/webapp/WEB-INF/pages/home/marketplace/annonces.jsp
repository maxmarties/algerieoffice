<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/marketplace"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li class="active"><spring:message code="wizard.screen.navbar2"/></li>
	</ol>
</div>
<div class="screen-container ${!empty sponsoreScreen ? 'screen-aobubs' : ''}">
	<div class="container">
		<c:if test="${!empty sponsoreScreen}"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></c:if>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.annonce"/></c:set>
		<c:set var="placeholderResult" scope="request"><spring:message code="tool.result.screen2"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<c:set var="treeviewNav" value="2" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_marketplace.jsp"/>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.marketplace2"/></h2>
		<p class="m-t-5"><spring:message code="txt.search.annonce1"/></p>
		<hr class="my-4">
		<div class="row">
			<div class="col-lg-3 m-t-10 m-b-10">
				<form:form name="searchAnnonceForm" action="/" method="POST" modelAttribute="searchAnnonce" enctype="utf8" novalidate="novalidate">
					<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
					<spring:bind path="token"><form:input type="hidden" path="token" /></spring:bind>
					<spring:bind path="row"><form:input type="hidden" path="row" /></spring:bind>
					<spring:bind path="sort"><form:input type="hidden" path="sort" /></spring:bind>
					<spring:bind path="page"><form:input type="hidden" path="page" /></spring:bind>
					<spring:bind path="desc"><form:input type="hidden" path="desc" /></spring:bind>
					<spring:bind path="easylist"><form:input type="hidden" path="easylist" /></spring:bind>
					<div class="screen-sidebar">
						<a class="sidebar-header bn-primary">
							<i class="cmsms-icon-filter m-r-10"></i><spring:message code="lbl.sub.search1"/>
							<i class="breadview-trigger cmsms-icon-angle-down i-segond"></i>
						</a>
						<div class="sidebar-body">
							<spring:bind path="keysword">
								<div id="keyswordForm" class="form-group form-sidebar">
									<form:label class="col-form-label" path="keysword"><spring:message code="tabs.keys" /></form:label>
									<form:input class="form-control" type="text" path="keysword" />
									<span class="error"></span>
									<span class="help-text"><spring:message code="txt.help.filter1.1" /></span>
								</div>
							</spring:bind>
							<ul class="screen-mainsidebar list-none" data-widget="tree">
								<li id="treeviewSectors" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.annonce1"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.1" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="31">
														<c:set var="sector" scope="page"><spring:message code="chose.sector${i}" /></c:set>
														<spring:bind path="sectors[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="sectors${i}" path="sectors[${i - 1}]" data-name="${pageScope.sector}"/>
																	<span class="input-span"></span><spring:message code="chose.sector${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewWilayas" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search7"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.2" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="48">
														<c:set var="wilaya" scope="page"><spring:message code="chose.wilaya${i}" /></c:set>
														<spring:bind path="wilayas[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="wilayas${i}" path="wilayas[${i - 1}]" data-name="${pageScope.wilaya}"/>
																	<span class="input-span"></span>
																	<c:out value="${i < 10 ? '0' : ''}${i}" /> - <spring:message code="chose.wilaya${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>		
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewDateOpen" class="treeview">
									<a class="lien">
										<spring:message code="tabs.explorer.dateOn"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.3" /></li>
										<li class="form-sidebar">
											<spring:bind path="dateOpenBegin">
												<div id="dateOpenBeginForm" class="form-group m-b-0">
													<div class="input-group-icon date">
														<c:set var="faholder" scope="page"><spring:message code="lbl.date.for" /></c:set>
														<form:input class="form-control" type="text" path="dateOpenBegin" placeholder="${pageScope.faholder}" />
														<span class="input-group-addon" style="display:none;"></span>
													</div>
													<span class="error"></span>
												</div>
											</spring:bind>
											<spring:bind path="dateOpenEnd">
												<div id="dateOpenEndForm" class="form-group m-t-5 m-b-0">
													<div class="input-group-icon date">
														<c:set var="faholder" scope="page"><spring:message code="lbl.date.to" /></c:set>
														<form:input class="form-control" type="text" path="dateOpenEnd" placeholder="${pageScope.faholder}" />
														<span class="input-group-addon" style="display:none;"></span>
													</div>
													<span class="error"></span>
												</div>
											</spring:bind>
										</li>
									</ul>
								</li>
								<li id="treeviewDateClose" class="treeview">
									<a class="lien">
										<spring:message code="tabs.explorer.dateOff"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.4" /></li>
										<li class="form-sidebar">
											<spring:bind path="dateCloseBegin">
												<div id="dateCloseBeginForm" class="form-group m-b-0">
													<div class="input-group-icon date">
														<c:set var="faholder" scope="page"><spring:message code="lbl.date.for" /></c:set>
														<form:input class="form-control" type="text" path="dateCloseBegin" placeholder="${pageScope.faholder}" />
														<span class="input-group-addon" style="display:none;"></span>
													</div>
													<span class="error"></span>
												</div>
											</spring:bind>
											<spring:bind path="dateCloseEnd">
												<div id="dateCloseEndForm" class="form-group m-t-5 m-b-0">
													<div class="input-group-icon date">
														<c:set var="faholder" scope="page"><spring:message code="lbl.date.to" /></c:set>
														<form:input class="form-control" type="text" path="dateCloseEnd" placeholder="${pageScope.faholder}" />
														<span class="input-group-addon" style="display:none;"></span>
													</div>
													<span class="error"></span>
												</div>
											</spring:bind>
										</li>
									</ul>
								</li>
								<li id="treeviewTypes" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search8"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.5" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="5">
														<c:set var="type" scope="page"><spring:message code="chose.annonce${i}" /></c:set>
														<spring:bind path="types[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="types${i}" path="types[${i - 1}]" data-name="${pageScope.type}"/>
																	<span class="input-span"></span><spring:message code="chose.annonce${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewVisibilities" class="treeview">
									<a class="lien">
										<spring:message code="lbl.visibility"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.6" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="5">
														<c:set var="visibility" scope="page"><spring:message code="overview.visibility${i}" /></c:set>
														<spring:bind path="visibilities[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="visibilities${i}" path="visibilities[${i - 1}]" 
													            		data-name="${pageScope.visibility}"/>
																	<span class="input-span"></span><spring:message code="overview.visibility${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewDigitals" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search9"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.7" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="2">
														<c:set var="digital" scope="page"><spring:message code="overview.digital${i}" /></c:set>
														<spring:bind path="digitals[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="digitals${i}" path="digitals[${i - 1}]" data-name="${pageScope.digital}"/>
																	<span class="input-span"></span><spring:message code="overview.digital${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewState" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search10"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.search2.8" /></li>
										<li class="form-sidebar">
											<spring:bind path="state">
												<c:forEach var="i" begin="1" end="3" step="1">
													<label class="ui-radio ui-radio-segond font-small">
														<form:radiobutton value="${i}" id="state${i}" path="state" />
														<span class="input-span"></span><spring:message code="overview.state${i}" />
													</label><br>
												</c:forEach>
											</spring:bind>
										</li>
									</ul>
								</li>
								<li class="form-sidebar">
									<div id="submitForm" class="form-submit form-block">
										<button type="submit" class="btn btn-primary btn-submit btn-block">
					            			<span><spring:message code="btn.filter"/></span></button>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</form:form>
				<c:set var="treeviewAlert" value="2" scope="request"></c:set>
				<c:import url="/WEB-INF/fields/screen/screen_alert.jsp"/>
			</div>
			<div class="col-lg-9 m-t-10 m-b-10">
				<div id="screenFilter" class="screen-filter">
					<p>
						<spring:message code="lbl.sub.filter2.1"/> : 
						<span id="headerScreenResult" class="i-segond" style="display:none;">
							<span id="countFormatted" class="h-header font-strong"></span> <spring:message code="lbl.sub.filter2.2"/>
						</span>
						<a id="clearScreenFilter" class="lien lien-hover lien-primary font-small pull-right" style="display:none;">
							<spring:message code="btn.clear.filtring"/>
						</a>
						<span class="clearfix"></span>
					</p>
					<div id="filterResult" class="result-filter m-t-10" style="display:none;">
						<p class="font-bold"><spring:message code="lbl.sub.filter1.3"/> :</p>
					</div>
				</div>
				<div id="screenElements">
					<div class="row row-mini">
						<div class="col-md-4 col-mini"><c:import url="/WEB-INF/fields/screen/screen_easylist.jsp"/></div>
						<div class="col-md-8 col-mini">
							<c:set var="choseSortersScreen" value="dateOn,dateOff,type" scope="request"></c:set>
							<c:import url="/WEB-INF/fields/screen/screen_sort.jsp"/>
						</div>
					</div>
					<div class="screen-loader m-t-20">
						<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
						<div id="screenLoad"></div>
					</div>
					<c:import url="/WEB-INF/fields/screen/screen_pagination.jsp"/>
				</div>
			</div>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/screen/screen_skills.jsp"/>