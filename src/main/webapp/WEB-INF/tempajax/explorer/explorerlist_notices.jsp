<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listNotices.isEmpty()}">
		<c:if test="${pageNotice == 1}"><li class="text-empty text-center text-help"><spring:message code="message.browser.notice"/></li></c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="line" items="${listNotices}">
			<li class="explorer-item">
				<div class="pull-left img-circle img-container">
					<img src="<c:url value="${line.userMini.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.userMini.username}" />">
				</div>
				<div class="explorer-brand">
					<p class="font-bold"><c:out value="${line.title}"/></p>
					<p class="font-small m-t-5"><c:out value="${line.message}"/></p>
					<p class="font-mini text-help h-header m-t-5">
						<joda:format value="${line.postedDate}" pattern="dd MMM yyyy"></joda:format>, 
						<a href="<c:url value="${line.userMini.pseudoURL}" />" class="lien lien-explorer-help" target="_blank">
							<c:out value="${line.userMini.username}" />
						</a><span class="i-certificated i-certificated${line.userMini.verified}"></span>
					</p>
				</div>
				<span class="clearfix"></span>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>