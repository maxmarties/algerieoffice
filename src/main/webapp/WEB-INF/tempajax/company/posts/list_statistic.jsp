<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="i-primary result-td">
					<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
					<span class="help-text"><spring:message code="chose.post.type${!line.service ? '1' : '2'}" /></span>
				</td>
				<td class="column2 text-center" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.modifiedDate}" /></td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.token}" /></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.filter}" /></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.tag}" /></td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><c:out value="${line.simultude}" /></td>
				<td class="column7 text-center" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.clickCount}" /></td>
				<td class="text-center">
					<c:set var="persent" value="${line.getPesrsentClicks()}" scope="page"></c:set>
					<span class="ind-statistic">
						<i class="cmsms-icon-${pageScope.persent >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-10"></i><c:out value="${pageScope.persent}%"/>
					</span>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>