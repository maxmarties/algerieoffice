<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-promote">
	<c:choose>
		<c:when test="${!empty promoteFeedback}">
			<c:choose>
				<c:when test="${!empty promoteFeedback.photoURL}">
					<img class="img-responsive" src="<c:url value="${promoteFeedback.photoURL}"/>" alt="<c:out value="${promoteFeedback.title}" />">
				</c:when>
				<c:otherwise><div class="line"></div></c:otherwise>
			</c:choose>
			<div class="widget-body m-t-10">
				<h4 class="h-header h-headerAds text-primary"><c:out value="${promoteFeedback.title}" /></h4>
				<p class="font-small m-t-5"><c:out value="${promoteFeedback.description}" /></p>
				<div class="m-t-10">
					<a id="iClickPrm" href="<c:url value="${promoteFeedback.hrefURL}" />" class="btn btn-primary btn-explorer-primary btn-block" 
						target="${promoteFeedback.hasBlank ? '_blank' : '_self'}" data-click="${promoteFeedback.id}">
						<span><spring:message code="chose.label${promoteFeedback.label}"/></span>
					</a>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<img class="img-responsive" src="<c:url value="/static/picts/aobns/aoprm-min.jpg" />">
			<div class="widget-body m-t-10">
				<h4 class="h-header h-headerAds text-primary"><spring:message code="txt.help.explorer4.1.1"/></h4>
				<p class="font-small m-t-5"><spring:message code="txt.help.explorer4.1.2"/></p>
				<div class="m-t-10">
					<a href="<c:url value="/recherche/entreprises" />" class="btn btn-primary btn-explorer-primary btn-block">
						<span><spring:message code="txt.help.explorer4.1.3"/></span>
					</a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="widget-more">
		<i class="cmsms-icon-explorer-angle m-r-10"></i>
		<a href="<c:url value="/company/marketplace/promotes/new"/>" 
			class="lien lien-primary lien-explorer-primary lien-hover h-header"><spring:message code="txt.help.explorer4.1"/></a>
	</div>
</div>
</compress:html>