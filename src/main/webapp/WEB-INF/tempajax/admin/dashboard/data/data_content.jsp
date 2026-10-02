<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="table-responsive">
	<table class="table table-page table-console">
		<thead><tr><th style="width:40%;"></th><th style="width:20%;"></th><th style="width:20%;"></th><th style="width:20%;"></th></tr></thead>
		<tbody class="font-small">
			<tr class="console-header i-primary"><c:forEach var="i" begin="1" end="4" step="1"><td><spring:message code="tool.dashboard.admin2.1.${i > 2 ? i + 1 : i}" /></td></c:forEach></tr>
			<c:forEach var="i" begin="1" end="3" step="1">
				<tr>
					<td><spring:message code="tool.dashboard.admin2.3.${i}" /></td>
					<c:forEach var="j" begin="1" end="3" step="1">
						<td class="${j == 1 ? 'font-bold' : j == 3 ? 'font-bold i-segond' : ''}"><c:out value="${dashboardContent[i - 1][j - 1]}"/></td>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr class="console-header i-primary">
				<td><spring:message code="tool.order.total" /></td>
				<c:forEach var="i" begin="1" end="3" step="1">
					<td class="${i == 1 ? 'font-bold' : i == 3 ? 'font-bold i-segond' : ''}"><c:out value="${dashboardContent[3][i - 1]}"/></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
</compress:html>