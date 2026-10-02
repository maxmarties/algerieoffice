<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
			<div class="screen-column widget-column m-b-20" data-about="${line.companyId}" data-document="${line.id}">
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
						<div class="widget-title" data-dir="${line.language == 'ar' ? 'rtl' : 'ltr'}">
							<span class="item-provider p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
							<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien sh-black explorer-result"><c:out value="${line.title}" /></a>
						</div>
						<div class="widget-category">
							<i class="cmsms-icon-${line.type == 1 ? 'basket' : 'box'} i-segond m-r-10"></i><spring:message code="chose.post.type${line.type}"/>
						</div>
						<div class="widget-descriptif"><p class="text-${line.language == 'ar' ? 'ar' : 'fr'}"><c:out value="${line.description}"/></p></div>
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
				<div class="widget-body">
					<div class="widget-about-post">
						<div class="row row-mini">
							<div class="col-md-8 col-mini">
								<div class="widget-about">
									<ul class="navbar-nav nav-flex-icons">
										<li class="widget-icon"><img class="img-circle" src="<c:url value="${line.urlAvatar}"/>" alt="<c:out value="${line.tradename}" />"></li>
										<li class="widget-abouter">
											<a href="<c:url value="${line.companyURL}" />" class="lien lien-black lien-underline"><c:out value="${line.tradename}" /></a>
											<span> - <c:out value="${line.address}"/></span>
										</li>
									</ul>
								</div>
							</div>
							<div class="col-md-4 col-mini">
								<div class="widget-button text-right">
									<a href="<c:url value="${line.identifyURL}?prospect=open" />" 
										class="btn btn-primary btn-simple btn-fixed"><span><spring:message code="btn.explorer.contact3"/></span></a>
									<div id="submitedFavorite${line.id}Form" class="form-submit">
										<a class="btn btn-segond btn-flat-favorite btn-simple btn-submit iFavorite ${line.favorite ? 'active' : ''}" 
											title="<spring:message code="tool.navigate.company2"/>"><span><i class="cmsms-icon-star-filled"></i></span></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="currFavorite${line.id}" value="${line.favorite}" />
			</div>
			<c:if test="${state.count == indexMarket && !empty blogMarket}"><c:import url="/WEB-INF/tempajax/screen/widget_blogmarket.jsp" /></c:if>
		</c:forEach>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult" value="${list.countResult}" />
<input type="hidden" id="countScreenSize" value="${list.lines.size()}" />
</compress:html>