<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div id="circleChartCompleted" class="circle-3d circle-completed m-auto" data-counter="${completedChart}">
	<div class="circle-content text-center"><span class="h-header font-bold i-segond"><c:out value="${completedChart}%" /></span></div>
	<div id="circleCanvasCompleted" class="circle-canvas"></div>
</div>
<c:choose>
	<c:when test="${completedChart <= 80}">
		<span class="help-text m-t-20"><spring:message code="txt.help.dashboard1.3.1" /></span>
		<div class="widget-more m-t-10">
			<a href="<c:url value="/company/help/begginer"/>" class="btn btn-segond btn-fixed"><span><spring:message code="txt.help.dashboard1.3.2"/></span></a>
		</div>
	</c:when>
	<c:otherwise><span class="help-text m-t-20"><i class="cmsms-icon-award i-dollar m-r-5"></i><spring:message code="txt.help.dashboard1.3" /></span></c:otherwise>
</c:choose>
</compress:html>