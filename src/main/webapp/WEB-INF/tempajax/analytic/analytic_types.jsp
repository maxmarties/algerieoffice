<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.2"/></h3></div>
<div class="analytic-content text-center">
	<c:choose>
		<c:when test="${!chartType.hasPresent()}">
			<i class="cmsms-icon-chart-pie-3 i-analytic m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.1"/></span>
		</c:when>
		<c:otherwise>
			<canvas id="chartAnalyticType" style="height:220px;max-width:100%;"></canvas>
			<c:set var="chosesType" scope="page">
				<c:forEach var="i" begin="1" end="4" step="1"><spring:message code="overview.briefcase3.${i}" /><c:out value="${i < 4 ? ',' : ''}"/></c:forEach>
			</c:set>
			<input type="hidden" id="typesAnalyticValue" value="${chartType.countTypesToString()}" />
			<input type="hidden" id="typesAnalyticText" value="${pageScope.chosesType}" />
		</c:otherwise>
	</c:choose>
</div>
</compress:html>