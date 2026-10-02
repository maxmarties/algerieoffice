<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin ${!line.approuved ? 'inchecked' : ''}">
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
							<c:when test="${!line.locked}">
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
				<td><spring:message code="chose.report${line.type}" /></td>
				<td>
					<a href="<c:url value="${line.identifyURL}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
						<c:out value="${line.username}" />
					</a>
				</td>
				<td><c:out value="${line.reason}" /></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${!empty line.fileUrl}">
							<a href="<c:url value="${line.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
								<spring:message code="tool.view.file" />
							</a>
						</c:when>
						<c:otherwise><c:out value="--" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><c:out value="${line.postedDate}" /></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.locked}"><i class="cmsms-icon-lock-5 i-red"></i></c:when>
						<c:otherwise><c:out value="--" /></c:otherwise>
					</c:choose>
				</td>
				<td>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<div class="form-groupe text-right m-b-0 m-r-10">
							<c:if test="${!line.approuved}">
								<div id="validate${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.approuved" />" 
										data-attribut="validate" data-validate="${line.id}"><i class="cmsms-icon-ok-5"></i></a>
								</div>
							</c:if>
							<c:if test="${!line.locked}">
								<div id="lock${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.locked" />" 
										data-attribut="locked" data-locked="${line.companyId}" data-submit="${line.id}"><i class="cmsms-icon-lock-6"></i></a>
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