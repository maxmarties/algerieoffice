<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-spn m-auto">
	<a id="iClickSpned" class="block" href="<c:url value="${sponsoreScreen.url}" />" target="_blank" data-click="${sponsoreScreen.id}">
		<img class="img-responsive" src="<c:url value="${sponsoreScreen.bannerURL}"/>">
	</a>
</div>