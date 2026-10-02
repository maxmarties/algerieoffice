<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="${wilayaURL}"/>" class="lien lien-black"><spring:message code="chose.wilaya${wilaya}"/></a></li>
		<li><a href="<c:url value="${sectorURL}"/>" class="lien lien-black"><spring:message code="chose.sector${sector}"/></a></li>
		<li class="active"><spring:message code="chose.activity.${activity.code}"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen">
			<h1 class="h-header m-auto">
				<spring:message code="chose.activity.${activity.code}"/> <spring:message code="subheader.screen.wilaya2.2"/> "<spring:message code="chose.wilaya${wilaya}"/>"
			</h1>
		</div>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.company"/></c:set>
		<c:set var="placeholderResult" scope="request"><spring:message code="tool.result.screen"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary">
			<spring:message code="subheader.screen.sector4.2"/> "<spring:message code="chose.activity.${activity.code}"/>" 
			<spring:message code="subheader.screen.wilaya2.2"/> "<spring:message code="chose.wilaya${wilaya}"/>" 
			<span id="headerScreenResult" style="display:none">
				(<span id="countFormatted" class="font-strong"></span>)
				<span id="textPageScreen"> - <spring:message code="tool.pagination.curr"/> <span id="pageScreenCurr">1</span></span>
			</span> 
		</h2>
		<div id="screenElements">
			<div class="row row-mini">
				<div class="col-md-4 col-mini"><c:import url="/WEB-INF/fields/screen/screen_easylist.jsp"/></div>
				<div class="col-md-8 col-mini">
					<c:set var="choseSortersScreen" value="date,actu,view,company" scope="request"></c:set>
					<c:import url="/WEB-INF/fields/screen/screen_sort.jsp"/>
				</div>
			</div>
			<div class="screen-loader m-t-20">
				<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
				<div id="screenLoad"></div>
			</div>
			<c:import url="/WEB-INF/fields/screen/screen_pagination.jsp"/>
		</div>
		<div class="explorer-auth-hr m-t-20 m-b-20"><span class="line"></span></div>
		<h2 class="h-header h-header3 i-primary">
			<spring:message code="chose.activity.${activity.code}"/> - <spring:message code="subheader.screen.sector4.3"/> "<spring:message code="chose.wilaya${wilaya}"/>"
		</h2>
		<div id="screenAnalytic" class="row m-t-30">
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-sm-6 col-lg-3 m-b-20">
					<div id="screenAnalytic${screenAnalytics[i - 1]}" class="screen-column screen-analytic h-100"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_marketplace.jsp"/>