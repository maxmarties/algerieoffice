<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/account/help"/>" class="lien lien-black">
			<i class="cmsms-icon-help-circled-1 m-r-5"></i><spring:message code="sidebar.company.dashboard11"/></a></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.user.help"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.account4"/></p>
	</div>
	<div class="page-container">
		<div class="row m-b-20">
			<div class="col-lg-8 m-t-20">
				<h2 class="h-header h-header3 i-primary"><spring:message code="lien.support"/></h2>
				<p class="font-small m-t-10">
					<spring:message code="tool.view.in"/> <a href="<c:url value="/infos/faq"/>" 
						class="lien lien-primary lien-hover" target="_blank"><spring:message code="lien.support"/></a> <spring:message code="txt.help.case1.1"/>
				</p>
				<hr class="my-4">
				<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.case1"/></h2>
				<div class="card-help m-t-20">
					<div class="hidden-md-down">
						<i class="cmsms-icon-explorer-hand i-22"></i><a id="iStarted" class="lien lien-company font-big"><spring:message code="subheader.case1.1"/></a>
					</div>
					<p class="font-small m-t-10"><spring:message code="txt.help.case1.1.1"/></p>
					<div class="alert alert-info hidden-md-up m-t-10"><i class="cmsms-icon-info i-alert"></i><p class="p-alert"><spring:message code="txt.help.appeerance2"/></p></div>
				</div>
				<div class="card-help m-t-20">
					<i class="cmsms-icon-explorer-hand i-22"></i><a href="mailto:<spring:message code="app.support"/>" 
						class="lien lien-company font-big"><spring:message code="subheader.case1.2"/></a>
					<p class="font-small m-t-10"><spring:message code="txt.help.case1.1.2.1"/> <a href="mailto:<spring:message code="app.support"/>" 
						class="lien lien-primary lien-hover"><spring:message code="app.support"/></a>. <spring:message code="txt.help.case1.1.2.2"/></p>
				</div>
				<div class="card-help m-t-20">
					<i class="cmsms-icon-explorer-hand i-22"></i><a id="iHelpSupport" class="lien lien-company font-big"><spring:message code="subheader.case1.3"/></a>
					<p class="font-small m-t-10"><spring:message code="txt.help.case1.1.3"/></p>
				</div>
			</div>
			<div class="col-lg-4 m-t-20">
				<hr class="my-4 hidden-md-up">
				<h2 class="h-header h-header3 i-primary"><spring:message code="subheader.case2"/></h2>
				<p class="font-small m-t-10"><spring:message code="txt.help.case1.2"/> :</p>
				<div class="font-small m-t-10">
					<spring:message code="txt.help.case1.2.1"/>:
					<ul class="list-none list-inline" style="display:inline-block;">
						<li class="m-l-10"><a href="<c:url value="https://www.facebook.com/algerieoffice"/>" class="lien lien-black"><i class="cmsms-icon-facebook"></i></a></li>
						<li class="m-l-10"><a href="<c:url value="https://twitter.com/AlgerieOffice"/>" class="lien lien-black"><i class="cmsms-icon-twitter"></i></a></li>
						<li class="m-l-10"><a href="<c:url value="https://www.linkedin.com/company/algerieoffice"/>" class="lien lien-black"><i class="cmsms-icon-linkedin"></i></a></li>
						<li class="m-l-10"><a href="<c:url value="https://www.youtube.com/channel/UCVtW1WLbMT63ny6X1lhehAw"/>" class="lien lien-black"><i class="cmsms-icon-youtube"></i></a></li>
					</ul>
				</div>
				<p class="font-small m-t-10">
					<spring:message code="txt.help.case1.2.2"/> <a href="mailto:<spring:message code="app.contact" />" class="lien lien-primary lien-hover"><spring:message code="app.contact" /></a>
				</p>
				<p class="font-small m-t-10"><spring:message code="txt.help.case1.2.3"/></p>
				<p class="font-big font-bold i-primary m-t-10"><spring:message code="app.brand"/></p>
				<p class="font-small m-t-5"><spring:message code="app.address"/></p>
				<p class="font-small m-t-10"><span class="font-bold"><spring:message code="app.mobile"/></span>, <spring:message code="app.footer"/></p>
			</div>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/help/started_user.jsp"/>