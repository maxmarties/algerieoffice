<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
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
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.tradename}"/>">
					<div class="brand-colspan">
						<c:choose>
							<c:when test="${line.published}">
								<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
									<c:out value="${line.tradename}" />
								</a>
							</c:when>
							<c:otherwise><span class="explorer-result"><c:out value="${line.tradename}" /></span></c:otherwise>
						</c:choose>
						<span class="help-text"><spring:message code="chose.activity.${line.activity}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><spring:message code="chose.wilaya${line.wilaya}"/></td>
				<td>
					<c:choose>
						<c:when test="${line.isUserPublished()}">
							<a href="<c:url value="/membres?id=${line.id}" />" class="lien lien-primary lien-underline" 
								title="<spring:message code="tool.navigate.company3.3" />" target="_blank"><c:out value="${line.username}" /></a>
						</c:when>
						<c:otherwise><c:out value="${line.username}" /></c:otherwise>
					</c:choose>
				</td>
				<td><spring:message code="chose.role.${line.role}" /></td>
				<td>
					<c:choose>
						<c:when test="${line.enabled}"><a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a></c:when>
						<c:otherwise><c:out value="${line.email}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><c:out value="${line.numberOfVisit}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.enabled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.locked}"><i class="cmsms-icon-lock i-red"></i></c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a class="btn btn-table btn-blue iSendSupport" title="<spring:message code="tooltip.popup.supports" />" data-id="${line.id}" 
							data-avatar="${line.userAvatar}" data-name="${line.username}"><i class="cmsms-icon-box"></i></a>
						<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
							<div id="lock${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.${line.locked ? 'unlocked' : 'locked'}" />" 
									data-attribut="locked" data-locked="${line.id}"><i class="cmsms-icon-${line.locked ? 'lock-open' : 'lock'}-6"></i></a>
							</div>
						</sec:authorize>
						<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
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