<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="10"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-check td-image"><img src="<c:url value="${line.urlAvatar}"/>" class="img-circle" alt="<c:out value="${line.username}"/>"></td>
				<td class="result-td explorer-result"><c:out value="${line.username}" /></td>
				<td><spring:message code="chose.role.${line.role}" /></td>
				<td>
					<c:choose>
						<c:when test="${!line.locked}">
							<a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a>
						</c:when>
						<c:otherwise><c:out value="${line.email}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><c:out value="${line.createDate}" /></td>
				<td class="text-center"><c:out value="${line.loginDate}" /></td>
				<td class="text-center"><c:out value="${line.numberOfVisit}" /></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.locked}"><i class="cmsms-icon-lock i-red"></i></c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${currUserId != line.id}">
							<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${line.id}" 
								data-avatar="${line.urlAvatar}" data-name="${line.username}"><i class="cmsms-icon-mail-alt"></i></a>
						</c:if>
						<c:if test="${!line.hasSuperAdmin()}">
							<a href="<c:url value="/admin/team/managers/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<div id="lock${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.${line.locked ? 'unlocked' : 'locked'}" />" 
									data-attribut="locked" data-locked="${line.id}"><i class="cmsms-icon-${line.locked ? 'lock-open' : 'lock'}-6"></i></a>
							</div>
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</c:if>
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