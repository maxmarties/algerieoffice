<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal">
	<spring:message code="wizard.help.begginer1"/> <span class="i-help">(<c:out value="${begginer.countFinished()}/${begginer.countTasks()}"/>)</span>
</h2>
<p class="font-small m-t-10"><spring:message code="txt.company.help1.2"/></p>
<hr class="my-4">
<ul class="list-none list-block list-begginer">
	<li>
		<span class="checkbox-span checkbox-checked pull-left"></span>
		<div class="begginer-brand">
			<span class="font-bold i-primary"><spring:message code="txt.help.begginer0"/></span>
			<p class="font-small m-t-5"><spring:message code="txt.help.begginer0.1"/></p>
		</div>
		<span class="clearfix"></span>
	</li>
	<c:forEach var="i" begin="1" end="21" step="1">
		<c:if test="${begginer.tasks[i - 1]}">
			<li>
				<span class="checkbox-span checkbox-checked pull-left"></span>
				<div class="begginer-brand">
					<span class="font-bold i-primary"><spring:message code="txt.help.begginer${i}"/></span>
					<p class="font-small m-t-5"><spring:message code="txt.help.begginer${i}.2"/></p>
				</div>
				<span class="clearfix"></span>
			</li>
		</c:if>
	</c:forEach>
</ul>