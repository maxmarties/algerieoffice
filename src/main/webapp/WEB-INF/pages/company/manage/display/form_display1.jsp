<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal"><spring:message code="wizard.manage.display1"/></h2>
<p class="font-small m-t-5"><spring:message code="txt.company.manage1.1"/></p>
<hr class="my-4">
<spring:bind path="widget[5]">
	<div class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="lbl.sub.display1.1" />
			<span class="help-text"><spring:message code="txt.help.display1.1" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<ul class="nav">
				<li class="m-r-10"><spring:message code="lbl.sub.display1.1.1" /></li>
				<li><label class="ui-switch ui-switch-action">
					<form:checkbox id="widget5" path="widget[5]" /><span class="input-span"></span><span class="layer-span"></span>
				</label></li>
				<li class="m-l-10"><spring:message code="lbl.sub.display1.1.2" /></li>
			</ul>
		</div>
	</div>
</spring:bind>
<div class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.sub.display1.2" />
		<span class="help-text"><spring:message code="txt.help.display1.2" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<label class="ui-checkbox ui-checkbox-segond font-small m-r-20 disabled">
			<input type="checkbox" checked />
			<span class="input-span"></span><spring:message code="explorer.mainmenu1" />
		</label>
		<c:forEach var="i" begin="1" end="4" step="1">
			<spring:bind path="mainmenu[${i - 1}]">
				<label class="ui-checkbox ui-checkbox-segond font-small m-r-20">
					<form:checkbox id="mainmenu${i - 1}" path="mainmenu[${i - 1}]" />
					<span class="input-span"></span><spring:message code="explorer.mainmenu${i + 1}" />
				</label>
			</spring:bind>
		</c:forEach>
	</div>
</div>
<hr class="my-2">
<div class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.sub.display1.3" />
		<span class="help-text"><spring:message code="txt.help.display1.3" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<c:forEach var="i" begin="1" end="6" step="1">
			<spring:bind path="society[${i - 1}]">
				<label class="ui-checkbox ui-checkbox-segond font-small ui-checkbox-society m-r-20 ${!maindisplay.mainmenu[0] ? 'disabled' : ''}">
					<form:checkbox id="society${i - 1}" path="society[${i - 1}]" />
					<span class="input-span"></span><spring:message code="explorer.mainmenu2.${i}" />
				</label>
			</spring:bind>
		</c:forEach>
	</div>
</div>
<div class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.sub.display1.4" />
		<span class="help-text"><spring:message code="txt.help.display1.4" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<c:forEach var="i" begin="1" end="6" step="1">
			<spring:bind path="mainsidbar[${i - 1}]">
				<label class="ui-checkbox ui-checkbox-segond font-small m-r-20">
					<form:checkbox id="mainsidbar${i - 1}" path="mainsidbar[${i - 1}]" />
					<span class="input-span"></span><spring:message code="explorer.mainmenu2.${i}" />
				</label>
			</spring:bind>
		</c:forEach>
	</div>
</div>
<hr class="my-2">
<spring:bind path="lateral">
	<div class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="lbl.sub.display1.6" />
			<span class="help-text"><spring:message code="txt.help.display1.6" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<ul class="nav">
				<li class="m-r-10"><spring:message code="lbl.sub.display1.1.3" /></li>
				<li><label class="ui-switch ui-switch-action">
					<form:checkbox id="lateral" path="lateral" /><span class="input-span"></span><span class="layer-span"></span>
				</label></li>
				<li class="m-l-10"><spring:message code="lbl.sub.display1.1.4" /></li>
			</ul>
		</div>
	</div>
</spring:bind>
<div class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.sub.display1.5" />
		<span class="help-text"><spring:message code="txt.help.display1.5" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<c:forEach var="i" begin="1" end="7" step="1">
			<spring:bind path="mainfooter[${i - 1}]">
				<label class="ui-checkbox ui-checkbox-segond font-small m-r-20">
					<form:checkbox id="mainfooter${i - 1}" path="mainfooter[${i - 1}]" />
					<span class="input-span"></span><spring:message code="explorer.mainmenu6.${i}" />
				</label>
			</spring:bind>
		</c:forEach>
	</div>
</div>
<div class="form-group row">
	<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
	<div class="col-md-8 col-lg-9">
		<hr class="my-2">
		<a class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-menus">
			<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.default"/></span>
		</a>
	</div>
</div>