<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.5"/></h3></div>
<div class="analytic-content text-center">
	<i class="cmsms-icon-dollar i-analytic m-t-10"></i>
	<c:choose>
		<c:when test="${!chartCapital.hasPresent()}">
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.3"/></span>
		</c:when>
		<c:otherwise>
			<p class="h-header font-bold font-value i-primary m-t-30">
				<c:out value="${chartCapital.getFormattedAvgCapital()}"/> <spring:message code="tool.order.devise"/>
			</p>
			<p class="i-help m-t-20">
				<spring:message code="subheader.screen.sector1.2.6"/>
				<span class="block h-header font-big"><c:out value="${chartCapital.getFormattedSumCapital()}"/> <spring:message code="tool.order.devise"/></span>
			</p>
		</c:otherwise>
	</c:choose>
</div>
</compress:html>