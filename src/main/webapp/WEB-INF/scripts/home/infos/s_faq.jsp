<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<script src="<c:url value="/static/webjars/js/window/wizard.min.js" />"></script>
<script type="text/javascript">
function isValidForm(){return!0}function isValidTabClicked(r){return!0}$(function(){var r,i='<c:out value="${sect}"/>';""!==i.trim()&&(0<=(r=Number(i))&&r<=4&&$(".wizard-card").bootstrapWizard("show",i),Algerieoffice.cleanParamUrl())});
</script>