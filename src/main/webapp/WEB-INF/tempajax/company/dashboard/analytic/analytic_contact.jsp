<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="chartAnalyticContact" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="contactAnalyticValue" value="${analyticContact.countMonthToString()}" />
	<input type="hidden" id="contactAnalyticText" value="${pageScope.chosesMonth}" />
</div>
<div class="card-footer m-t-10">
	<p class="text-center">
		<c:set var="sumStats" value="${analyticContact.getSumStats()}" scope="page"></c:set>
		<span class="h-header font-big ${pageScope.sumStats > 0 ? 'i-green' : 'i-red'} m-r-5">
			<i class="cmsms-icon-${pageScope.sumStats > 0 ? 'up-1' : 'down-1'} m-r-5"></i><c:out value="${pageScope.sumStats}"/>
		</span><span class="font-small i-help"><spring:message code="txt.help.analytic5.1.1" /></span>
	</p>
	<div class="row m-t-10">
		<c:forEach var="i" begin="1" end="4">
			<div class="col-sm-6 m-t-20">
				<c:set var="persent" value="${analyticContact.getPersentStats(i)}" scope="page"></c:set>
				<div class="row">
					<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.persent}%"/></p></div>
					<div class="col-8 text-right">
						<span class="help-text"><spring:message code="txt.help.analytic5.3.${i}"/></span>
						<p class="h-header font-small"><c:out value="${analyticContact.getFormattedStats(i)}"/></p>
					</div>
				</div>
				<div class="progress m-t-10">
					<div class="progress-bar progress-access${i} animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
</compress:html>