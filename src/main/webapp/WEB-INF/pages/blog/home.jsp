<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard5"/></li>
	</ol>
</div>
<div class="screen-container screen-blog">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="subheader.blog.home1"/></h1></div>
		<p class="parag-blog text-center m-auto"><spring:message code="txt.blog.home1"/></p>
	</div>
	<c:import url="/WEB-INF/fields/blog/blog_categories.jsp"/>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<c:set var="choseSortersScreen" value="actu,view,title" scope="request"></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_sort.jsp"/>
		<div class="screen-loader m-t-20">
			<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
			<div id="screenLoad"></div>
		</div>
		<c:import url="/WEB-INF/fields/screen/screen_pagination.jsp"/>
	</div>
</div>