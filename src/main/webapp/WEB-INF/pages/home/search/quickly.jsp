<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/entreprises"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li class="active"><spring:message code="explorer.home.mainmenu2.4"/></li>
	</ol>
</div>
<div class="screen-container screen-mini" style="${!empty sponsoreScreen ? 'padding:0px!important;' : ''}">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="explorer.home.mainmenu2.4"/></h1></div>
		<p class="parag-blog header-actu text-center m-auto"><spring:message code="seo.search.quickly"/></p>
	</div>
</div>
<c:if test="${!empty sponsoreScreen}">
	<div class="screen-container screen-aobubs" style="padding-bottom:20px!important;"><div class="container"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></div></div>
</c:if>
<div class="screen-navnews">
	<div class="table-overlay"></div>
	<div class="container">
		<form:form name="searchQuicklyForm" action="/" method="POST" modelAttribute="searchQuickly" enctype="utf8" novalidate="novalidate">
			<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
			<spring:bind path="row"><form:input type="hidden" path="row" /></spring:bind>
			<spring:bind path="sort"><form:input type="hidden" path="sort" /></spring:bind>
			<spring:bind path="page"><form:input type="hidden" path="page" /></spring:bind>
			<spring:bind path="desc"><form:input type="hidden" path="desc" /></spring:bind>
			<spring:bind path="filtred"><form:input type="hidden" path="filtred" /></spring:bind>
			<div class="row row-mini">
				<div class="col-md-6 col-lg-3 col-mini">
					<ul class="navbar-nav nav-screenfilter nav-quickly">
						<li class="dropdown">
							<a class="transition-35 text-truncate" data-toggle="dropdown" title="<spring:message code="tool.filter.quickly"/>">
								<i class="breadview-trigger cmsms-icon-down-open"></i>
								<i class="cmsms-icon-filter i-20 m-r-20"></i><span id="resultFilterCategory"><spring:message code="tool.filter.quickly"/></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li class="dropdown-content flexed">
									<spring:bind path="category">
										<ul class="list-none list-block list-quickcat" data-toggle="buttons">
											<li>
												<label class="btn btn-simple item-dropdown text-truncate active" title="<spring:message code="explorer.desktop.element9.1"/>">
													<i class="cmsms-icon-list i-22"></i><span class="hidden-sm-down"><spring:message code="explorer.desktop.element9.1"/></span>
													<form:radiobutton class="hidden" value="0" id="category0" path="category" checked="true" />
												</label>
											</li>
											<c:set var="providers" value="food,home-1,medkit,basket-1,bus,flight,extinguisher,hammer,ellipsis" scope="page"></c:set>
											<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
												<c:set var="categoryName" scope="page"><spring:message code="chose.family.btoc${state.count}" /></c:set>
												<li class="dropdown-cleanup">
													<label class="btn btn-simple item-dropdown text-truncate" title="${pageScope.categoryName}">
														<i class="cmsms-icon-${pageScope.provider} i-22"></i>
														<span class="hidden-sm-down"><c:out value="${pageScope.categoryName}"/></span>
														<form:radiobutton class="hidden" value="${state.count}" id="category${state.count}" path="category" data-name="${pageScope.categoryName}"/>
														<i class="treeview-trigger cmsms-icon-explorer-right transition-35"></i>
													</label>
												</li>
											</c:forEach>
										</ul>
									</spring:bind>
									<div class="quickact-content">
										<div id="quickact0">
											<p><i class="cmsms-icon-list i-22"></i><spring:message code="explorer.desktop.element9.1"/></p>
											<span class="help-text m-t-5"><spring:message code="txt.help.filter2.1"/></span>
											<hr class="my-1">
											<spring:bind path="family">
												<div class="row row-mini m-b-10" data-toggle="buttons">
													<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
														<c:set var="familyName" scope="page"><spring:message code="chose.family.btoc${state.count}" /></c:set>
														<div class="col-6 col-md-4 col-lg-3 col-mini m-t-10">
															<div class="btn btn-simple btn-block card-quickly background-container m-auto"
																style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/vectors/widget/m_widget${state.count}-min.jpg"/>');">
																<i class="cmsms-icon-${pageScope.provider} i-24 i-white"></i>
																<p class="font-mini i-gray m-t-10"><c:out value="${pageScope.familyName}"/></p>
																<form:radiobutton class="hidden" value="${state.count}" path="family" data-name="${pageScope.familyName}"/>
															</div>
														</div>
													</c:forEach>
													<div class="col-lg-9 col-mini m-t-10">
														<p class="font-small i-help quickfamily-help">
															<spring:message code="txt.help.filter2.2"/>
															<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="txt.help.filter2.3" />"></i>
														</p>
													</div>
												</div>
											</spring:bind>
										</div>
										<spring:bind path="activity">
											<div data-toggle="buttons">
												<c:set var="limits" value="7,7,14,18,10,9,8,8,6" scope="page"></c:set>
												<c:forEach var="limit" items="${pageScope.limits}" varStatus="state">
													<ul id="quickact${state.count}" class="nav list-quickact animated fadeIn" style="display:none;">
														<li class="subitem-header">
															<c:set var="activityName" scope="page"><spring:message code="chose.family.btoc${state.count}" /></c:set>
															<label class="btn btn-simple subitem-dropdown">
																<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} i-22"></i>
																<c:out value="${pageScope.activityName}"/>
																<form:radiobutton class="hidden" value="0" id="activity0_${state.count}" path="activity" data-name="${pageScope.activityName}" checked="${state.count == 1}" />
															</label>
														</li>
														<c:forEach var="i" begin="1" end="${pageScope.limit}">
															<c:set var="activityName" scope="page"><spring:message code="chose.family.btoc${state.count}.${i}" /></c:set>
															<li>
																<label class="btn btn-simple subitem-dropdown">
																	<c:out value="${pageScope.activityName}"/>
																	<form:radiobutton class="hidden" value="${i}" id="activity${state.count}_${i}" path="activity" data-name="${pageScope.activityName}"/>
																</label>
															</li>
														</c:forEach>
													</ul>
												</c:forEach>
											</div>
										</spring:bind>
									</div>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="col-md-6 col-lg-3 col-mini">
					<spring:bind path="wilaya">
						<div class="form-group form-filter-ville m-b-0">
							<c:set var="placeholderFilter" scope="page"><spring:message code="tool.filter.wilaya"/></c:set>
							<div class="input-group-ville">
								<i class="cmsms-icon-location i-red trigger-location"></i>
								<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.placeholderFilter}" >
									<option></option>
									<option value="${0}"><spring:message code="comp.target" /></option>
									<c:forEach var="i" begin="1" end="48" step="1">
										<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="col-lg-6 col-mini">
					<spring:bind path="token">
						<div class="form-group form-find-news m-b-0">
							<c:set var="placeholderFind" scope="page"><spring:message code="tool.find.tradename"/></c:set>
							<div class="input-group-screen">
								<i class="cmsms-icon-search-1 icon-screen"></i>
								<form:input class="form-control" type="search" path="token" placeholder="${pageScope.placeholderFind}" />
								<button type="submit" class="btn btn-primary btn-simple" title="<spring:message code="btn.find" />"><span><spring:message code="btn.ok"/></span></button>
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
		</form:form>
	</div>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 id="headerScreenFilter" class="h-header h-header3 i-primary"><spring:message code="explorer.desktop.element9.1"/></h2>
		<p class="m-t-5"><span id="countFormatted" class="h-header font-strong m-r-5">...</span><spring:message code="explorer.desktop.element9.2"/></p>
		<hr class="my-4 m-b-0">
		<div id="screenElements">
			<div class="row">
				<div class="col-md-4">
					<ul class="nav m-t-20">
						<li class="m-r-10"><spring:message code="txt.help.filter2.4" /></li>
						<li style="padding-top:5px;"><label class="ui-switch ui-switch-action">
							<input type="checkbox" id="filtredOnline" name="filtredOnline"><span class="input-span"></span><span class="layer-span"></span>
						</label></li>
					</ul>
				</div>
				<div class="col-md-8">
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
<c:import url="/WEB-INF/fields/blog/blog_carrousel.jsp"/>