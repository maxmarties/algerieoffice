<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.7"/></h3></div>
<div class="analytic-content text-center">
	<canvas id="chartAnalyticBuild" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="buildsAnalyticValue" value="${chartBuild.countTypesToString()}" />
	<input type="hidden" id="buildsAnalyticText" value="${yearsBuild}" />
</div>
</compress:html>