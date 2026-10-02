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
				<td class="td-check td-image"><img src="<c:url value="${line.urlAvatar}"/>" class="img-circle" alt="<c:out value="${line.autorname}"/>"></td>
				<td class="i-primary result-td">
					<c:choose>
						<c:when test="${line.countBlog != 0}">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank">
								<c:out value="${line.autorname}" />
							</a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.autorname}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td><c:out value="${line.function}" /></td>
				<td><a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a></td>
				<td class="text-center"><c:out value="${line.countBlog}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.countBlog != 0 ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${line.countBlog != 0}">
							<a href="<c:url value="${line.identifyURL}" />" class="btn btn-table btn-green" 
								title="<spring:message code="btn.overiew" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						</c:if>
						<a href="<c:url value="/admin/blog/autors/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
							title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
						<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit ${line.countBlog != 0 ? 'disabled' : ''}" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</sec:authorize>
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