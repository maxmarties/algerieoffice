<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${!empty infos}">
	<li class="chater-infos" style="display:none;">
		<input type="hidden" id="popupTradename${currRecepientId}" value="${infos.tradename}" />
		<input type="hidden" id="popupCompanyURL${currRecepientId}" value="${infos.companyURL}" />
		<input type="hidden" id="popupHasBlocked${currRecepientId}" value="${infos.hasBlocked}" />
		<input type="hidden" id="popupHasLogin${currRecepientId}" value="${infos.hasLogin}" />
	</li>
</c:if>
<c:choose>
	<c:when test="${list.isEmpty()}"><c:if test="${currPage == 1}"><li class="chater-item chater-empty font-small"><spring:message code="tool.chat.empty2" /></li></c:if></c:when>
	<c:otherwise>
		<c:forEach var="i" begin="1" end="${list.size()}" step="1">
			<c:set var="line" value="${list.get(list.size() - i)}" scope="page"></c:set>
			<c:choose>
				<c:when test="${line.senderId == currUserId}">
					<li class="chater-item chater-right">
						<span class="chater-pull font-small">
							<c:choose>
								<c:when test="${line.emojis}"><img src="<c:url value="/static/vectors/emoticons/${line.message}.png"/>"></c:when>
								<c:otherwise><c:out value="${line.message}"/></c:otherwise>
							</c:choose>
						</span>
						<span class="chater-time font-mini i-help"><c:out value="${line.time}"/></span>
					</li>
				</c:when>
				<c:otherwise>
					<li class="chater-item chater-left">
						<img src="<c:url value="${line.avatarURL}"/>" class="img-circle pull-left" alt="<spring:message code="tooltip.avatar" />">
						<div class="chater-brand">
							<span class="chater-pull font-small">
								<c:choose>
									<c:when test="${line.emojis}"><img src="<c:url value="/static/vectors/emoticons/${line.message}.png"/>"></c:when>
									<c:otherwise><c:out value="${line.message}"/></c:otherwise>
								</c:choose>
							</span>
							<span class="chater-time font-mini i-help"><c:out value="${line.time}"/></span>
						</div>
						<span class="clearfix"></span>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>