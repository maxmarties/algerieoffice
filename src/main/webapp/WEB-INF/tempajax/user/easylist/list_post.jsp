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
					<c:choose>
						<c:when test="${line.published}">
							<a href="<c:url value="${line.documentURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
						</c:when>
						<c:otherwise><span class="i-primary explorer-result"><c:out value="${line.title}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.companyname}" /></td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><spring:message code="chose.post.type${line.type}"/></td>
				<td class="column4 text-center i-green" style="${currTable.column[3] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${line.priceType == 2}"><c:out value="${line.priceValue}"/> <spring:message code="tool.order.devise"/></c:when>
						<c:otherwise><spring:message code="chose.post.price${line.priceType}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.modifiedDate}" /></td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.published ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${line.published}">
							<a href="<c:url value="${line.documentURL}" />" class="btn btn-table btn-green" title="<spring:message code="btn.view" />" 
								target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="8"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>