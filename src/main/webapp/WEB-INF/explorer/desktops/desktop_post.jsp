<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column widget-document">
	<p class="ind-screen"><spring:message code="explorer.desktop.grid5" /></p>
	<div id="submitDocumentForm" class="document-favorite form-submit p-right">
		<a class="transition-35 btn-submit iDocument ${explorerCurrent.hasPreview ? 'disabled' : favoriteDocument ? 'active' : ''}" 
			title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-empty-2"></i></a>
	</div>
	<h1 class="h-doc h-doc1 text-title"><c:out value="${explorerPage.inbox.title}"/></h1>
	<div class="document-content">
		<div class="row">
			<div class="col-6"><p class="font-small"><spring:message code="tool.navigate.company" /> : <c:out value="${explorerPage.inbox.modifiedDate}" /></p></div>
			<div class="col-6">
				<p class="font-small text-right no-print">
					<a id="iPrint" class="lien lien-explorer-black lien-underline"><spring:message code="btn.print"/></a><i class="cmsms-icon-print m-l-5"></i>
				</p>
			</div>
		</div>
		<div class="widget-grid m-t-20">
			<div class="row">
				<c:if test="${explorerPage.inbox.photosURL.size() > 1}">
					<div class="col-sm-6">
						<div class="row row-mini">
							<c:forEach var="photoURL" items="${explorerPage.inbox.photosURL}" varStatus="state">
								<div class="col-6 col-mini m-b-10">
									<div class="widget-header">
										<a href="<c:url value="${photoURL}" />" class="inner-link"></a>
										<div class="inner-overlay"><i class="cmsms-icon-search-6 i-white"></i></div>
										<div class="inner-thumbnail">
											<img class="img-responsive" src="<c:url value="${photoURL}"/>" alt="<c:out value="${explorerPage.inbox.photosAlt.get(state.count - 1)}" />">
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<div class="col-sm-${explorerPage.inbox.photosURL.size() == 1 ? '12' : '6'} m-b-20">
					<div class="widget-image">
						<div class="inner-label p-left">
							<c:if test="${explorerPage.inbox.labelNew}"><span class="label label-red m-r-5 m-b-5"><spring:message code="tool.explorer.new"/></span></c:if>
							<c:if test="${explorerPage.inbox.labelExclusif}"><span class="label label-blue"><spring:message code="tool.explorer.excl"/></span></c:if>
						</div>
						<div class="inner-thumbnail">
							<img class="img-responsive" src="<c:url value="${explorerPage.inbox.photosURL.get(0)}"/>" alt="<c:out value="${explorerPage.inbox.photosAlt.get(0)}" />">
						</div>
					</div>
				</div>
			</div>
		</div>
		<h2 class="h-header h-explorer3"><spring:message code="lbl.sub.explorer5.1"/></h2>
		<div class="table-responsive m-t-10">
			<table class="table table-explorer table-desktop">
				<thead><tr><th style="width:30px;"></th><th style="width:34%;"></th><th style="width:calc(66% - 30px);"></th></tr></thead>
				<tbody class="h-header">
					<tr>
						<td><i class="cmsms-icon-basket"></i></td>
						<td><spring:message code="tabs.type"/></td>
						<td><spring:message code="chose.post.type${explorerPage.inbox.type}"/></td>
					</tr>
					<tr>
						<td><i class="cmsms-icon-folder-2"></i></td>
						<td><spring:message code="tabs.category"/></td>
						<td>
							<c:choose>
								<c:when test="${empty explorerPage.inbox.category}"><span class="font-small"><c:out value="--"/></span></c:when>
								<c:otherwise>
									<a class="lien lien-explorer-primary lien-underline" href="<c:url value="${explorerPage.inbox.categoryURL}" />">
										<c:out value="${explorerPage.inbox.category}"/>
									</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><i class="cmsms-icon-dollar"></i></td>
						<td><spring:message code="lbl.price"/></td>
						<td class="font-big">
							<c:choose>
								<c:when test="${explorerPage.inbox.priceType == 2}">
									<span class="text-segond">
										<c:out value="${explorerPage.inbox.priceValue}"/> <spring:message code="tool.order.devise"/> 
										<c:out value="${explorerPage.inbox.precision}"/>
									</span>
									<c:if test="${!empty explorerPage.inbox.priceParrain}">
										<span class="widget-parrain m-l-20">
											<c:out value="${explorerPage.inbox.priceParrain}"/> <spring:message code="tool.order.devise"/>
										</span>
									</c:if>
								</c:when>
								<c:otherwise><span class="text-segond"><spring:message code="chose.post.price${explorerPage.inbox.priceType}" /></span></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<c:if test="${explorerPage.credit.hasPresentCredit()}">
			<h2 class="h-header h-explorer3 m-t-20"><spring:message code="lbl.sub.explorer5.2"/></h2>
			<div class="table-responsive m-t-10">
				<table class="table table-explorer table-desktop">
					<thead><tr><th style="width:30px;"></th><th style="width:34%;"></th><th style="width:calc(66% - 30px);"></th></tr></thead>
					<tbody class="h-header font-small">
						<c:if test="${explorerPage.credit.hasPresentTicket()}">
							<tr>
								<td><i class="cmsms-icon-credit-card"></i></td>
								<td><spring:message code="lbl.sub.credit1"/></td>
								<td>
									<ul class="list-ticket list-none list-inline">
										<c:forEach var="i" begin="1" end="5">
											<c:if test="${explorerPage.credit.ticket[i - 1]}">
												<li><spring:message code="overview.credit${i}"/></li>
											</c:if>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty explorerPage.credit.taxe}">
							<tr>
								<td><i class="cmsms-icon-chart-pie-2"></i></td>
								<td><spring:message code="lbl.sub.credit2"/></td>
								<td><c:out value="${explorerPage.credit.taxe}" /></td>
							</tr>
						</c:if>
						<c:if test="${!empty explorerPage.credit.truck}">
							<tr>
								<td><i class="cmsms-icon-truck"></i></td>
								<td><spring:message code="lbl.sub.credit3"/></td>
								<td><c:out value="${explorerPage.credit.truck}" /></td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</c:if>
		<h2 class="h-header h-explorer3 m-t-20"><spring:message code="lbl.sub.explorer5.3"/></h2>
		<div class="fr-view fr-explorer m-t-20"><c:out value="${explorerPage.inbox.detail}" escapeXml="false" /></div>
		<c:if test="${!empty explorerPage.meta.keysword}">
			<ul class="list-keysword list-none m-t-10">
				<c:forEach var="keyword" items="${explorerPage.meta.buildKeysword()}">
					<li>
						<i class="cmsms-icon-hash-1 text-segond i-tags m-r-5"></i>
						<a href="<c:url value="/marketplace/produits-et-services?tag=${explorerPage.meta.parsKey(keyword)}" />" 
							class="lien lien-keyword lien-explorer-black"><c:out value="${keyword}" /></a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${!empty explorerPage.inbox.urlExtern}">
			<hr class="my-2">
			<h2 class="h-header h-explorer3 m-b-10"><spring:message code="lbl.sub.explorer5.4"/></h2>
			<a href="<c:url value="${explorerPage.inbox.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" target="_blank">
				<c:out value="${explorerPage.inbox.urlExtern}" />
			</a>
		</c:if>
	</div>
	<div class="document-footer">
		<div>
			<c:set var="documentBackword" value="1" scope="request"></c:set>
			<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
		</div>
	</div>
</div>