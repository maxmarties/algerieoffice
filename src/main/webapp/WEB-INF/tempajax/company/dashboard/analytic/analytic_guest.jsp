<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="chartAnalyticGuest" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="guestAnalyticValue" value="${analyticGuest.countMonthToString()}" />
	<input type="hidden" id="guestAnalyticText" value="${pageScope.chosesMonth}" />
</div>
<div class="card-footer m-t-10">
	<p class="text-center">
		<c:set var="sumStats" value="${analyticGuest.getSumStats()}" scope="page"></c:set>
		<span class="h-header font-big ${pageScope.sumStats > 0 ? 'i-green' : 'i-red'} m-r-5">
			<i class="cmsms-icon-${pageScope.sumStats > 0 ? 'up-1' : 'down-1'} m-r-5"></i><c:out value="${pageScope.sumStats}"/>
		</span><span class="font-small i-help"><spring:message code="txt.help.analytic5.2.1" /></span>
	</p>
	<div class="row m-t-10">
		<c:set var="providers" value="3,1,2,4" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-sm-6 m-t-20">
				<c:set var="persent" value="${analyticGuest.getPersentStats(pageScope.provider)}" scope="page"></c:set>
				<div class="row">
					<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.persent}%"/></p></div>
					<div class="col-8 text-right">
						<span class="help-text"><spring:message code="txt.help.analytic5.4.${pageScope.provider}"/></span>
						<p class="h-header font-small"><c:out value="${analyticGuest.getFormattedStats(pageScope.provider)}"/></p>
					</div>
				</div>
				<div class="progress m-t-10">
					<div class="progress-bar progress-access${state.count} animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
</compress:html>