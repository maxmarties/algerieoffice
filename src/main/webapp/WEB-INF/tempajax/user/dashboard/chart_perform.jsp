<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div id="circleChartPerform" class="circle-3d circle-completed m-auto" data-counter="${chartPerform}">
	<div class="circle-content text-center"><span class="h-header font-bold i-segond"><c:out value="${chartPerform}%" /></span></div>
	<div id="circleCanvasPerform" class="circle-canvas"></div>
</div>
<c:set var="persent" value="${chartPerform <= 25 ? 1 : chartPerform <= 50 ? 2 : chartPerform <= 75 ? 3 : 4}" scope="page"></c:set>
<c:choose>
	<c:when test="${pageScope.persent == 4}">
		<span class="help-text text-center m-t-20"><i class="cmsms-icon-feather i-dollar m-r-10"></i><spring:message code="txt.help.dashboard6.1.5" /></span>
	</c:when>
	<c:otherwise><span class="help-text text-center m-t-20"><spring:message code="txt.help.dashboard6.1.4" /></span></c:otherwise>
</c:choose>
<div class="widget-more widget-progress font-small m-t-10">
	<span class="font-bold"><spring:message code="tool.dashboard.welcome1.3.5" /></span> : <spring:message code="chose.user.award${pageScope.persent}" />
	<div class="progress m-t-5">
		<div class="progress-bar progress-primary animated onne progress-animated" style="width:${pageScope.persent * 25}%" role="progressbar"></div>
	</div>
</div>
</compress:html>