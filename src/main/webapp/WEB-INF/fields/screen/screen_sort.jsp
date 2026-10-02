<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-toolbar m-t-20">
	<nav class="navbar">
		<ul class="nav nav-pagination nav-pagination-sort ml-auto">
			<li class="nav-text font-small m-r-10"><spring:message code="tool.explorer.sort"/> :</li>
			<li class="form-explorer-sort">
				<select class="form-select2-simple" id="screenSort" name="screenSort">
					<c:forEach var="choseSorter" items="${requestScope.choseSortersScreen}" varStatus="state">
						<option value="${state.count}"><spring:message code="tabs.explorer.${choseSorter}"/></option>
					</c:forEach>
				</select>
			</li>
		</ul>
		<ul class="nav nav-pagination" data-toggle="buttons">
			<li>
				<label class="btn btn-icon btn-simple ${empty desc ? 'active' : ''}" title="<spring:message code="tooltip.explorer.sort1" />">
					<i class="cmsms-icon-sort-name-up"></i><input type="radio" name="screenDesc" class="hidden" value="false" ${empty desc ? 'checked' : ''} />
				</label>
			</li>
			<li>
				<label class="btn btn-icon btn-simple ${!empty desc ? 'active' : ''}" title="<spring:message code="tooltip.explorer.sort2" />">
					<i class="cmsms-icon-sort-name-down"></i><input type="radio" name="screenDesc" class="hidden" value="true" ${!empty desc ? 'checked' : ''}/>
				</label>
			</li>
		</ul>
	</nav>
</div>