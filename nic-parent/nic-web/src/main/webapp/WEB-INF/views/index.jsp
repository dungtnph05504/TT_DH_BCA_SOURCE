<script type="text/javascript" language="JavaScript"><!--
<%
  String loginPage=request.getContextPath()+"/welcome";
%>
        newWin=window.open("<%=loginPage%>",'mainframewin','toolbar=no,menubar=no,scrollbars=yes,location=no,directories=no,status=yes');
        newWin.moveTo(0,0);
        newWin.resizeTo(screen.availWidth,screen.availHeight);
        window.opener='a';window.close();
</script>