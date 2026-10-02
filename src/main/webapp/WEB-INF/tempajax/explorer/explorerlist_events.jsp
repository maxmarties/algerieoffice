<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="${hasView ? 'col-12' : 'col-md-6'} m-b-20">
					<div class="explorer-column ${hasView ? 'explorer-row' : 'h-100'}">
						<c:choose>
							<c:when test="${hasView}">
								<div class="widget-header explorer-header background-container" style="background-image: url('${line.photoURL}');">
									<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
									<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow text-white"></i></div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="widget-header explorer-header">
									<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
									<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow text-white"></i></div>
									<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="explorer-content">
							<div class="widget-title">
								<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
								<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien lien-explorer-title sh-black explorer-resultFind"><c:out value="${line.title}" /></a>
							</div>
							<div class="widget-body">
								<div class="table-responsive">
									<table class="table table-explorer table-last">
										<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
										<tbody class="font-small">
											<tr><td><spring:message code="lbl.sub.explorer3.1" /></td><td class="text-right"><c:out value="${line.eventDate}"/></td></tr>
											<tr><td><spring:message code="lbl.sub.explorer3.5" /></td><td class="text-right"><c:out value="${line.eventClock}"/></td></tr>
											<tr><td><spring:message code="lbl.sub.explorer3.2" /></td><td class="text-right"><spring:message code="chose.wilaya${line.wilaya}"/></td></tr>
										</tbody>
									</table>
								</div>
								<p class="m-t-10"><c:out value="${line.description}" /></p>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countDesktopResult" value="${explorerList.count}" />
<input type="hidden" id="countDesktopSize" value="${explorerList.lines.size()}" />
</compress:html>