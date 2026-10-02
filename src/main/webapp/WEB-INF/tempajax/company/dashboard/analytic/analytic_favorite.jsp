<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!analyticFavorite.hasPresent()}">
		<div class="card-empty text-center">
			<i class="cmsms-icon-star-3 i-empty m-t-10"></i>
			<p class="h-header font-bold font-big i-primary m-t-20"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text m-t-5"><spring:message code="txt.help.analytic2.2"/></span>
		</div>
	</c:when>
	<c:otherwise>
		<div class="card-body">
			<canvas id="chartAnalyticFavorite" style="height:220px;max-width:100%;"></canvas>
			<input type="hidden" id="favoritesAnalyticValue" value="${analyticFavorite.countFavoritesToString()}" />
		</div>
		<div class="card-footer m-t-10">
			<div class="row">
				<div class="col-6">
					<p class="font-small i-help"><spring:message code="txt.help.analytic2.3"/></p>
					<p class="h-header font-large i-segond m-t-5"><c:out value="${analyticFavorite.countAlert}"/></p>
				</div>
				<div class="col-6 text-right">
					<p class="font-small i-help"><spring:message code="tool.order.total"/></p>
					<p class="h-header font-large i-primary m-t-5"><c:out value="${analyticFavorite.getSumCountFavorites()}"/></p>
				</div>
			</div>
			<div class="progress m-t-10">
				<div class="progress-bar progress-segoond animated onne progress-animated" style="width:${analyticFavorite.getPesrsentAlert()}%" role="progressbar"></div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
</compress:html>