<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="table-responsive">
	<table class="table table-page table-console">
		<thead><tr><th style="width:28%;"></th><th style="width:18%;"></th><th style="width:18%;"></th><th style="width:18%;"></th><th style="width:18%;"></th></tr></thead>
		<tbody class="font-small">
			<tr class="console-header i-primary"><c:forEach var="i" begin="1" end="5" step="1"><td><spring:message code="tool.dashboard.admin2.1.${i}" /></td></c:forEach></tr>
			<c:forEach var="i" begin="1" end="6" step="1">
				<tr>
					<td><spring:message code="tool.dashboard.admin2.2.${i}" /></td>
					<c:forEach var="j" begin="1" end="4" step="1">
						<td class="${j == 1 ? 'font-bold' : j == 4 ? 'font-bold i-segond' : ''}"><c:out value="${dashboardMarketplace[i - 1][j - 1]}"/></td>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr class="console-header i-primary">
				<td><spring:message code="tool.order.total" /></td>
				<c:forEach var="i" begin="1" end="4" step="1">
					<td class="${i == 1 ? 'font-bold' : i == 4 ? 'font-bold i-segond' : ''}"><c:out value="${dashboardMarketplace[6][i - 1]}"/></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
</compress:html>