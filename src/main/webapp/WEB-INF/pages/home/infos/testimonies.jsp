<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="sidebar.home.mainfooter1.10"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="txt.infos.tsetimonies1"/></h1></div>
		<c:choose>
			<c:when test="${testimonies.isEmpty()}"><p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.infos.tsetimonies1.1"/></p></c:when>
			<c:otherwise>
				<div class="row m-t-40 m-b-20">
					<c:forEach var="testimonial" items="${testimonies}" varStatus="state">
						<div class="col-md-6 m-t-20">
							<div class="card-testimonial flexed flex-colone flex-jusitify h-100">
								<div>
									<i class="cmsms-icon-quote breadview-trigger i-segond1"></i>
									<p class="font-big m-t-20 m-b-20"><c:out value="${testimonial.message}" /></p>
								</div>
								<div class="widget-more">
									<ul class="navbar-nav nav-flex-icons">
										<li>
											<span class="font-bold i-primary"><c:out value="${testimonial.username}" /></span>
											<span class="block font-mini i-help"><c:out value="${testimonial.function}" /></span>
										</li>
										<li class="ml-auto">
											<c:set var="countStar" value="${testimonial.note / 2}" scope="page"></c:set>
											<ul class="list-none list-evaluation">
												<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
												<c:if test="${testimonial.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
											</ul>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="screen-container screen-skills screen-white">
	<div class="container">
		<h2 class="h-header h-header2 h-skills i-primary m-auto"><spring:message code="header.repport.testimonial"/></h2>
		<p class="parag-blog header-detect font-big text-center i-help m-auto"><spring:message code="txt.user.repport1.1"/></p>
		<div class="text-center m-t-20">
			<a href="<c:url value="/user/repports/testimonial" />" class="btn btn-segond btn-big" 
				style="min-width:230px;"><span><spring:message code="dashboard.app.popup6"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span></a>
		</div>
	</div>
</div>