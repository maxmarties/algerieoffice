<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small ${!line.consulted ? 'inchecked' : ''}">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-check td-image"><i class="cmsms-icon-reply i-cmscs i-blue"></i></td>
				<td class="td-check td-image">
					<div class="img-circle img-container">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
					</div>
				</td>
				<td>
					<a class="lien lien-table iSendSupport" data-id="${line.userId}" data-avatar="${line.urlAvatar}" data-name="${line.username}">
						<c:out value="${line.username}" />
					</a>
				</td>
				<td>
					<c:choose>
						<c:when test="${line.screenshot}">
							<a href="<c:url value="${line.message}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
								<spring:message code="tabs.filereader" />
							</a>
						</c:when>
						<c:otherwise><c:out value="${line.message}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><c:out value="${line.postedDate}" /></td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
							<c:if test="${!line.consulted}">
								<div id="consulted${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.consulted" />" 
										data-attribut="consulted" data-consulted="${line.id}"><i class="cmsms-icon-minus"></i></a>
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
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>