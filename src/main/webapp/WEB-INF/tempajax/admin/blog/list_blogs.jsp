<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="10"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td><span class="ind-language ind-${line.language}"><c:out value="${line.language}" /></span></td>
				<td class="i-primary result-td">
					<c:choose>
						<c:when test="${line.published}">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.title}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td><spring:message code="chose.blog.family${line.category}" /></td>
				<td><c:out value="${line.autor}"/></td>
				<td class="text-center"><c:out value="${line.modifiedDate}" /></td>
				<td class="text-center"><c:out value="${line.viewCount}" /></td>
				<td class="text-center"><c:out value="${line.likeCount}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.published ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${line.published}">
							<a href="<c:url value="${line.identifyURL}" />" class="btn btn-table btn-green" 
								title="<spring:message code="btn.overiew" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						</c:if>
						<a href="<c:url value="/admin/blog/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
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