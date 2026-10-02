<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="message" items="${messages}">
	<li id="dropdownMessage${message.userId}">
		<a class="dropdown-inbox transition-35 ${!message.consulted && message.senderId != currUserId ? 'active' : ''}" data-avatar="${message.avatarURL}" 
			data-name="${message.username}" data-login="${message.online}" data-user="${message.userId}">
			<span class="img-circle img-container pull-left ${message.online ? 'ind-login' : ''}">
				<img src="<c:url value="${message.avatarURL}"/>" class="img-circle img-responsive" alt="<c:out value="${message.username}" />">
			</span>
			<span class="inbox-header font-small">
				<span class="block">
					<span class="font-bold"><c:out value="${message.username}"/></span>
					<span class="font-mini i-help pull-right"><joda:format value="${message.postedDate}" pattern="${message.getDatePattern()}"></joda:format></span>
				</span>
				<span class="inbox-text text-truncate i-help">
					<c:if test="${message.senderId == currUserId}"><span class="font-bold"><spring:message code="tool.view.me" />: </span></c:if>
					<c:choose>
						<c:when test="${message.emojis}"><img src="<c:url value="/static/vectors/emoticons/${message.message}.png"/>"></c:when>
						<c:otherwise><c:out value="${message.message}"/></c:otherwise>
					</c:choose>
				</span>
			</span>
			<span class="clearfix"></span>
		</a>
	</li>
</c:forEach>
</compress:html>