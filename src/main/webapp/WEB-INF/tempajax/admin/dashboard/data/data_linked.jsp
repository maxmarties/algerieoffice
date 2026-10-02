<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<div class="row">
		<c:forEach var="i" begin="1" end="5">
			<div class="col-sm-6 m-t-10 m-b-10">
				<c:set var="linkedPersent" value="${dashboardLinked.getPesrsentLinked(i)}" scope="page"></c:set>
				<div class="row">
					<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.linkedPersent}%"/></p></div>
					<div class="col-8 text-right">
						<span class="help-text"><spring:message code="tool.dashboard.admin4.2.${i}"/></span>
						<p class="h-header font-small"><c:out value="${dashboardLinked.parseLinked(i)}"/></p>
					</div>
				</div>
				<div class="progress"><div class="progress-bar progress-access${i} animated onne progress-animated" style="width:${pageScope.linkedPersent}%" role="progressbar"></div></div>
			</div>
		</c:forEach>
		<div class="col-sm-6 m-t-10 m-b-10">
			<c:set var="allPersent" value="${dashboardLinked.getPesrsentAll()}" scope="page"></c:set>
			<div class="row">
				<div class="col-4"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.allPersent}%"/></p></div>
				<div class="col-8 text-right">
					<span class="help-text"><spring:message code="tool.dashboard.admin4.2.6"/></span>
					<p class="h-header font-small"><c:out value="${dashboardLinked.parseSum()}"/></p>
				</div>
			</div>
			<div class="progress"><div class="progress-bar progress-access6 animated onne progress-animated" style="width:${pageScope.allPersent}%" role="progressbar"></div></div>
		</div>
	</div>
</compress:html>