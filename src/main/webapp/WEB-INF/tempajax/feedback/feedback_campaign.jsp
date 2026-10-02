<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-campaign">
	<c:choose>
		<c:when test="${!empty campaign}">
			<a href="<c:url value="${campaign.companyURL}"/>" class="lien-campaign pull-left iClickCampaign" 
				target="_blank" data-click="${campaign.id}">
				<img src="<c:url value="${campaign.urlAvatar}"/>" class="img-responsive" alt="<spring:message code="tooltip.avatar" />">
			</a>
			<div class="campaign-brand">
				<a href="<c:url value="${campaign.identifyURL}"/>" class="lien lien-company sh-black iClickCampaign" 
					target="_blank" data-click="${campaign.id}"><c:out value="${campaign.title}" /></a>
				<p class="font-small m-t-5"><spring:message code="tool.dashboard.campaign${campaign.type}" />
					<span class="help-text pull-right"><joda:format value="${campaign.modifiedDate}" pattern="dd MMM yyyy"></joda:format></span></p>
			</div>
		</c:when>
		<c:otherwise>
			<a href="<c:url value="/solutions/publicite"/>" class="lien-campaign pull-left" target="_blank">
				<img src="<c:url value="/static/vectors/algerieoffice-min.jpg"/>" class="img-responsive" alt="<spring:message code="tooltip.avatar" />">
			</a>
			<div class="campaign-brand">
				<a href="<c:url value="/solutions/publicite"/>" class="lien lien-company sh-black" target="_blank"><spring:message code="subheader.screen.solution5.5" /></a>
				<p class="font-small m-t-5"><spring:message code="txt.help.explorer4.2" /></p>
			</div>
		</c:otherwise>
	</c:choose>
	<span class="clearfix"></span>
	<div class="widget-more m-t-10">
		<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/company/marketplace/campaigns/new"/>" 
			class="lien lien-primary lien-hover h-header"><spring:message code="txt.help.explorer4.1"/></a>
	</div>
</div>
</compress:html>