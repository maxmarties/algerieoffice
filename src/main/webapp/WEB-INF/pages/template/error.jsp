<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<div class="card-error text-center m-auto">
		<h1 class="h-header h-error font-bold i-segond1"><c:out value="${empty errorAccess ? '404' : '403'}"/></h1>
		<h2 class="h-header h-header1 i-white sh-black"><spring:message code="txt.help.explorer4.3"/></h2>
		<p class="parag-blog font-large i-gray sh-black m-auto" style="line-height:22px;"><spring:message code="txt.help.explorer4.3.${empty errorState ? '3' : errorState}"/></p>
		<form name="searchScreenForm" action="/" novalidate="novalidate">
			<div class="form-group m-t-30">
				<c:set var="placeholderFind" scope="page"><spring:message code="tool.find.error${empty errorState || errorState == 6 ? '3' : errorState}"/></c:set>
				<div class="input-group-icon">
					<input class="form-control" type="search" type="search" id="findtoken" name="findtoken" placeholder="${pageScope.placeholderFind}" />
					<button type="submit" class="btn btn-transparent btn-simple" title="<spring:message code="btn.find" />">
						<span><i class="cmsms-icon-search-1 i-segond1"></i></span></button>
				</div>
			</div>
		</form>
		<div class="form-group m-t-40 m-b-0" style="padding-top:40px;">
			<i class="cmsms-icon-explorer-arrow i-gray font-mini m-r-10"></i>
			<a href="<c:url value="/" />" class="lien lien-segond1 lien-hover lien-small"><spring:message code="chose.detect.home"/></a>
		</div>
	</div>
	<div class="bn-tours text-center m-auto m-t-20">
		<hr class="m-t-60 i-light">
		<div class="card-thumbnail m-auto" style="padding:40px 60px;"><img class="img-responsive" src="<c:url value="/static/vectors/tours/m_tours7-min.png"/>"
			alt="<spring:message code="txt.solution.tours5.3"/>" style="opacity:.8;"></div>
	</div>
</div>