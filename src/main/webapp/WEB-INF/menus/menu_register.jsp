<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<header class="register-menu">
	<nav class="navbar">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand i-white hidden-xs-down" href="<c:url value="/"/>">
					<img height="38" src="<c:url value="/static/icons/branded-white.svg"/>" alt="<spring:message code="app.brand"/>" />
				</a>
				<a class="navbar-brand i-white hidden-xs-up" href="<c:url value="/"/>">
					<img height="38" src="<c:url value="/static/icons/apple-white-min.png"/>" alt="<spring:message code="app.brand"/>" />
				</a>
			</div>
			<ul class="navbar-nav ml-auto">
				<li class="nav-text">
					<a href="mailto:<spring:message code="app.contact" />" class="nav-link lien" title="<spring:message code="tooltip.contact"/>">
						<i class="cmsms-icon-mail-alt m-r-5"></i><spring:message code="app.contact" /></a>
				</li>
			</ul>
		</div>
	</nav>
</header>