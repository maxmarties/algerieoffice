<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-slider slider-companies background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/aomrkt-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay m-auto">
			<h1 class="h-header h-header1 text-center"><spring:message code="subheader.screen.solution3"/></h1>
			<p class="font-big text-center i-white m-t-10"><spring:message code="txt.solution.marketplace1.1"/></p>
			<div class="slider-content m-t-20">	
				<form name="searchScreenForm" action="/" novalidate="novalidate">
					<div class="row row-mini">
						<div class="form-group col-md-6 col-lg-5 col-mini">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.marketplace"/></c:set>
							<span class="indSearch i-white"><spring:message code="tool.view.market" /></span>
							<input class="form-control" type="search" id="findtoken" name="findtoken" placeholder="${pageScope.placeholderFind}"/>
						</div>
						<div class="form-group col-md-6 col-lg-4 col-mini">
							<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.location"/></c:set>
							<span class="indSearch i-white"><spring:message code="tool.view.location" /></span>
							<select class="form-select2" id="findlocation" name="findlocation" data-placeholder="${pageScope.placeholderFind}" >
								<option></option>
								<option value="0"><spring:message code="comp.target" /></option>
								<c:forEach var="i" begin="1" end="48" step="1">
									<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group form-button col-md-12 col-lg-3 col-mini">
							<nav class="navbar">
								<ul class="nav">
									<li class="dropdown brand-menu brand-fixed">
										<a class="btn btn-segond btn-flat btn-big btn-block" data-toggle="dropdown">
											<span><i class="cmsms-icon-search-1 m-r-10"></i><spring:message code="tool.view.marketin" /><i class="cmsms-icon-down-dir i-11 m-l-20"></i></span>
										</a>
										<ul class="dropdown-menu animated slideInY" role="menu">
											<c:forEach var="i" begin="1" end="5" step="1">
												<li><a class="dropdown-item dropdown-findin" data-findin="${i}"><spring:message code="tool.dashboard.data${i}"/></a></li>
												<c:if test="${i == 4}"><li class="dropdown-divider"></li></c:if>
											</c:forEach>
										</ul>
									</li>
								</ul>
							</nav>
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
			<li class="active"><spring:message code="sidebar.admin.dashboard4"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect header-min"><h2 class="h-header m-auto"><spring:message code="txt.solution.marketplace1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.marketplace1.3"/></p>
		<div class="row row-mini m-t-20">
			<c:set var="providers" value="bag,pin-1,calendar-7,coffee" scope="page"></c:set>
			<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi" scope="page"></c:set>
			<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
				<div class="col-md-6 col-lg-3 col-mini m-t-20">
					<div class="card-marketplace flexed flex-colone flex-jusitify h-100">
						<div class="card-visibility widget-flexed">
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger pull-left"></i>
							<div class="brand-visibility">
								<h3 class="h-header h-header4 i-primary"><spring:message code="wizard.screen.navbar${state.count}"/></h3>
								<p class="font-small i-help m-t-20"><spring:message code="txt.solution.marketplace1.3.${state.count}.1"/></p>
							</div>
							<span class="clearfix"></span>
						</div>
						<div class="card-thumbnail">
							<a href="<c:url value="/marketplace/${pageScope.lien}"/>" class="background-container" 
								style="background-image: url('<c:url value="/static/vectors/tours/m_tool${state.count}-min.jpg" />');">
								<span class="inner-link flexed">
									<span class="h-header text-center sh-black m-auto transition-35"><spring:message code="txt.solution.marketplace1.3.${state.count}.2"/></span>
								</span>
								<span class="inner-overlay"></span>
							</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.marketplace2.1"/></h2>
		<div class="card-alert m-t-10">
			<div class="row">
				<div class="col-md-6 m-t-20">
					<div class="card-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/display/m_aoler1-min.png"/>"
						alt="<spring:message code="txt.solution.marketplace2.1.1"/>"></div>
				</div>
				<div class="col-md-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.marketplace2.1.1"/></h3>
						<p class="font-big i-help m-t-20 m-l-20"><spring:message code="txt.solution.marketplace2.1.2"/></p>
						<div class="m-t-30">
							<a href="<c:url value="/user/alerts/posts/new" />" class="btn btn-segond btn-big m-l-20" style="min-width:200px;">
								<span><spring:message code="header.alert.new"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-40 m-b-20">
		<div class="card-alert">
			<div class="row">
				<div class="col-md-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.marketplace2.2.1"/></h3>
						<p class="font-big i-help m-t-20 m-l-20"><spring:message code="txt.solution.marketplace2.2.2"/></p>
						<div class="m-t-30">
							<a href="<c:url value="/marketplace/actualites" />" class="btn btn-segond btn-big m-l-20" style="min-width:200px;">
								<span><spring:message code="wizard.screen.navbar5"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
						</div>
					</div>
				</div>
				<div class="col-md-6 m-t-20">
					<div class="card-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/display/m_aoler2-min.png"/>"
						alt="<spring:message code="txt.solution.marketplace2.2.1"/>"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="iExplorerSkills"></div>