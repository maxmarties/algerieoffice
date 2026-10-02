<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/marketplace"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li class="active"><spring:message code="wizard.screen.navbar5"/></li>
	</ol>
</div>
<div class="screen-container screen-mini">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="wizard.screen.navbar5"/></h1></div>
		<p class="parag-blog header-actu text-center m-auto"><spring:message code="txt.search.news1"/></p>
	</div>
</div>
<div class="screen-navnews">
	<div class="table-overlay"></div>
	<div class="container">
		<form:form name="searchNewsForm" action="/" method="POST" modelAttribute="searchNews" enctype="utf8" novalidate="novalidate">
			<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
			<spring:bind path="row"><form:input type="hidden" path="row" /></spring:bind>
			<spring:bind path="page"><form:input type="hidden" path="page" /></spring:bind>
			<div class="row row-mini">
				<div class="col-md-6 col-lg-3 col-mini">
					<ul class="navbar-nav nav-screenfilter nav-sectornews">
						<li class="dropdown">
							<a class="transition-35 text-truncate" data-toggle="dropdown" title="<spring:message code="tool.filter.sector"/>">
								<i class="breadview-trigger cmsms-icon-down-open"></i>
								<i class="cmsms-icon-filter i-20 m-r-20"></i><span id="resultFilterSector"><spring:message code="tool.filter.sector"/></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li class="dropdown-content" data-toggle="buttons">
									<spring:bind path="sector">
										<ul class="list-none list-block">
											<li>
												<label class="btn btn-simple item-dropdown text-truncate active" title="<spring:message code="chose.sector.all"/>">
													<i class="cmsms-icon-cancel-2 m-r-20"></i><spring:message code="chose.sector.all"/>
													<form:radiobutton class="hidden" value="0" path="sector" checked="true" />
												</label>
											</li>
											<c:forEach var="i" begin="1" end="10" step="1">
												<c:set var="sectorName" scope="page"><spring:message code="chose.sector${i}" /></c:set>
												<li>
													<label class="btn btn-simple item-dropdown text-truncate" title="${pageScope.sectorName}">
														<c:out value="${pageScope.sectorName}"/>
														<form:radiobutton class="hidden" value="${i}" path="sector" data-name="${pageScope.sectorName}" />
													</label>
												</li>
											</c:forEach>
										</ul>
										<ul class="list-none list-block">
											<c:forEach var="i" begin="11" end="21" step="1">
												<c:set var="sector" scope="page"><spring:message code="chose.sector${i}" /></c:set>
												<li>
													<label class="btn btn-simple item-dropdown text-truncate" title="${pageScope.sector}">
														<c:out value="${pageScope.sector}"/>
														<form:radiobutton class="hidden" value="${i}" path="sector" data-name="${pageScope.sector}" />
													</label>
												</li>
											</c:forEach>
										</ul>
										<ul class="list-none list-block">
											<c:forEach var="i" begin="22" end="31" step="1">
												<c:set var="sector" scope="page"><spring:message code="chose.sector${i}" /></c:set>
												<li>
													<label class="btn btn-simple item-dropdown text-truncate" title="${pageScope.sector}">
														<c:out value="${pageScope.sector}"/>
														<form:radiobutton class="hidden" value="${i}" path="sector" data-name="${pageScope.sector}" />
													</label>
												</li>
											</c:forEach>
										</ul>
									</spring:bind>
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
							<c:set var="placeholderFind" scope="page"><spring:message code="tool.find.news"/></c:set>
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
		<div class="row row-mini">
			<div class="col-lg-8 col-mini">
				<div id="screenElements">
					<div class="screen-loader screen-load-news">
						<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
						<div class="screenLoad"></div>
					</div>
					<c:set var="placeholderViewload" scope="request"><spring:message code="tool.navigate.news" arguments="${searchNews.row}"/></c:set>
					<c:import url="/WEB-INF/fields/screen/screen_viewload.jsp"/>
				</div>
			</div>
			<div class="col-lg-4 col-mini hidden-md-down">
				<div class="screen-column m-b-10">
					<div class="screen-title"><h3 class="h-doc h-doc3"><spring:message code="explorer.subheader.promote"/> ...</h3></div>
					<div id="iScreenPrm" class="widget-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="screen-column m-b-10">
					<div class="screen-title"><h3 class="h-doc h-doc3"><spring:message code="explorer.subheader.blog"/></h3></div>
					<div id="iScreenBlog" class="widget-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
				<div class="screen-sticky"><c:import url="/WEB-INF/fields/screen/screen_newsletter.jsp"/></div>
			</div>
		</div>
	</div>
</div>
<div id="iExplorerSkills"></div>