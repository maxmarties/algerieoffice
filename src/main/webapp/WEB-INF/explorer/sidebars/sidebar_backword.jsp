<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-backword m-t-10">
	<ul class="nav nav-pagination">
		<li class="m-r-15"><a href="<c:url value="${requestScope.hrefBackword}"/>" class="btn btn-explorer-icon btn-simple"><i class="cmsms-icon-explorer-back"></i></a></li>
		<li class="h-header"><spring:message code="explorer.desktop.backword${requestScope.explorerBackword}"/></li>
	</ul>
</div>