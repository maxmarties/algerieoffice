<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview4"/></p>
	</div>
	<div class="bn-overview bn-header m-t-10" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
		<div id="sliderOverview" class="bn-box slider-overview m-auto ${!mainslider.hasNavigation ? 'slider-notnav' : ''} ${!mainslider.hasDots ? 'slider-notdots' : ''}" 
			style="${mainslider.titles.isEmpty() ? 'display:none;' : ''}">
			<input type="hidden" id="lastAnimateFade" value="${mainslider.animateFade}"/>
			<div class="slider-body">
				<c:forEach var="itemDescription" items="${mainslider.descriptions}" varStatus="state">
					<div id="sliderFade${state.count}" class="slider-fade animated onne ${mainslider.animateFade != '-' ? mainslider.animateFade : ''}" 
						style="color:${mainslider.textColor}; background:${mainslider.fadeColor}; display:none;">
						<c:out value="${itemDescription}"/>
					</div>
				</c:forEach>
				<div class="owl-carousel owl-theme">
					<c:forEach var="itemTitle" items="${mainslider.titles}" varStatus="state">
						<div class="item" data-item="${state.count}">
							<img class="img-responsive" src="<c:url value="/media/photo?photoId=${mainslider.photosUUID.get(state.count - 1)}"/>" 
								alt="<c:out value="${itemTitle}" />">
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<p id="sliderEmptyOverview" class="font-small font-bold text-center" style="${!mainslider.titles.isEmpty() ? 'display:none;' : ''}">
			<spring:message code="tool.ind.slider"/></p>
	</div>
	<hr class="my-4">
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<form:form name="mainsliderForm" action="/" method="POST" modelAttribute="mainslider" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
			<spring:bind path="titles"><form:input type="hidden" path="titles" /></spring:bind>
			<spring:bind path="descriptions"><form:input type="hidden" path="descriptions" /></spring:bind>
			<spring:bind path="photosUUID"><form:input type="hidden" path="photosUUID" /></spring:bind>
			<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
			<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
			<spring:bind path="updateSlider"><form:input type="hidden" path="updateSlider" /></spring:bind>
			<div class="row">
				<div class="col-md-6">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.slider1"/></h2>
					<div id="itemsForm" class="form-group row">
						<label class="col-form-label col-lg-4">
							<spring:message code="lbl.images" /> 
							<small class="min">(<span id="itemSize"><c:out value="${mainslider.titles.size()}"/></span>/9)</small>
							<span class="help-text"><spring:message code="txt.help.slider1" /></span>
						</label>
						<div class="col-lg-8">
							<table id="tableItems" class="table table-page m-t-10 ${!mainslider.titles.isEmpty() ? 'm-b-20' : ''}">
								<thead>
									<tr>
										<th style="width:26px;"></th><th style="width:calc(100% - 94px);"></th>
										<th style="width:34px;"></th><th style="width:34px;"></th>
									</tr>
								</thead>
								<tbody class="font-small">
									<c:forEach var="itemTitle" items="${mainslider.titles}" varStatus="state">
										<tr id="lineItem${state.count}" data-ident="${mainslider.idents.get(state.count - 1)}"
											data-uuid="${mainslider.photosUUID.get(state.count - 1)}">
											<td><i class="cmsms-icon-picture i-help"></i></td>
											<td class="i-input">
												<c:out value="${mainslider.descriptions.get(state.count - 1)}" />
												<span class="help-text i-help">[<c:out value="${itemTitle}" />]</span>
											</td>
											<td class="btn-td">
												<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
													onclick="editLineItem('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
											</td>
											<td class="btn-td">
												<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
													onclick="deleteLineItem('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<span class="block"><span class="error"></span></span>
							<input type="hidden" id="countItems" value="${mainslider.titles.size()}" />
							<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${mainslider.titles.size() == 9 ? 'disabled' : ''}">
								<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.image"/></span>
							</a>
							<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
							<hr class="my-4">
							<p class="font-mini">
								<spring:message code="txt.company.overview1.1"/> 
								<a href="<c:url value="https://imagecompressor.com/fr/" />" class="lien lien-hover lien-primary" target="_blank">
								<spring:message code="lien.optimizilla"/></a> <spring:message code="txt.company.overview1.2"/>
							</p>
						</div>
					</div>
					<hr class="my-4">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.slider2"/></h2>
					<spring:bind path="fadeColor">
						<div id="fadeColorForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="fadeColor">
								<spring:message code="lbl.fadecolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.6" /></span>
							</form:label>
							<div class="col-lg-8">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="fadeColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="textColor">
						<div id="textColorForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="textColor">
								<spring:message code="lbl.textcolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-lg-8">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="textColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="col-md-6">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.slider3"/></h2>
					<spring:bind path="speed">
						<div id="speedForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="speed">
								<spring:message code="lbl.speed" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.1" /></span>
							</form:label>
							<div class="col-lg-8">
								<div class="font-mini"><span class="pull-left">0.3</span><span class="pull-right">3</span></div>
								<form:input class="slider" type="text" path="speed" data-slider-min="0.3"
									data-slider-max="3" data-slider-step="0.3" data-slider-value="${mainslider.speed}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="timeout">
						<div id="timeoutForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="timeout">
								<spring:message code="lbl.timeout" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.2" /></span>
							</form:label>
							<div class="col-lg-8">
								<div class="font-mini"><span class="pull-left">2</span><span class="pull-right">12</span></div>
								<form:input class="slider" type="text" path="timeout" data-slider-min="2"
									data-slider-max="12" data-slider-step="1" data-slider-value="${mainslider.timeout}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="animateIn">
						<div id="animateInForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="animateIn">
								<spring:message code="lbl.animateIn" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.3" /></span>
							</form:label>
							<div class="col-lg-8">
								<c:set var="providers" value="bounceIn,bounceInLeft,bounceInRight,bounceInUp,bounceInDown,fadeIn,fadeInLeft,fadeInRight,fadeInUp,fadeInDown,fadeScaleIn,sliderInLeft,sliderInRight,sliderInUp,sliderInDown,zoomIn,zoomInLeft,zoomInRight,zoomInUp,zoomInDown" scope="page"></c:set>
								<form:select class="form-select2-simple" path="animateIn" >
									<option value="-" ${mainslider.animateIn == '-' ? 'selected' : ''}><spring:message code="chose.animate.default" /></option>
									<c:forEach var="provider" items="${pageScope.providers}">
										<option value="${pageScope.provider}" ${pageScope.provider == mainslider.animateIn ? 'selected' : ''}><spring:message code="chose.animate.in.${pageScope.provider}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="animateOut">
						<div id="animateOutForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="animateOut">
								<spring:message code="lbl.animateOut" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.4" /></span>
							</form:label>
							<div class="col-lg-8">
								<c:set var="providers" value="bounceOut,bounceOutLeft,bounceOutRight,bounceOutUp,bounceOutDown,fadeOut,fadeOutLeft,fadeOutRight,fadeOutUp,fadeOutDown,fadeScaleOut,sliderOutLeft,sliderOutRight,sliderOutUp,sliderOutDown,zoomOut,zoomOutLeft,zoomOutRight,zoomOutUp,zoomOutDown" scope="page"></c:set>
								<form:select class="form-select2-simple" path="animateOut" >
									<option value="-" ${mainslider.animateOut == '-' ? 'selected' : ''}><spring:message code="chose.animate.default" /></option>
									<c:forEach var="provider" items="${pageScope.providers}">
										<option value="${pageScope.provider}" ${pageScope.provider == mainslider.animateOut ? 'selected' : ''}><spring:message code="chose.animate.out.${pageScope.provider}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="animateFade">
						<div id="animateFadeForm" class="form-group row">
							<form:label class="col-form-label col-lg-4" path="animateFade">
								<spring:message code="lbl.animateFade" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.slider3.5" /></span>
							</form:label>
							<div class="col-lg-8">
								<c:set var="providers" value="flash,fadeIn,fadeInUp,fadeScaleIn,sliderInUp" scope="page"></c:set>
								<form:select class="form-select2-simple" path="animateFade" >
									<option value="-" ${mainslider.animateFade == '-' ? 'selected' : ''}><spring:message code="chose.animate.none" /></option>
									<c:forEach var="provider" items="${pageScope.providers}">
										<option value="${pageScope.provider}" ${pageScope.provider == mainslider.animateFade ? 'selected' : ''}><spring:message code="chose.animate.fade.${pageScope.provider}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<hr class="my-4">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.slider4"/></h2>
					<spring:bind path="hasAutoplay">
						<div class="form-group m-t-20">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasAutoplay" />
								<span class="input-span"></span><spring:message code="comp.slider1" />
							</label>
						</div>
					</spring:bind>
					<spring:bind path="hasHover">
						<div class="form-group">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasHover" />
								<span class="input-span"></span><spring:message code="comp.slider2" />
							</label>
						</div>
					</spring:bind>
					<spring:bind path="hasNavigation">
						<div class="form-group">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasNavigation" />
								<span class="input-span"></span><spring:message code="comp.slider3" />
							</label>
						</div>
					</spring:bind>
					<spring:bind path="hasDots">
						<div class="form-group">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasDots" />
								<span class="input-span"></span><spring:message code="comp.slider4" />
							</label>
						</div>
					</spring:bind>
				</div>
			</div>
			<hr class="my-4">
			<div class="form-group row m-b-20">
				<div class="col-md-6">
					<div class="row">
						<div class="col-lg-4 hidden-md-down"></div>
						<div class="col-lg-8">
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