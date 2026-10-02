<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="/membres?id=${line.userId}" />" class="lien lien-table explorer-result" target="_blank">
							<c:out value="${line.username}" />
						</a>
						<span class="help-text"><c:out value="${line.companyname}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><c:out value="${line.object}" /></td>
				<td>
					<c:choose>
						<c:when test="${!empty line.app}"><spring:message code="chose.assist.app${line.app}" /></c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${!empty line.management}"><spring:message code="chose.assist.management${line.management}" /></c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${!empty line.program}"><spring:message code="chose.assist.program${line.program}" /></c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td><c:out value="${line.message}" /></td>
				<td class="text-center"><c:out value="${line.postedDate}" /></td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
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