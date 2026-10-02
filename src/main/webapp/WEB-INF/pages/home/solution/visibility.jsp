<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-visibility background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/visibility-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5.2"/></h1>
			<div class="slider-content m-t-20">
				<p class="font-big i-white"><spring:message code="txt.solution.visibility1.1"/></p>
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/profile/identity" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
				<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
				<div class="form-group m-t-30 m-b-0">
					<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-slider" style="min-width:200px;">
						<span>
							<span class="font-mini i-gray"><spring:message code="txt.solution.visibility1.2.1"/></span>
							<span class="block text-uppercase"><spring:message code="txt.solution.visibility1.2.2"/></span>
							<i class="cmsms-icon-explorer-arrow breadview-trigger"></i>
						</span>
					</a>
					<p class="font-mini i-gray m-t-5"><spring:message code="txt.solution.visibility1.2.3"/></p>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-breadcrumb">
	<div class="container">
		<ol class="bread-crumb bread-screen bread-mini">
			<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/solutions"/>" class="lien lien-black"><spring:message code="sidebar.home.dashboard6"/></a></li>
			<li class="active"><spring:message code="explorer.home.mainmenu4.2"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.visibility2.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.visibility2.2"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="info-2,magnet-1,home-2,graduation-cap,magic,flag-3,thumbs-up-alt,gift,basket-1" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-visibility h-100">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger pull-left"></i>
						<div class="brand-visibility">
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.visibility2.2.${state.count}.1"/></h3>
							<p class="font-small i-help m-t-20"><spring:message code="txt.solution.visibility2.2.${state.count}.2"/></p>
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
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.visibility3.1"/></h2>
		<div class="card-desktop card-left m-t-30">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.visibility3.2"/></h3>
						<p class="m-t-30"><spring:message code="txt.solution.visibility3.2.1"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.visibility3.2.2"/></p>
						<div class="m-t-30">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/solutions/publicite" />" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="txt.solution.visibility3.2.3"/></a>
						</div>
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aovis1-min.png"/>" alt="<spring:message code="txt.solution.visibility3.2"/>">
					</div>
				</div>
			</div>
		</div>
		<h2 class="h-header h-header4 i-primary text-center m-t-40"><spring:message code="txt.solution.visibility3.3"/></h2>
		<div class="row m-t-20">
			<div class="col-lg-2 hidden-md-down"></div>
			<c:forEach var="i" begin="1" end="2" step="1">
				<div class="col-md-6 col-lg-4">
					<div class="card-value text-center">
						<h3 class="h-header font-bold i-segond"><spring:message code="txt.solution.visibility3.3.${i}.1"/></h3>
						<p class="font-small i-help m-auto"><spring:message code="txt.solution.visibility3.3.${i}.2"/></p>
					</div>
				</div>	
			</c:forEach>
		</div>
		<hr class="m-t-40 m-b-30">
		<div class="card-desktop card-right">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aovis2-min.png"/>" alt="<spring:message code="txt.solution.visibility3.4"/>">
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.visibility3.4"/></h3>
						<p class="m-t-30"><spring:message code="txt.solution.visibility3.4.1"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.visibility3.4.2"/></p>
						<div class="m-t-30">
							<i class="cmsms-icon-explorer-angle m-r-15"></i><a href="<c:url value="/${pageScope.lien}" />" 
								class="lien lien-primary lien-hover lien-small"><spring:message code="txt.solution.visibility3.4.3"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<h2 class="h-header h-header4 i-primary text-center m-t-40"><spring:message code="txt.solution.visibility3.5"/></h2>
		<div class="row m-t-20">
			<c:forEach var="i" begin="1" end="3" step="1">
				<div class="col-md-4">
					<div class="card-value text-center">
						<h3 class="h-header font-bold i-segond"><spring:message code="txt.solution.visibility3.5.${i}.1"/></h3>
						<p class="font-small i-help m-auto"><spring:message code="txt.solution.visibility3.5.${i}.2"/></p>
					</div>
				</div>	
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.visibility3.6"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.visibility3.7"/></p>
		<ul class="nav navcard-solution m-t-30">
			<c:set var="delays" value="500,700,600,800" scope="page"></c:set>
			<c:set var="colors" value="p,s1,g,s2" scope="page"></c:set>
			<c:set var="providers" value="map-2,attach-7,star-3,wallet-1" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-mini h-100" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.visibility3.7.${state.count}.1"/></h3>
						<p class="font-small"><spring:message code="txt.solution.visibility3.7.${state.count}.2"/></p>
						<ul class="list-none list-block font-small m-t-10">
							<c:forEach var="i" begin="1" end="${state.count == 1 ? 6 : 5}" step="1">
								<li class="m-t-5">
									<i class="cmsms-icon-ok breadview-ok pull-left"></i>
									<span class="block breadview-brand"><spring:message code="txt.solution.visibility3.7.${state.count}.2.${i}"/></span>
									<span class="clearfix"></span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container background-container m-t-10" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.8) 0%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/aobns/visibility-min.jpg" />');">
	<div class="container">
		<div class="bn-visibility">
			<div class="row">
				<div class="col-md-6 col-lg-8">
					<p class="font-mini text-uppercase i-gray"><spring:message code="txt.solution.visibility3.8"/></p>
					<h4 class="h-header h-header1 sh-black m-t-20"><spring:message code="txt.solution.visibility3.8.1"/></h4>
					<p class="font-big m-t-20"><spring:message code="txt.solution.visibility3.8.2"/></p>
					<p class="font-big m-t-30"><spring:message code="txt.solution.visibility3.8.3"/></p>
				</div>
				<div class="col-md-6 col-lg-4">
					<div class="flexed h-100">
						<div class="widget-body text-right">
							<a href="<c:url value="/contacts" />" class="btn btn-segond btn-flat btn-big">
								<span><spring:message code="txt.solution.visibility3.8.4"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.visibility3.9"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.visibility3.10"/></p>
		<div class="row m-t-20">
			<c:set var="delays" value="500,700,600,800,1000,900" scope="page"></c:set>
			<c:set var="providers" value="bag-1,paper-plane-4,award-1,heart-3,diamond-1,globe-alt-2" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-6 col-lg-4 m-t-20 m-b-20">
					<div class="card-detect text-center">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger" data-aos="zoom-in" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}"></i>
						<h3 class="h-header h-header4 i-primary m-t-20"><spring:message code="txt.solution.visibility3.10.${state.count}.1"/></h3>
						<p class="font-small i-help m-t-20"><spring:message code="txt.solution.visibility3.10.${state.count}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto" style="padding-bottom:10px;"><spring:message code="txt.solution.visibility3.11"/></h2>
		<p class="text-center"><spring:message code="txt.solution.home6.8"/></p>
		<div class="card-desktop card-right m-t-20">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="zoom-in-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aosil3-min.png"/>" alt="<spring:message code="txt.solution.home6.7.1"/>">
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.home6.6"/></h3>
						<p class="m-t-20"><spring:message code="txt.solution.home6.7"/></p>
						<ul class="list-none list-block m-t-30 m-l-20">
							<c:set var="providers" value="mail-3,calendar-3,location-3,message,shuffle-5,lock-3,flag-4" scope="page"></c:set>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<li class="m-b-10">
									<i class="cmsms-icon-ok-2 i-segond1 m-r-20"></i><i class="cmsms-icon-${pageScope.provider} i-primary font-large m-r-10"></i>
									<spring:message code="txt.solution.home6.7.${state.count}"/>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-30 m-b-10">
		<p class="parag-blog header-detect font-big i-help text-center m-auto"><spring:message code="txt.solution.visibility3.11.1"/></p>
		<div class="text-center m-t-20">
			<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-big" style="min-width:230px;"><span><spring:message code="txt.solution.visibility3.11.2"/></span></a>
		</div>
	</div>
</div>