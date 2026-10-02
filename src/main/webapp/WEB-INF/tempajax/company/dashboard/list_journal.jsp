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
				<td class="td-check td-image"><i class="cmsms-icon-${line.icon} i-table"></i></td>
				<td class="result-td column2" style="${currTable.column[1] ? '' : 'display:none;'}">
					<span class="i-primary explorer-result"><c:out value="${line.username}"/></span>
					<span class="help-text"><spring:message code="chose.role.${line.rolename}" /></span>
				</td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}"><spring:message code="txt.journal.company${line.action}" /></td>
				<td class="column5" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.element}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
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