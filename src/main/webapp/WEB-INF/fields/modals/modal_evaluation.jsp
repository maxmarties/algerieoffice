<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="evaluationModal" class="modal explorerModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-evaluation bn-segond bn-explorer-segond animated pulse" role="document">
		<form name="formEvaluation" action="/" method="POST" novalidate="novalidate">
			<div class="modal-content">
				<div class="modal-header">
					<div></div>
					<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-parag font-big text-center i-white text-white m-auto">
						<spring:message code="txt.help.explorer3.2"/> <strong><c:out value="${explorerCompany.profile.tradename}"/></strong>
						<span class="help-text i-gray text-gray m-t-10"><spring:message code="txt.help.explorer3.2.3" /></span>
					</div>
					<div class="modal-form">
						<h1 class="h-header h-header5 text-center i-white text-white sh-black"><spring:message code="txt.help.explorer3.2.1"/></h1>
						<div id="likedEvaluationForm" class="form-group m-b-10">
							<div class="first-parag m-auto">
								<c:set var="liked1" value="${!empty currentVisitor.liked && currentVisitor.liked}" scope="page"></c:set>
								<c:set var="liked2" value="${!empty currentVisitor.liked && !currentVisitor.liked}" scope="page"></c:set>
								<div class="row row-min" data-toggle="buttons">
									<div class="col-6 col-mini">
										<div class="text-center">
											<label class="btn btn-liked ${pageScope.liked1 ? 'active' : ''}">
												<i class="cmsms-icon-thumbs-up i-28"></i>
												<span><spring:message code="lbl.sub.liked1"/></span>
												<input type="radio" class="hidden" value="${true}" id="likedEvaluation" name="likedEvaluation" ${pageScope.liked1 ? 'checked' : ''}/>
											</label>
										</div>
									</div>
									<div class="col-6 col-mini">
										<div class="text-center">
											<label class="btn btn-liked ${pageScope.liked2 ? 'active' : ''}">
												<i class="cmsms-icon-thumbs-down i-28"></i>
												<span><spring:message code="lbl.sub.liked2"/></span>
												<input type="radio" class="hidden" value="${false}" id="likedEvaluation" name="likedEvaluation" ${pageScope.liked2 ? 'checked' : ''}/>
											</label>
										</div>
									</div>
								</div>
							</div>
							<span class="error"></span>
						</div>
						<h1 class="h-header h-header5 text-center i-white text-white sh-black"><spring:message code="txt.help.explorer3.2.2"/> :</h1>
						<div id="noteEvaluationForm" class="form-group m-b-30">
							<ul class="navbar-nav nav-flex-icons nav-note">
								<c:forEach var="i" begin="1" end="10" step="1">
									<li class="i-white text-white">
										<span class="font-small text-center block m-b-5"><c:out value="${i}"/></span>
										<label class="ui-radio ui-radio-primary font-small">
											<input type="radio" value="${i}" id="noteEvaluation" name="noteEvaluation" ${currentVisitor.note == i ? 'checked' : ''}/>
											<span class="input-span"></span>
										</label>	
									</li>
								</c:forEach>
							</ul>
							<span class="error"></span>
						</div>
						<hr class="my-4 i-light">
						<div class="form-group text-center m-t-20 m-b-20">
            				<div id="submitEvaluationForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.send"/></span></button>
            				</div>
            			</div>
					</div>
				</div>
			</div>
		</form>
		<div class="modal-more bn-primary bn-explorer-primary">
			<p class="text-center font-mini i-gray text-gray">
				<span style="opacity:.75;"><spring:message code="txt.help.explorer3.2.4" /> </span><a href="<c:url value="/infos/cgu" />" 
					class="lien lien-hover lien-gray" target="_blank"><spring:message code="lien.cgu"/></a>.</p>
		</div>
	</div>
</div>