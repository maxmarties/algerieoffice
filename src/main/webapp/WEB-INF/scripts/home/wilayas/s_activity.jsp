<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/owlcarousel/owlcarousel.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/lib/chartjs/chart.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/plugin/scripanalytic.min.js" />"></script>
<c:import url="/WEB-INF/tags/plugins/script_widget.jsp"/>
<script type="text/javascript">
$(function(){$("#screenElements").scripwidget({url:'<c:url value="/feedback/screen/companies-sector"/>',activity:'<c:out value="${activity.code}"/>',wilaya:'<c:out value="${wilaya}"/>',desc:!0}),$("#screenAnalytic").scripanalytic({url:'<c:url value="/feedback/analyse/"/>',activity:'<c:out value="${activity.code}"/>',wilaya:'<c:out value="${wilaya}"/>',primaryColor:'<c:out value="${currentConfig.aocolor(3)}"/>',greenColor:'<c:out value="${currentConfig.aocolor(36)}"/>',yellowColor:'<c:out value="${currentConfig.aocolor(37)}"/>',blueColor:'<c:out value="${currentConfig.aocolor(38)}"/>',redColor:'<c:out value="${currentConfig.aocolor(35)}"/>',whiteColor:'<c:out value="${currentConfig.aocolor(2)}"/>',lineColor:'<c:out value="${currentConfig.aocolor(59)}"/>'}),$("#cardScreenBlog").find(".owl-carousel").owlCarousel({autoplay:!1,dots:!0,smartSpeed:300,animateIn:"fadeFromLeft",animateOut:"fadeOutFromLeft",rtl:Algerieoffice.rtl()})});
</script>