<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<div class="row">
		<div class="col-md-4 m-t-10">
			<div class="flexed flex-colone flex-jusitify h-100">
				<div>
					<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin6.4.1"/></h4>
					<div class="card-body">
						<canvas id="canvaMarketBDD" style="height:220px;max-width:100%;"></canvas>
						<input type="hidden" id="bddMarketValue" value="${chartMarket.countAllToString()}" />
					</div>
				</div>
				<div class="card-footer m-t-10">
					<div class="card-refering card-left" style="padding:0;">
						<i class="cmsms-icon-database-3 card-trigger pull-left"></i>
						<div class="card-brand text-right">
							<span class="text-truncate font-mini i-help"><spring:message code="tool.dashboard.admin2.1.1" /></span>
							<p class="h-header i-primary"><c:out value="${chartMarket.getFormattedSumAll()}"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-4 m-t-10">
			<div class="flexed flex-colone flex-jusitify h-100">
				<div>
					<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin6.4.2"/></h4>
					<div class="card-body">
						<canvas id="canvaMarketPublished" style="height:220px;max-width:100%;"></canvas>
						<input type="hidden" id="publishedMarketValue" value="${chartMarket.countPublishedToString()}" />
					</div>
				</div>
				<div class="card-footer m-t-10">
					<div class="card-refering card-left" style="padding:0;">
						<i class="cmsms-icon-pin-1 card-trigger pull-left"></i>
						<div class="card-brand text-right">
							<span class="text-truncate font-mini i-help"><spring:message code="tool.dashboard.admin2.1.5" /></span>
							<p class="h-header i-primary"><c:out value="${chartMarket.getFormattedSumPublished()}"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-4 m-t-10">
			<div class="flexed flex-colone flex-jusitify h-100">
				<div>
					<h4 class="h-header h-header6"><spring:message code="tool.dashboard.admin6.4.3"/></h4>
					<div class="card-body" style="padding-top:15px;">
						<c:set var="providers" value="4,1,2,3,5" scope="page"></c:set>
						<c:forEach var="i" begin="1" end="5" step="1">
							<c:set var="visitPersent" value="${chartMarket.getPersentMarket(i)}" scope="page"></c:set>
							<div class="row m-t-10">
								<div class="col-6"><p class="h-header font-big i-primary m-t-5"><c:out value="${pageScope.visitPersent}%"/></p></div>
								<div class="col-6 text-right">
									<p class="font-mini i-help m-t-5"><spring:message code="tool.dashboard.data${i}"/></p>
								</div>
							</div>
							<div class="progress m-t-5">
								<div class="progress-bar progress-access${pageScope.providers.split(',')[i - 1]} animated onne progress-animated" 
									style="width:${pageScope.visitPersent}%" role="progressbar"></div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="card-footer m-t-10">
					<div class="card-refering card-left" style="padding:0;">
						<i class="cmsms-icon-chart-area-1 card-trigger pull-left"></i>
						<div class="card-brand text-right">
							<span class="text-truncate font-mini i-help"><spring:message code="tabs.statistic" /></span>
							<p class="h-header i-primary"><c:out value="${chartMarket.getPersentAllMarket()}%"/></p>
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</compress:html>