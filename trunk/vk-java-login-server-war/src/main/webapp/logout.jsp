<%System.out.println(">>> alexey: logout.jsp = "); %>
<%request.getSession().invalidate();%>
<%response.sendRedirect(request.getContextPath() + "/vk");%>
