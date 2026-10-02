<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listAgents.isEmpty()}">
		<c:if test="${pageAgent == 1}">
			<li>
				<c:choose>
					<c:when test="${hasPreview}">
						<div class="alert alert-info">
							<i class="cmsms-icon-info-circled-3 i-alert"></i>
							<p class="p-alert">
								<spring:message code="message.explorer.agent"/><br>
								<a href="<c:url value="/company/team/agents"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.agent"/></a>
							</p>
						</div>
					</c:when>
					<c:otherwise><p class="font-small"><spring:message code="message.browser.agent"/></p></c:otherwise>
				</c:choose>
			</li>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="line" items="${listAgents}">
			<li class="flexed flex-row flex-jusitify">
				<div class="explorer-item">
					<div class="pull-left img-circle img-container">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
					</div>
					<div class="explorer-brand">
						<p class="text-input text-truncate"><c:out value="${line.username}"/></p>
						<span class="font-small text-help text-truncate"><c:out value="${line.function}"/></span>
					</div>
					<span class="clearfix"></span>
				</div>
				<ul class="navbar-nav">
					<li class="dropdown brand-menu">
						<a class="nav-explorer-icon transition-35" data-toggle="dropdown" 
							title="<spring:message code="tooltip.explorer.agent"/>"><i class="cmsms-icon-ellipsis-vert"></i></a>
						<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
							<li>
								<ul class="navbar-nav nav-flex-icons">
									<li><a href="mailto:<c:out value="${line.email}"/>" class="nav-explorer-icon transition-35"
										title="<spring:message code="tool.navigate.company3"/>"><i class="cmsms-icon-mail-alt"></i></a></li>
									<li><a href="tel:+213${line.phone}" class="nav-explorer-icon transition-35"
										title="<spring:message code="tool.navigate.company3.1"/>"><i class="cmsms-icon-phone-3"></i></a></li>
									<c:if test="${line.hasPresentSocial()}">
										<li class="divider"></li>
										<c:set var="providers" value="facebook,twitter,linkedin" scope="page"></c:set>
										<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
											<c:if test="${!empty line.socialURL[state.count - 1]}">
												<li><a href="<c:url value="${line.socialURL[state.count - 1]}"/>" class="nav-explorer-icon transition-35" 
													target="_blank" title="<spring:message code="tool.navigate.company3.2" 
													arguments="${pageScope.provider}" />"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${!empty line.userId}">
										<li class="divider"></li>
										<li><a href="<c:url value="/membres?id=${line.userId}"/>" class="nav-explorer-icon transition-35"
											title="<spring:message code="tool.navigate.company3.3"/>"><i class="cmsms-icon-user-2"></i></a></li>
									</c:if>
								</ul>
							</li>
						</ul>
					</li>
				</ul>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>