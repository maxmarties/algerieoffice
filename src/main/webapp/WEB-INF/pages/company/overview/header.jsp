<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview1"/></p>
	</div>
	<div class="bn-overview bn-header m-t-10" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
		<div id="coverOverview" class="mainheader mainheader-${mainheader.language == 'ar' ? 'rtl' : 'ltr'} background-container" 
			style="background-image: url('<c:url value="${mainheader.hasCover ? mainheader.urlCover : '/static/picts/aobns/header-min.jpg'}" />');">
			<div class="mainheader-overlay p-position" style="opacity:${mainheader.canva}; background-color:${mainheader.canvaColor};"></div>
			<div class="container h-100" style="color:${mainheader.textColor};">
				<img id="logoOverview" class="mainheader-logo" src="<c:url value="${mainheader.urlLogo}"/>" alt="${currentCompany.tradename}">
				<div class="mainheader-text">
					<h1 class="h-header h-header1 font-bold sh-black"><c:out value="${currentCompany.tradename}"/></h1>
					<p class="font-small">
						<i class="cmsms-icon-location-1 i-red m-r-10"></i><c:out value="${mainheader.address}"/> 
						<span class="text-uppercase"><spring:message code="chose.wilaya${mainheader.wilaya}"/></span>
					</p>
					<ul class="list-none list-evaluation m-t-5">
						<c:forEach var="i" begin="1" end="4" step="1">
							<li class="i-yellow"><i class="cmsms-icon-star-1"></i></li>
						</c:forEach>
						<li class="font-small font-bold i-yellow m-l-5"><c:out value="2"/></li>
						<li class="font-small font-bold i-green m-l-20">
							<i class="cmsms-icon-thumbs-up m-r-5"></i><c:out value="100%"/>
						</li>
					</ul>
					<h2 class="h-header h-header5 m-t-5 hidden-sm-down"><spring:message code="chose.activity.${mainheader.activity}"/></h2>
				</div>
				<div class="mainheader-navbar hidden-md-down">
					<p class="nav-text font-small font-italic m-b-5"><spring:message code="tool.navigate.company"/> : <c:out value="${mainheader.modifiedDate}" /></p>
					<ul class="navbar-nav nav-flex-icons nav-company">
						<c:set var="providers" value="call-out,star-3,mail-1" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<li>
								<a href="#" class="disabled">
									<i class="cmsms-icon-${pageScope.provider} i-navigate block"></i>
									<span class="font-mini"><spring:message code="tool.navigate.company${state.count}"/></span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<hr class="my-4">
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<form:form name="mainheaderForm" action="/" method="POST" modelAttribute="mainheader" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="row">
				<div class="col-md-6">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.mainheader1"/></h2>
					<spring:bind path="hasLogo"><form:input type="hidden" path="hasLogo" /></spring:bind>
					<spring:bind path="hasLogoChanged"><form:input type="hidden" path="hasLogoChanged" /></spring:bind>
					<div id="logoForm" class="form-group row m-t-30">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.companylogo" />
							<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
						</label>
						<div class="col-md-8">
							<div class="avatar-content avatar-company">
								<div class="avatar-view">
									<img id="logoImg" class="img-responsive transition-35"
										src="<c:url value="${mainheader.urlLogo}"/>" alt="<spring:message code="tooltip.avatar" />">
									<input type="file" id="choseLogo" accept="image/*" title="<spring:message code="tooltip.image" />">
								</div>
								<a id="clearLogo" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
									style="${mainheader.hasLogo ? '' : 'display:none;'}">
									<i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<spring:bind path="hasCover"><form:input type="hidden" path="hasCover" /></spring:bind>
					<spring:bind path="hasCoverChanged"><form:input type="hidden" path="hasCoverChanged" /></spring:bind>
					<div id="coverForm" class="form-group row">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.companycover" />
							<span class="help-text"><spring:message code="txt.help.companycover" /></span>
						</label>
						<div class="col-md-8">
							<div class="avatar-content avatar-cover">
								<div class="avatar-view">
									<img id="coverImg" class="img-cover transition-35"
										src="<c:url value="${mainheader.urlCover}"/>" alt="<spring:message code="tooltip.avatar" />">
									<input type="file" id="choseCover" accept="image/*" title="<spring:message code="tooltip.image" />">
								</div>
								<a id="clearCover" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
									style="${mainheader.hasCover ? '' : 'display:none;'}">
									<i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<div class="form-group row m-t-10">
						<div class="col-md-4 hidden-sm-down"></div>
						<div class="col-md-8">
							<hr class="my-4">
							<p class="font-mini m-b-20">
								<spring:message code="txt.company.overview1.1"/> 
								<a href="<c:url value="https://imagecompressor.com/fr/" />" class="lien lien-hover lien-primary" target="_blank">
									<spring:message code="lien.optimizilla"/></a> <spring:message code="txt.company.overview1.2"/>
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.mainheader2"/></h2>
					<spring:bind path="canva">
						<div id="canvaForm" class="form-group row m-t-30">
							<form:label class="col-form-label col-md-4" path="canva">
								<spring:message code="lbl.canva" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.canva" /></span>
							</form:label>
							<div class="col-md-8">
								<div class="font-mini"><span class="pull-left">0</span><span class="pull-right">1</span></div>
								<form:input class="slider" type="text" path="canva" data-slider-min="0"
									data-slider-max="1" data-slider-step="0.1" data-slider-value="${mainheader.canva}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="canvaColor">
						<div id="canvaColorForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="canvaColor">
								<spring:message code="lbl.canvacolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.canvacolor" /></span>
							</form:label>
							<div class="col-md-8">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="canvaColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="textColor">
						<div id="textColorForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="textColor">
								<spring:message code="lbl.textcolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="textColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
			<hr class="my-4">
			<div class="form-group row m-b-30">
				<div class="col-md-6">
					<div class="row">
						<div class="col-md-4 hidden-sm-down"></div>
						<div class="col-md-8">
							<div id="submitForm" class="form-submit m-t-10">
								<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
									<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>