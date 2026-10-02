<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li id="navSearchHome" class="nav-search hidden-md-down">
	<form name="searchHomeForm" action="/" novalidate="novalidate">
		<div class="form-group m-b-0">
			<div class="input-group-find">
				<i class="cmsms-icon-search-1 icon-find"></i>
				<input class="form-control" type="search" id="searchhome" name="searchhome" placeholder="<spring:message code="tool.find.company" />" />
			</div>
		</div>
	</form>
</li>