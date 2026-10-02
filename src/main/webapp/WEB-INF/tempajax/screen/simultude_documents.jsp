<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${list.hasPresent()}">
	<div class="row row-mini m-t-10 screen-noprint">
		<div class="col-md-6 col-mini m-t-10">
			<div class="screen-column screen-simultude h-100">
				<h3 class="h-doc h-doc3"><spring:message code="subheader.screen.marketplace${docState}.1"/></h3>
				<c:choose>
					<c:when test="${!list.proxis.isEmpty()}">
						<ul class="list-none">
							<c:forEach var="proxi" items="${list.proxis}" varStatus="state">
								<li>
									<span class="item-provider p-left"><c:out value="00${state.count}"/></span>
									<a href="<c:url value="${proxi.identifyURL}" />" class="lien lien-table"><c:out value="${proxi.title}" /></a>
								</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise><span class="help-text"><spring:message code="tool.empty.desktop"/></span></c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="col-md-6 col-mini m-t-10">
			<div class="screen-column screen-simultude h-100">
				<h3 class="h-doc h-doc3"><spring:message code="subheader.screen.marketplace${docState}.2"/></h3>
				<c:choose>
					<c:when test="${!list.sources.isEmpty()}">
						<ul class="list-none">
							<c:forEach var="source" items="${list.sources}" varStatus="state">
								<li>
									<span class="item-provider p-left"><c:out value="00${state.count}"/></span>
									<a href="<c:url value="${source.identifyURL}" />" class="lien lien-table"><c:out value="${source.title}" /></a>
								</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise><span class="help-text"><spring:message code="tool.empty.desktop"/></span></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</c:if>
</compress:html>