<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div class="screen-slider slider-home background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.8) 50%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/images/home-min.jpg" />');">
	<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
	<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
	<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
	<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
	<div id="cardScreenHome">
		<div class="owl-carousel owl-theme">
			<div class="item">
				<div class="container">
					<div class="row">
						<div class="col-lg-6">
							<div class="flexed h-100">
								<div class="slider-overlay anime-home fadeInUp">
									<h1 class="h-header h-header1"><spring:message code="subheader.screen.solution1.1"/></h1>
									<div class="slider-content m-t-30">
										<p class="font-big i-white"><spring:message code="txt.solution.office1.1.1"/></p>
										<div class="form-group m-t-40 m-b-0">
											<a href="<c:url value="/solutions" />" class="btn btn-segond btn-flat btn-big" style="min-width:200px;">
												<span><spring:message code="txt.solution.office1.1.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6 hidden-md-down">
							<div class="inner-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aosil1-min.png"/>"
								alt="<spring:message code="app.name"/>"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="item">
				<div class="container">
					<div class="row">
						<div class="col-lg-6 hidden-md-down">
							<div class="inner-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aosil2-min.png"/>"
								alt="<spring:message code="app.name"/>"></div>
						</div>
						<div class="col-lg-6">
							<div class="flexed h-100">
								<div class="slider-overlay slider-overlay2 anime-home fadeInUp">
									<h1 class="h-header h-header1"><spring:message code="subheader.screen.solution1.2"/></h1>
									<div class="slider-content m-t-30">
										<p class="font-big i-white"><spring:message code="txt.solution.office1.2.1"/></p>
										<div class="form-group m-t-40 m-b-0">
											<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-flat btn-big" style="min-width:200px;">
												<span><spring:message code="txt.solution.office1.2.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.office2.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.office2.2"/></p>
		<div class="card-vector">
			<div class="row">
				<div class="col-lg-6 m-t-20"><div class="vector-map"><div id="vmapRegion" class="vmap"></div></div></div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body m-t-20">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.office2.2.1"/></h3>
						<p class="m-t-20 m-l-20"><spring:message code="txt.solution.office2.2.2"/></p>
						<div class="card-search m-t-30 m-l-20">
							<form name="searchScreenForm" action="/" novalidate="novalidate">
								<div class="form-group">
									<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.token"/></c:set>
									<p class="indSearch font-bold i-primary"><spring:message code="tool.view.token" /></p>
									<input class="form-control" type="search" id="findtoken" name="findtoken" placeholder="${pageScope.placeholderFind}"/>
								</div>
								<div class="form-group m-t-20">
									<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.location"/></c:set>
									<span class="indSearch font-bold i-primary"><spring:message code="tool.view.location" /></span>
									<ul class="navbar-nav nav-flex-icons">
										<li>
											<select class="form-select2" id="findlocation" name="findlocation" data-placeholder="${pageScope.placeholderFind}">
												<option></option>
												<option value="0"><spring:message code="comp.target" /></option>
												<c:forEach var="i" begin="1" end="48" step="1">
													<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
												</c:forEach>
											</select>
										</li>
										<li class="m-l-10">
											<button type="submit" class="btn btn-primary" title="<spring:message code="btn.find"/>">
												<span><i class="cmsms-icon-search-1"></i></span></button>
										</li>
									</ul>
								</div>
							</form>
						</div>
						<div class="m-t-30">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/recherche/entreprises" />" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="txt.solution.office2.2.3"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-20">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.office2.3"/></h2>
		<p class="parag-blog text-center m-auto"><spring:message code="txt.solution.office2.4"/></p>
		<div class="row m-t-10">
			<c:set var="providers" value="bag-alt,attach-3,calendar-3,cup-2" scope="page"></c:set>
			<c:set var="markets" value="produits-et-services,annonces,evenements,offres-emploi" scope="page"></c:set>
			<c:forEach var="market" items="${pageScope.markets}" varStatus="state">
				<div class="col-sm-6 col-lg-3 m-t-20">
					<div class="card-office" data-aos="fade-up" data-aos-delay="${state.count * 200}">
						<a href="<c:url value="/marketplace/${pageScope.market}"/>" class="inner-link"></a>
						<div class="card-back card-back${state.count} transition-5">
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger i-segond1"></i>
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.marketplace1.3.${state.count}.2"/></h3>
							<p class="font-big i-help"><spring:message code="txt.solution.office2.4.${state.count}"/></p>
						</div>
						<div class="card-thumbnail background-container flexed transition-5"
							style="background-image: radial-gradient(circle at center, rgba(23,129,67,.75) 0%, ${currentConfig.aocolor(44)} 100%), url('<c:url value="/static/vectors/tours/m_tool${state.count}-min.jpg" />');">
							<p class="h-header text-center sh-black m-auto transition-5">
								<spring:message code="subheader.screen.marketplace${state.count}"/><i class="cmsms-icon-explorer-arrow breadview-trigger"></i>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="form-group text-center m-t-40 m-b-0">
			<a href="<c:url value="/marketplace" />" class="btn btn-segond btn-big" style="min-width:220px;">
				<span><spring:message code="txt.solution.office2.5"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
		</div>
	</div>
</div>
<div class="bn-office">
	<div class="card-body">
		<div class="container">
			<h2 class="h-header h-header2 h-skills i-white sh-black m-auto"><spring:message code="txt.solution.office3.1"/></h2>
			<c:if test="${homeBlogs.size() >= 3}">
				<div id="cardHomeBlog" class="card-homeblog">
					<div class="owl-carousel owl-theme">
						<c:forEach var="homeBlog" items="${homeBlogs}" varStatus="state">
							<div class="item">
								<div class="card-content flexed flex-colone flex-jusitify">
									<div class="text-${homeBlog.language == 'ar' ? 'ar' : 'fr'}">
										<a href="<c:url value="${homeBlog.categoryURL}" />" class="lien lien-segond lien-hover lien-small">
											<spring:message code="chose.blog.family${homeBlog.category}"/></a>
										<a href="<c:url value="${homeBlog.identifyURL}" />" class="h-header lien lien-office"><c:out value="${homeBlog.title}" /></a>
										<p class="font-small i-help m-t-20"><c:out value="${homeBlog.description}" /></p>
									</div>
									<div class="widget-more m-t-10">
										<ul class="navbar-nav nav-flex-icons font-small" style="width:100%;">
											<li class="font-bold"><joda:format value="${homeBlog.modifiedDate}" pattern="dd MMM YYYY"></joda:format></li>
											<li class="ml-auto"><span class="h-header"><i class="cmsms-icon-eye i-primary m-r-5"></i><c:out value="${homeBlog.viewCount}" /></span></li>
											<li class="m-l-20"><span class="h-header"><i class="cmsms-icon-thumbs-up i-primary m-r-5"></i><c:out value="${homeBlog.likeCount}" /></span></li>
										</ul>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<div class="form-group text-center m-t-20">
				<a href="<c:url value="/bolg" />" class="btn btn-segond btn-big" style="min-width:240px;">
					<span><spring:message code="txt.solution.office3.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
			</div>
		</div>
	</div>
	<div class="card-background background-container"
		style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/aobns/bnsf1-min.jpg" />');"></div>
</div>
<c:if test="${currentSocial.promoted}">
	<div class="screen-container bn-primary bn-skills">
		<div class="container">
			<div class="row">
				<div class="col-lg-4">
					<div class="card-body">
						<h2 class="h-header h-header2 i-white sh-black"><spring:message code="txt.solution.office3.3"/></h2>
						<p class="h-header i-gray m-t-10"><spring:message code="txt.solution.office3.4"/></p>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="row">
						<c:forEach var="i" begin="1" end="4" step="1">
							<div class="col-6 col-md-3">
								<div class="card-skills">
									<p class="h-header i-gray text-truncate"><c:out value="${skills.getFormattedValue(i)}"/></p>
									<p class="font-mini font-bold i-white text-uppercase"><spring:message code="txt.solution.office3.4.${i}"/></p>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.office4.1"/></h2></div>
		<p class="text-center m-t-30"><spring:message code="txt.solution.office4.2"/></p>
		<ul class="nav navcard-solution m-t-40">
			<c:set var="delays" value="600,500,800,700" scope="page"></c:set>
			<c:set var="colors" value="s2,s1,g,p" scope="page"></c:set>
			<c:set var="providers" value="tablet-2,target-3,chart-pie-3,eye-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-mini h-100" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.office4.2.${state.count}.1"/></h3>
						<p class="font-small"><spring:message code="txt.solution.office4.2.${state.count}.2"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
		<div class="text-center m-t-30">
			<i class="cmsms-icon-explorer-arrow i-primary m-r-15"></i><a href="<c:url value="/infos/temoignages-clients" />"
				class="lien lien-segond lien-hover font-big font-bold"><spring:message code="txt.solution.office4.3"/></a>
		</div>
	</div>
</div>
<div class="screen-container background-container m-t-10" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/aobns/bnsf2-min.jpg" />');">
	<div class="container">
		<div class="bn-home text-center m-auto">
			<p class="font-big m-auto"><spring:message code="txt.solution.office4.4"/></p>
			<span class="btn-content"><a href="<c:url value="/solutions/presentation"/>" class="btn btn-segond btn-simple" data-aos="zoom-out" 
				data-aos-delay="1000"><span><i class="cmsms-icon-play-2"></i></span></a></span>
			<h2 class="h-header h-header1 sh-black"><spring:message code="txt.solution.office4.5"/></h2>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.office5.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.office5.2"/></p>
		<div class="card-planing m-t-10">
			<div class="row">
				<c:set var="providers" value="basket-2,chat-2,chart-outline" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<div class="col-lg-4 col-step col-step${state.count} m-t-20">
						<div class="number-step h-header m-auto" data-aos="fade" data-aos-delay="1000">
							<i class="cmsms-icon-${pageScope.provider}"></i><i class="cmsms-icon-ok-4 breadview-trigger"></i>
						</div>
						<div class="widget-body text-center"><p class="font-big i-primary m-t-10"><spring:message code="txt.solution.office5.2.${state.count}"/></p></div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="text-center m-t-20 m-b-20">
			<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-primary btn-big" style="min-width:240px;">
				<span><spring:message code="txt.solution.office5.3"/></span></a>
			<p class="font-mini m-t-10"><spring:message code="txt.solution.office5.4"/></p>
		</div>
		<c:if test="${!empty currentSocial.socialFrame}"><div id="iEmbedAovideo" class="embed-video-algerieoffice"><c:out value="${currentSocial.socialFrame}" escapeXml="false" /></div></c:if>
		<hr class="m-t-20 m-b-20 m-auto" style="max-width:560px;">
		<h2 class="h-header h-header2 h-skills text-center m-auto" style="max-width:520px;"><spring:message code="txt.solution.office6.3"/></h2>
		<div class="text-center m-t-30 m-b-20">
			<a href="<c:url value="/entreprises/salama-assurance-exemple" />" class="btn btn-segond btn-big" style="min-width:230px;">
				<span><spring:message code="txt.solution.office6.4"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
		</div>
	</div>
</div>