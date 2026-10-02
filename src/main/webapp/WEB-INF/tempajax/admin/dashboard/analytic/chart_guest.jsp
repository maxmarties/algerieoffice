<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartGuest.getFormattedSumUser()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="tool.dashboard.admin6.6.1" /></span>
	</li>
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartGuest.getFormattedSumCompany()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tool.dashboard.admin6.6.2" /></span>
	</li>
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartGuest.getFormattedSumCompany()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-yellow m-r-5"></i><spring:message code="tool.dashboard.admin6.6.3" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartGuest.getFormattedSumGuest()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-red m-r-5"></i><spring:message code="tool.dashboard.admin6.6.4" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartGuest" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="postGuestValue" value="${chartGuest.countUserToString()}" />
	<input type="hidden" id="adsGuestValue" value="${chartGuest.countCompanyToString()}" />
	<input type="hidden" id="eventGuestValue" value="${chartGuest.countAdminToString()}" />
	<input type="hidden" id="jobGuestValue" value="${chartGuest.countGuestToString()}" />
	<input type="hidden" id="legendsGuestValue" value="${pageScope.chosesMonth}" />
</div>
</compress:html>