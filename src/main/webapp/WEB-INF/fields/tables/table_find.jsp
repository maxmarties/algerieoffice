<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="table-find">
	<div class="table-overlay"></div>
	<form name="findTableForm" action="/" novalidate="novalidate">
		<div class="form-group m-t-5 m-b-0 ml-auto">
			<div class="input-group-find">
				<i class="cmsms-icon-search-1 icon-find"></i>
				<input class="form-control" type="search" id="findtable" name="findtable" placeholder="${requestScope.placeholderFind}" />
				<button type="submit" class="btn btn-primary btn-simple" title="<spring:message code="btn.find" />">
					<span><spring:message code="btn.ok"/></span></button>
			</div>
		</div>
	</form>
</div>