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
						<div class="row row-mini">
							<div class="col-3 col-md-2 col-mini">
								<div class="explorer-imgpartner m-auto">
									<img class="img-responsive" src="<c:url value="${line.urlAvatar}"/>" alt="<c:out value="${line.name}" />">
								</div>
							</div>
							<div class="col-9 col-md-10 col-mini">
								<div class="widget-title">
									<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
									<h2 class="h-doc h-doc4 text-title sh-black explorer-resultFind"><c:out value="${line.name}" /></h2>
								</div>
								<div class="explorer-body">
									<p class="font-small m-b-5"><c:out value="${line.biography}" /></p>
									<a href="<c:url value="${line.url}" />" class="lien lien-explorer-primary lien-underline lien-small" target="_blank"><c:out value="${line.url}" /></a>
								</div>
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