<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/blog"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li class="active"><spring:message code="tool.find.resultset"/></li>
	</ol>
</div>
<div class="screen-container screen-blog">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="tool.find.resultset"/></h1></div>
	</div>
	<c:import url="/WEB-INF/fields/blog/blog_categories.jsp"/>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary">
			(<span class="countLine"></span>) <spring:message code="subheader.blog.search1"/> "<span class="i-segond"><c:out value="${search}"/></span>"
		</h2>
		<hr class="my-4">
		<c:set var="choseSortersScreen" value="actu,view,title" scope="request"></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_sort.jsp"/>
		<div class="screen-loader m-t-20">
			<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
			<div id="screenLoad"></div>
		</div>
		<c:import url="/WEB-INF/fields/screen/screen_pagination.jsp"/>
	</div>
</div>