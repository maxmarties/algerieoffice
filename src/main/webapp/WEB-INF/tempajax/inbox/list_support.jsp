<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${!empty nbrModerators}"><li class="chater-infos" style="display:none;"><input type="hidden" id="nbrSupport" value="${nbrModerators}" /></li></c:if>
<c:choose>
	<c:when test="${list.isEmpty()}">
		<c:if test="${currPage == 1}">
			<li class="chater-item chater-left chater-empty">
				<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" class="pull-left img-circle" alt="<spring:message code="app.brand" />">
				<div class="chater-brand">
					<span class="chater-pull font-small"><spring:message code="tool.support.welcome"/></span>
				</div>
				<span class="clearfix"></span>
			</li>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="i" begin="1" end="${list.size()}" step="1">
			<c:set var="line" value="${list.get(list.size() - i)}" scope="page"></c:set>
			<c:choose>
				<c:when test="${empty line.adminId}">
					<li class="chater-item chater-right">
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
						<span class="chater-time font-mini i-help"><c:out value="${line.time}"/></span>
					</li>
				</c:when>
				<c:otherwise>
					<li class="chater-item chater-left">
						<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" class="pull-left img-circle" alt="<spring:message code="app.brand" />">
						<div class="chater-brand">
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