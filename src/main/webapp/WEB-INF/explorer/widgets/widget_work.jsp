<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="explorer-column explorer-work h-100 m-auto">
	<div class="widget-header">
		<a href="<c:url value="${requestScope.previewWork.identifyURL}" />" class="inner-link" title="<c:out value="${requestScope.previewWork.title}" />"></a>
		<div class="inner-overlay"><i class="cmsms-icon-plus-2"></i></div>
		<div class="inner-thumbnail">
			<img class="img-responsive" src="<c:url value="${requestScope.previewWork.photoURL}"/>" alt="<c:out value="${requestScope.previewWork.title}" />">
		</div>
	</div>
	<div class="explorer-title">
		<a href="<c:url value="${requestScope.previewWork.identifyURL}" />" 
			class="h-doc lien lien-explorer-title"><c:out value="${requestScope.previewWork.title}" /></a>
	</div>
	<div class="explorer-body">
		<p class="font-small text-segond"><c:out value="${requestScope.previewWork.expertise}" /></p>
		<span class="font-mini text-help"><joda:format value="${requestScope.previewWork.workDate}" pattern="MMMM dd, yyyy"></joda:format></span>
	</div>
</div>