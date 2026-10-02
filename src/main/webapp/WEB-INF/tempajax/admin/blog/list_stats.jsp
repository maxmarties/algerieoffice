<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="result-td">
					<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
				</td>
				<td><spring:message code="chose.blog.family${line.category}" /></td>
				<td class="text-center"><c:out value="${line.viewCount}" /></td>
				<td class="text-center"><c:out value="${line.likeCount}" /></td>
				<td class="text-center"><c:out value="${line.simultude}" /></td>
				<td class="text-center"><c:out value="${line.follow}" /></td>
				<td class="text-center"><c:out value="${line.market}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
							<a href="<c:url value="/admin/blog/stats/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>