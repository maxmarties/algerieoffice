<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="talk" items="${talks}">
	<li>
		<c:choose>
			<c:when test="${talk.consulted}"><c:set var="href" scope="page" value="${talk.link}"></c:set></c:when>
			<c:otherwise><c:set var="href" scope="page" value="/inbox/talk/href?uuid=${talk.id}"></c:set></c:otherwise>
		</c:choose>
		<a href="<c:url value="${pageScope.href}" />" class="dropdown-inbox transition-35 ${talk.consulted ? '' : 'active'}"
			title="<spring:message code="txt.inbox.talk${talk.type}"/>">
			<i class="icon-talk icon-talk${talk.type} pull-left"></i>
			<span class="inbox-talk font-small">
				<span class="block text-truncate"><spring:message code="txt.inbox.talk${talk.type}"/></span>
				<span class="help-text i-help">
					<i class="cmsms-icon-clock-circled t-talk i-clock"></i><span class="moment-talk"><joda:format value="${talk.talkedDate}" pattern="dd/MM/yyyy HH:mm:ss"></joda:format></span>
				</span>
			</span>
			<span class="clearfix"></span>
		</a>
	</li>
</c:forEach>
</compress:html>