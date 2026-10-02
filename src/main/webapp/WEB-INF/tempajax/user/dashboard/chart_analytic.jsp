<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-30">
		<span class="h-header font-large i-primary"><c:out value="${chartAnalytic.getFormattedSumAccess()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="txt.help.dashboard6.3.1" /></span>
	</li>
	<li class="m-r-30">
		<span class="h-header font-large i-primary"><c:out value="${chartAnalytic.getFormattedSumFavorites()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="txt.help.dashboard6.3.2" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartAnalytic.getFormattedSumHistories()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-red m-r-5"></i><spring:message code="txt.help.dashboard6.3.3" /></span>
	</li>
</ul>
<c:set var="chosesDay" scope="page">
	<c:forEach var="day" items="${days}" varStatus="state"><spring:message code="chose.day${day}" /><c:out value="${state.count < 7 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartAnalytic" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="accessAnalyticValue" value="${chartAnalytic.countAccessToString()}" />
	<input type="hidden" id="favoritesAnalyticValue" value="${chartAnalytic.countFavoritesToString()}" />
	<input type="hidden" id="historiesAnalyticValue" value="${chartAnalytic.countHistoriesToString()}" />
	<input type="hidden" id="legendsAnalyticValue" value="${pageScope.chosesDay}" />
</div>
</compress:html>