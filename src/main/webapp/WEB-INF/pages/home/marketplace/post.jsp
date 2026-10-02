<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/marketplace"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/marketplace/produits-et-services"/>" class="lien lien-black"><spring:message code="wizard.screen.navbar1"/></a></li>
		<li class="active"><c:out value="${inbox.title}"/></li>
	</ol>
</div>
<div class="screen-container screen-noprint ${!empty sponsoreScreen ? 'screen-aobubs' : ''}">
	<div class="container">
		<c:if test="${!empty sponsoreScreen}"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></c:if>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.post"/></c:set>
		<c:set var="placeholderResult" scope="request"><spring:message code="tool.result.screen1"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<c:set var="treeviewNav" value="1" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_marketplace.jsp"/>
<c:set var="hrefBackword" value="/marketplace/produits-et-services" scope="request"></c:set>
<c:set var="screenBackword" value="5" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_backword.jsp"/>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="row">
			<c:set var="screenAbout" value="1" scope="request"></c:set>
			<c:import url="/WEB-INF/fields/screen/screen_about.jsp"/>
			<div class="col-lg-9 m-t-10 m-b-10">
				<div id="screenDocument" class="screen-column widget-document">
					<p class="ind-screen"><spring:message code="explorer.desktop.grid5" /></p>
					<div id="submitFavoriteForm" class="document-favorite form-submit p-right no-print">
						<a class="transition-35 btn-submit iFavorite ${hasFavorite ? 'active' : ''}" 
							title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-empty-2"></i></a>
					</div>
					<h1 class="h-doc h-doc1"><c:out value="${inbox.title}"/></h1>
					<div class="document-content">
						<div class="row">
							<div class="col-6"><p class="font-small"><spring:message code="tool.navigate.company" /> : <c:out value="${inbox.modifiedDate}" /></p></div>
							<div class="col-6">
								<p class="font-small text-right no-print">
									<a id="iPrint" class="lien lien-black lien-underline"><spring:message code="btn.print"/></a><i class="cmsms-icon-print m-l-5"></i>
								</p>
							</div>
						</div>
						<div class="widget-grid m-t-20">
							<div class="row">
								<c:if test="${inbox.photosURL.size() > 1}">
									<div class="col-sm-6">
										<div class="row row-mini">
											<c:forEach var="photoURL" items="${inbox.photosURL}" varStatus="state">
												<div class="col-6 col-mini m-b-10">
													<div class="widget-header">
														<a href="<c:url value="${photoURL}" />" class="inner-link"></a>
														<div class="inner-overlay"><i class="cmsms-icon-search-6 i-white"></i></div>
														<div class="inner-thumbnail">
															<img class="img-responsive" src="<c:url value="${photoURL}"/>" alt="<c:out value="${inbox.photosAlt.get(state.count - 1)}" />">
														</div>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:if>
								<div class="col-sm-${inbox.photosURL.size() == 1 ? '12' : '6'} m-b-20">
									<div class="widget-image">
										<div class="inner-label p-left">
											<c:if test="${inbox.labelNew}"><span class="label label-red m-r-5 m-b-5"><spring:message code="tool.explorer.new"/></span></c:if>
											<c:if test="${inbox.labelExclusif}"><span class="label label-blue"><spring:message code="tool.explorer.excl"/></span></c:if>
										</div>
										<div class="inner-thumbnail">
											<img class="img-responsive" src="<c:url value="${inbox.photosURL.get(0)}"/>" alt="<c:out value="${inbox.photosAlt.get(0)}" />">
										</div>
									</div>
								</div>
							</div>
						</div>
						<h2 class="h-header h-header4 i-header"><spring:message code="lbl.sub.explorer5.1"/></h2>
						<div class="table-responsive m-t-10">
							<table class="table table-widget">
								<thead><tr><th style="width:30px;"></th><th style="width:34%;"></th><th style="width:calc(66% - 30px);"></th></tr></thead>
								<tbody class="h-header">
									<tr>
										<td><i class="cmsms-icon-${inbox.type == 1 ? 'basket' : 'box'}"></i></td>
										<td><spring:message code="tabs.type"/></td>
										<td><spring:message code="chose.post.type${inbox.type}"/></td>
									</tr>
									<tr>
										<td><i class="cmsms-icon-dollar"></i></td>
										<td><spring:message code="lbl.price"/></td>
										<td class="font-big">
											<c:choose>
												<c:when test="${inbox.priceType == 2}">
													<span class="i-segond">
														<c:out value="${inbox.priceValue}"/> <spring:message code="tool.order.devise"/> <c:out value="${inbox.precision}"/>
													</span>
													<c:if test="${!empty inbox.priceParrain}">
														<span class="widget-parrain m-l-20"><c:out value="${inbox.priceParrain}"/> <spring:message code="tool.order.devise"/></span>
													</c:if>
												</c:when>
												<c:otherwise><span class="text-segond"><spring:message code="chose.post.price${inbox.priceType}" /></span></c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="m-t-20">
							<h2 class="h-header h-header4 i-header"><spring:message code="lbl.sub.explorer5.3"/></h2>
							<div class="fr-view fr-explorer m-t-20"><c:out value="${inbox.detail}" escapeXml="false" /></div>
							<c:if test="${!empty inbox.keysword}">
								<ul class="list-keysword list-none m-t-10">
									<c:forEach var="keyword" items="${inbox.buildKeysword()}">
										<li>
											<i class="cmsms-icon-hash-1 i-segond i-tags m-r-5"></i>
											<a href="<c:url value="/marketplace/produits-et-services?tag=${inbox.parsKey(keyword)}" />"
												class="lien lien-keyword"><c:out value="${keyword}" /></a>
										</li>
									</c:forEach>
								</ul>
							</c:if>
							<c:if test="${!empty inbox.urlExtern}">
								<hr class="my-2">
								<h2 class="h-header h-header4 i-header m-b-20"><spring:message code="lbl.sub.explorer5.4"/></h2>
								<a href="<c:url value="${inbox.urlExtern}" />" class="lien lien-primary lien-underline lien-small" 
									target="_blank"><c:out value="${inbox.urlExtern}" /></a>
							</c:if>
						</div>
					</div>
					<div class="document-footer screen-noprint">
						<div>
							<c:set var="documentBackword" value="1" scope="request"></c:set>
							<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
						</div>
					</div>
				</div>
				<div id="screenSimultudes"></div>
			</div>
		</div>
	</div>
</div>