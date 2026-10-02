<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<div class="row row-mini">
		<c:set var="providers" value="email,megaphone-1,suitcase-1,folder-2,chat-5,folder-4" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-sm-4 col-mini ${state.count > 3 ? 'm-t-10' : ''}">
				<div class="card-data card-left" style="padding:10px 0 0 0;">
					<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-left"></i>
					<div class="card-brand">
						<span class="text-truncate font-mini"><spring:message code="tool.dashboard.admin5.1.${state.count}" /></span>
						<p class="h-header i-primary"><c:out value="${dashboardAdmin[state.count - 1]}"/></p>
					</div>
					<span class="clearfix"></span>
				</div>
			</div>
		</c:forEach>
	</div>
</compress:html>