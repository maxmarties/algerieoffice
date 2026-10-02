<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/secteurs"/>" class="lien lien-black"><spring:message code="explorer.home.mainmenu2.1"/></a></li>
		<li><a href="<c:url value="${sectorURL}"/>" class="lien lien-black"><spring:message code="chose.sector${activity.sector}"/></a></li>
		<li class="active"><spring:message code="chose.activity.${activity.code}"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen"><h1 class="h-header m-auto"><spring:message code="chose.activity.${activity.code}"/></h1></div>
		<h2 class="h-header h-header3 i-primary">
			<spring:message code="subheader.screen.sector3.1"/> "<spring:message code="chose.activity.${activity.code}"/>" <spring:message code="subheader.screen.sector3.2"/>
		</h2>
		<div class="row m-t-30">
			<div class="col-lg-6 m-b-20"><div class="vector-map vector-screen-lg"><div id="vmapRegion" class="vmap"></div></div></div>
			<div class="col-lg-6 m-b-20">
				<div class="row">
					<div class="col-sm-6">
						<ul class="list-none">
							<c:forEach var="i" begin="1" end="24" step="1">
								<c:set var="wilaya" value="${wilayas.get(i - 1)}" scope="page"></c:set>
								<li class="m-b-5">
									<i class="cmsms-icon-explorer-angle i-select m-r-5"></i><a href="<c:url value="${pageScope.wilaya.wilayaURL}"/>" class="lien lien-table" 
										title="<spring:message code="chose.wilaya${i}"/>" data-region="${i}">
										<strong><c:out value="${i < 10 ? '0' : ''}${i}" /></strong> - <spring:message code="chose.wilaya${i}"/>
									</a>
									<span class="font-small i-select">(<c:out value="${pageScope.wilaya.count}"/>)</span>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="col-sm-6">
						<ul class="list-none">
							<c:forEach var="i" begin="25" end="48" step="1">
								<c:set var="wilaya" value="${wilayas.get(i - 1)}" scope="page"></c:set>
								<li class="m-b-5">
									<i class="cmsms-icon-explorer-angle i-select m-r-5"></i><a href="<c:url value="${pageScope.wilaya.wilayaURL}"/>" class="lien lien-table" 
										title="<spring:message code="chose.wilaya${i}"/>" data-region="${i}">
										<strong><c:out value="${i}" /></strong> - <spring:message code="chose.wilaya${i}"/>
									</a>
									<span class="font-small i-select">(<c:out value="${pageScope.wilaya.count}"/>)</span>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="screenAnalytic" class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary">
			<spring:message code="chose.activity.${activity.code}"/> - <spring:message code="subheader.screen.sector3.3"/>
		</h2>
		<div class="row m-t-30">
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-sm-6 col-lg-3 m-b-20">
					<div id="screenAnalytic${screenAnalytics[i - 1]}" class="screen-column screen-analytic h-100"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_marketplace.jsp"/>