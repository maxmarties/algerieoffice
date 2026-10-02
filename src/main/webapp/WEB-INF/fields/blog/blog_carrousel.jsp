<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:if test="${marketBlogs.size() >= 3}">
	<div class="screen-container m-t-10">
		<div class="container">
			<div class="header-screen header-mini"><h1 class="h-header m-auto"><spring:message code="subheader.blog.carousel"/></h1></div>
			<p class="parag-blog parag-mini text-center m-auto"><spring:message code="txt.blog.carousel"/></p>
			<div id="cardScreenBlog" class="card-blog card-carousel">
				<div class="owl-carousel owl-theme">
					<c:forEach var="marketBlog" items="${marketBlogs}" varStatus="state">
						<div class="item">
							<div class="widget-header">
								<a href="<c:url value="${marketBlog.identifyURL}" />" class="inner-link" title="<c:out value="${marketBlog.title}" />"></a>
								<img class="img-responsive transition-35" src="<c:url value="${marketBlog.photoURL}"/>" alt="<c:out value="${marketBlog.title}" />">
							</div>
							<div class="widget-title">
								<a href="<c:url value="${marketBlog.identifyURL}" />" class="lien lien-company"><c:out value="${marketBlog.title}" /></a>
							</div>
							<div class="widget-more">
								<ul class="navbar-nav nav-flex-icons font-small">
									<li><span class="h-header"><i class="cmsms-icon-eye i-primary m-r-10"></i><c:out value="${marketBlog.viewCount}" /></span></li>
									<li class="m-l-20"><span class="h-header"><i class="cmsms-icon-thumbs-up i-primary m-r-10"></i><c:out value="${marketBlog.likeCount}" /></span></li>
									<li class="ml-auto">
										<a href="<c:url value="${marketBlog.categoryURL}" />" class="lien lien-black"><spring:message code="chose.blog.family${marketBlog.category}"/></a>
									</li>		
								</ul>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="text-center m-t-30 m-b-30">
				<a href="<c:url value="/blog" />" class="btn btn-segond btn-big" style="min-width:230px;">
					<span><spring:message code="sidebar.home.dashboard4"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
			</div>
		</div>
	</div>
</c:if>