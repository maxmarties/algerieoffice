<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
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
				<td class="td-brand result-td">
					<img src="<c:url value="${line.companyMini.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.companyMini.tradename}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="${line.companyMini.companyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.companyMini.tradename}" /></a>
						<span class="help-text"><spring:message code="chose.activity.${line.companyMini.activity}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2 td-parag" style="${currTable.column[1] ? '' : 'display:none;'}">
					<span class="font-bold i-primary"><c:out value="${line.title}" /></span>
					<span class="help-text"><c:out value="${line.message}" /></span>
				</td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.autorized ? 'lock-open-alt i-green' : 'lock i-red'}"></i></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.approuved ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
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