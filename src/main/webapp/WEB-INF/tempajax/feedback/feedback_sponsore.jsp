<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!empty sponsoreFeedback}">
		<a id="iClickSpn" class="m-auto" href="<c:url value="${sponsoreFeedback.url}" />" target="_blank" data-click="${sponsoreFeedback.id}">
			<img class="img-responsive" src="<c:url value="${sponsoreFeedback.bannerURL}"/>">
		</a>
		<div class="widget-more hidden-md-up m-t-10">
			<i class="cmsms-icon-explorer-angle m-r-10"></i>
			<a href="<c:url value="/solutions/publicite"/>" 
				class="lien lien-primary lien-explorer-primary lien-hover h-header"><spring:message code="txt.help.explorer4.1"/></a>
		</div>
	</c:when>
	<c:otherwise><p class="font-small text-right text-help"><spring:message code="txt.help.explorer4.2"/></p></c:otherwise>
</c:choose>
</compress:html>