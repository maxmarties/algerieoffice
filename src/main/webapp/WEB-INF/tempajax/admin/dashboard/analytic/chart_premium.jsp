<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartPremium.getFormattedSumCreate()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tabs.buildate" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartPremium.getFormattedSumExpire()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-red m-r-5"></i><spring:message code="tabs.expire" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartPremium" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="createPremiumValue" value="${chartPremium.countCreateToString()}" />
	<input type="hidden" id="expirePremiumValue" value="${chartPremium.countExpireToString()}" />
	<input type="hidden" id="legendsPremiumValue" value="${pageScope.chosesMonth}" />
</div>
<div class="card-footer m-t-10">
	<p class="text-center">
		<span class="h-header font-big ${chartPremium.countActive > 0 ? 'i-green' : 'i-red'} m-r-5">
			<i class="cmsms-icon-${chartPremium.countActive > 0 ? 'up-1' : 'down-1'} m-r-5"></i><c:out value="${chartPremium.parseCountActive()}"/>
		</span><span class="font-small i-help"><spring:message code="sidebar.admin.dashboard8.1" /></span>
	</p>
	<div class="row">
		<c:forEach var="i" begin="1" end="4" step="1">
			<div class="col-sm-6 m-t-20">
				<c:set var="persent" value="${chartPremium.getPersentPass(i)}" scope="page"></c:set>
				<div class="row">
					<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.persent}%"/></p></div>
					<div class="col-8 text-right">
						<span class="help-text"><spring:message code="chose.subscribe.order${i + 1}"/></span>
						<p class="h-header font-small"><c:out value="${chartPremium.parseCountPass(i)}"/></p>
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