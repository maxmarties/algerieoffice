<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listAccess.isEmpty()}"><p class="font-small"><spring:message code="tool.empty.access" /></p></c:when>
	<c:otherwise>
		<p class="font-small"><spring:message code="txt.company.journal1.2" /></p>
		<hr class="my-2">
		<ul class="list-none list-block">
			<c:forEach var="line" items="${listAccess}">
				<li class="timeline-access">
					<span class="access-time h-header p-left"><c:out value="${line.time}"/></span>
					<div class="access-item m-b-10">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}" />">
						<div class="access-brand font-small">
							<p class="text-truncate i-primary"><c:out value="${line.username}"/></p>
							<span class="help-text text-truncate"><c:out value="${line.device}"/></span>
						</div>
						<span class="clearfix"></span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>
</compress:html>