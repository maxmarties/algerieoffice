<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!analyticEvaluation.hasPresent()}">
		<div class="card-empty text-center">
			<i class="cmsms-icon-speed i-empty m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-20"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text m-t-5"><spring:message code="txt.help.analytic4.3"/></span>
		</div>
	</c:when>
	<c:otherwise>
		<div class="card-body">
			<div class="row row-mini">
				<div class="col-sm-6 col-md-12 col-lg-6 m-b-10">
					<div class="card-evaluation">
						<div id="circleEvaluationLiked" class="circle-3d circle-evaluation pull-left" data-counter="${analyticEvaluation.getPesrsentLiked()}">
							<div class="circle-content text-center">
								<span class="h-header font-bold i-segond"><c:out value="${analyticEvaluation.getPesrsentLiked()}%" /></span>
							</div>
							<div id="circleCanvasLiked" class="circle-canvas"></div>
						</div>
						<div class="card-brand">
							<p class="font-small i-help"><spring:message code="tool.view.like"/></p>
							<p class="h-header font-large i-primary"><c:out value="${analyticEvaluation.countLiked}"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
				<div class="col-sm-6 col-md-12 col-lg-6 m-b-10">
					<div class="card-evaluation">
						<div id="circleEvaluationNote" class="circle-3d circle-evaluation pull-left" data-counter="${analyticEvaluation.getPesrsentNote()}">
							<div class="circle-content text-center">
								<span class="h-header font-bold i-blue"><c:out value="${analyticEvaluation.getPesrsentNote()}%" /></span>
							</div>
							<div id="circleCanvasNote" class="circle-canvas"></div>
						</div>
						<div class="card-brand">
							<p class="font-small i-help"><spring:message code="tabs.note"/></p>
							<p class="h-header font-large i-primary"><c:out value="${analyticEvaluation.getFormattedNote()} / 10"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="card-footer m-t-10">
			<c:set var="persent" value="${analyticEvaluation.getPesrsentCount()}" scope="page"></c:set>
			<p class="font-small"><spring:message code="txt.help.analytic4.4" arguments="${pageScope.persent}"/></p>
			<div class="progress m-t-5">
				<div class="progress-bar progress-primary animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
</compress:html>