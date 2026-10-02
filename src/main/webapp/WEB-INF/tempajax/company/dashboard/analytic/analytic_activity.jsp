<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline m-t-20">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${analyticActivity.getFormattedSumLogins()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="txt.help.analytic4.1.1" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${analyticActivity.getFormattedSumActivities()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="txt.help.analytic4.1.2" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="chartAnalyticActivity" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="loginAnalyticValue" value="${analyticActivity.countLoginsToString()}" />
	<input type="hidden" id="activityAnalyticValue" value="${analyticActivity.countActivitiesToString()}" />
	<input type="hidden" id="legendsAnalyticActivity" value="${pageScope.chosesMonth}" />
</div>
</compress:html>