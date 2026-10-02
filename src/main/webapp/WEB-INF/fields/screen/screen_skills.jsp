<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-container bn-primary bn-skills">
	<div class="container">
		<div class="row">
			<div class="col-lg-4"><div class="card-body"><h2 class="h-header h-header2 i-white sh-black"><spring:message code="subheader.screen.marketplace5.1"/></h2></div></div>
			<div class="col-lg-8">
				<div class="row">
					<c:forEach var="i" begin="1" end="4" step="1">
						<div class="col-6 col-md-3">
							<div class="card-skills">
								<p class="h-header i-gray text-truncate"><c:out value="${skills.getFormattedValue(i)}"/></p>
								<p class="font-mini font-bold i-white text-uppercase"><spring:message code="wizard.screen.navbar${i}"/></p>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container screen-gray">
	<div class="container">
		<div class="text-center">
			<h2 class="h-header h-header1 i-primary m-t-20"><spring:message code="txt.solution.ads6.1"/></h2>
			<p class="parag-blog header-actu m-auto"><spring:message code="txt.solution.ads6.2"/></p>
		</div>
		<ul class="nav navcard-aobubs text-center m-t-30">
			<c:set var="colors" value="s2,s1,w,h,p" scope="page"></c:set>
			<c:set var="providers" value="ok-1,upload-3,refresh,layers,sign" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li class="${state.count == 5 ? 'hidden-md-down' : ''}">
					<div class="card-solution card-aobub card-aobub${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]}">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-30"><spring:message code="txt.solution.ads6.2.${state.count}"/></h3>
					</div>
				</li>
			</c:forEach>
		</ul>
		<div class="text-center m-t-40 m-b-10">
			<a href="<c:url value="/company/marketplace/ads/new" />" class="btn btn-primary btn-big" style="min-width:240px;">
				<span><spring:message code="txt.solution.ads6.3"/><i class="cmsms-icon-explorer-arrow i-segond1 m-l-10"></i></span></a>
		</div>
	</div>
</div>
<div id="iExplorerSkills"></div>