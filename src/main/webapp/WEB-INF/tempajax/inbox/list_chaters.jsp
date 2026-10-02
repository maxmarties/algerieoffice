<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.isEmpty()}"><c:if test="${currPage == 1}">
		<li class="chater-item chater-empty font-small"><i class="cmsms-icon-comment m-r-10"></i><spring:message code="tool.chat.empty1" /></li></c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="i" begin="1" end="${list.size()}" step="1">
			<c:set var="line" value="${list.get(list.size() - i)}" scope="page"></c:set>
			<li class="chater-item">
				<img src="<c:url value="${pageScope.line.avatarURL}"/>" class="img-circle pull-left" alt="<c:out value="${pageScope.line.username}" />">
				<div class="chater-brand">
					<div class="chater-username text-truncate">
						<a href="<c:url value="/membres?id=${pageScope.line.userId}"/>" class="lien lien-black lien-small h-header" target="_blank">
							<c:out value="${pageScope.line.username}" /></a>
						<span class="font-mini i-help pull-right"><c:out value="${pageScope.line.time}"/></span>
						<span class="clearfix"></span>
					</div>
					<div class="chater-pull font-small"><p><c:out value="${pageScope.line.message}"/></p></div>
					<div class="chater-about font-mini">
						<ul class="navbar-nav nav-flex-icons">
							<li class="text-truncate">
								<c:choose>
									<c:when test="${empty pageScope.line.companyURL}"><span class="h-header i-primary"><c:out value="${pageScope.line.tradename}"/></span></c:when>
									<c:otherwise>
										<a href="<c:url value="/entreprises/${pageScope.line.companyURL}"/>" title="<c:out value="${pageScope.line.tradename}" />" 
											class="lien lien-help lien-underline" target="_blank"><c:out value="${pageScope.line.tradename}" /></a>
									</c:otherwise>
								</c:choose>		
							</li>
							<c:if test="${pageScope.line.premium != 0}">
								<li class="m-l-5">
									<div class="ind-premium text-center btn-warning">
										<span><c:out value="PRO"/></span>
										<ul class="list-none list-premium list-inline">
											<c:forEach var="i" begin="1" end="${pageScope.line.premium}" step="1"><li><i class="cmsms-icon-plus-circled"></i></li></c:forEach>
										</ul>
									</div>
								</li>
							</c:if>
							<c:if test="${currUserId != pageScope.line.userId}">
								<li class="ml-auto"><a class="lien lien-table iReply" data-reply="<c:out value="${pageScope.line.parseReply()}"/>">
									<spring:message code="btn.reply" /></a></li>
							</c:if>
						</ul>
					</div>
				</div>
				<span class="clearfix"></span>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>