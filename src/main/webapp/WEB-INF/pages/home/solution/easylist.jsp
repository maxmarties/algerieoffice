<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-easylist background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.7) 100%), url('<c:url value="/static/picts/images/easylist-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5.6"/></h1>
			<div class="slider-content m-t-20">
				<p class="font-big i-white"><spring:message code="txt.solution.easylist1.1"/></p>
				<div class="form-group m-t-40 m-b-0">
					<a href="<c:url value="/recherche/entreprises" />" class="btn btn-segond btn-flat btn-big" style="min-width:200px;">
						<span><spring:message code="header.esaylist.new"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
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
			<li class="active"><spring:message code="explorer.home.mainmenu4.5"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.easylist1.2"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.easylist1.2.1"/></p>
		<div class="card-planing m-t-10">
			<div class="row">
				<c:forEach var="i" begin="1" end="3" step="1">
					<div class="col-lg-4 col-step col-step${i} m-t-20">
						<div class="number-step h-header m-auto"><c:out value="${i}"/></div>
						<div class="widget-body text-center m-t-10">
							<h3 class="h-header h-header3 i-primary"><spring:message code="txt.solution.easylist1.2.${i}.1"/></h3>
							<p class="font-small i-help m-t-10"><spring:message code="txt.solution.easylist1.2.${i}.2"/></p>
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
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.easylist2.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.easylist2.2"/></p>
		</div>
		<c:set var="providers" value="check,database,gmail" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="card-dashboard m-b-20">
				<div class="row">
					<div class="col-lg-6 m-t-20">
						<c:choose>
							<c:when test="${state.count == 2}">
								<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
									<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aoeas2-min.png"/>" alt="<spring:message code="txt.solution.easylist2.2.2.1"/>">
								</div>
							</c:when>
							<c:otherwise>
								<div class="widget-body">
									<i class="cmsms-icon-${pageScope.provider} breadview-trigger" data-aos="zoom-in" data-aos-delay="800"></i>
									<h3 class="h-header h-header4 i-segond m-t-20"><spring:message code="txt.solution.easylist2.2.${state.count}.1"/></h3>
									<p class="m-t-20"><spring:message code="txt.solution.easylist2.2.${state.count}.2"/></p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-lg-6 m-t-20">
						<c:choose>
							<c:when test="${state.count == 2}">
								<div class="widget-body">
									<i class="cmsms-icon-database breadview-trigger" data-aos="zoom-in" data-aos-delay="800"></i>
									<h3 class="h-header h-header4 i-segond m-t-20"><spring:message code="txt.solution.easylist2.2.2.1"/></h3>
									<p class="m-t-20"><spring:message code="txt.solution.easylist2.2.2.2"/></p>
								</div>
							</c:when>
							<c:otherwise>
								<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
									<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aoeas${state.count}-min.png"/>" 
										alt="<spring:message code="txt.solution.easylist2.2.${state.count}.1"/>">
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<c:if test="${state.count != 3}"><hr class="m-t-40 m-b-20"></c:if>
		</c:forEach>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-20"><h2 class="h-header m-auto"><spring:message code="txt.solution.easylist3.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.easylist3.2"/></p>
		<ul class="nav navcard-skills m-t-30">
			<c:set var="delays" value="600,700,500,800" scope="page"></c:set>
			<c:set var="colors" value="p,h,s1,s2" scope="page"></c:set>
			<c:set var="providers" value="export-5,lkdto,arrows-cw-2,database-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-big text-center" 
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.easylist3.2.${state.count}"/></h3>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-10">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.easylist4.1"/></h2>
			<p class="parag-blog header-detect m-auto"><spring:message code="txt.solution.easylist4.2"/></p>
		</div>
		<ul class="list-none list-block list-card-easylist m-t-10">
			<c:forEach var="i" begin="1" end="3" step="1">
				<li class="${i == 2 ? 'item-right' : 'item-left'}">
					<div class="number-step h-header" data-aos="zoom-in" data-aos-delay="${(i * 100) + 500}"><c:out value="${i}"/></div>
					<div class="card-body">
						<h3 class="h-header h-header3 i-primary"><spring:message code="txt.solution.easylist4.2.${i}.1"/></h3>
						<p class="font-small i-help m-t-10"><spring:message code="txt.solution.easylist4.2.${i}.2"/></p>
					</div>
					<div class="card-thumbnail">
						<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="${(i * 100) + 500}">
							<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aoeas${i + 3}-min.png"/>" alt="<spring:message code="txt.solution.easylist4.2.${i}.1"/>">
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-segond1 sh-black m-t-10"><spring:message code="txt.solution.easylist4.3"/></h2>
			<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
			<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
			<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
			<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
			<div class="form-groupe m-t-30 m-b-20">
				<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-primary btn-big" style="min-width:250px;">
					<span><spring:message code="txt.solution.easylist4.4"/></span></a>
			</div>
		</div>
	</div>
</div>