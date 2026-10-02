<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column widget-document">
	<p class="ind-screen"><spring:message code="explorer.desktop.grid7" /></p>
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
		<div class="table-responsive m-t-20">
			<table class="table table-explorer table-desktop">
				<thead><tr><th style="width:50%;"></th><th style="width:50%;"></th></tr></thead>
				<tbody class="font-small">
					<tr>
						<td><spring:message code="tabs.contract" /> : <strong><spring:message code="chose.contract${explorerPage.inbox.contract}"/></strong></td>
						<td class="text-right"><spring:message code="lbl.contract.discover" /> : 
							<strong class="text-segond">
								<c:choose>
									<c:when test="${explorerPage.inbox.discoverType != 3}"><spring:message code="chose.discover${explorerPage.inbox.discoverType}" /></c:when>
									<c:otherwise><c:out value="${explorerPage.inbox.discoverValue}"/> <spring:message code="tool.order.devise"/></c:otherwise>
								</c:choose>
							</strong>
						</td>
					</tr>
					<tr>
						<td><spring:message code="lbl.contract.domaine" /> : <strong><spring:message code="chose.domaine${explorerPage.inbox.domaine}"/></strong></td>
						<td class="text-right"><spring:message code="tabs.expire" /> : <span class="text-red"><strong><c:out value="${explorerPage.inbox.expiredDate}"/></strong></span></td>
					</tr>
					<tr>
						<td colspan="2">
							<i class="cmsms-icon-location m-r-10"></i><spring:message code="lbl.sub.search11" /> : 
							<strong> 
								<c:forEach var="location" items="${explorerPage.inbox.locations}" varStatus="state">
									<spring:message code="chose.wilaya${location}"/>
									<c:if test="${state.count < explorerPage.inbox.locations.size()}"><c:out value=", "/></c:if>
								</c:forEach>
							</strong>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h2 class="h-header h-explorer3 m-t-20"><spring:message code="lbl.sub.explorer6.4"/></h2>
		<div class="fr-view fr-explorer m-t-20"><c:out value="${explorerPage.inbox.detail}" escapeXml="false" /></div>
		<c:if test="${!empty explorerPage.meta.keysword}">
			<ul class="list-keysword list-none m-t-10">
				<c:forEach var="keyword" items="${explorerPage.meta.buildKeysword()}">
					<li>
						<i class="cmsms-icon-hash-1 text-segond i-tags m-r-5"></i>
						<a href="<c:url value="/marketplace/offres-emploi?tag=${explorerPage.meta.parsKey(keyword)}" />" 
							class="lien lien-keyword lien-explorer-keyword"><c:out value="${keyword}" /></a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${!empty explorerPage.inbox.urlExtern}">
			<hr class="my-2">
			<h2 class="h-header h-explorer3 m-b-20"><spring:message code="lbl.sub.explorer6.3"/></h2>
			<a href="<c:url value="${explorerPage.inbox.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" target="_blank">
				<c:out value="${explorerPage.inbox.urlExtern}" />
			</a>
		</c:if>
	</div>
	<div class="document-footer">
		<div>
			<c:set var="documentBackword" value="4" scope="request"></c:set>
			<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
		</div>
	</div>
</div>