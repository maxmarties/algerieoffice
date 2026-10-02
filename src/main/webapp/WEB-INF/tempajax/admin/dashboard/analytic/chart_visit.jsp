<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartVisit.getFormattedSumAuthtified()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="txt.help.analytic2.5.3" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartVisit.getFormattedSumAnonyme()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-yellow m-r-5"></i><spring:message code="txt.help.analytic2.5.4" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartVisit" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="authentifiedVisitValue" value="${chartVisit.countAuthentifiedToString()}" />
	<input type="hidden" id="anonymeVisitValue" value="${chartVisit.countAnonymeToString()}" />
	<input type="hidden" id="legendsVisitValue" value="${pageScope.chosesMonth}" />
</div>
<div class="card-footer m-t-10">
	<p class="text-center">
		<span class="h-header font-big ${chartVisit.sumVisit() > 0 ? 'i-green' : 'i-red'} m-r-5">
			<i class="cmsms-icon-${chartVisit.sumVisit() > 0 ? 'up-1' : 'down-1'} m-r-5"></i><c:out value="${chartVisit.parseCountVisit()}"/>
		</span><span class="font-small i-help"><spring:message code="tool.analytic.refering6" /></span>
	</p>
	<div class="row">
		<c:forEach var="i" begin="1" end="4" step="1">
			<div class="col-sm-6 m-t-20">
				<c:set var="persent" value="${chartVisit.getPersentVisit(i)}" scope="page"></c:set>
				<div class="row">
					<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.persent}%"/></p></div>
					<div class="col-8 text-right">
						<span class="help-text"><spring:message code="txt.help.analytic2.5.${i < 3 ? i + 2 : i - 2}"/></span>
						<p class="h-header font-small"><c:out value="${chartVisit.parseCountVisit(i)}"/></p>
					</div>
				</div>
				<div class="progress">
					<div class="progress-bar progress-access${i} animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
</compress:html>