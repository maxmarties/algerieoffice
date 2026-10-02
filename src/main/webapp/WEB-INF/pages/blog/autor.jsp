<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/blog"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="sidebar.admin.dashboard5.4"/></a></li>
		<li class="active"><c:out value="${autor.autorname}"/></li>
	</ol>
</div>
<div class="screen-container screen-blog">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="subheader.blog.autor1"/> <c:out value="${autor.autorname}"/></h1></div>
	</div>
	<c:import url="/WEB-INF/fields/blog/blog_categories.jsp"/>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="widget-blog-autor">
			<div class="img-contianer pull-left"><img src="<c:url value="${autor.urlAvatar}"/>" class="img-responsive img-circle" alt="<c:out value="${autor.autorname}" />"></div>
			<div class="blog-brand">
				<h3 class="h-header h-header4 i-primary"><c:out value="${autor.autorname}"/></h3>
				<span class="help-text"><c:out value="${autor.function}"/></span>
				<hr class="my-1">
				<p class="font-small"><c:out value="${autor.biography}"/></p>
				<ul class="navbar-nav nav-flex-icons list-inline list-blog-linked m-t-20">
					<c:if test="${!empty autor.email}">
						<li><a href="mailto:<c:out value="${autor.email}"/>" class="lien" target="_blank"><i class="cmsms-icon-mail-alt"></i></a></li>
					</c:if>
					<c:if test="${autor.hasPresentSocial()}">
						<c:set var="providers" value="facebook,twitter,linkedin" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<c:if test="${!empty autor.socialURL[state.count - 1]}">
								<li><a href="<c:url value="${autor.socialURL[state.count - 1]}"/>" class="lien" target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
			</div>
			<span class="clearfix"></span>
		</div>
		<div class="explorer-auth-hr m-t-20"><span class="line"></span></div>
		<c:set var="choseSortersScreen" value="actu,view,title" scope="request"></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_sort.jsp"/>
		<div class="screen-loader m-t-20">
			<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
			<div id="screenLoad"></div>
		</div>
		<c:import url="/WEB-INF/fields/screen/screen_pagination.jsp"/>
	</div>
</div>