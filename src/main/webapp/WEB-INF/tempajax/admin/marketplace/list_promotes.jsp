<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="11"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="i-primary result-td">
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<a href="<c:url value="/admin/marketplace/promotes/edit?id=${line.id}" />" class="lien lien-table explorer-result">
							<c:out value="${line.title}" />
						</a>
					</sec:authorize>
					<sec:authorize access="!hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<span class="explorer-result"><c:out value="${line.title}" /></span>
					</sec:authorize>
				</td>
				<td><c:out value="${line.description}" /></td>
				<td>
					<c:choose>
						<c:when test="${empty line.url}"><spring:message code="lbl.sub.sticky1.2.3" /></c:when>
						<c:otherwise>
							<a href="<c:url value="${line.url}" />" class="lien lien-primary lien-underline" target="_blank">
								<c:out value="${line.url}" />
							</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td><a href="<c:url value="${line.companyURL}" />" class="lien lien-table"><c:out value="${line.company}" /></a></td>
				<td class="text-center"><i class="cmsms-icon-${line.enabled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="text-center"><c:out value="${line.view}" /></td>
				<td class="text-center"><c:out value="${line.credit}" /></td>
				<td class="text-center font-bold ${line.potentiel > 0 ? 'i-green' : 'i-red'}"><c:out value="${line.potentiel}" /></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.trashed}"><i class="cmsms-icon-trash i-red"></i></c:when>
						<c:otherwise><c:out value="-" /></c:otherwise>
					</c:choose>
				</td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
							<a href="<c:url value="/admin/marketplace/promotes/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
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