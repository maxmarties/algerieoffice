<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column m-t-10">
	<div class="row">
		<div class="col-lg-6">
			<div class="explorer-404 text-center m-auto">
				<div>
					<h1 class="h-header h-header1"><c:out value="404"/></h1>
					<h2 class="h-header h-header4 text-primary"><spring:message code="txt.help.explorer4.3"/></h2>
					<p class="font-small m-t-5"><spring:message code="txt.help.explorer4.3.1"/></p>
					<hr class="my-6">
					<a href="<c:url value="${explorerCurrent.companyURL}"/>" class="btn btn-explorer-segond btn-add btn-left">
						<span><i class="cmsms-icon-home"></i><spring:message code="btn.explorer.home"/></span>
					</a>
				</div>
			</div>
		</div>
		<div class="col-lg-6"><div class="explorer-404 m-auto"><img src="<c:url value="/static/picts/images/404-min.png"/>" class="img-responsive"></div></div>
	</div>
</div>