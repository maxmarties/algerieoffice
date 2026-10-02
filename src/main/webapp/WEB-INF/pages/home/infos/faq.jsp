<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.1"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="subheader.screen.faq"/></h1></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.infos.faq"/></p>
		<div class="wizard wizard-screen">
			<div class="wizard-card m-t-20">
				<div class="wizard-nav">
					<ul class="nav nav-pills">
						<c:set var="providers" value="user-5,commerical-building,bookmark,megaphone-1,basket" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<c:set var="fatitle" scope="page"><spring:message code="txt.infos.faq${state.count}"/></c:set>
							<li style="width: 20%;">
								<a class="lien" data-toggle="tab" title="${pageScope.fatitle}">
									<i class="cmsms-icon-${pageScope.provider} m-r-5"></i>
									<span class="hidden-sm-down"><spring:message code="txt.infos.faq${state.count}"/></span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="wizard-content">
					<div class="wizard-body">
						<c:forEach var="i" begin="1" end="5" step="1">
							<div id="tab-pill${i}" class="animated fadeInUp" style="${i != 1 ? 'display:none;' : ''}">
								<c:import url="/WEB-INF/pages/home/infos/faq/form_faq${i}.jsp"/>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<hr class="m-t-40 m-b-40">
		<p class="font-big text-center m-b-30">
			<spring:message code="txt.infos.faq6.1"/><a href="<c:url value="/contacts" />" class="lien lien-black font-bold m-l-5"><spring:message code="lien.subscribe"/></a>
		</p>
	</div>
</div>