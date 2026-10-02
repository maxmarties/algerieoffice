<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="notification" items="${notifications}">
	<li>
		<c:choose>
			<c:when test="${notification.consulted}"><c:set var="href" scope="page" value="${notification.link}"></c:set></c:when>
			<c:otherwise><c:set var="href" scope="page" value="/inbox/notification/href?uuid=${notification.id}"></c:set></c:otherwise>
		</c:choose>
		<a href="<c:url value="${pageScope.href}" />" class="dropdown-inbox transition-35 ${notification.consulted ? '' : 'active'}">
			<c:choose>
				<c:when test="${notification.hasIcon}"><i class="${notification.iconimage} pull-left"></i></c:when>
				<c:otherwise>
					<img src="<c:url value="${notification.iconimage}"/>" class="pull-left img-circle b-white" alt="<spring:message code="tooltip.avatar" />">
				</c:otherwise>
			</c:choose>
			<span class="inbox-header font-small">
				<c:if test="${!empty notification.notifiedname}"><span class="font-bold"><c:out value="${notification.notifiedname} "/></span> </c:if>
				<spring:message code="${notification.message}"/>
				<span class="help-text i-help">
					<i class="cmsms-icon-${notification.cmsms} i-clock"></i>
					<span class="moment-notification"><joda:format value="${notification.notifiedDate}" pattern="dd/MM/yyyy HH:mm:ss"></joda:format></span>
				</span>
			</span>
			<span class="clearfix"></span>
		</a>
	</li>
</c:forEach>
</compress:html>