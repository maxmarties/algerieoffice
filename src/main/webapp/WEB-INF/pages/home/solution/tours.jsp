<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-tours background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.7) 100%), url('<c:url value="/static/picts/images/tours-min.jpg" />');">
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-lg-8">
				<div class="slider-overlay">
					<h1 class="h-header h-header1"><spring:message code="subheader.screen.solution5.1"/></h1>
					<p class="font-big i-white m-t-20"><spring:message code="txt.solution.tours1.1"/></p>
				</div>
			</div>
			<div class="col-md-6 col-lg-4">
				<div class="slider-content h-100">
					<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
					<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
					<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
					<div class="form-group text-center m-auto">
						<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-flat btn-big"><span><spring:message code="txt.solution.tours1.1.1"/></span></a>
						<p class="h-header i-white m-auto"><spring:message code="txt.solution.tours1.1.2"/></p>
					</div>
				</div>
			</div>
		</div>
		<div class="inner-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aotrs1-min.png"/>"
			alt="<spring:message code="txt.solution.tours1.1.3"/>"></div>
	</div>
</div>
<div class="screen-breadcrumb">
	<div class="container">
		<ol class="bread-crumb bread-screen bread-mini">
			<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/solutions"/>" class="lien lien-black"><spring:message code="sidebar.home.dashboard6"/></a></li>
			<li class="active"><spring:message code="explorer.home.mainmenu4.1"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.tours1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.tours1.2.1"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="commerical-building,joystick,globe-4,chart-bar-2,grocery-store,guest" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-6 col-lg-4 m-t-20">
					<div class="card-visibility h-100">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger pull-left"></i>
						<div class="brand-visibility">
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.tours1.2.${state.count}.1"/></h3>
							<p class="font-small i-help m-t-20"><spring:message code="txt.solution.tours1.2.${state.count}.2"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-tours screen-skills screen-white background-container" style="background-image: url('<c:url value="/static/picts/aobns/tours1-min.jpg" />');">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.tours2.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.tours2.2"/></p>
		</div>
	</div>
	<div class="card-thumbnail m-auto" data-aos="fade-in" data-aos-delay="600">
		<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aotrs2-min.png"/>" alt="<spring:message code="txt.solution.tours2.3"/>">
	</div>
</div>
<div class="screen-container screen-afterslider">
	<div class="container">
		<div class="header-screen header-detect header-min m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.tours2.2.1"/></h2></div>
		<p class="text-center m-t-30"><spring:message code="txt.solution.tours2.2.2"/></p>
		<div class="row m-t-20">
			<c:set var="delays" value="600,500,700,900,800,1000" scope="page"></c:set>
			<c:set var="providers" value="user-group,shield-1,chart-line,book-2,settings,lifebuoy-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-6 col-lg-4 m-t-20">
					<div class="card-detect text-center">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger" data-aos="zoom-out" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}"></i>
						<h3 class="h-header h-header4 i-primary m-t-20"><spring:message code="txt.solution.tours2.2.${state.count}.1"/></h3>
						<p class="font-small i-help m-t-20"><spring:message code="txt.solution.tours2.2.${state.count}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.tours3.1"/></h2>
			<p class="parag-blog header-detect font-small i-help m-auto"><spring:message code="txt.solution.tours3.2"/></p>
		</div>
		<div class="row m-t-20">
			<c:forEach var="i" begin="1" end="3" step="1">
				<div class="col-sm-4 m-t-20">
					<div class="card-tours m-auto h-100" data-aos="fade-up" data-aos-delay="${(i * 100) + 500}">
						<div class="card-thumbnail"><img class="img-responsive" src="<c:url value="/static/vectors/tours/m_tours${i}-min.png"/>"
							alt="<spring:message code="txt.solution.tours3.2.4"/>_${i}"></div>
						<div class="widget-more m-t-20"><spring:message code="txt.solution.tours3.2.${i}"/></div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container bn-primary">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-white sh-black m-t-10"><spring:message code="txt.solution.tours3.3"/></h2>
			<p class="parag-blog header-detect font-small i-gray m-auto"><spring:message code="txt.solution.tours3.4"/></p>
		</div>
		<ul class="nav navcard-tours text-center">
			<c:set var="providers" value="search-2,user-add-1,briefcase-1,basket,gauge-1" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<i class="cmsms-icon-${pageScope.provider} breadview-trigger m-auto" data-aos="zoom-in" data-aos-delay="${(state.count * 100) + 500}"></i>
					<p class="h-header font-mini text-uppercase i-white hidden-sm-down"><spring:message code="txt.solution.tours3.4.${state.count}"/></p>
				</li>
			</c:forEach>
		</ul>
		<div class="card-desktop card-right m-t-20">
			<div class="row">
				<div class="col-md-6 col-lg-7 m-t-20">
					<a href="<c:url value="/forums" />" class="inner-thumbnail i-white m-auto" data-aos="zoom-in-up" data-aos-delay="600" style="max-width:740px!important;" target="_blank">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aotrs4-min.png"/>" alt="<spring:message code="txt.solution.tours3.5"/>">
					</a>
				</div>
				<div class="col-md-6 col-lg-5 m-t-20">
					<div class="widget-body m-t-40">
						<h3 class="h-doc h-header3 i-white"><spring:message code="txt.solution.tours3.5"/></h3>
						<p class="i-gray m-t-30"><spring:message code="txt.solution.tours3.6"/></p>
						<ul class="list-none list-block i-white m-l-30 m-t-30">
							<c:set var="providers" value="comment-3,flag-4,mic-3,youtube-1" scope="page"></c:set>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<li class="m-b-10"><i class="cmsms-icon-${pageScope.provider} i-segond1 m-r-10" style="font-size:18px;"></i>
									<spring:message code="txt.solution.tours3.6.${state.count}"/></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group text-center m-t-40 m-b-20">
			<a href="<c:url value="/forums" />" class="btn btn-segond" style="min-width:210px;" target="_blank"><span><spring:message code="lien.more"/> *</span></a>
			<p class="font-mini i-gray m-t-10"><spring:message code="txt.solution.ads2.5"/></p>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.tours4.1"/></h2></div>
		<p class="text-center m-t-30"><spring:message code="txt.solution.tours4.2"/></p>
		<ul class="nav navcard-solution m-t-30">
			<c:set var="delays" value="600,700,500,800" scope="page"></c:set>
			<c:set var="colors" value="p,g,s2,s1" scope="page"></c:set>
			<c:set var="providers" value="desktop-4,building,user-confirm,lab" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-mini h-100" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.tours4.2.${state.count}.1"/></h3>
						<p class="font-small"><spring:message code="txt.solution.tours4.2.${state.count}.2"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<h2 class="h-header h-header2 h-skills h-down i-primary m-auto"><spring:message code="txt.solution.tours5.1"/></h2>
		<p class="parag-blog text-center m-auto"><spring:message code="txt.solution.tours5.2"/></p>
		<div class="row">
			<c:forEach var="i" begin="1" end="3" step="1">
				<div class="col-sm-4 m-t-20">
					<div class="card-tours m-auto h-100" data-aos="flip-down" data-aos-delay="${(i * 100) + 500}">
						<div class="card-thumbnail"><img class="img-responsive" src="<c:url value="/static/vectors/tours/m_tours${3 + i}-min.jpg"/>"
							alt="<spring:message code="txt.solution.tours5.2.4"/>_${i}"></div>
						<div class="widget-more font-big i-help m-t-10"><spring:message code="txt.solution.tours5.2.${i}"/></div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container background-container" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 0%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/aobns/tours2-min.jpg" />');">
	<div class="container">
		<div class="bn-tours text-center m-auto">
			<p class="font-mini text-uppercase i-gray"><spring:message code="txt.solution.tours5.3"/></p>
			<div class="card-thumbnail m-auto"><img class="img-responsive" src="<c:url value="/static/vectors/tours/m_tours7-min.png"/>"
				alt="<spring:message code="txt.solution.tours5.3"/>"></div>
			<p class="h-header h-header4 sh-black"><spring:message code="txt.solution.tours5.3.1"/></p>
			<div class="form-group m-t-40 m-b-0">
				<a href="<c:url value="/contacts" />" class="btn btn-segond btn-flat btn-big" style="min-width:210px;"><span><spring:message code="txt.solution.tours5.3.2"/></span></a>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 font-bold i-segond1 sh-black m-t-10"><spring:message code="txt.solution.tours6.1"/></h2>
			<p class="m-t-10"><spring:message code="txt.solution.tours6.2"/></p>
		</div>
		<div class="row">
			<c:set var="delays" value="800,600,500,700" scope="page"></c:set>
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-md-6 col-lg-3 m-t-20">
					<div class="card-navigate">
						<div class="card-thumbnail m-auto" data-aos="zoom-out" data-aos-delay="${pageScope.delays.split(',')[i - 1]}">
							<img class="img-responsive" src="<c:url value="/static/vectors/tours/m_tours${i + 7}-min.png"/>" alt="<spring:message code="txt.solution.tours6.2.${i}.1"/>">
						</div>
						<h3 class="h-header h-header4 text-center i-primary"><spring:message code="txt.solution.tours6.2.${i}.1"/></h3>
						<p class="text-justify i-help m-t-20"><spring:message code="txt.solution.tours6.2.${i}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-20">
	<div class="container">
		<div class="text-center">
			<p class="font-mini text-uppercase i-help m-t-10"><spring:message code="txt.solution.tours6.3.1"/></p>
			<h2 class="h-header h-header1 i-primary"><spring:message code="txt.solution.tours6.3.2"/></h2>
		</div>
		<div class="card-desktop card-left m-t-30">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.tours6.4"/></h3>
						<p class="font-big m-t-30"><spring:message code="txt.solution.tours6.4.1"/></p>
						<p class="font-big m-t-20"><spring:message code="txt.solution.tours6.4.2"/></p>
						<div class="form-group m-t-30 m-b-0">
							<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-big m-l-20" style="min-width:230px;"><span><spring:message code="txt.solution.tours6.4.3"/></span></a>
						</div>
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="zoom-in-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aotrs3-min.png"/>" alt="<spring:message code="txt.solution.tours6.3.2"/>">
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-30 m-b-30">
		<div class="text-center">
			<i class="cmsms-icon-explorer-arrow i-primary m-r-15"></i><a href="<c:url value="/infos/temoignages-clients" />"
				class="lien lien-segond lien-hover font-bold"><spring:message code="txt.solution.tours6.4.5"/></a>
		</div>
	</div>
</div>