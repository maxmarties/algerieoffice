<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="loginModal" class="modal loginModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-login bn-segond bn-explorer-segond animated pulse" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<div></div>
				<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
	    			<i class="cmsms-icon-cancel-2"></i>
	    		</button>
			</div>
			<div class="modal-body">
				<div class="modal-parag font-big text-center i-white text-white m-auto"><spring:message code="explorer.subheader.browser2.1"/></div>
				<div class="modal-form">
					<h1 class="h-header h-header2 text-center i-white text-white sh-black"><spring:message code="btn.partner2"/></h1>
					<form name="formLogin" action="/" method="POST" novalidate="novalidate">
						<div id="usernameForm" class="form-group m-t-20 m-b-10">
							<c:set var="faholder" scope="page"><spring:message code="lbl.login.email" /></c:set>
							<input class="form-control form-simple" type="email" id="username" name="username" placeholder="${pageScope.faholder}" />
							<span class="error"></span>
						</div>
						<div id="passwordForm" class="form-group m-b-20">
							<c:set var="faholder" scope="page"><spring:message code="lbl.login.password" /></c:set>
							<input class="form-control form-simple" type="password" id="password" name="password" placeholder="${pageScope.faholder}" />
							<span class="error"></span>
						</div>
						<div class="form-group m-b-20">
							<label class="ui-checkbox ui-checkbox-primary font-small">
							    <input type="checkbox" id="remember-me" name="remember-me">
								<span class="input-span"></span><spring:message code="comp.rememberme" />
							</label>
            			</div>
            			<div class="form-group text-center m-b-30">
            				<div id="submitLoginForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.login"/></span></button>
            				</div>
            			</div>
					</form>
					<hr class="my-4 i-light">
					<p class="m-b-20"><a href="<c:url value="/users/reset-password"/>" class="lien lien-hover lien-gray lien-small">
						<spring:message code="lien.password"/></a></p>
				</div>
			</div>
		</div>
		<div class="modal-more bn-primary bn-explorer-primary">
			<p class="text-center font-small i-gray text-gray">
				<span style="opacity:.75;"><spring:message code="txt.help.explorer2.4" /> </span><a href="<c:url value="/register/user"/>" 
					class="lien lien-hover lien-gray"><spring:message code="btn.signin"/></a></p>
		</div>
	</div>
</div>