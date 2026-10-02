<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="explorer-column">
	<p class="ind-screen"><spring:message code="explorer.desktop.grid4" /></p>
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
		<hr class="my-2">
		<div class="row">
			<div class="col-md-6 col-lg-3 m-t-10 m-b-10">
				<span class="font-small text-help"><spring:message code="tabs.partner"/></span>
				<p class="font-bold text-input"><c:out value="${explorerPage.inbox.partner}"/></p>
			</div>
			<div class="col-md-6 col-lg-3 m-t-10 m-b-10">
				<span class="font-small text-help"><spring:message code="tabs.work"/></span>
				<p class="font-bold text-input"><joda:format value="${explorerPage.inbox.workDate}" pattern="MMMM dd, yyyy"></joda:format></p>
			</div>
			<div class="col-md-6 col-lg-3 m-t-10 m-b-10">
				<span class="font-small text-help"><spring:message code="lbl.sub.explorer4.1"/></span>
				<p class="font-bold text-input"><c:out value="${explorerPage.meta.escapeKeysword()}"/></p>
			</div>
			<div class="col-md-6 col-lg-3 m-t-10 m-b-10">
				<span class="font-small text-help"><spring:message code="tabs.url"/></span>
				<p class="font-bold text-input">
					<c:choose>
						<c:when test="${empty explorerPage.inbox.urlExtern}"><c:out value="--"/></c:when>
						<c:otherwise>
							<spring:message code="lbl.sub.explorer4.2"/>
							<a href="<c:url value="${explorerPage.inbox.urlExtern}" />" class="lien lien-explorer-primary" target="_blank"><i class="cmsms-icon-link-ext m-l-5"></i></a>
						</c:otherwise>
					</c:choose>
				</p>
			</div>
		</div>
		<div class="document-background background-container" style="background-image: url('${explorerPage.inbox.photoURL}');"></div>
		<h2 class="h-header h-explorer3"><spring:message code="tool.explorer.description"/></h2>
		<p class="text-justify m-t-20"><c:out value="${explorerPage.meta.description}"/></p>
		<hr class="my-2">
		<h2 class="h-header h-explorer3"><spring:message code="lbl.sub.explorer4.3"/></h2>
		<div class="fr-view fr-explorer m-t-20"><c:out value="${explorerPage.inbox.detail}" escapeXml="false" /></div>
	</div>
</div>