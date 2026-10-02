<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
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
				<td class="td-check td-image column2" style="${currTable.column[1] ? '' : 'display:none;'}">
					<div class="img-circle img-container ${line.online ? 'ind-login' : ''}">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
					</div>
				</td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}">
					<a class="lien lien-table iMessage" data-id="${line.userId}" data-avatar="${line.urlAvatar}" data-name="${line.username}" data-login="${line.online}">
						<c:out value="${line.username}" />
					</a>
				</td>
				<td class="column4 ${line.emojis ? 'td-emojis' : 'explorer-result'}" style="${currTable.column[3] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${line.emojis}"><img src="<c:url value="/static/vectors/emoticons/${line.message}.png"/>"></c:when>
						<c:otherwise><c:out value="${line.message}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td>
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