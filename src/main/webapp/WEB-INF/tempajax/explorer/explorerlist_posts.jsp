<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<div class="col-md-6 m-b-20 ${!lateral ? 'col-lg-4' : ''}">
					<c:set var="previewPost" value="${line}" scope="request"></c:set>
					<c:set var="companyURI" value="${companyURI}" scope="request"></c:set>
					<c:import url="/WEB-INF/explorer/widgets/widget_post.jsp" />
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countDesktopResult" value="${explorerList.count}" />
<input type="hidden" id="countDesktopSize" value="${explorerList.lines.size()}" />
</compress:html>