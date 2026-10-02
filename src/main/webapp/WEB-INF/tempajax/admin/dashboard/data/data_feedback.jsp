<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<div class="row row-mini">
		<c:set var="providers" value="calendar-7,user-5,mail-5,star-5,comment-4,umbrella" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<div class="col-sm-4 col-mini ${state.count > 3 ? 'm-t-10' : ''}">
				<div class="card-data card-left" style="padding:0;">
					<i class="cmsms-icon-${pageScope.provider} card-trigger card-trigger${state.count} pull-left"></i>
					<div class="card-brand">
						<span class="text-truncate font-mini"><spring:message code="tool.dashboard.admin3.1.${state.count}" /></span>
						<p class="h-header i-primary"><c:out value="${dashboardFeedback[state.count - 1]}"/></p>
					</div>
					<span class="clearfix"></span>
				</div>
			</div>
		</c:forEach>
	</div>
</compress:html>