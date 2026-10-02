<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-website m-t-10">
	<div id="widgetExplorerWebsite" class="widget-website m-auto ${explorerPage.linked.websiteNames.size() == 1 ? 'widget-block' : ''}">
		<div class="owl-carousel owl-theme">
			<c:forEach var="websiteName" items="${explorerPage.linked.websiteNames}" varStatus="state">
				<div class="item">
					<span class="tags-type text-white"><spring:message code="chose.linked${explorerPage.linked.websiteTypes.get(state.count - 1)}" /></span>
					<img class="img-responsive" src="<c:url value="${explorerPage.linked.websitePhotos.get(state.count - 1)}" />" 
						alt="<c:out value="${websiteName}" />">
					<div class="website-footer">
						<a href="<c:url value="${explorerPage.linked.websiteUrls.get(state.count - 1)}" />" target="_blank"
							class="btn btn-explorer-segond btn-block"><span><spring:message code="btn.website"/></span></a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>