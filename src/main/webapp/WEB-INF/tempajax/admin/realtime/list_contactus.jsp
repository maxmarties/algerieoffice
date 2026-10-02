<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="8"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin ${!line.consulted ? 'inchecked' : ''}">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="result-td">
					<span class="explorer-result i-primary"><c:out value="${line.username}" /></span>
					<span class="help-text"><c:out value="${line.userId}" /></span>
				</td>
				<td><c:out value="${line.phone}" /></td>
				<td><a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a></td>
				<td><spring:message code="overview.request${line.object}" /></td>
				<td><c:out value="${line.message}" /></td>
				<td class="text-center"><c:out value="${line.postedDate}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="/admin/realtime/contacts/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
							title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
						<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
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