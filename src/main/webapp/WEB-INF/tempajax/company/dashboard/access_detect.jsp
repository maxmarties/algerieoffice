<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listAccess.lines.isEmpty()}"><p class="font-small"><spring:message code="tool.empty.detect" /></p></c:when>
	<c:otherwise>
		<p class="font-small"><spring:message code="txt.company.detect1.2" /></p>
		<hr class="my-2">
		<ul class="list-none list-block">
			<c:forEach var="line" items="${listAccess.lines}">
				<li class="timeline-detect">
					<div class="detect-user pull-left">
						<div class="img-circle img-container ${line.online ? 'ind-login' : ''} m-auto">
							<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
						</div>
						<div class="detect-footer font-mini text-center m-t-10">
							<a href="<c:url value="/membres?id=${line.userId}" />" class="lien lien-black" target="_blank"
								title="<spring:message code="tool.navigate.company3.3" />"><c:out value="${line.username}" /></a>
						</div>
					</div>
					<div class="detect-brand font-small">
						<ul class="list-none list-block">
							<li>
								<span class="font-bold"><spring:message code="tabs.company" /></span>: 
								<c:choose>
									<c:when test="${!empty line.companyname}">
										<c:choose>
											<c:when test="${!empty line.companyURL}">
												<a href="<c:url value="${line.companyURL}" />" class="lien lien-primary lien-hover" target="_blank"
													title="<spring:message code="tooltip.followed" />"><c:out value="${line.companyname}" /></a>
											</c:when>
											<c:otherwise><c:out value="${line.companyname}"/></c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise><c:out value="--"/></c:otherwise>
								</c:choose>
							</li>
							<li><span class="font-bold"><spring:message code="tabs.access" /></span>: <span class="h-header"><c:out value="${line.countAccess}"/></span></li>
							<li><span class="font-bold"><spring:message code="tool.access" /></span>: <span class="h-header"><c:out value="${line.accessDate}"/></span></li>
						</ul>
						<div class="m-t-5">
							<a class="btn btn-file btn-simple btn-fixed btn-message" data-id="${line.userId}" data-avatar="${line.urlAvatar}" 
								data-name="${line.username}"><span><spring:message code="tooltip.messenger" /></span></a>
						</div>
					</div>
					<div class="clearfix"></div>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>
</compress:html>