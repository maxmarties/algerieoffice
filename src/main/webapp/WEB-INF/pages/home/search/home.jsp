<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-slider slider-companies background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.7) 100%), url('<c:url value="/static/picts/images/companies-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay m-auto">
			<h1 class="h-header h-header1 text-center"><spring:message code="subheader.screen.solution2"/></h1>
			<p class="font-big text-center i-white m-t-10"><spring:message code="txt.solution.companies1.1"/></p>
			<div class="slider-content m-t-20">
				<form name="searchScreenForm" action="/" novalidate="novalidate">
					<div class="row row-mini">
						<div class="form-group col-md-6 col-lg-5 col-mini">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.token"/></c:set>
							<span class="indSearch i-white"><spring:message code="tool.view.token" /></span>
							<input class="form-control" type="search" id="findtoken" name="findtoken" placeholder="${pageScope.placeholderFind}"/>
						</div>
						<div class="form-group col-md-6 col-lg-4 col-mini">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.location"/></c:set>
							<span class="indSearch i-white"><spring:message code="tool.view.location" /></span>
							<select class="form-select2" id="findlocation" name="findlocation" data-placeholder="${pageScope.placeholderFind}">
								<option></option>
								<option value="0"><spring:message code="comp.target" /></option>
								<c:forEach var="i" begin="1" end="48" step="1">
									<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group form-button col-md-12 col-lg-3 col-mini">
							<button type="submit" class="btn btn-segond btn-flat btn-big btn-block">
								<span><i class="cmsms-icon-search-1 m-r-10"></i><spring:message code="btn.find" /></span>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="screen-breadcrumb">
	<div class="container">
		<ol class="bread-crumb bread-screen bread-mini">
			<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
			<li class="active"><spring:message code="sidebar.admin.dashboard3"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.companies1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.companies1.3"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="suitcase,location-6,cog-5" scope="page"></c:set>
			<c:set var="liens" value="secteurs,villes,recherche/entreprises" scope="page"></c:set>
			<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-visibility flexed flex-colone flex-jusitify h-100">
						<div>
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger pull-left"></i>
							<div class="brand-visibility">
								<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.companies1.3.${state.count}.1"/></h3>
								<p class="font-small i-help m-t-20"><spring:message code="txt.solution.companies1.3.${state.count}.2"/></p>
							</div>
							<span class="clearfix"></span>
						</div>
						<div class="brand-href m-t-10">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/${pageScope.lien}"/>" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="explorer.home.mainmenu2.${state.count}"/></a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.companies2"/></h2>
		<div class="card-vector m-t-20">
			<div class="row">
				<div class="col-lg-6 m-t-20"><div class="vector-map"><div id="vmapRegion" class="vmap"></div></div></div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body m-t-20">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.companies2.1"/></h3>
						<p class="m-t-20 m-l-20"><spring:message code="txt.solution.companies2.2"/> :</p>
						<ul class="list-none list-block i-help m-t-20 m-l-20">
							<c:forEach var="i" begin="1" end="7" step="1">
								<li class="m-b-5"><i class="cmsms-icon-ok-2 i-segond1 m-r-10"></i><spring:message code="txt.solution.companies2.2.${i}"/></li>
							</c:forEach>
						</ul>
						<div class="m-t-30">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/villes" />" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="txt.solution.companies2.3"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-40 m-b-20">
		<div class="card-activities">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.companies3.1"/></h3>
						<p class="m-t-20 m-l-20"><spring:message code="txt.solution.companies3.2"/></p>
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<form name="searchActivityForm" action="/" novalidate="novalidate">
							<ul class="navbar-nav nav-flex-icons">
								<li>
									<div id="findactivityForm" class="form-group">
										<c:set var="placeholderFind" scope="page"><spring:message code="txt.solution.companies3.2.1"/></c:set>
										<select class="form-select2" id="findactivity" name="findactivity" data-placeholder="${pageScope.placeholderFind}" >
											<option></option>
											<c:forEach var="activity" items="${activities}" varStatus="state">
												<option value="${activity.url}"><c:out value="${activity.code}"/> - <spring:message code="chose.activity.${activity.code}" /></option>
											</c:forEach>
										</select>
										<span class="error"></span>
									</div>
								</li>
								<li class="m-l-10">
									<button type="submit" class="btn btn-primary" title="<spring:message code="btn.find"/>">
										<span><i class="cmsms-icon-search-1"></i></span>
									</button>
								</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_marketplace.jsp"/>