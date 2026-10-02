<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column explorer-post flexed flex-colone flex-jusitify h-100 m-auto">
	<div class="widget-header" style="margin:14px 14px 0 14px!important;">
		<a href="<c:url value="${requestScope.previewPost.identifyURL}" />" class="inner-link" title="<c:out value="${requestScope.previewPost.title}" />"></a>
		<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
		<div class="inner-label p-left">
			<c:if test="${requestScope.previewPost.labelNew}"><span class="label label-red m-r-5 m-b-5"><spring:message code="tool.explorer.new"/></span></c:if>
			<c:if test="${requestScope.previewPost.labelExclusif}"><span class="label label-blue"><spring:message code="tool.explorer.excl"/></span></c:if>
		</div>
		<div class="inner-thumbnail">
			<img class="img-responsive" src="<c:url value="${requestScope.previewPost.photoURL}"/>" alt="<c:out value="${requestScope.previewPost.photoAlt}" />">
		</div>
	</div>
	<div class="widget-flexed flexed flex-colone flex-jusitify">
		<div>
			<div class="explorer-title">
				<a href="<c:url value="${requestScope.previewPost.identifyURL}" />" 
					class="h-doc lien lien-explorer-title explorer-resultFind"><c:out value="${requestScope.previewPost.title}" /></a>
			</div>
			<div class="widget-category">
				<i class="cmsms-icon-folder-2 text-segond m-r-10"></i>
				<c:choose>
					<c:when test="${empty requestScope.previewPost.category}"><span class="font-small"><c:out value="--"/></span></c:when>
					<c:otherwise>
						<a href="<c:url value="${requestScope.previewPost.categoryURL}" />" 
							class="lien lien-explorer-black lien-small"><c:out value="${requestScope.previewPost.category}"/></a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="widget-externe">
				<c:choose>
					<c:when test="${empty requestScope.previewPost.urlExtern}"><span class="font-small text-select"><c:out value="--"/></span></c:when>
					<c:otherwise>
						<a href="<c:url value="${requestScope.previewPost.urlExtern}" />" 
							class="lien lien-explorer-primary lien-underline lien-small" target="_blank"><c:out value="${requestScope.previewPost.urlExtern}" /></a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="widget-descriptif"><p><c:out value="${requestScope.previewPost.description}"/></p></div>
		</div>
		<div class="widget-body">
			<div class="widget-price h-header">
				<c:choose>
					<c:when test="${requestScope.previewPost.priceType == 2}">
						<c:if test="${!empty requestScope.previewPost.priceParrain}">
							<span class="widget-parrain m-r-20">
								<c:out value="${requestScope.previewPost.priceParrain}"/> <spring:message code="tool.order.devise"/>
							</span>
						</c:if>
						<span class="text-segond">
							<c:out value="${requestScope.previewPost.priceValue}"/> <spring:message code="tool.order.devise"/> 
							<c:out value="${requestScope.previewPost.precision}"/>
						</span>
					</c:when>
					<c:otherwise><span class="text-segond"><spring:message code="chose.post.price${requestScope.previewPost.priceType}" /></span></c:otherwise>
				</c:choose>
			</div>
			<div class="widget-button text-center">
				<a href="<c:url value="${requestScope.previewPost.identifyURL}" />" 
					class="btn btn-explorer-segond m-b-5"><span><spring:message code="btn.explorer.detail"/></span></a>
				<a href="<c:url value="${requestScope.previewPost.identifyURL}?prospect=devis"/>" 
					class="btn btn-explorer-primary m-b-5"><span><spring:message code="btn.explorer.quote"/></span></a>
			</div>
			<div class="widget-more">
				<i class="cmsms-icon-explorer-angle m-r-10"></i>
				<a href="<c:url value="${requestScope.previewPost.identifyURL}" />" class="lien lien-explorer-more h-header">
					<spring:message code="btn.explorer.read"/>
				</a>
			</div>
		</div>
	</div>
</div>