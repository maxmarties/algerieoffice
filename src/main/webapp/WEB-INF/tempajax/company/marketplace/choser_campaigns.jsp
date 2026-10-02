<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
<select class="form-select2" id="documentCampaign" name="documentCampaign" data-placeholder="${pageScope.faholder}">
	<option></option>
	<c:forEach var="choseCampaign" items="${choseCampaigns}">
		<option value="${choseCampaign.uuid()}"><c:out value="${choseCampaign.name}"/></option>
	</c:forEach>
</select>
</compress:html>