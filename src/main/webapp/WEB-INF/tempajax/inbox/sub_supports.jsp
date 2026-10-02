<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:forEach var="support" items="${supports}">
	<li id="dropdownSupport${support.userId}">
		<a class="dropdown-inbox transition-35 ${!support.consulted && empty support.adminId ? 'active' : ''}" data-avatar="${support.avatarURL}" 
			data-name="${support.username}" data-user="${support.userId}">
			<span class="img-circle img-container pull-left">
				<img src="<c:url value="${support.avatarURL}"/>" class="img-circle img-responsive" alt="<c:out value="${support.username}" />">
			</span>
			<span class="inbox-header font-small">
				<span class="block">
					<span class="font-bold"><c:out value="${support.username}"/></span>
					<span class="font-mini i-help pull-right"><joda:format value="${support.postedDate}" pattern="${support.getDatePattern()}"></joda:format></span>
				</span>
				<span class="inbox-text text-truncate i-help">
					<c:if test="${!empty support.adminId}"><span class="font-bold"><spring:message code="tool.view.admin" />: </span></c:if>
					<c:choose>
						<c:when test="${support.screenshot}"><spring:message code="tabs.filereader" /></c:when>
						<c:otherwise><c:out value="${support.message}"/></c:otherwise>
					</c:choose>
				</span>
			</span>
			<span class="clearfix"></span>
		</a>
	</li>
</c:forEach>
</compress:html>