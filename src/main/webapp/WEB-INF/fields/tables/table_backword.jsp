<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="page-backword ${empty requestScope.backwordBorder ? 'm-b-10' : 'ignoreBorder'}">
	<ul class="nav nav-pagination">
		<li class="m-r-15"><a href="<c:url value="${requestScope.backwordURL}"/>" class="btn btn-icon btn-simple"><i class="cmsms-icon-explorer-back"></i></a></li>
		<li class="h-header"><spring:message code="explorer.page.backword${requestScope.backwordPage}"/></li>
	</ul>
</div>