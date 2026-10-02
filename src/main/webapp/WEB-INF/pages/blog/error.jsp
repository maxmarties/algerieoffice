<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/blog"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li class="active"><c:out value="404"/></li>
	</ol>
</div>
<div class="screen-container screen-blog">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="txt.help.explorer4.3"/></h1></div>
	</div>
	<c:import url="/WEB-INF/fields/blog/blog_categories.jsp"/>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="screen-column">
			<div class="widget-blog-404 m-auto"><img src="<c:url value="/static/picts/images/404-min.png"/>" class="img-responsive"></div>
			<div class="widget-blog-errors text-center m-auto">
				<h2 class="h-header h-header2 i-primary"><spring:message code="txt.blog.error1"/></h2>
				<p class="font-small m-t-5"><spring:message code="txt.blog.error1.1"/></p>
				<hr class="my-6">
				<a href="<c:url value="/blog"/>" class="btn btn-segond btn-add btn-left">
					<span><i class="cmsms-icon-explorer-back"></i><spring:message code="txt.blog.error1.2"/></span>
				</a>
			</div>
		</div>
	</div>
</div>