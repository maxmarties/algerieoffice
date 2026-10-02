<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="8"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-check td-image"><img src="<c:url value="${line.iconUrl}"/>" class="img-circle b-white" alt="<c:out value="${line.tradename}"/>"></td>
				<td class="i-primary result-td">
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<a href="<c:url value="/admin/data/identities/edit?id=${line.id}" />" class="lien lien-table explorer-result">
							<c:out value="${line.tradename}" />
						</a>
					</sec:authorize>
					<sec:authorize access="!hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<span class="explorer-result"><c:out value="${line.tradename}" /></span>
					</sec:authorize>
				</td>
				<td class="text-center"><c:out value="${line.requestedDate}" /></td>
				<td class="text-center"><c:out value="${line.createdDate}" /></td>
				<td class="text-center">
					<a href="<c:url value="${line.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
						<spring:message code="tool.view.file" />
					</a>
				</td>
				<td class="text-center"><i class="cmsms-icon-${line.hasConsulted ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
							<a href="<c:url value="/admin/data/identities/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</div>
					</sec:authorize>
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