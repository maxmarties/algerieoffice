<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.9"/></h3></div>
<div class="analytic-content text-center">
	<canvas id="chartAnalyticMonth" style="height:220px;max-width:100%;"></canvas>
	<c:set var="chosesMonth" scope="page">
		<c:forEach var="month" items="${months}" varStatus="state">
			<spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/>
		</c:forEach>
	</c:set>
	<input type="hidden" id="monthsAnalyticValue" value="${chartMonth.countTypesToString()}" />
	<input type="hidden" id="monthsAnalyticText" value="${pageScope.chosesMonth}" />
</div>
</compress:html>