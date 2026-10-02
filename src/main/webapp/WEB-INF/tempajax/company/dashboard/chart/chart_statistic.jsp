<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartStatistic.getFormattedCounts(9)}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="txt.help.dashboard3.1.4" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartStatistic.getFormattedCounts(10)}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-red m-r-5"></i><spring:message code="txt.help.dashboard3.1.5" /></span>
	</li>
</ul>
<div class="card-body">
	<canvas id="canvaChartStatistic" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="chartStatisticValue" value="${chartStatistic.countsToString()}" />
</div>
</compress:html>