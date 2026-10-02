<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-services background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/solutions-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5"/></h1>
			<div class="slider-content m-t-20">
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
				<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
				<p class="font-big i-white"><spring:message code="txt.solution.home1.1"/></p>
				<div class="form-group m-t-40 m-b-0">
					<a href="<c:url value="/secteurs" />" class="btn btn-segond btn-flat btn-big" style="max-width:100%!important;">
						<span><spring:message code="txt.solution.home1.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-breadcrumb">
	<div class="container">
		<ol class="bread-crumb bread-screen bread-mini">
			<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
			<li class="active"><spring:message code="sidebar.home.dashboard6"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.home2.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.home2.2"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="beaker,eye-5,bell-4" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-visibility h-100">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger pull-left"></i>
						<div class="brand-visibility">
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.home2.2.${state.count}.1"/></h3>
							<p class="font-small i-help m-t-20"><spring:message code="txt.solution.home2.2.${state.count}.2"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.home3.1"/></h2>
		<div class="card-desktop card-right m-b-20">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" style="padding:0 14px;" data-aos="fade-up">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aovice1-min.png"/>" alt="<spring:message code="txt.solution.home3.2"/>">
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body m-t-10">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.home3.2"/></h3>
						<p class="m-t-20"><spring:message code="txt.solution.home3.3"/> :</p>
						<ul class="list-none list-block i-help m-t-20 m-l-20">
							<c:forEach var="i" begin="1" end="6" step="1">
								<li class="m-b-5"><i class="cmsms-icon-ok-2 i-segond1 m-r-10"></i><spring:message code="txt.solution.home3.3.${i}"/></li>
							</c:forEach>
						</ul>
						<div class="m-t-30">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/recherche/entreprises" />" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="txt.solution.home3.4"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container bn-primary">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-white sh-black m-t-10"><spring:message code="txt.solution.home4.1"/></h2>
			<p class="parag-blog header-detect font-small i-gray m-auto"><spring:message code="txt.solution.home4.2"/></p>
		</div>
		<div class="row m-t-20">
			<c:forEach var="i" begin="1" end="3" step="1">
				<div class="col-md-4 m-t-20">
					<div class="card-services text-center flexed flex-colone flex-jusitify m-auto h-100" data-aos="fade-up" data-aos-delay="${(i * 100) + 500}">
						<h3 class="h-header h-header4"><spring:message code="txt.solution.home4.2.${i}"/></h3>
						<div class="card-thumbnail"><img class="img-responsive" src="<c:url value="/static/vectors/tours/m_solgerie${i}-min.png"/>"
							alt="<spring:message code="txt.solution.home4.2.${i}"/>"></div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="form-group text-center m-t-40 m-b-10">
			<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond" style="min-width:250px;"><span><spring:message code="txt.solution.tours6.4.3"/></span></a>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.home5.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.home5.2"/></p>
		<div class="row">
			<c:set var="delays" value="600,500,700" scope="page"></c:set>
			<c:forEach var="i" begin="1" end="3" step="1">
				<div class="col-md-4 m-b-20">
					<div class="card-navigate">
						<div class="card-thumbnail m-auto" data-aos="zoom-out" data-aos-delay="${pageScope.delays.split(',')[i - 1]}">
							<img class="img-responsive" src="<c:url value="/static/vectors/tours/m_solgerie${i + 3}-min.png"/>" alt="<spring:message code="txt.solution.home5.2.${i}.1"/>">
						</div>
						<h3 class="h-header h-header4 text-center i-primary"><spring:message code="txt.solution.home5.2.${i}.1"/></h3>
						<p class="text-center i-help m-auto"><spring:message code="txt.solution.home5.2.${i}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.home6.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.home6.2"/></p>
		</div>
		<div class="row m-b-10">
			<c:set var="delays" value="600,700,500" scope="page"></c:set>
			<c:set var="services" value="visibilite,detect,publicite" scope="page"></c:set>
			<c:set var="providers" value="plane-1,glasses-1,sign" scope="page"></c:set>
			<c:forEach var="service" items="${pageScope.services}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-business m-auto h-100" data-aos="flip-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<p class="font-mini i-help"><spring:message code="txt.solution.home6.2.${state.count}.1"/></p>
						<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger i-segond1"></i>
						<a href="<c:url value="/solutions/${pageScope.service}"/>" class="h-header h-header4 lien lien-black">
							<spring:message code="txt.solution.home6.2.${state.count}.2"/></a>
						<p class="font-small i-help m-t-20"><spring:message code="txt.solution.home6.2.${state.count}.3"/></p>
						<div class="widget-more m-t-20">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/solutions/${pageScope.service}"/>"
								class="lien lien-primary lien-hover lien-small"><spring:message code="chose.label1"/></a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="background-container" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 0%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/aobns/services-min.jpg" />');">
	<div class="container">
		<div class="bn-services">
			<div class="row">
				<div class="col-md-6 m-t-20">
					<div class="flexed h-100">
						<div class="widget-body">
							<p class="font-big"><spring:message code="txt.solution.home6.3"/></p>
							<p class="h-header font-bold sh-black m-t-10"><spring:message code="txt.solution.home6.3.1"/></p>
							<div class="form-group m-t-40 m-b-20">
								<a href="<c:url value="/solutions/presentation" />" class="btn btn-segond btn-flat btn-big">
									<span><spring:message code="txt.solution.home6.3.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 hidden-sm-down">
					<div class="card-thumbnail m-auto">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aovice2-min.png"/>" alt="<spring:message code="txt.solution.home6.3.2"/>">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect header-min m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.home6.4"/></h2></div>
		<ul class="nav navcard-services m-t-30">
			<c:forEach var="i" begin="1" end="3" step="1">
				<li>
					<span class="number-step h-header font-bold" data-aos="zoom-in" data-aos-delay="${(i * 100) + 500}"><c:out value="${i}"/></span>
					<div class="col-step">
						<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.home6.4.${i}.1"/></h3>
						<p class="font-small i-help m-t-10"><spring:message code="txt.solution.home6.4.${i}.2"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-20">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.home6.5"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.home6.5.1"/></p>
			<div class="form-groupe m-t-10">
				<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-big" 
					style="min-width:230px;"><span><spring:message code="txt.solution.home6.4.1.1"/></span></a>
			</div>
		</div>
	</div>
</div>