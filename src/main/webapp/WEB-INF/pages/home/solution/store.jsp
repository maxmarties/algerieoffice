<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-store background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.7) 100%), url('<c:url value="/static/picts/images/store-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5.3"/></h1>
			<div class="slider-content m-t-20">
				<p class="font-big i-white"><spring:message code="txt.solution.store1.1"/></p>
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
				<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
				<div class="form-group m-t-40 m-b-0">
					<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-flat btn-big" style="min-width:200px;max-width:100%!important;">
						<span><spring:message code="txt.solution.store1.1.1"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
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
			<li class="active"><spring:message code="explorer.home.mainmenu4.3"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.store1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.store1.2.1"/></p>
		<div class="card-planing m-t-10">
			<div class="row">
				<c:forEach var="i" begin="1" end="3" step="1">
					<div class="col-lg-4 col-step col-step${i} m-t-20">
						<div class="number-step h-header m-auto"><c:out value="${i}"/></div>
						<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="${(i * 100) + 500}">
							<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aostr${i}-min.png"/>" alt="<spring:message code="txt.solution.store1.3.${i}.1"/>">
						</div>
						<div class="widget-body text-center">
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.store1.3.${i}.1"/></h3>
							<p class="font-small i-help m-t-10"><spring:message code="txt.solution.store1.3.${i}.2"/></p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.store2.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.store2.2"/></p>
		</div>
		<div class="card-desktop card-right m-t-30">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="zoom-in-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aostr4-min.png"/>" alt="<spring:message code="txt.solution.store2.3"/>">
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header"><spring:message code="txt.solution.store2.3"/></h3>
						<p class="m-t-30"><spring:message code="txt.solution.store2.3.1"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.store2.3.2"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.store2.3.3"/></p>
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-40 m-b-30">
		<div class="card-desktop card-left">
			<div class="row">
				<div class="col-lg-6 m-t-20">
					<div class="widget-body">
						<h3 class="h-doc h-header3 i-header m-t-10"><spring:message code="txt.solution.store2.4"/></h3>
						<p class="m-t-30"><spring:message code="txt.solution.store2.4.1"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.store2.4.2"/></p>
						<p class="m-t-20"><spring:message code="txt.solution.store2.4.3"/></p>
					</div>
				</div>
				<div class="col-lg-6 m-t-20">
					<div class="inner-thumbnail m-auto" data-aos="zoom-in-up" data-aos-delay="600">
						<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aostr5-min.png"/>" alt="<spring:message code="txt.solution.store2.4"/>">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.store2.5"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.store2.6"/></p>
		<ul class="nav navcard-skills m-t-30">
			<c:set var="delays" value="600,700,500,800" scope="page"></c:set>
			<c:set var="colors" value="p,s2,g,s1" scope="page"></c:set>
			<c:set var="providers" value="user-famale,briefcase-4,search-3,headphones-alt" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-big text-center" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.store2.6.${state.count}"/></h3>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.store3.1"/></h2>
			<p class="font-small text-uppercase i-help m-t-5"><spring:message code="txt.solution.store3.2"/></p>
		</div>
		<div class="card-stored m-t-40 m-b-30">
			<div class="inner-thumbnail m-auto" data-aos="zoom-out" data-aos-delay="600">
				<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aostr6-min.png"/>" alt="<spring:message code="txt.solution.store3.1"/>">
			</div>
		</div>
		<span class="screen-separator m-auto"></span>
		<p class="parag-blog parag-down text-center m-auto"><spring:message code="txt.solution.store3.3"/></p>
	</div>
</div>
<div class="screen-container background-container" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.8) 0%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/aobns/store-min.jpg" />');">
	<div class="container">
		<div class="bn-visibility">
			<div class="row">
				<div class="col-md-6 col-lg-8">
					<p class="font-mini text-uppercase i-gray"><spring:message code="txt.solution.store3.4"/></p>
					<h4 class="h-header h-header2 sh-black m-t-20"><spring:message code="txt.solution.store3.4.1"/></h4>
					<p class="font-big m-t-20"><spring:message code="txt.solution.store3.4.2"/></p>
					<p class="font-big m-t-30"><spring:message code="txt.solution.store3.4.3"/></p>
				</div>
				<div class="col-md-6 col-lg-4">
					<div class="flexed h-100">
						<div class="widget-body text-right">
							<a href="<c:url value="/contacts" />" class="btn btn-segond btn-flat btn-big">
								<span><spring:message code="txt.solution.store3.4.4"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.store4.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.store4.2"/></p>
		<div class="row m-t-20">
			<c:set var="delays" value="600,700,500,1000,800,900" scope="page"></c:set>
			<c:set var="providers" value="truck-1,eye-3,globe-6,game,vector-1,anchor-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-6 col-lg-4 m-t-20 m-b-20">
					<div class="card-detect text-center">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger" data-aos="zoom-in" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}"></i>
						<h3 class="h-header h-header4 i-primary m-t-20"><spring:message code="txt.solution.store4.2.${state.count}.1"/></h3>
						<p class="font-small i-help m-t-20"><spring:message code="txt.solution.store4.2.${state.count}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-gray">
	<div class="container">
		<div class="text-center">
			<h4 class="h-header h-header2 i-segond"><spring:message code="txt.solution.store4.3"/></h4>
			<p class="font-big text-center m-t-20"><spring:message code="txt.solution.store4.3.1"/></p>
			<div class="m-t-30 m-b-20">
				<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-big" style="min-width:190px;">
					<span><spring:message code="txt.solution.store4.3.2"/></span></a>
			</div>
		</div>
	</div>
</div>