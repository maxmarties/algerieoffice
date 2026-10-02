<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-detect background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.7) 100%), url('<c:url value="/static/picts/images/detect-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<h1 class="h-header h-header1 h-border"><spring:message code="subheader.screen.solution5.4"/></h1>
			<div class="slider-content m-t-20">
				<p class="font-big i-white"><spring:message code="txt.solution.detect1.1"/></p>
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard/detect" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
				<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
				<div class="form-group m-t-30 m-b-0">
					<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-slider" style="min-width:200px;">
						<span>
							<span class="font-mini i-gray"><spring:message code="txt.solution.detect1.2.1"/></span>
							<span class="block text-uppercase"><spring:message code="txt.solution.detect1.2.2"/></span>
							<i class="cmsms-icon-explorer-arrow breadview-trigger"></i>
						</span>
					</a>
					<p class="font-mini i-gray m-t-5"><spring:message code="txt.solution.detect1.2.3"/></p>
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
			<li class="active"><spring:message code="explorer.home.mainmenu4.4"/></li>
		</ol>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.detect2.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.detect2.2"/></p>
		<div class="row m-t-20">
			<c:set var="providers" value="diagram,target-3,message" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-md-4 m-t-20">
					<div class="card-detect text-center">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header h-header3 i-primary m-t-20"><spring:message code="txt.solution.detect2.2.${state.count}.1"/></h3>
						<p class="font-small i-help m-t-20"><spring:message code="txt.solution.detect2.2.${state.count}.2"/></p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-white m-t-20">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.detect3.1"/></h2>
			<p class="font-small text-uppercase i-help m-t-5"><spring:message code="txt.solution.detect3.2"/></p>
		</div>
		<c:forEach var="i" begin="1" end="3" step="1">
			<div class="card-dashboard m-b-20">
				<div class="row">
					<div class="col-lg-6 m-t-20">
						<c:choose>
							<c:when test="${i == 2}">
								<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
									<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aodet2-min.png"/>" alt="<spring:message code="txt.solution.detect3.2.2.1"/>">
								</div>
							</c:when>
							<c:otherwise>
								<div class="widget-body">
									<span class="breadview-trigger" data-aos="zoom-in" data-aos-delay="800"><c:out value="${i}"/></span>
									<h3 class="h-header h-header3 i-segond m-t-30"><spring:message code="txt.solution.detect3.2.${i}.1"/></h3>
									<p class="font-big m-t-20"><spring:message code="txt.solution.detect3.2.${i}.2"/></p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-lg-6 m-t-20">
						<c:choose>
							<c:when test="${i == 2}">
								<div class="widget-body">
									<span class="breadview-trigger" data-aos="zoom-in" data-aos-delay="800"><c:out value="2"/></span>
									<h3 class="h-header h-header3 i-segond m-t-30"><spring:message code="txt.solution.detect3.2.2.1"/></h3>
									<p class="font-big m-t-20"><spring:message code="txt.solution.detect3.2.2.2"/></p>
								</div>
							</c:when>
							<c:otherwise>
								<div class="inner-thumbnail m-auto" data-aos="fade-up" data-aos-delay="600">
									<img class="img-responsive" src="<c:url value="/static/vectors/aodet/m_aodet${i}-min.png"/>" alt="<spring:message code="txt.solution.detect3.2.${i}.1"/>">
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<c:if test="${i != 3}"><hr class="m-t-40 m-b-20"></c:if>
		</c:forEach>
	</div>
</div>
<div class="screen-container background-container" 
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.8) 0%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/aobns/detect-min.jpg" />');">
	<div class="container">
		<div class="bn-detect text-center">
			<p class="font-large i-segond1"><spring:message code="txt.solution.detect4.1"/></p>
			<h4 class="h-header i-white sh-black m-auto"><spring:message code="txt.solution.detect4.2"/></h4>
			<span class="screen-separator m-auto"></span>
		</div>
	</div>
</div>
<div class="screen-container m-t-10">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="txt.solution.detect5.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.solution.detect5.2"/></p>
		<ul class="nav navcard-solution m-t-30">
			<c:set var="delays" value="500,700,600,800" scope="page"></c:set>
			<c:set var="colors" value="p,g,s1,s2" scope="page"></c:set>
			<c:set var="providers" value="award-alt,speed,user-3,chart-pie-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} h-100"
						data-aos="fade-up" data-aos-delay="${pageScope.delays.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.detect5.2.${state.count}.1"/></h3>
						<p class="font-small"><spring:message code="txt.solution.detect5.2.${state.count}.2"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
		<hr class="m-t-40 m-b-40">
		<div class="text-center">
			<h4 class="h-header h-header2 i-segond"><spring:message code="txt.solution.detect5.3"/></h4>
			<p class="font-large m-t-10"><spring:message code="txt.solution.detect5.3.1"/></p>
			<div class="m-t-30 m-b-30">
				<a href="<c:url value="/contacts" />" class="btn btn-segond btn-big" style="min-width:210px;">
					<span><spring:message code="txt.solution.detect5.3.2"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
			</div>
		</div>
	</div>
</div>
<c:if test="${partners.size() >= 5}">
	<div class="screen-container screen-gray">
		<div class="container">
			<div class="text-center">
				<h2 class="h-header h-header2 i-primary m-t-20"><spring:message code="txt.solution.detect6.1"/></h2>
				<p class="font-small i-help m-t-5"><spring:message code="txt.solution.detect6.2"/></p>
			</div>
			<ul class="nav navlogo-skills text-center m-t-40 m-b-30">
				<c:set var="maxItems" value="${partners.size() == 10 ? 10 : 5}" scope="page"></c:set>
				<c:forEach var="i" begin="1" end="${pageScope.maxItems}" step="1">
					<c:set var="partner" value="${partners.get(i - 1)}" scope="page"></c:set>
					<li>
						<img class="img-responsive transition-35 m-auto" src="<c:url value="${pageScope.partner.urlAvatar}"/>" alt="<c:out value="${pageScope.partner.tradename}" />" 
							title="<c:out value="${pageScope.partner.tradename}" />" data-aos="zoom-out" data-aos-delay="${(i * 100) + 500}">
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>