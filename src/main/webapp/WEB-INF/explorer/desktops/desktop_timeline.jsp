<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="explorer-timeline">
	<c:if test="${!empty explorerPage.timeline.history}"><p><c:out value="${explorerPage.timeline.history}" /></p></c:if>
	<c:if test="${!empty explorerPage.timeline.history && !explorerPage.timeline.titles.isEmpty()}"><hr class="my-6 hr-05"></c:if>
	<c:if test="${!explorerPage.timeline.titles.isEmpty()}">
		<ul class="list-none list-timeline">
			<c:forEach var="itemTitle" items="${explorerPage.timeline.titles}" varStatus="state">
				<li>
					<div class="timeline-item flexed">
						<div class="timeline-header"><joda:format value="${explorerPage.timeline.linesDate.get(state.count - 1)}" pattern="yyyy"></joda:format></div>
						<div class="timeline-body">
							<h2 class="h-header h-header4 text-title"><c:out value="${itemTitle}" /></h2>
							<span class="font-mini text-help"><joda:format value="${explorerPage.timeline.linesDate.get(state.count - 1)}" pattern="dd/MM/yyyy"></joda:format></span>
							<p class="font-small text-help m-t-10"><c:out value="${explorerPage.timeline.descriptions.get(state.count - 1)}" /></p>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</div>