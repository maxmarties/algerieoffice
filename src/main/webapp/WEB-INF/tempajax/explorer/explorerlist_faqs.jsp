<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="col-12 m-b-20">
					<div class="explorer-column">
						<div class="widget-title">
							<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
							<h2 class="h-doc h-doc4 text-title sh-black explorer-resultFind"><c:out value="${line.title}" /></h2>
						</div>
						<div class="explorer-body">
							<div class="fr-view fr-explorer"><c:out value="${line.description}" escapeXml="false" /></div>
							<c:if test="${!empty line.urlExtern}">
								<div class="widget-more m-t-10">
									<a href="<c:url value="${line.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" 
										target="_blank"><c:out value="${line.urlExtern}" /></a>
								</div>
							</c:if>
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