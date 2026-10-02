<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-sticky hidden-md-down m-t-10">
	<div class="widget-sticky text-white">
		<h2 class="h-doc">
			<c:choose>
				<c:when test="${empty explorerPage.sticky.title}"><spring:message code="lbl.sub.sticky1.1" /></c:when>
				<c:otherwise><c:out value="${explorerPage.sticky.title}" /></c:otherwise>
			</c:choose>
		</h2>
		<div class="widget-body m-t-20">
			<p class="h-header font-small"><c:out value="${explorerPage.sticky.description}" /></p>
			<div class="m-t-20">
				<c:choose>
					<c:when test="${empty explorerPage.sticky.urlExtern}"><c:set var="fahref" value="${explorerCurrent.companyURL}/contact" scope="page"></c:set></c:when>
					<c:otherwise><c:set var="fahref" value="${explorerPage.sticky.urlExtern}" scope="page"></c:set></c:otherwise>
				</c:choose>
				<a href="<c:url value="${pageScope.fahref}"/>" class="btn btn-explorer-sticky btn-simple btn-block" target="${explorerPage.sticky.target ? '_self' : '_blank'}">
					<span>
						<c:choose>
							<c:when test="${empty explorerPage.sticky.label}"><spring:message code="tooltip.contact" /></c:when>
							<c:otherwise><c:out value="${explorerPage.sticky.label}" /></c:otherwise>
						</c:choose>
					</span>
				</a>
			</div>
		</div>
	</div>
</div>
<c:if test="${explorerCurrent.hasPreview && empty explorerPage.sticky.title}">
	<div class="alert alert-info m-t-10 hidden-md-down">
		<i class="cmsms-icon-info-circled-3 i-alert"></i>
		<p class="p-alert">
			<spring:message code="message.explorer.sticky"/><br>
			<a href="<c:url value="/company/manage/sticky"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.sticky"/></a>
		</p>
	</div>
</c:if>