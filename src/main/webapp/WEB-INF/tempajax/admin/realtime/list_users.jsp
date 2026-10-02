<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="6"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin ${!line.consulted ? 'inchecked' : ''}">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}"/>">
					<div class="brand-colspan" style="padding-top:6px;">
						<span class="explorer-result i-primary"><c:out value="${line.username}" /></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><spring:message code="chose.deactivate.company${line.reason}" /></td>
				<td><c:out value="${line.observation}" /></td>
				<td class="text-center"><c:out value="${line.deactivateDate}" /></td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
							<div id="restore${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.restore" />" 
									data-attribut="restore" data-restore="${line.id}"><i class="cmsms-icon-up-6"></i></a>
							</div>
							<c:if test="${!line.consulted}">
								<div id="validate${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.consulted" />" 
										data-attribut="validate" data-validate="${line.id}"><i class="cmsms-icon-minus-4"></i></a>
								</div>
							</c:if>
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