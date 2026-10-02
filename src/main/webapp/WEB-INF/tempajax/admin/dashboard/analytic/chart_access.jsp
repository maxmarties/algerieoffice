<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartAccess.getFormattedSumPro()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tool.dashboard.admin1.5" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartAccess.getFormattedSumIndividualy()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-yellow m-r-5"></i><spring:message code="tool.dashboard.admin1.1" /></span>
	</li>
</ul>
<c:set var="chosesDay" scope="page">
	<c:forEach var="day" items="${days}" varStatus="state"><spring:message code="chose.day${day}" /><c:out value="${state.count < 7 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartAccess" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="proAccessValue" value="${chartAccess.countProToString()}" />
	<input type="hidden" id="indAccessValue" value="${chartAccess.countIndividualyToString()}" />
	<input type="hidden" id="legendsAccessValue" value="${pageScope.chosesDay}" />
</div>
</compress:html>