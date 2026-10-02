<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.blog" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${list.lines}" varStatus="state">
				<div class="col-md-6 m-b-20">
					<div class="screen-column widget-blog flexed flex-colone flex-jusitify h-100" data-dir="${line.language == 'ar' ? 'rtl' : 'ltr'}">
						<div class="widget-window">
							<div class="widget-header">
								<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
								<div class="inner-overlay"></div>
								<div class="inner-label p-left">
									<span class="ind-language text-center text-uppercase ind-${line.language}"><c:out value="${line.language}"/></span>
								</div>
								<div class="inner-thumbnail"><img class="img-responsive transition-35" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
							</div>
						</div>
						<div class="widget-flexed flexed flex-colone flex-jusitify">
							<div>
								<a href="<c:url value="${line.identifyURL}" />" class="h-blog h-doc2 lien sh-black explorer-result"><c:out value="${line.title}" /></a>
								<div class="blog-description i-help"><p><c:out value="${line.description}" /></p></div>
							</div>
							<div class="widget-descriptif">
								<div class="widget-about-post">
									<ul class="navbar-nav nav-flex-icons">
										<li>
											<a href="<c:url value="${line.categoryURL}" />" class="tag-blog transition-color">
												<i class="cmsms-icon-folder-2 i-segond m-r-10"></i><spring:message code="chose.blog.family${line.category}"/>
											</a>
										</li>
										<li><span class="ind-blog h-header"><i class="cmsms-icon-eye-1 i-primary m-r-10"></i><c:out value="${line.viewCount}" /></span></li>
										<li><span class="ind-blog h-header"><i class="cmsms-icon-thumbs-up-2 i-primary m-r-10"></i><c:out value="${line.likeCount}" /></span></li>
										<li class="ml-auto">
											<span class="ind-blog h-header"><i class="cmsms-icon-clock-5 i-blue m-r-5"></i><span class="i-help"><c:out value="${line.modifiedDate}" /></span></span>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countBlogResult" value="${list.countResult}" />
<input type="hidden" id="countBlogSize" value="${list.lines.size()}" />
</compress:html>