<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartAnalyse.getFormattedSumPro()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="tabs.buildate" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartAnalyse.getFormattedSumIndividualy()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tabs.modified" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartAnalyse" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="createdAnalyseValue" value="${chartAnalyse.countProToString()}" />
	<input type="hidden" id="modifiedAnalyseValue" value="${chartAnalyse.countIndividualyToString()}" />
	<input type="hidden" id="legendsAnalyseValue" value="${pageScope.chosesMonth}" />
</div>
</compress:html>