<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<p class="h-header font-large i-primary"><c:out value="${chartContact.getFormattedSum()}"/></p>
<span class="help-text m-t-5"><spring:message code="txt.help.dashboard4.1.${currContact == 'Communication' ? '1' : '2'}" /></span>
<c:set var="chosesDay" scope="page">
	<c:forEach var="day" items="${days}" varStatus="state"><spring:message code="chose.days${day}" /><c:out value="${state.count < 7 ? ',' : ''}"/></c:forEach>
</c:set>
<div class="card-body">
	<canvas id="canvaChart${currContact}" style="height:220px;max-width:100%;"></canvas>
	<input type="hidden" id="contact${currContact}Value" value="${chartContact.countsToString()}" />
	<input type="hidden" id="legends${currContact}Value" value="${pageScope.chosesDay}" />
</div>
</compress:html>