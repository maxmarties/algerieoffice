<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column widget-document">
	<p class="ind-screen"><spring:message code="explorer.desktop.grid6" /></p>
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
				<thead><tr><th style="width:40%;"></th><th style="width:30%;"></th><th style="width:30%;"></th></tr></thead>
				<tbody class="font-small">
					<tr>
						<td><spring:message code="tabs.type" /> : <strong><spring:message code="chose.annonce${explorerPage.inbox.type}"/></strong></td>
						<td><spring:message code="tabs.explorer.dateOn" /> : <strong><c:out value="${explorerPage.inbox.startDate}"/></strong></td>
						<td class="text-right"><spring:message code="tabs.explorer.dateOff" /> : <span class="text-red"><strong><c:out value="${explorerPage.inbox.endDate}"/></strong></span></td>
					</tr>
					<tr>
						<td colspan="3">
							<spring:message code="lbl.sub.annonce1" /> :
							<strong> 
								<c:forEach var="sector" items="${explorerPage.inbox.sectors}" varStatus="state">
									<spring:message code="chose.sector${sector}"/>
									<c:if test="${state.count < explorerPage.inbox.sectors.size()}"><c:out value=", "/></c:if>
								</c:forEach>
							</strong>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<i class="cmsms-icon-location m-r-10"></i><spring:message code="sidebar.company.dashboard6.4" /> :
							<strong> 
								<c:choose>
									<c:when test="${explorerPage.inbox.wilayas.isEmpty()}"><spring:message code="comp.target" /></c:when>
									<c:otherwise>
										<c:forEach var="wilaya" items="${explorerPage.inbox.wilayas}" varStatus="state">
											<spring:message code="chose.wilaya${wilaya}"/>
											<c:if test="${state.count < explorerPage.inbox.wilayas.size()}"><c:out value=", "/></c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</strong>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="m-t-20">
			<c:choose>
				<c:when test="${hasAutorized}">
					<h2 class="h-header h-explorer3"><spring:message code="lbl.sub.explorer6.1"/></h2>
					<div class="fr-view fr-explorer m-t-20"><c:out value="${explorerPage.inbox.detail}" escapeXml="false" /></div>
					<c:if test="${!empty explorerPage.meta.keysword}">
						<ul class="list-keysword list-none m-t-10">
							<c:forEach var="keyword" items="${explorerPage.meta.buildKeysword()}">
								<li>
									<i class="cmsms-icon-hash-1 text-segond i-tags m-r-5"></i>
									<a href="<c:url value="/marketplace/annonces?tag=${explorerPage.meta.parsKey(keyword)}" />" 
										class="lien lien-keyword lien-explorer-keyword"><c:out value="${keyword}" /></a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${!empty explorerPage.inbox.urlFile}">
						<hr class="my-2">
						<h2 class="h-header h-explorer3 m-b-20"><spring:message code="lbl.file.detail"/></h2>
						<i class="cmsms-icon-file-pdf i-red m-r-10"></i>
						<a href="<c:url value="${explorerPage.inbox.urlFile}" />" class="lien lien-explorer-black lien-underline lien-small" target="_blank">
							<spring:message code="lbl.sub.explorer6.2"/>
						</a>
					</c:if>
					<c:if test="${!empty explorerPage.inbox.urlExtern}">
						<hr class="my-2">
						<h2 class="h-header h-explorer3 m-b-20"><spring:message code="lbl.sub.explorer6.3"/></h2>
						<a href="<c:url value="${explorerPage.inbox.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" target="_blank">
							<c:out value="${explorerPage.inbox.urlExtern}" />
						</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<p class="explorer-alert"><i class="cmsms-icon-lock-3 i-34"></i><spring:message code="message.browser.annonce${explorerPage.inbox.visibility}"/></p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="document-footer">
		<div>
			<c:set var="documentBackword" value="2" scope="request"></c:set>
			<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
		</div>
	</div>
</div>