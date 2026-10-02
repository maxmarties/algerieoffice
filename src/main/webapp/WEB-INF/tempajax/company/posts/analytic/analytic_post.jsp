<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="chartAnalyticPost" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="postAnalyticValue" value="${analyticPost.countMonthToString()}" />
	<input type="hidden" id="postAnalyticText" value="${pageScope.chosesMonth}" />
</div>
<div class="card-footer m-t-10">
	<c:set var="persent" value="${analyticPost.getPersentStatsPost()}" scope="page"></c:set>
	<span class="font-small i-help"><spring:message code="txt.help.statistic1.2" /></span>
	<span class="h-header font-large i-primary pull-right">
		<i class="cmsms-icon-${pageScope.persent >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-10"></i><c:out value="${pageScope.persent}%"/>
	</span>
	<span class="clearfix"></span>
</div>
</compress:html>