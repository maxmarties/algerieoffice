<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.4"/></h3></div>
<div class="analytic-content text-center">
	<c:choose>
		<c:when test="${!chartAgent.hasPresent()}">
			<i class="cmsms-icon-user-group i-analytic m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.2"/></span>
		</c:when>
		<c:otherwise>
			<canvas id="chartAnalyticAgent" style="height:220px;max-width:100%;"></canvas>
			<c:set var="chosesAgent" scope="page"><spring:message code="overview.sexe1"/>,<spring:message code="overview.sexe2"/></c:set>
			<input type="hidden" id="agentsAnalyticValue" value="${chartAgent.countTypesToString()}" />
			<input type="hidden" id="agentsAnalyticText" value="${pageScope.chosesAgent}" />
		</c:otherwise>
	</c:choose>
</div>
</compress:html>