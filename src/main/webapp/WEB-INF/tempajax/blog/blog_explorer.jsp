<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${!list.isEmpty() && list.size() >= 3}">
	<div class="blog-explorer-footer m-t-10">
		<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer7"/><i class="cmsms-icon-rss i-dollar m-l-10"></i></h3>
		<div class="blog-explorer-column">
			<div class="row">
				<c:forEach var="line" items="${list}" varStatus="state">
					<div class="col-md-4 m-t-10 m-b-10">
						<div class="flexed flex-colone flex-jusitify h-100">
							<div class="widget-header transition-35">
								<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
								<img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />">
							</div>
							<div class="widget-flexed flexed flex-colone flex-jusitify m-t-10">
								<div><a href="<c:url value="${line.identifyURL}" />" class="h-header h-header5 lien lien-explorer-footer" 
									target="_blank"><c:out value="${line.title}" /></a></div>
								<div class="widget-about-blog m-t-10">
									<ul class="navbar-nav nav-flex-icons font-small">
										<li><span class="h-header"><i class="cmsms-icon-eye m-r-10"></i><c:out value="${line.viewCount}" /></span></li>
										<li class="m-l-20"><span class="h-header"><i class="cmsms-icon-thumbs-up m-r-10"></i><c:out value="${line.likeCount}" /></span></li>
										<li class="ml-auto">
											<a href="<c:url value="${line.categoryURL}" />" class="lien lien-table lien-explorer-footer" target="_blank">
												<spring:message code="chose.blog.family${line.category}"/>
											</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>
</compress:html>