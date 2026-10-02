<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.12"/></h3></div>
<div class="analytic-content text-center">
	<c:choose>
		<c:when test="${!chartRegion.hasPresent()}">
			<i class="cmsms-icon-globe-alt-2 i-analytic m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.5"/></span>
		</c:when>
		<c:otherwise>
			<canvas id="chartAnalyticRegion" style="height:220px;max-width:100%;"></canvas>
			<c:set var="chosesRegion" scope="page">
				<c:forEach var="i" begin="1" end="4" step="1"><spring:message code="chose.region${i}" /><c:out value="${i < 4 ? ',' : ''}"/></c:forEach>
			</c:set>
			<input type="hidden" id="regionsAnalyticValue" value="${chartRegion.countTypesToString()}" />
			<input type="hidden" id="regionsAnalyticText" value="${pageScope.chosesRegion}" />
		</c:otherwise>
	</c:choose>
</div>
</compress:html>