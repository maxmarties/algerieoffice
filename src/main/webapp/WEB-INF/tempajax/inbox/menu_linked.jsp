<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<label class="col-form-label" for="fr-link-insert-layer-url-${linkId}"><spring:message code="chose.link${linkType}" /></label>
<c:choose>
	<c:when test="${linkType == 1}">
		<input class="fr-link-attr form-control" id="fr-link-insert-layer-url-${linkId}" name="href" type="text" tabIndex="1" aria-required="true" value="${companyURL}">
	</c:when>
	<c:otherwise>
		<select id="fr-link-select-${linkId}" class="form-select2">
			<option></option>
			<c:forEach var="choseLinked" items="${choseLinkeds}" >
				<option value="${choseLinked.identify}"><c:out value="${choseLinked.title}" /></option>
			</c:forEach>
		</select>
		<input id="fr-link-insert-layer-url-${linkId}" name="href" type="text" class="fr-link-attr" tabIndex="1" style="display:none;">
	</c:otherwise>
</c:choose>
</compress:html>