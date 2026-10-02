<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.marketplace.annonce1"/></h2>
<div class="row m-t-20">
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace1"/></h3>
		<p class="font-small m-t-10"><spring:message code="txt.company.marketplace2.1.1"/></p>
		<hr class="my-4">
		<spring:bind path="sectors"><form:input type="hidden" path="sectors" /></spring:bind>
		<spring:bind path="updateSectors"><form:input type="hidden" path="updateSectors" /></spring:bind>
		<div id="formTargetForm" class="form-group">
			<div class="input-group-icon">
				<input class="form-control form-error" type="search" id="findtarget" name="findtarget" placeholder="<spring:message code="tool.find.sector"/>" />
				<span class="input-group-addon input-group-simple"><i class="cmsms-icon-search-1"></i></span>
			</div>
			<div class="table-scroll m-t-5">
				<table class="table table-target">
					<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
					<tbody class="font-small">
						<tr class="header">
							<td class="td-check">
								<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
									<input type="checkbox" name="checkAllRowTarget" ${annonce.sectors.size() == 31 ? 'checked' : ''}/>
									<span class="input-span"></span>
								</label>
							</td>
							<td><spring:message code="chose.sector.all"/></td>
						</tr>
						<c:forEach var="i" begin="1" end="31" step="1">
							<tr>
								<td class="td-check">
									<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
										<input type="checkbox" name="checkRowTarget" data-row="${i}" ${annonce.inSectors(i) ? 'checked' : ''}/>
										<span class="input-span"></span>
									</label>
								</td>
								<td class="td-result"><spring:message code="chose.sector${i}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<input type="hidden" id="countSectors" value="${annonce.sectors.size()}" />
			</div>
			<span class="error"></span>
		</div>
	</div>
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace2"/></h3>
		<p class="font-small m-t-10"><spring:message code="txt.company.marketplace2.1.2"/></p>
		<hr class="my-4">
		<spring:bind path="wilayas"><form:input type="hidden" path="wilayas" /></spring:bind>
		<spring:bind path="updateWilayas"><form:input type="hidden" path="updateWilayas" /></spring:bind>
		<div id="formZoneForm" class="form-group">
			<div class="input-button-target" data-toggle="buttons">
				<label class="btn btn-checkbox btn-simple btn-add btn-left ${!empty annonce.id && annonce.wilayas.isEmpty() ? 'active' : ''}">
					<span><i class="cmsms-icon-ok-5"></i><spring:message code="comp.target"/></span>
					<input type="checkbox" name="checkDefaultZone" class="hidden" ${!empty annonce.id && annonce.wilayas.isEmpty() ? 'checked' : ''} />
				</label>
			</div>
			<div class="table-scroll m-t-5">
				<table class="table table-target">
					<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
					<tbody class="font-small">
						<tr class="header">
							<td class="td-check">
								<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
									<input type="checkbox" name="checkAllRowZone" ${annonce.wilayas.size() == 48 ? 'checked' : ''} />
									<span class="input-span"></span>
								</label>
							</td>
							<td><spring:message code="chose.wilaya.all"/></td>
						</tr>
						<c:forEach var="i" begin="1" end="48" step="1">
							<tr>
								<td class="td-check">
									<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
										<input type="checkbox" name="checkRowZone" data-row="${i}" ${annonce.inWilayas(i) ? 'checked' : ''} />
										<span class="input-span"></span>
									</label>
								</td>
								<td><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<input type="hidden" id="countWilayas" value="${annonce.wilayas.size()}" />
			</div>
		</div>
	</div>
</div>