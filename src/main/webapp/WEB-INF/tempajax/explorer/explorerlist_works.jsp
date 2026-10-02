<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="col-md-6 m-b-20">
					<div class="explorer-column h-100">
						<div class="widget-header">
							<a href="<c:url value="${line.identifyURL}" />" class="inner-link" title="<c:out value="${line.title}" />"></a>
							<div class="inner-overlay"><i class="cmsms-icon-plus-2 text-white"></i></div>
							<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="${line.photoURL}"/>" alt="<c:out value="${line.title}" />"></div>
						</div>
						<div class="widget-title">
							<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
							<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien lien-explorer-title sh-black explorer-resultFind"><c:out value="${line.title}" /></a>
						</div>
						<div class="explorer-body">
							<p class="font-small text-segond"><c:out value="${line.expertise}" /></p>
							<span class="font-mini text-help"><joda:format value="${line.workDate}" pattern="MMMM dd, yyyy"></joda:format></span>
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