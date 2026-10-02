<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/entreprises"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li><a href="<c:url value="/secteurs"/>" class="lien lien-black"><spring:message code="explorer.home.mainmenu2.1"/></a></li>
		<li class="active"><spring:message code="chose.sector${sector}"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen"><h1 class="h-header m-auto"><spring:message code="tabs.sector"/> <spring:message code="chose.sector${sector}"/></h1></div>
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.sector2.1"/> <spring:message code="chose.sector${sector}"/></h2>
		<div class="row m-t-30 m-b-20">
			<c:set var="size" value="${activities.size()}" scope="page"></c:set>
			<c:set var="tab" value="${pageScope.size <= 16 ? 1 : 2}" scope="page"></c:set>
			<div class="col-md-6">
				<ul class="list-none">
					<c:forEach var="i" begin="1" end="${pageScope.tab == 1 ? pageScope.size : (pageScope.size / 2) + (pageScope.size % 2)}" step="1">
						<c:set var="activity" value="${activities.get(i - 1)}" scope="page"></c:set>
						<li class="m-b-5">
							<i class="cmsms-icon-explorer-angle i-select m-r-5"></i><a href="<c:url value="${pageScope.activity.activityURL}"/>" class="lien lien-table" 
								title="<spring:message code="chose.activity.${pageScope.activity.code}"/>"><spring:message code="chose.activity.${pageScope.activity.code}"/></a>
							<span class="font-small i-select">(<c:out value="${pageScope.activity.count}"/>)</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<c:if test="${pageScope.tab == 2}">
				<div class="col-md-6">
					<ul class="list-none">
						<c:forEach var="i" begin="${(pageScope.size / 2) + (pageScope.size % 2) + 1}" end="${pageScope.size}" step="1">
							<c:set var="activity" value="${activities.get(i - 1)}" scope="page"></c:set>
							<li class="m-b-5">
								<i class="cmsms-icon-explorer-angle i-select m-r-5"></i><a href="<c:url value="${pageScope.activity.activityURL}"/>" class="lien lien-table" 
									title="<spring:message code="chose.activity.${pageScope.activity.code}"/>"><spring:message code="chose.activity.${pageScope.activity.code}"/></a>
								<span class="font-small i-select">(<c:out value="${pageScope.activity.count}"/>)</span>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
	</div>
</div>
<div id="screenAnalytic" class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.screen.sector2.2"/> <spring:message code="chose.sector${sector}"/></h2>
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