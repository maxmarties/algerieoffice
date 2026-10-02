<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!analyticAccess.hasPresent()}">
		<div class="card-empty text-center">
			<i class="cmsms-icon-award-alt i-empty m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-20"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text m-t-5"><spring:message code="txt.help.analytic2.5"/></span>
		</div>
	</c:when>
	<c:otherwise>
		<div class="card-body">
			<canvas id="chartAnalyticAccess" style="height:220px;max-width:100%;"></canvas>
			<input type="hidden" id="accessAnalyticValue" value="${analyticAccess.countAccessToString()}" />
		</div>
		<div class="card-footer m-t-10">
			<div class="row">
				<c:forEach var="i" begin="1" end="4">
					<div class="col-sm-6 m-t-10 m-b-10">
						<c:set var="accessPersent" value="${analyticAccess.getPesrsentAttribut(i)}" scope="page"></c:set>
						<div class="row">
							<div class="col-6"><p class="h-header font-large i-primary m-t-5"><c:out value="${pageScope.accessPersent}%"/></p></div>
							<div class="col-6 text-right">
								<p class="font-small i-help m-t-5"><spring:message code="txt.help.analytic2.5.${i}"/></p>
							</div>
						</div>
						<div class="progress m-t-10">
							<div class="progress-bar progress-access${i} animated onne progress-animated" style="width:${pageScope.accessPersent}%" role="progressbar"></div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:otherwise>
</c:choose>
</compress:html>