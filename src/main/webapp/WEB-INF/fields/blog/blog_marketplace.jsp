<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-container m-t-10">
	<div class="container">
		<div class="header-screen header-mini"><h1 class="h-header m-auto"><spring:message code="subheader.blog.market"/></h1></div>
		<p class="text-center"><spring:message code="txt.blog.market"/></p>
		<c:if test="${!marketBlogs.isEmpty()}">
			<div id="cardScreenBlog" class="card-blog card-block m-b-30">
				<div class="owl-carousel owl-theme">
					<c:forEach var="marketBlog" items="${marketBlogs}" varStatus="state">
						<div class="item">
							<div class="row">
								<div class="col-lg-6"><div class="card-thumbnail"><img class="img-responsive" src="<c:url value="${marketBlog.photoURL}"/>" 
									alt="<c:out value="${marketBlog.title}" />"></div></div>
								<div class="col-lg-6">
									<div class="card-content text-${marketBlog.language == 'ar' ? 'ar' : 'fr'}">
										<h2 class="h-header h-header2 font-bold i-segond"><c:out value="${marketBlog.title}" /></h2>
										<span class="help-text m-t-10">
											<img class="img-vector m-r-5" height="14" src="<c:url value="/static/vectors/${marketBlog.language}-min.png" />" alt="${langage.lang}">
											<c:out value="${marketBlog.language == 'fr' ? 'Français' : marketBlog.language == 'en' ? 'English' : 'العربية'}" />
										</span>
										<p class="font-big m-t-10"><c:out value="${marketBlog.description}" /></p>
										<hr class="my-6">
										<a href="<c:url value="${marketBlog.identifyURL}" />" class="btn btn-primary btn-fixed btn-flat">
											<span><spring:message code="btn.explorer.more"/></span></a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</div>
</div>