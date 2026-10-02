<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline">
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartJournal.getFormattedSumUser()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="tool.dashboard.admin1.1" /></span>
	</li>
	<li class="m-r-40">
		<span class="h-header font-large i-primary"><c:out value="${chartJournal.getFormattedSumCompany()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tool.dashboard.admin1.5" /></span>
	</li>
	<li>
		<span class="h-header font-large i-primary"><c:out value="${chartJournal.getFormattedSumAdmin()}"/></span>
		<span class="help-text"><i class="cmsms-icon-circle i-yellow m-r-5"></i><spring:message code="tool.dashboard.admin5.1" /></span>
	</li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body m-t-10">
	<canvas id="canvaChartJournal" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="userJournalValue" value="${chartJournal.countUserToString()}" />
	<input type="hidden" id="companyJournalValue" value="${chartJournal.countCompanyToString()}" />
	<input type="hidden" id="adminJournalValue" value="${chartJournal.countAdminToString()}" />
	<input type="hidden" id="legendsJournalValue" value="${pageScope.chosesMonth}" />
</div>
</compress:html>