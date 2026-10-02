<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartTrafic.getFormattedSumLogins()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="txt.help.dashboard3.1.1" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartTrafic.getFormattedSumActivities()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="txt.help.dashboard3.1.2" /></span>
	</li>
</ul>
<c:set var="chosesDay" scope="page">
	<c:forEach var="day" items="${days}" varStatus="state"><spring:message code="chose.day${day}" /><c:out value="${state.count < 7 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="canvaChartTrafic" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="loginTraficValue" value="${chartTrafic.countLoginsToString()}" />
	<input type="hidden" id="accessTraficValue" value="${chartTrafic.countActivitiesToString()}" />
	<input type="hidden" id="legendsTraficValue" value="${pageScope.chosesDay}" />
</div>
</compress:html>