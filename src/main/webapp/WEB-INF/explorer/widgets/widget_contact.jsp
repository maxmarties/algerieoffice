<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-auth-hr text-center m-t-20 m-b-10"><span class="line"></span><span class="h-header"><spring:message code="explorer.subheader.contact3.3"/></span></div>
<div class="row row-mini">
	<c:forEach var="agent" items="${explorerPage.agents}" varStatus="state">
		<div class="col-md-6 col-lg-4 col-mini m-t-10 ${state.count == 3 ? 'hidden-md-center' : ''}">
			<div class="explorer-column explorer-agent h-100">
				<div class="img-contianer pull-left"><img src="<c:url value="${agent.urlAvatar}"/>" class="img-responsive" alt="<c:out value="${agent.username}" />"></div>
				<div class="explorer-brand">
					<h3 class="h-header text-primary"><c:out value="${agent.username}"/></h3>
					<p class="font-small text-help"><c:out value="${agent.function}"/></p>
					<hr class="my-1">
					<ul class="navbar-nav nav-flex-icons navbar-linked list-explorer-linked">
						<li><a href="mailto:<c:out value="${agent.email}"/>" class="btn icon-viadeo" target="_blank"><i class="cmsms-icon-mail-alt"></i></a></li>
						<li><a href="tel:+213${agent.phone}" class="btn icon-google"><i class="cmsms-icon-phone-3"></i></a></li>
						<c:if test="${agent.hasPresentSocial()}">
							<c:set var="providers" value="facebook,twitter,linkedin" scope="page"></c:set>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="subState">
								<c:if test="${!empty agent.socialURL[subState.count - 1]}">
									<li><a href="<c:url value="${agent.socialURL[subState.count - 1]}"/>" class="btn icon-${pageScope.provider}" 
										target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
								</c:if>
							</c:forEach>
						</c:if>
					</ul>
				</div>
				<span class="clearfix"></span>
			</div>
		</div>
	</c:forEach>
</div>