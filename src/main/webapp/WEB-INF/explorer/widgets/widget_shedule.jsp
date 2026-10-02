<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<table class="table table-empty m-t-10">
	<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
	<tbody class="font-small">
		<c:forEach var="i" begin="1" end="7" step="1">
			<tr>
				<td class="font-bold"><spring:message code="chose.day${i}" /></td>
				<td>
					<c:choose>
						<c:when test="${explorerCompany.shedule.stateday[i - 1] == 0}"><spring:message code="lbl.sub.shedule1.1" /></c:when>
						<c:otherwise><c:out value="${explorerCompany.shedule.getFormattedTime(i - 1)}" /></c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>