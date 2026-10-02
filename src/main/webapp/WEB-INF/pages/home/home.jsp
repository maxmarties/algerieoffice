<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="screen-slider slider-visibility dashboard-slider background-container"
	style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/algerieoffice-min.jpg" />');">
	<div class="container">
		<div class="slider-overlay">
			<span class="block text-uppercase font-bold i-segond1" style="padding:20px 0;"><spring:message code="subheader.office.slider1"/></span>
			<h1 class="h-header h-header1"><spring:message code="subheader.office.slider2"/></h1>
			<div class="slider-content m-t-30">
				<p class="font-big i-white"><spring:message code="txt.solution.visibility1.1"/></p>
				<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')"><c:set var="lien" value="admin/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')"><c:set var="lien" value="company/dashboard" scope="page"></c:set></sec:authorize>
				<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')"><c:set var="lien" value="guest/add-company" scope="page"></c:set></sec:authorize>
				<sec:authorize access="isAnonymous()"><c:set var="lien" value="register/company" scope="page"></c:set></sec:authorize>
				<div class="form-group m-t-40 m-b-0">
					<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-segond btn-flat btn-big" style="min-width:200px;">
						<span><spring:message code="txt.solution.store1.1.1"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
				</div>
			</div>
		</div>
		<ul class="navbar-nav nav-flex-icons m-b-30">
			<c:set var="providers" value="bell-3,calendar-3,basket-2,puzzle-2,attach-3,help-2,user-group,anchor-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li class="text-center" style="width:12.5%;">
					<i class="cmsms-icon-${pageScope.provider} i-white" title="<spring:message code="subheader.office.slider2.${state.count}"/>" style="font-size:22px;"></i>
					<span class="block font-mini hidden-sm-down i-gray" style="padding-top:10px;"><spring:message code="subheader.office.slider2.${state.count}"/></span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-gray" style="padding:10px 0!important;">
	<div class="container">
		<div class="row row-mini">
			<c:set var="linksites" value="recherche/entreprises,marketplace/produits-et-services,solutions,blog" scope="page"></c:set>
			<c:set var="providers" value="location-3,calendar-3,settings-alt,newspaper-2" scope="page"></c:set>
			<c:forEach var="linksite" items="${pageScope.linksites}" varStatus="state">
				<div class="col-sm-6 col-lg-3 col-mini m-t-10 m-b-10">
					<div class="card-business flexed flex-colone flex-jusitify m-auto h-100">
						<div>
							<p class="font-mini i-help"><spring:message code="subheader.office.slider3.1.${state.count}"/></p>
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger i-segond1"></i>
							<a href="<c:url value="/${pageScope.linksite}"/>" class="h-header h-header4 lien lien-black">
								<spring:message code="subheader.office.slider3.2.${state.count}"/></a>
							<p class="font-small i-help m-t-20"><spring:message code="subheader.office.slider3.3.${state.count}"/></p>
						</div>
						<div class="widget-more m-t-20">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/${pageScope.linksite}"/>"
								class="lien lien-primary lien-hover lien-small"><spring:message code="subheader.office.slider3.4.${state.count}"/></a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<div class="screen-container screen-skills screen-demos">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="subheader.office.slider4.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto">
			<spring:message code="subheader.office.slider4.2.1"/> <span class="font-bold i-segond1 font-big"><spring:message code="subheader.office.slider4.2.2"/></span>
			<spring:message code="subheader.office.slider4.2.3"/> <span class="font-bold i-segond1 font-big"><spring:message code="subheader.office.slider4.2.4"/></span>
			<span class="h-header i-demores i-yellow font-bold"><spring:message code="subheader.office.slider4.2.5"/></span><spring:message code="subheader.office.slider4.2.6"/>
			<img height="18" src="<c:url value="/static/vectors/emoticons/1f637.png"/>" alt=";)" class="m-l-5"/>
		</p>
		<div class="row m-t-20">
			<c:set var="aocodes" value="507202,109201,501111,604622" scope="page"></c:set>
			<c:set var="providers" value="GoStore,Bildhub,Bacola,LaveAuto" scope="page"></c:set>
			<c:set var="linksites" value="gostore-demo,bildhub-demo,bacola-demo,laveauto-demo" scope="page"></c:set>
			<c:forEach var="linksite" items="${pageScope.linksites}" varStatus="state">
				<div class="col-6 m-b-20">
					<div class="widget-header widget-demopage transition-35">
						<a href="<c:url value="/entreprises/${pageScope.linksite}"/>" class="inner-link" title="${pageScope.providers.split(',')[state.count - 1]}" target="_blank"></a>
						<div class="inner-thumbnail">
							<img class="img-responsive" src="<c:url value="/static/picts/aodemos/demopage${state.count}-min.jpg"/>" alt="<c:out value="${pageScope.providers.split(',')[state.count - 1]}" />">
						</div>
					</div>
					<div class="widget-more text-center m-t-10" style="padding-bottom:10px;">
						<a class="lien lien-table lien-small" href="<c:url value="/entreprises/${pageScope.linksite}"/>">
							<spring:message code="chose.activity.${pageScope.aocodes.split(',')[state.count - 1]}"/>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="text-center m-t-20">
			<a href="<c:url value="/${pageScope.lien}" />" class="btn btn-primary btn-big" style="min-width:210px;">
				<span><spring:message code="txt.solution.office5.3"/></span></a>
			<p class="font-mini m-t-10"><spring:message code="txt.solution.office5.4"/></p>
		</div>
	</div>
</div>
<div class="screen-container bn-primary bn-skills">
	<div class="container">
		<div class="row">
			<div class="col-lg-4">
				<div class="card-body">
					<h2 class="h-header h-header2 i-white sh-black"><spring:message code="subheader.screen.solution1.1"/></h2>
					<p class="h-header i-gray m-t-10"><spring:message code="txt.solution.office1.1.1"/></p>
				</div>
			</div>
			<div class="col-lg-8">
				<div class="row">
					<div class="col-sm-6">
						<ul class="list-none liste-block m-l-30">
							<c:forEach var="i" begin="1" end="5" step="1">
								<li class="i-white m-t-10"><i class="cmsms-icon-ok-2 i-segond1 m-r-10"></i><spring:message code="subheader.office.slider4.3.1.${i}"/></li>
							</c:forEach>
						</ul>
					</div>
					<div class="col-sm-6">
						<ul class="list-none liste-block m-l-30">
							<c:forEach var="i" begin="1" end="5" step="1">
								<li class="i-white m-t-10"><i class="cmsms-icon-star-1 i-yellow m-r-10"></i><spring:message code="subheader.office.slider4.3.2.${i}"/></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="m-t-40 m-b-30 m-l-30">
					<a href="<c:url value="/solutions" />" class="btn btn-segond btn-big" style="min-width:210px;">
						<span><spring:message code="subheader.office.slider3.2.3"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="screen-container screen-gray" style="border-top:none!important;">
	<div class="container">
		<h2 class="h-header h-header2 h-skills text-center i-primary m-auto"><spring:message code="subheader.screen.solution1.2"/></h2>
		<p class="parag-blog text-center m-auto"><spring:message code="txt.solution.office1.2.1"/></p>
		<div class="m-t-10 m-b-10">
			<div class="inner-demodash m-auto">
				<img class="img-responsive" src="<c:url value="/static/picts/aodemos/demodash1-min.jpg"/>" alt="<spring:message code="subheader.screen.solution1.2"/>">
			</div>
		</div>
		<div class="parag-blog text-center m-auto">
			<p><spring:message code="subheader.office.slider4.4.1"/></p>
			<ul class="list-none liste-inline font-small i-help m-t-10">
				<c:forEach var="i" begin="1" end="4" step="1">
					<li class="m-t-10 m-r-20" style="display:inline-block!important;"><i class="cmsms-icon-ok-2 i-segond1 m-r-10"></i><spring:message code="subheader.office.slider4.4.1.${i}"/></li>
				</c:forEach>
			</ul>
		</div>
		<div class="text-center m-t-20 m-b-20">
			<a href="<c:url value="/solutions/presentation" />" class="btn btn-primary btn-big" style="min-width:210px;">
				<span><spring:message code="txt.solution.home6.3.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
		</div>
	</div>
</div>
<div class="screen-area bn-segond">
	<div class="container">
		<div class="widget-demonewsletter">
			<div class="card-desktop card-right m-b-20">
				<div class="row">
					<div class="col-sm-4 col-md-6 col-lg-5 m-t-20">
						<div class="inner-thumbnail m-auto" style="padding:0 14px;">
							<img class="img-responsive" src="<c:url value="/static/picts/aodemos/demomail-min.png"/>" alt="<spring:message code="subheader.office.slider4.4.2"/>">
						</div>
					</div>
					<div class="col-sm-8 col-md-6 col-lg-7 m-t-20">
						<div class="widget-body">
							<h3 class="h-doc h-header3 i-white"><spring:message code="subheader.office.slider4.4.2"/></h3>
							<p class="font-big i-white m-t-30"><spring:message code="subheader.office.slider4.4.2.1"/></p>
							<p class="i-gray m-t-20"><spring:message code="subheader.office.slider4.4.2.2"/> :</p>
							<ul class="list-none liste-block m-t-30 m-l-30">
								<c:forEach var="i" begin="1" end="4" step="1">
									<li class="i-white m-t-10"><i class="cmsms-icon-ok-2 i-primary m-r-10"></i><spring:message code="subheader.office.slider4.4.3.${i}"/></li>
								</c:forEach>
								<li class="i-white m-t-10"><i class="cmsms-icon-star-1 i-yellow m-r-10"></i><spring:message code="subheader.office.slider4.4.3.5"/></li>
							</ul>
							<div class="form-group m-t-40 m-l-30">
								<a href="<c:url value="/solutions/easylist" />" class="btn btn-primary" style="min-width:160px;">
									<span><spring:message code="lien.more"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<canvas id="playArea" class="p-position"></canvas>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect m-t-10"><h2 class="h-header m-auto"><spring:message code="txt.solution.office4.1"/></h2></div>
		<p class="text-center m-t-30"><spring:message code="txt.solution.office4.2"/></p>
		<ul class="nav navcard-solution m-t-40">
			<c:set var="colors" value="s2,s1,g,p" scope="page"></c:set>
			<c:set var="providers" value="tablet-2,target-3,chart-pie-3,eye-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<div class="card-solution card-solution${state.count} cardcolor-${pageScope.colors.split(',')[state.count - 1]} card-mini h-100">
						<i class="cmsms-icon-${pageScope.provider} breadview-trigger"></i>
						<h3 class="h-header m-t-20"><spring:message code="txt.solution.office4.2.${state.count}.1"/></h3>
						<p class="font-small"><spring:message code="txt.solution.office4.2.${state.count}.2"/></p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="txt.solution.office2.3"/></h2>
		<p class="parag-blog text-center m-auto"><spring:message code="txt.solution.office2.4"/></p>
		<div class="row m-t-10">
			<c:set var="providers" value="bag-alt,attach-3,calendar-3,cup-2" scope="page"></c:set>
			<c:set var="markets" value="produits-et-services,annonces,evenements,offres-emploi" scope="page"></c:set>
			<c:forEach var="market" items="${pageScope.markets}" varStatus="state">
				<div class="col-sm-6 col-lg-3 m-t-20">
					<div class="card-office">
						<a href="<c:url value="/marketplace/${pageScope.market}"/>" class="inner-link"></a>
						<div class="card-back card-back${state.count} transition-5">
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger i-segond1"></i>
							<h3 class="h-header h-header4 i-primary"><spring:message code="txt.solution.marketplace1.3.${state.count}.2"/></h3>
							<p class="font-big i-help"><spring:message code="txt.solution.office2.4.${state.count}"/></p>
						</div>
						<div class="card-thumbnail background-container flexed transition-5"
							style="background-image: radial-gradient(circle at center, rgba(23,129,67,.75) 0%, ${currentConfig.aocolor(44)} 100%), url('<c:url value="/static/vectors/tours/m_tool${state.count}-min.jpg" />');">
							<p class="h-header text-center sh-black m-auto transition-5">
								<spring:message code="subheader.screen.marketplace${state.count}"/><i class="cmsms-icon-explorer-arrow breadview-trigger"></i>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="form-group text-center m-t-40 m-b-0">
			<a href="<c:url value="/marketplace/produits-et-services" />" class="btn btn-segond btn-big" style="min-width:190px;">
				<span><spring:message code="sidebar.company.dashboard2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
		</div>
	</div>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-detect"><h2 class="h-header m-auto"><spring:message code="subheader.office.slider4.5.1"/></h2></div>
		<p class="parag-blog header-detect text-center m-auto">
			<spring:message code="subheader.office.slider4.5.3.1"/> 
			<a class="lien lien-primary lien-hover" href="<c:url value="https://themeforest.net/category/wordpress"/>" target="_blank"><spring:message code="subheader.office.slider4.2.9"/></a>, 
			<spring:message code="subheader.office.slider4.5.3.2"/><br><span class="h-header i-demores i-yellow font-bold"><spring:message code="subheader.office.slider4.5.3.3"/></span>
			<spring:message code="subheader.office.slider4.5.3.4"/>
		</p>
		<div class="row m-t-20">
			<c:set var="providers" value="GoStore,Bildhub,Bacola,LaveAuto" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-6 m-b-20">
					<div class="widget-header widget-demopage transition-35">
						<a href="<c:url value="/maquettes/${state.count}"/>" class="inner-link" title="${pageScope.provider}" target="_blank"></a>
						<div class="inner-thumbnail">
							<img class="img-responsive" src="<c:url value="/static/picts/aodemos/demostore${state.count}-min.jpg"/>" alt="<c:out value="${pageScope.provider}" />">
						</div>
					</div>
					<div class="widget-more font-small text-center m-t-10" style="padding-bottom:10px;">
						<a href="<c:url value="/maquettes/${state.count}"/>" class="lien lien-primary lien-hover" target="_blank"><c:out value="${pageScope.provider}" /></a>
						<span class="font-bold i-segond1 m-l-10"><spring:message code="subheader.office.slider4.6.3"/></span>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="parag-blog text-center m-auto">
			<p><spring:message code="subheader.office.slider4.5.4"/></p>
			<ul class="list-none liste-inline font-small i-help m-t-20">
				<c:forEach var="i" begin="1" end="5" step="1">
					<li class="m-t-10 m-r-20" style="display:inline-block!important;"><i class="cmsms-icon-ok-2 i-segond1 m-r-10"></i><spring:message code="subheader.office.slider4.5.4.${i}"/></li>
				</c:forEach>
			</ul>
		</div>
		<hr class="my-2">
		<p class="parag-blog header-detect text-center m-auto">
			<spring:message code="subheader.office.slider4.5.5.3"/>
			<img height="18" src="<c:url value="/static/vectors/emoticons/1f44${langage.lang == 'ar' ? '8' : '9'}.png"/>" alt=";)" class="m-l-10 m-r-5"/>
			<a class="lien lien-segond lien-hover" href="<c:url value="/contacts"/>"><spring:message code="subheader.office.slider4.5.5.4"/></a>.
		</p>
		<hr class="my-2">
		<c:if test="${!empty currentSocial.socialFrame}">
			<p class="parag-blog header-detect text-center m-auto"><spring:message code="subheader.office.slider4.6.4"/></p>
			<div id="iEmbedAovideo" class="embed-video-algerieoffice"><c:out value="${currentSocial.socialFrame}" escapeXml="false" /></div>
		</c:if>
	</div>
</div>
<div class="screen-container screen-gray" style="padding:10px 0 20px 0!important;">
	<div class="container">
		<div class="row row-mini">
			<c:set var="linksites" value="recherche/entreprises,marketplace/produits-et-services,solutions,blog" scope="page"></c:set>
			<c:set var="providers" value="location-3,calendar-3,settings-alt,newspaper-2" scope="page"></c:set>
			<c:forEach var="linksite" items="${pageScope.linksites}" varStatus="state">
				<div class="col-sm-6 col-lg-3 col-mini m-t-10 m-b-10">
					<div class="card-business flexed flex-colone flex-jusitify m-auto h-100">
						<div>
							<p class="font-mini i-help"><spring:message code="subheader.office.slider3.1.${state.count}"/></p>
							<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} breadview-trigger i-segond1"></i>
							<a href="<c:url value="/${pageScope.linksite}"/>" class="h-header h-header4 lien lien-black">
								<spring:message code="subheader.office.slider3.2.${state.count}"/></a>
							<p class="font-small i-help m-t-20"><spring:message code="subheader.office.slider3.3.${state.count}"/></p>
						</div>
						<div class="widget-more m-t-20">
							<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/${pageScope.linksite}"/>"
								class="lien lien-primary lien-hover lien-small"><spring:message code="subheader.office.slider3.4.${state.count}"/></a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>