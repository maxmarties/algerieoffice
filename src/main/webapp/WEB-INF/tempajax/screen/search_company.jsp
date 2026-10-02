<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row row-mini">
			<c:forEach var="line" items="${list.lines}" varStatus="state">
				<div class="col-md-6 col-mini m-b-10">
					<c:set var="widgetCompany" value="${line}" scope="request"></c:set>
					<c:import url="/WEB-INF/tempajax/screen/widget_company.jsp" />
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
</compress:html>