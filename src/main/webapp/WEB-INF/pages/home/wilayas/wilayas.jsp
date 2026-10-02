<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/entreprises"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li class="active"><spring:message code="explorer.home.mainmenu2.2"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen"><h1 class="h-header m-auto"><spring:message code="subheader.screen.wilaya1"/></h1></div>
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.wilaya1.1"/></h2>
		<div class="row m-t-30">
			<div class="col-lg-4 m-b-20"><div class="vector-map vector-screen-sm"><div id="vmapRegion" class="vmap"></div></div></div>
			<div class="col-lg-8 m-t-20 m-b-20">
				<div class="row">
					<c:forEach var="i" begin="0" end="3" step="1">
						<div class="col-6 col-md-3">
							<ul class="list-none">
								<c:forEach var="j" begin="${(i * 12) + 1}" end="${(i + 1) * 12}" step="1">
									<li class="m-b-5">
										<i class="cmsms-icon-explorer-angle i-select m-r-5"></i><a href="<c:url value="/villes/${wilayas[j - 1]}"/>" class="lien lien-table" 
											title="<spring:message code="chose.wilaya${j}"/>" data-region="${j}">
											<strong><c:out value="${j < 10 ? '0' : ''}${j}" /></strong> - <spring:message code="chose.wilaya${j}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>		
				</div>
			</div>
		</div>
	</div>
</div>
<div id="screenAnalytic" class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.sector1.2"/></h2>
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