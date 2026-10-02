<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/blog"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li class="active"><c:out value="${inbox.title}"/></li>
	</ol>
</div>
<div class="screen-container screen-blog ${!empty sponsoreScreen ? 'screen-aobubs' : ''}">
	<c:if test="${!empty sponsoreScreen}"><div class="container"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></div></c:if>
	<c:import url="/WEB-INF/fields/blog/blog_categories.jsp"/>
</div>
<c:set var="hrefBackword" value="/blog" scope="request"></c:set>
<c:set var="screenBackword" value="8" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_backword.jsp"/>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="row row-mini">
			<div class="col-lg-9 col-mini m-t-10 m-b-10">
				<div class="screen-column widget-article">
					<div class="widget-category">
						<a href="<c:url value="${inbox.categoryURL}" />" class="tag-blog transition-color">
							<i class="cmsms-icon-folder-2 i-segond m-r-10"></i><spring:message code="chose.blog.family${inbox.category}"/>
						</a>
					</div>
					<h1 class="h-blog h-doc1"><c:out value="${inbox.title}"/></h1>
					<div class="widget-navbar">
						<ul class="navbar-nav nav-flex-icons">
							<li class="link-autor">
								<img src="<c:url value="${inbox.autorAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${inbox.autorname}" />">
								<span class="autor-brand">
									<spring:message code="tool.view.autor"/> : <a href="<c:url value="${inbox.autorURL}"/>" class="lien lien-black"><c:out value="${inbox.autorname}" /></a>
								</span>
								<span class="clearfix"></span>
							</li>
							<li class="link-comment h-header"><i class="cmsms-icon-eye-1 i-primary m-r-10"></i><c:out value="${inbox.viewCount}" /></li>
							<li>
								<a class="link-like transition-35 iLike ${hasLiked ? 'active' : ''}">
									<i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
									<span class="iLikeCount animated m-l-10"><c:out value="${inbox.likeCount}" /></span>
								</a>
							</li>
							<li class="ind-time h-header ml-auto">
								<i class="cmsms-icon-clock-5 i-blue m-r-5"></i><span class="i-help"><c:out value="${inbox.modifiedDate}" /></span>
							</li>
						</ul>
					</div>
					<div class="article-background background-container" style="background-image: url('${inbox.photoURL}');"></div>
					<div class="widget-detail">
						<div class="fr-view fr-explorer"><c:out value="${inbox.detail}" escapeXml="false" /></div>
						<c:if test="${!empty inbox.keysword}">
							<hr class="my-2">
							<ul class="list-keysword list-none">
								<c:forEach var="keyword" items="${inbox.buildKeysword()}">
									<li>
										<i class="cmsms-icon-tag i-segond i-tags m-r-5"></i>
										<a href="<c:url value="/blog/tag/${inbox.parsKey(keyword)}" />" class="lien lien-keyword"><c:out value="${keyword}" /></a>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<div class="widget-navbar widget-about-post">
						<ul class="navbar-nav nav-flex-icons">
							<li class="ind-time i-help"><spring:message code="tool.view.niced"/></li>
							<li class="m-l-20">
								<a class="link-like transition-35 iLike ${hasLiked ? 'active' : ''}">
									<i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
									<span class="iLikeCount animated m-l-10"><c:out value="${inbox.likeCount}" /></span>
								</a>
							</li>
							<li class="ml-auto">
								<a class="icon-facebook transition-35 iFollow" title="<spring:message code="tool.navigate.company5.2" arguments="Facebook" />"
									href="<c:url value="https://www.facebook.com/sharer/sharer.php?url=${mapsiteURL}" />"><i class="cmsms-icon-facebook"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-twitter transition-35 iFollow" title="<spring:message code="tool.navigate.company5.2" arguments="Twitter" />"
									href="<c:url value="https://twitter.com/intent/tweet?url=${mapsiteURL}" />"><i class="cmsms-icon-twitter"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-google transition-35 iFollow" title="<spring:message code="tool.navigate.company5.2" arguments="Google" />"
									href="<c:url value="https://plus.google.com/share?url=${mapsiteURL}" />"><i class="cmsms-icon-google"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-linkedin transition-35 iFollow" title="<spring:message code="tool.navigate.company5.2" arguments="Linkedin" />"
									href="<c:url value="https://www.linkedin.com/shareArticle?url=${mapsiteURL}" />"><i class="cmsms-icon-linkedin"></i></a>
							</li>
							<li class="m-l-5">
								<a class="icon-viadeo transition-35 iFollow" title="<spring:message code="tool.navigate.company5.2" arguments="Viadeo" />"
									href="<c:url value="https://www.viadeo.com/shareit/share/?url=${mapsiteURL}" />"><i class="cmsms-icon-viadeo"></i></a>
							</li>
						</ul>
					</div>
				</div>
				<div id="authDivider" class="explorer-auth-hr text-center m-t-20 m-b-20">
					<span class="line"></span><span class="h-header"><spring:message code="subheader.blog.simultude"/></span>
				</div>
				<div id="loadSimultude"></div>
			</div>
			<div class="col-lg-3 col-mini m-b-10">
				<div class="row row-mini">
					<div class="col-md-6 col-lg-12 col-mini m-t-10">
						<div class="screen-column h-100">
							<div class="screen-title"><h2 class="h-doc h-doc3"><spring:message code="subheader.blog.popular"/></h2></div>
							<div id="loadPopular" class="widget-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
						</div>
					</div>
					<div class="col-md-6 col-lg-12 col-mini m-t-10">
						<div class="screen-column h-100">
							<div class="screen-title"><h2 class="h-doc h-doc3"><spring:message code="subheader.blog.recent"/></h2></div>
							<div id="loadRecent" class="widget-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
						</div>
					</div>
				</div>
				<div class="screen-sticky m-t-10 hidden-md-down"><c:import url="/WEB-INF/fields/screen/screen_newsletter.jsp"/></div>
			</div>
		</div>
	</div>
</div>