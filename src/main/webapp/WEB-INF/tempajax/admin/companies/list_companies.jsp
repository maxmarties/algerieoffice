<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="12"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td><span class="ind-language ind-${line.language}"><c:out value="${line.language}" /></span></td>
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.tradename}"/>">
					<div class="brand-colspan">
						<c:choose>
							<c:when test="${line.hasPublished()}">
								<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
									<c:out value="${line.tradename}" />
								</a>
							</c:when>
							<c:otherwise><span class="i-primary explorer-result"><c:out value="${line.tradename}" /></span></c:otherwise>
						</c:choose>
						<span class="help-text"><spring:message code="chose.activity.${line.activity}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><c:out value="${line.postal}" /></td>
				<td><spring:message code="chose.wilaya${line.wilaya}" /></td>
				<td class="text-center"><c:out value="${line.phone}" /></td>
				<td><a href="mailto:${line.mail}" class="lien lien-primary lien-underline"><c:out value="${line.mail}" /></a></td>
				<td class="text-center"><c:out value="${line.buildDate}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.enabled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.locked}"><i class="cmsms-icon-lock-5 i-red"></i></c:when>
						<c:otherwise><c:out value="--" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><i class="cmsms-icon-${line.active ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
							<c:if test="${line.active}">
								<div id="diactivate${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-green btn-submit" title="<spring:message code="lbl.sub.display1.1.4" />" 
										data-attribut="diactivate" data-line="${line.id}"><i class="cmsms-icon-eye-off"></i></a>
								</div>
							</c:if>
							<c:if test="${line.enabled}">
								<div id="disalow${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.revoke" />" 
										data-attribut="disalow" data-line="${line.id}"><i class="cmsms-icon-error-alt"></i></a>
								</div>
							</c:if>
							<div id="lock${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.${line.locked ? 'unlocked' : 'locked'}" />" 
									data-attribut="locked" data-line="${line.id}" data-locked="${!line.locked}"><i class="cmsms-icon-lock-${line.locked ? 'open-6' : '6'}"></i></a>
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