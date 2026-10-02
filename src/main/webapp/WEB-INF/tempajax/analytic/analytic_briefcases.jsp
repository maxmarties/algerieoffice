<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.8"/></h3></div>
<div class="analytic-content text-center">
	<c:choose>
		<c:when test="${!chartBriefcase.hasPresent()}">
			<i class="cmsms-icon-diagram i-analytic m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.3"/></span>
		</c:when>
		<c:otherwise>
			<canvas id="chartAnalyticBriefcase" style="height:220px;max-width:100%;"></canvas>
			<c:set var="chosesBriefcase" scope="page">
				<c:forEach var="i" begin="1" end="10" step="1"><spring:message code="overview.briefcase${i}" /><c:out value="${i < 10 ? ',' : ''}"/></c:forEach>
			</c:set>
			<input type="hidden" id="briefcasesAnalyticValue" value="${chartBriefcase.countTypesToString()}" />
			<input type="hidden" id="briefcasesAnalyticText" value="${pageScope.chosesBriefcase}" />
		</c:otherwise>
	</c:choose>
</div>
</compress:html>