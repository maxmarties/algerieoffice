<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
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
					<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.name}" /></a>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.description}" /></td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.parent}" /></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.sizePosts}" /></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.hasPingled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="${line.identifyURL}" />" class="btn btn-table btn-green" 
							title="<spring:message code="btn.overiew" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
							<a href="<c:url value="/company/posts/categories/edit?id=${line.id}"/>" 
								class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>