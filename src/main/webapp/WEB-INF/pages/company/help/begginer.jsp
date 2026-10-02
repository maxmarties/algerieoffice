<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/help"/>" class="lien lien-black">
			<i class="cmsms-icon-help-circled-1 m-r-5"></i><spring:message code="sidebar.company.dashboard11"/></a></li>
		<li class="active"><spring:message code="subheader.case2.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="subheader.case2.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.help1"/></p>
	</div>
	<div class="page-container">
		<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="txt.company.help1.1"/></h2>
		<c:set var="persent" value="${begginer.persentFinished()}" scope="page"></c:set>
		<div class="row m-t-20">
			<div class="col-6"><p class="h-header font-large i-primary"><c:out value="${pageScope.persent}%"/></p></div>
			<div class="col-6 text-right"><p class="font-small i-help"><spring:message code="tool.checklist"/></p></div>
		</div>
		<div class="progress m-t-5"><div class="progress-bar progress-segoond animated onne progress-animated" style="width:${pageScope.persent}%" role="progressbar"></div></div>
		<div class="wizard wizard-user m-t-10">
			<div class="wizard-tabbed">
				<ul class="nav nav-tabs nav-tabs2" role="tablist">
					<c:set var="providers" value="check-3,edit-alt" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<li>
							<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
								title="<spring:message code="wizard.help.begginer${state.count}"/>">
								<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
								<span class="help-tab"><spring:message code="wizard.help.begginer${state.count}"/></span>
							</a>
						</li>
					</c:forEach>
				</ul>
				<div class="wizard-content">
					<div class="wizard-body">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
								<c:import url="/WEB-INF/pages/company/help/begginer/form_begginer${i}.jsp"/>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>