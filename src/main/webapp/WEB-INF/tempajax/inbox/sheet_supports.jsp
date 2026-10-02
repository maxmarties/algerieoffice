<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${!empty infos}">
	<li class="chater-infos" style="display:none;">
		<input type="hidden" id="sheetTradename${currUserId}" value="${infos.tradename}" />
		<input type="hidden" id="sheetCompanyURL${currUserId}" value="${infos.companyURL}" />
		<input type="hidden" id="sheetPremium${currUserId}" value="${infos.premium}" />
		<input type="hidden" id="sheetHasLogin${currUserId}" value="${infos.hasLogin}" />
	</li>
</c:if>
<c:choose>
	<c:when test="${list.isEmpty()}"><c:if test="${currPage == 1}"><li class="chater-item chater-empty font-small"><spring:message code="tool.chat.empty2" /></li></c:if></c:when>
	<c:otherwise>
		<c:forEach var="i" begin="1" end="${list.size()}" step="1">
			<c:set var="line" value="${list.get(list.size() - i)}" scope="page"></c:set>
			<li class="chater-item chater-${empty line.adminId ? 'left' : 'right'}">
				<span class="chater-pull font-small">
					<c:choose>
						<c:when test="${line.screenshot}">
							<a href="<c:url value="${line.message}"/>" class="lien-file" target="_blank" title="<spring:message code="btn.view" />">
								<img class="img-responsive transition-35" src="<c:url value="${line.message}"/>" alt="<spring:message code="lbl.sub.linked2.1" />">
							</a>
						</c:when>
						<c:otherwise><c:out value="${line.message}"/></c:otherwise>
					</c:choose>
				</span>
				<span class="chater-time font-mini i-help">
					<c:if test="${!empty line.adminId}"><c:out value="${line.adminName}"/> - </c:if><c:out value="${line.time}"/>
				</span>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>