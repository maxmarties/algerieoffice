<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!analyticDocument.hasPresent()}">
		<div class="card-empty text-center">
			<i class="cmsms-icon-attach-3 i-empty m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-20"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text m-t-5"><spring:message code="txt.help.analytic2.7"/></span>
		</div>
	</c:when>
	<c:otherwise>
		<div class="card-body">
			<canvas id="chartAnalyticDocument" style="height:220px;max-width:100%;"></canvas>
			<input type="hidden" id="documentsAnalyticValue" value="${analyticDocument.countFavoritesToString()}" />
		</div>
		<div class="card-footer m-t-10">
			<div class="row">
				<div class="col-8">
					<p class="font-small i-help"><spring:message code="txt.help.analytic2.8"/></p>
					<p class="h-header font-big i-prmary"><c:out value="${analyticDocument.countAlert}"/></p>
				</div>
				<div class="col-4 text-right">
					<c:set var="persentDocument" value="${analyticDocument.getPesrsentAlert()}" scope="page"></c:set>
					<p class="h-header font-large i-primary m-t-10">
						<i class="cmsms-icon-${pageScope.persentDocument >= 50 ? 'up-1 i-green' : 'down-1 i-red'} m-r-10"></i><c:out value="${pageScope.persentDocument}%"/>
					</p>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
</compress:html>