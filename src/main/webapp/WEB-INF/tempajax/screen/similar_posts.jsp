<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${list.hasPresent()}">
	<div class="screen-noprint">
		<c:if test="${!list.sources.isEmpty()}">
			<div class="explorer-auth-hr text-center m-t-20 m-b-20">
				<span class="line"></span><span class="h-header"><spring:message code="subheader.screen.marketplace1.2"/></span>
			</div>
			<c:forEach var="line" items="${list.sources}" varStatus="state">
				<div class="screen-column widget-column m-b-10">
					<div class="row row-mini">
						<div class="col-md-4 col-mini">
							<div class="widget-window">
								<div class="widget-header">
									<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
									<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
									<div class="inner-label p-left">
										<c:if test="${line.labelNew}"><span class="label label-red m-r-5 m-b-5"><spring:message code="tool.explorer.new"/></span></c:if>
										<c:if test="${line.labelExclusif}"><span class="label label-blue"><spring:message code="tool.explorer.excl"/></span></c:if>
									</div>
									<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.photoAlt}" />"></div>
								</div>
							</div>
						</div>
						<div class="col-md-8 col-mini">
							<div class="widget-title" data-dir="${langage.lang == 'ar' ? 'rtl' : 'ltr'}">
								<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien sh-black"><c:out value="${line.title}" /></a>
							</div>
							<div class="widget-category">
								<i class="cmsms-icon-${line.type == 1 ? 'basket' : 'box'} i-segond m-r-10"></i><spring:message code="chose.post.type${line.type}"/>
							</div>
							<div class="widget-descriptif"><p class="text-${langage.lang == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}"/></p></div>
							<div class="widget-body">
								<div class="widget-price h-header">
									<c:choose>
										<c:when test="${line.priceType == 2}">
											<c:if test="${!empty line.priceParrain}">
												<span class="widget-parrain m-r-20"><c:out value="${line.priceParrain}"/> <spring:message code="tool.order.devise"/></span>
											</c:if>
											<span class="i-segond1"><c:out value="${line.priceValue}"/> <spring:message code="tool.order.devise"/> <c:out value="${line.precision}"/></span>
										</c:when>
										<c:otherwise><span class="i-segond1"><spring:message code="chose.post.price${line.priceType}" /></span></c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${!list.proxis.isEmpty()}">
			<div class="explorer-auth-hr text-center m-t-20 m-b-20">
				<span class="line"></span><span class="h-header"><spring:message code="subheader.screen.marketplace1.1"/></span>
			</div>
			<c:forEach var="line" items="${list.proxis}" varStatus="state">
				<div class="screen-column widget-column m-b-10">
					<div class="row row-mini">
						<div class="col-md-4 col-mini">
							<div class="widget-window">
								<div class="widget-header">
									<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
									<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
									<div class="inner-label p-left">
										<c:if test="${line.labelNew}"><span class="label label-red m-r-5 m-b-5"><spring:message code="tool.explorer.new"/></span></c:if>
										<c:if test="${line.labelExclusif}"><span class="label label-blue"><spring:message code="tool.explorer.excl"/></span></c:if>
									</div>
									<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.photoAlt}" />"></div>
								</div>
							</div>
						</div>
						<div class="col-md-8 col-mini">
							<div class="widget-title" data-dir="${langage.lang == 'ar' ? 'rtl' : 'ltr'}">
								<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien sh-black"><c:out value="${line.title}" /></a>
							</div>
							<div class="widget-category">
								<i class="cmsms-icon-${line.type == 1 ? 'basket' : 'box'} i-segond m-r-10"></i><spring:message code="chose.post.type${line.type}"/>
							</div>
							<div class="widget-descriptif"><p class="text-${langage.lang == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}"/></p></div>
							<div class="widget-body">
								<div class="widget-price h-header">
									<c:choose>
										<c:when test="${line.priceType == 2}">
											<c:if test="${!empty line.priceParrain}">
												<span class="widget-parrain m-r-20"><c:out value="${line.priceParrain}"/> <spring:message code="tool.order.devise"/></span>
											</c:if>
											<span class="i-segond1"><c:out value="${line.priceValue}"/> <spring:message code="tool.order.devise"/> <c:out value="${line.precision}"/></span>
										</c:when>
										<c:otherwise><span class="i-segond1"><spring:message code="chose.post.price${line.priceType}" /></span></c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
</c:if>
</compress:html>