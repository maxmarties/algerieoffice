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
				<td class="td-check td-image"><i class="icon-talk icon-talk${line.type}"></i></td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><spring:message code="txt.inbox.talk${line.type}"/></td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${!empty line.link}">
							<c:choose>
								<c:when test="${line.consulted}"><c:set var="href" scope="page" value="${line.link}"></c:set></c:when>
								<c:otherwise><c:set var="href" scope="page" value="/inbox/talk/href?uuid=${line.id}"></c:set></c:otherwise>
							</c:choose>
							<a href="<c:url value="${pageScope.href}" />" class="lien lien-hover lien-primary lien-small">
								<spring:message code="lien.notification" />
							</a>		
						</c:when>
						<c:otherwise><c:out value="--"/></c:otherwise>
					</c:choose>
				</td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.talkedDate}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
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
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>