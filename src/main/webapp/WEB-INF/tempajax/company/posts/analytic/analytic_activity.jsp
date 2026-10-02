<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<ul class="list-none list-inline m-t-10">
	<li class="m-r-40"><span class="help-text"><i class="cmsms-icon-circle i-blue m-r-5"></i><spring:message code="tabs.buildate" /></span></li>
	<li><span class="help-text"><i class="cmsms-icon-circle i-green m-r-5"></i><spring:message code="tabs.modified" /></span></li>
</ul>
<c:set var="chosesMonth" scope="page">
	<c:forEach var="month" items="${months}" varStatus="state"><spring:message code="chose.month${month}" /><c:out value="${state.count < 6 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="chartAnalyticActivity" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="cratedAnalyticValue" value="${activityPost.countCreatedToString()}" />
	<input type="hidden" id="updatedAnalyticValue" value="${activityPost.countUpdatedToString()}" />
	<input type="hidden" id="activityAnalyticText" value="${pageScope.chosesMonth}" />
</div>
</compress:html>