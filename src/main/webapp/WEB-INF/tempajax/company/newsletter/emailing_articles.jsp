<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="targetItemsForm" class="form-group">
	<c:set var="states" value="2,3,7,8,0" scope="page"></c:set>
	<c:set var="providers" value="actu,event,annonce,employe,post" scope="page"></c:set>
	<div class="input-group-icon">
		<input class="form-control form-error" type="search" id="findtarget" name="findtarget" placeholder="<spring:message code="tool.find.${pageScope.providers.split(',')[currType - 1]}"/>" />
		<span class="input-group-addon input-group-simple"><i class="cmsms-icon-search-1"></i></span>
	</div>
	<div class="table-scroll m-t-5" style="min-height:420px;">
		<table class="table table-target">
			<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
			<tbody class="font-small">
				<tr class="header">
					<td class="td-check">
						<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
							<input type="checkbox" name="checkAllRowItem"/>
							<span class="input-span"></span>
						</label>
					</td>
					<td><spring:message code="explorer.desktop.element${pageScope.states.split(',')[currType - 1]}.1"/></td>
				</tr>
				<c:forEach var="choseArticle" items="${choseArticles}">
					<tr>
						<td class="td-check">
							<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
								<input type="checkbox" name="checkRowItem" data-row="${choseArticle.uuid()}"/>
								<span class="input-span"></span>
							</label>
						</td>
						<td class="td-result"><c:out value="${choseArticle.name}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="countItems" value="0" />
	</div>
	<span class="help-text m-t-10"><span class="font-bold countTarget">0</span> <spring:message code="tool.find.select" /></span>
</div>