<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-backword screen-noprint">
	<div class="container">
		<ul class="nav nav-pagination">
			<li class="m-r-15"><a href="<c:url value="${requestScope.hrefBackword}"/>" class="btn btn-icon btn-simple"><i class="cmsms-icon-explorer-back"></i></a></li>
			<li class="h-header"><spring:message code="explorer.desktop.backword${requestScope.screenBackword}"/></li>
		</ul>
	</div>
</div>