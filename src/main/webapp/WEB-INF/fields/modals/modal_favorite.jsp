<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div id="favoriteModal" class="modal explorerModal" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog modal-favorite bn-segond bn-explorer-segond animated pulse" role="document">
		<form name="formFovorite" action="/" method="POST" novalidate="novalidate">
			<div class="modal-content">
				<div class="modal-header">
					<div></div>
					<button type="button" class="btn btn-simple btn-modal btn-transparent" data-dismiss="modal" title='<spring:message code="btn.close"/>'>
		    			<i class="cmsms-icon-cancel-2"></i>
		    		</button>
				</div>
				<div class="modal-body">
					<div class="modal-parag font-big text-center i-white text-white m-auto"><spring:message code="txt.help.explorer3.1"/> :</div>
					<div class="modal-form">
						<div id="typeFavoriteForm" class="form-group m-b-0">
							<div class="row">
								<c:forEach var="i" begin="1" end="4" step="1">
									<div class="col-6 m-b-10">
										<label class="ui-radio ui-radio-primary font-small m-l-20">
											<input type="radio" value="${i}" id="typeFavorite${i}" name="typeFavorite" ${currentVisitor.favorite == i ? 'checked' : ''}/>
											<span class="input-span"></span><spring:message code="chose.favorite${i}" />
										</label>
									</div>
								</c:forEach>
							</div>
							<span class="error"></span>
						</div>
						<hr class="my-4 i-light">
						<div class="form-group text-center m-t-20 m-b-0">
            				<div id="submitFavoriteForm" class="form-submit">
            					<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-fixed"><span><spring:message code="btn.validate"/></span></button>
            				</div>
            			</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>