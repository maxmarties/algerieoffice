<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="blogCategories" class="blog-categories m-t-10">
	<div class="owl-carousel owl-theme">
		<c:forEach var="urlBlogFamily" items="${urlBlogFamilies}" varStatus="state">
			<div class="item">
				<div class="cat-blog ${urlBlogFamily == categoryURL ? 'active' : ''}">
					<a href="<c:url value="/blog/categorie/${urlBlogFamily}" />" class="inner-link" title="<spring:message code="chose.blog.family${state.count}"/>"></a>
					<div class="inner-content flexed flex-colone flex-jusitify">
						<div>
							<h4 class="h-header h-header4 i-white sh-black"><spring:message code="chose.blog.family${state.count}"/></h4>
							<span class="help-text i-gray m-t-5"><spring:message code="overview.blog.family${state.count}"/></span>
						</div>
						<p class="font-small font-bold i-yellow"><c:out value="${countBlogFamilies.get(state.count - 1)}" /> <spring:message code="overviewer.blog.family${state.count}"/></p>
					</div>
					<div class="inner-mask"></div>
					<div class="inner-thumbnail">
						<img class="img-responsive" src="<c:url value="/static/vectors/aobl/m_aobl${state.count}-min.jpg"/>" alt="<spring:message code="chose.blog.family${state.count}"/>">
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>