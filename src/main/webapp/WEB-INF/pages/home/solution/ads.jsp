<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-slider slider-bub background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/bub-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5.5"/></h1>
			<div class="slider-content m-t-20"><p class="font-big i-white"><spring:message code="txt.solution.ads1.1"/></p></div>
		</div>
	</div>
</div>
<div class="screen-breadcrumb">
	<div class="container">
		<ol class="bread-crumb bread-screen">
			<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/solutions"/>" class="lien lien-black"><spring:message code="sidebar.home.dashboard6"/></a></li>
			<li class="active"><spring:message code="explorer.home.mainmenu4.6"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.ads1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.ads1.2.1"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="traffic-cone,flashlight,mouse" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-visibility h-100">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger pull-left"></i>
						<div class="brand-visibility">
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.ads1.2.${state.count}.1"/></h3>
							<p class="font-small i-help m-t-20"><spring:message code="txt.solution.ads1.2.${state.count}.2"/></p>
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
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.ads2.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.ads2.2"/></p>
		</div>
		<div class="card-desktop card-right">
			<div class="row">
				<div class="col-lg-6 m-t-10">
					<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aopro1-min.png"/>" alt="<spring:message code="txt.solution.ads2.1"/>">
					</div>
				</div>
				<div class="col-lg-6 m-t-10">
					<div class="widget-body">
						<ul class="list-none list-block list-card-aobub">
							<c:forEach var="i" begin="1" end="4" step="1">
								<li>
									<span class="breadview-trigger h-header pull-left" data-aos="zoom-in" data-aos-delay="${(i * 100) + 500}"><c:out value="${i}"/></span>
									<p class="font-big i-help"><spring:message code="txt.solution.ads2.2.${i}"/></p>
									<span class="clearfix"></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="text-center m-t-40">
			<a href="<c:url value="/company/marketplace/promotes/new" />" class="btn btn-segond btn-big" style="min-width:190px;">
				<span><spring:message code="txt.solution.ads2.3"/></span></a>
			<p class="font-mini m-t-10"><spring:message code="txt.solution.ads2.4"/></p>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.ads3.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.ads3.2"/></p>
		<ul class="nav navcard-aobubs text-center m-t-30">
			<c:set var="delays" value="900,700,500,800,600" scope="page"></c:set>
			<c:set var="colors" value="p,g,h,s1,s2" scope="page"></c:set>
			<c:set var="providers" value="params-1,rss-3,calc-1,refresh-alt,mouse-1" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li class="${state.count == 5 ? 'hidden-md-down' : ''}">
					<div class="card-solution card-aobub card-aobub${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]}" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-30"><spring:message code="txt.solution.ads3.2.${state.count}"/></h3>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-20">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.ads4.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.ads4.2"/></p>
		</div>
		<div class="card-desktop card-left">
			<div class="row">
				<div class="col-lg-6 m-t-10">
					<div class="widget-body">
						<ul class="list-none list-block list-card-aobub">
							<c:forEach var="i" begin="1" end="3" step="1">
								<li>
									<span class="breadview-trigger h-header pull-left" data-aos="zoom-in" data-aos-delay="${(i * 100) + 500}"><c:out value="${i}"/></span>
									<p class="font-big i-help"><spring:message code="txt.solution.ads4.2.${i}"/></p>
									<span class="clearfix"></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="col-lg-6 m-t-10">
					<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aopro2-min.png"/>" alt="<spring:message code="txt.solution.ads4.1"/>">
					</div>
				</div>
			</div>
		</div>
		<div class="text-center m-t-10">
			<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.ads4.3"/></h3>
			<p class="font-small i-help m-t-10 m-b-30"><spring:message code="txt.solution.ads4.3.1"/></p>
			<a href="<c:url value="/company/marketplace/campaigns/new" />" class="btn btn-segond btn-big" style="min-width:190px;">
				<span><spring:message code="txt.solution.ads4.3.2"/></span></a>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.ads5.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.ads5.2"/></p>
		<div class="bub-thumbnail m-auto" data-aos="zoom-out" data-aos-delay="600">
			<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aopro3-min.png"/>" alt="<spring:message code="txt.solution.ads5.1"/>">
		</div>
		<div class="text-center m-t-30 m-b-30">
			<p class="font-big m-b-30"><spring:message code="txt.solution.ads5.2.1"/></p>
			<a href="<c:url value="/contacts" />" class="btn btn-primary btn-big" style="min-width:230px;">
				<span><spring:message code="txt.solution.ads5.2.2"/><i class="cmsms-icon-explorer-arrow i-segond m-l-10"></i></span></a>
		</div>
	</div>
</div>