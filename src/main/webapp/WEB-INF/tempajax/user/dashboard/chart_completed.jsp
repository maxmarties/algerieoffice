<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div id="circleChartCompleted" class="circle-3d circle-completed m-auto" data-counter="${chartCompleted}">
	<div class="circle-content text-center"><span class="h-header font-bold i-primary"><c:out value="${chartCompleted}%" /></span></div>
	<div id="circleCanvasCompleted" class="circle-canvas"></div>
</div>
<c:choose>
	<c:when test="${chartCompleted <= 75}">
		<span class="help-text m-t-20"><spring:message code="txt.help.dashboard6.1.1" /></span>
		<div class="widget-more m-t-10">
			<a href="<c:url value="/user/account/profile"/>" class="btn btn-segond btn-fixed"><span><spring:message code="txt.help.dashboard6.1.2"/></span></a>
		</div>
	</c:when>
	<c:otherwise><span class="help-text m-t-20"><spring:message code="txt.help.dashboard6.1" /></span></c:otherwise>
</c:choose>
</compress:html>