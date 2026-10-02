<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.10"/></h3></div>
<div class="analytic-content text-center">
	<c:choose>
		<c:when test="${!chartWarehouse.hasPresent()}">
			<i class="cmsms-icon-home-3 i-analytic m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.4"/></span>
		</c:when>
		<c:otherwise>
			<canvas id="chartAnalyticWarehouse" style="height:220px;max-width:100%;"></canvas>
			<c:set var="chosesWarehouse" scope="page"><spring:message code="chose.briefcase2.1"/>,<spring:message code="chose.briefcase2.2"/></c:set>
			<input type="hidden" id="warehousesAnalyticValue" value="${chartWarehouse.countTypesToString()}" />
			<input type="hidden" id="warehousesAnalyticText" value="${pageScope.chosesWarehouse}" />
		</c:otherwise>
	</c:choose>
</div>
</compress:html>