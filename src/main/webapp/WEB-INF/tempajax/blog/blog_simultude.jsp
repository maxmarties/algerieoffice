<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="line" items="${list}" varStatus="state">
	<div class="screen-column widget-blog wdget-mini m-b-10">
		<div class="row row-mini">
			<div class="col-md-5 col-mini">
				<div class="widget-window">
					<div class="widget-header">
						<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
						<div class="inner-overlay"></div>
						<div class="inner-thumbnail"><img class="img-responsive transition-35" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
					</div>
				</div>
			</div>
			<div class="col-md-7 col-mini">
				<div class="widget-body">
					<a href="<c:url value="${line.identifyURL}" />" class="h-blog h-doc2 lien sh-black"><c:out value="${line.title}" /></a>
					<div class="blog-description i-help"><p><c:out value="${line.description}" /></p></div>
				</div>
				<div class="widget-about-post">
					<ul class="navbar-nav nav-flex-icons">
						<li><span class="ind-blog h-header"><i class="cmsms-icon-eye-1 i-primary m-r-10"></i><c:out value="${line.viewCount}" /></span></li>
						<li><span class="ind-blog h-header"><i class="cmsms-icon-thumbs-up-2 i-primary m-r-10"></i><c:out value="${line.likeCount}" /></span></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
</compress:html>