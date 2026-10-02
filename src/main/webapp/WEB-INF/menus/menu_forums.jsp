<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<header class="home-menu fixed-top">
	<c:import url="/WEB-INF/fields/navbars/navbar_forums.jsp" />
	<nav class="navbar navbar-pc navbar-forums navbar-expand-lg transition-height">
		<div class="container">
			<div class="navbar-header ${!empty inbox ? 'animated fadeIn' : ''}">
				<a class="navbar-brand i-white hidden-xs-down" href="<c:url value="/forums"/>">
					<img class="transition-height" src="<c:url value="/static/icons/branded-forums.svg"/>" alt="<spring:message code="app.forums"/>" />
				</a>
				<a class="navbar-brand i-white hidden-xs-up" href="<c:url value="/"/>">
					<img height="38" src="<c:url value="/static/icons/apple-white-min.png"/>" alt="<spring:message code="app.brand"/>" />
				</a>
			</div>
			<c:if test="${!empty inbox}">
				<ul class="navbar-nav nav-flex-icons nav-title animated fadeInDown">
					<li><a href="<c:url value="/forums"/>" class="lien-home transition-color"><i class="cmsms-icon-home"></i></a></li>
					<li class="m-l-20 hidden-sm-down">
						<p class="h-header h-header4 i-white sh-black text-truncate"><c:out value="${inbox.title}"/></p>
						<span class="i-gray">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/forums?category=${inbox.category}" />" 
								class="lien lien-segond lien-underline lien-small"><spring:message code="chose.topic.category${inbox.category}"/></a>
						</span>
					</li>
				</ul>
			</c:if>
			<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
				<li class="nav-search hidden-md-down">
					<form name="searchTopicForm" action="/" novalidate="novalidate">
						<div class="form-group m-b-0">
							<div class="input-group-find">
								<i class="cmsms-icon-search-1 icon-find"></i>
								<input class="form-control" type="search" id="searchtopic" name="searchtopic" placeholder="<spring:message code="tool.find.topic" />" value="${token}"/>
							</div>
						</div>
					</form>
				</li>
				<li class="divider"></li>
				<c:import url="/WEB-INF/fields/popups/popup_messages.jsp"/>
				<c:import url="/WEB-INF/fields/popups/popup_notifications.jsp"/>
				<c:import url="/WEB-INF/fields/popups/popup_clouds.jsp"/>
			</ul>
		</div>
	</nav>
</header>