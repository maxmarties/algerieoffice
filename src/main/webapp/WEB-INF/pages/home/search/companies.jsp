<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/entreprises"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li class="active"><spring:message code="explorer.home.mainmenu2.3"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen"><h1 class="h-header m-auto"><spring:message code="subheader.screen.search1"/></h1></div>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.tradename"/></c:set>
		<c:set var="placeholderResult" scope="request"><spring:message code="tool.result.screen"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.search1.1"/></h2>
		<p class="m-t-5"><spring:message code="txt.search.company1"/></p>
		<hr class="my-4">
		<div class="row">
			<div class="col-lg-3 m-t-10 m-b-10">
				<form:form name="searchCompanyForm" action="/" method="POST" modelAttribute="searchCompany" enctype="utf8" novalidate="novalidate">
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
						<div class="sidebar-body animated slideInY">
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
										<li class="help-text"><spring:message code="txt.help.filter1.2" /></li>
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
										<spring:message code="sidebar.company.dashboard6.4"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.3" /></li>
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
								<li id="treeviewPostal" class="treeview">
									<a class="lien">
										<spring:message code="lbl.postal"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.4" /></li>
										<li class="form-sidebar">
											<spring:bind path="equalPostal">
												<div class="form-group m-b-5">
													<form:select class="form-select2-simple" path="equalPostal">
														<option value="${true}"><spring:message code="lbl.sub.search1.1" /></option>
														<option value="${false}"><spring:message code="lbl.sub.search1.2" /></option>
													</form:select>
												</div>
											</spring:bind>
											<spring:bind path="postal">
												<div id="postalForm" class="form-group m-b-0">
													<form:input class="form-control" type="text" path="postal" />
													<span class="error"></span>
												</div>
											</spring:bind>
										</li>
									</ul>
								</li>
								<li id="treeviewDate" class="treeview">
									<a class="lien">
										<spring:message code="tabs.buildate"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.5" /></li>
										<li class="form-sidebar">
											<spring:bind path="indexDate">
												<div class="form-group m-b-5">
													<form:select class="form-select2-simple" path="indexDate">
														<c:forEach var="i" begin="1" end="3">
															<option value="${i}"><spring:message code="lbl.sub.search1.3.${i}" /></option>
														</c:forEach>
													</form:select>
												</div>
											</spring:bind>
											<spring:bind path="dateBegin">
												<div id="dateBeginForm" class="form-group m-b-0">
													<div class="input-group-icon date">
														<form:input class="form-control" type="text" path="dateBegin" />
														<span class="input-group-addon" style="display:none;"></span>
													</div>
													<span class="error"></span>
												</div>
											</spring:bind>
											<spring:bind path="dateEnd">
												<div id="dateEndForm" class="form-group m-t-5 m-b-0" style="display:none;">
													<div class="input-group-icon date">
														<form:input class="form-control" type="text" path="dateEnd" />
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
										<spring:message code="lbl.sub.search2"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.6" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="4">
														<c:set var="type" scope="page"><spring:message code="overview.briefcase3.${i}" /></c:set>
														<spring:bind path="types[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="types${i}" path="types[${i - 1}]" data-name="${pageScope.type}"/>
																	<span class="input-span"></span><spring:message code="chose.briefcase3.${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewBriefcases" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.briefcase1"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.7" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="10">
														<c:set var="briefcase" scope="page"><spring:message code="overview.briefcase${i}" /></c:set>
														<spring:bind path="briefcases[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="briefcases${i}" path="briefcases[${i - 1}]" data-name="${pageScope.briefcase}"/>
																	<span class="input-span"></span><spring:message code="overview.briefcase1.${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewWarehouse" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search3"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.8" /></li>
										<li class="form-sidebar">
											<spring:bind path="warehouse">
												<c:forEach var="i" begin="1" end="3" step="1">
													<label class="ui-radio ui-radio-segond font-small">
														<form:radiobutton value="${i}" id="warehouse${i}" path="warehouse" />
														<span class="input-span"></span><spring:message code="overview.briefcase2.${i}" />
													</label><br>
												</c:forEach>
											</spring:bind>
										</li>
									</ul>
								</li>
								<li id="treeviewCapital" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.briefcase3"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.9" /></li>
										<li class="form-sidebar">
											<spring:bind path="indexCapital">
												<div class="form-group m-b-5">
													<form:select class="form-select2-simple" path="indexCapital">
														<c:forEach var="i" begin="1" end="3">
															<option value="${i}"><spring:message code="lbl.sub.search1.4.${i}" /></option>
														</c:forEach>
													</form:select>
												</div>
											</spring:bind>
											<div id="capitalForm" class="form-group m-b-0">
												<spring:bind path="capital">
													<form:input class="form-control" type="text" path="capital" />
													<span class="error"></span>
												</spring:bind>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewCredits" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.credit1"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.10" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="5">
														<c:set var="credit" scope="page"><spring:message code="overview.credit${i}" /></c:set>
														<spring:bind path="credits[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="credits${i}" path="credits[${i - 1}]" data-name="${pageScope.credit}"/>
																	<span class="input-span"></span><spring:message code="overview.credit${i}" />
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
										<spring:message code="lbl.sub.search5"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.12" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="3">
														<c:set var="digital" scope="page"><spring:message code="lbl.sub.search5.${i}" /></c:set>
														<spring:bind path="digitals[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="digitals${i}" path="digitals[${i - 1}]" data-name="${pageScope.digital}"/>
																	<span class="input-span"></span><spring:message code="lbl.sub.search5.${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewContacts" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search4"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.11" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:forEach var="i" begin="1" end="4">
														<c:set var="contact" scope="page"><spring:message code="lbl.sub.search4.${i}" /></c:set>
														<spring:bind path="contacts[${i - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="contacts${i}" path="contacts[${i - 1}]" data-name="${pageScope.contact}"/>
																	<span class="input-span"></span><spring:message code="lbl.sub.search4.${i}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
										</li>
									</ul>
								</li>
								<li id="treeviewLanguages" class="treeview">
									<a class="lien">
										<spring:message code="lbl.sub.search6"/><i class="breadview-trigger cmsms-icon-angle-down"></i>
									</a>
									<ul class="treeview-menu">
										<li class="help-text"><spring:message code="txt.help.filter1.13" /></li>
										<li class="form-sidebar">
											<div class="breadview-form">
												<ul class="list-none">
													<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
													<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
														<c:set var="language" scope="page"><c:out value="${pageScope.provider}" /></c:set>
														<spring:bind path="languages[${state.count - 1}]">
															<li>
																<label class="ui-checkbox ui-checkbox-segond font-small">
													            	<form:checkbox id="languages${state.count}" path="languages[${state.count - 1}]" 
													            		data-name="${pageScope.language}"/>
																	<span class="input-span"></span><c:out value="${pageScope.provider}" />
																</label>
															</li>
														</spring:bind>
													</c:forEach>
												</ul>
											</div>
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
				<div class="screen-sticky hidden-md-down">
					<div class="screen-column m-t-10">
						<div id="iExplorerSpn" class="explorer-spn"><p class="font-small i-help"><spring:message code="txt.help.explorer4.2"/></p></div>
					</div>
					<c:import url="/WEB-INF/fields/screen/screen_topics.jsp"/>
				</div>
			</div>
			<div class="col-lg-9 m-t-10 m-b-10">
				<div id="screenFilter" class="screen-filter">
					<p>
						<spring:message code="lbl.sub.filter1.1"/> : 
						<span id="headerScreenResult" class="i-segond" style="display:none;">
							<span id="countFormatted" class="h-header font-strong"></span> <spring:message code="lbl.sub.filter1.2"/>
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
							<c:set var="choseSortersScreen" value="date,actu,view,company" scope="request"></c:set>
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
		<div class="explorer-auth-hr m-t-20 m-b-20"><span class="line"></span></div>
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.search1.2"/></h2>
		<div id="analyticLoader" class="screen-loader m-t-30"><c:import url="/WEB-INF/fields/tables/table_loading.jsp"/></div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_carrousel.jsp"/>