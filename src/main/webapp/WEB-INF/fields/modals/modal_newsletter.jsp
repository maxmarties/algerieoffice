<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="newsletterModal" class="modal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-newsletter background-container animated speed pulse" role="document"
		style="background-image: linear-gradient(to ${language.lang == 'ar' ? 'right' : 'left'}, rgba(24,31,42,.30) 30%, #181F2A 100%), url('<c:url value="/static/picts/images/blog-min.jpg" />');">
		<div class="modal-content">
			<div class="modal-header">
				<div></div>
				<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    		<i class="cmsms-icon-cancel-2"></i></button>
			</div>
			<div class="modal-body">
				<h1 class="h-header h-header1 sh-black"><spring:message code="txt.blog.newsletter1"/></h1>
				<form name="formNewsblog" action="/" method="POST" novalidate="novalidate">
					<div class="widget-flexed flexed flex-colone flex-jusitify">
						<div class="modal-form">
							<h2 class="h-header h-header2 m-t-20"><spring:message code="txt.blog.newsletter1.1"/></h2>
							<div class="form-content">
								<div id="newsblogForm" class="form-group m-t-30 m-b-10">
									<c:set var="faholder" scope="page"><spring:message code="lbl.login.email" /></c:set>
									<div class="input-group-contact">
										<i class="cmsms-icon-mail-6 icon-contact"></i>
										<input class="form-control form-simple" type="email" id="newsblog" name="newsblog" placeholder="${pageScope.faholder}" />
									</div>
									<span class="error"></span>
								</div>
								<div class="form-group">
			            			<div id="submitNewsblogForm" class="form-submit form-block">
			            				<button type="submit" class="btn btn-segond btn-submit btn-block"><span><spring:message code="btn.newsletter"/></span></button>
			            			</div>
			            		</div>
		            		</div>
						</div>
						<p class="font-small i-gray"><spring:message code="txt.blog.newsletter1.2"/></p>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>