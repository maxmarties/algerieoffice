<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.manage.display2"/></h2>
<p class="font-small m-t-5"><spring:message code="txt.company.manage1.2"/></p>
<hr class="my-4">
<div class="row">
	<div class="col-lg-6">
		<c:forEach var="i" begin="1" end="4" step="1">
			<spring:bind path="widget[${i - 1}]">
				<div class="form-group row">
					<label class="col-form-label col-md-4">
						<spring:message code="lbl.sub.display2.${i}" />
						<span class="help-text"><spring:message code="txt.help.display2.${i}" /></span>
					</label>
					<div class="col-md-8">
						<ul class="nav">
							<li class="m-r-10"><spring:message code="lbl.sub.display1.1.3" /></li>
							<li><label class="ui-switch ui-switch-action">
								<form:checkbox id="widget${i - 1}" path="widget[${i - 1}]" /><span class="input-span"></span><span class="layer-span"></span>
							</label></li>
							<li class="m-l-10"><spring:message code="lbl.sub.display1.1.4" /></li>
						</ul>
					</div>
				</div>
			</spring:bind>
		</c:forEach>		
	</div>
	<div class="col-lg-6">
		<hr class="my-2 hidden-md-up">
		<c:forEach var="i" begin="5" end="8" step="1">
			<spring:bind path="widget[${i + 1}]">
				<div class="form-group row">
					<label class="col-form-label col-md-4">
						<spring:message code="lbl.sub.display2.${i}" />
						<span class="help-text"><spring:message code="txt.help.display2.${i}" /></span>
					</label>
					<div class="col-md-8">
						<ul class="nav">
							<li class="m-r-10"><spring:message code="lbl.sub.display1.1.3" /></li>
							<li><label class="ui-switch ui-switch-action">
								<form:checkbox id="widget${i + 1}" path="widget[${i + 1}]" /><span class="input-span"></span><span class="layer-span"></span>
							</label></li>
							<li class="m-l-10"><spring:message code="lbl.sub.display1.1.4" /></li>
						</ul>
					</div>
				</div>
			</spring:bind>
		</c:forEach>
	</div>
</div>
<hr class="my-2">
<spring:bind path="widget[4]">
	<div class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="lbl.sub.display2.9" />
			<span class="help-text"><spring:message code="txt.help.display2.9" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<c:forEach var="i" begin="1" end="2" step="1">
				<div>
					<label class="ui-radio ui-radio-segond font-small">
						<form:radiobutton value="${i == 1}" id="widget4_${i}" path="widget[4]" />
						<span class="input-span"></span><spring:message code="lbl.sub.display2.9.${i}" />
					</label>
				</div>	
			</c:forEach>
		</div>
	</div>
</spring:bind>
<div class="form-group row">
	<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
	<div class="col-md-8 col-lg-9">
		<hr class="my-2">
		<a class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-widgets">
			<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.default"/></span>
		</a>
	</div>
</div>