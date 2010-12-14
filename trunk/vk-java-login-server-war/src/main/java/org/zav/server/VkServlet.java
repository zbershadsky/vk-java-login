package org.zav.server;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class VkServlet extends GenericServlet
{

   @Override
   public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
   {
      System.out.println("\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      System.out.println(">>> alexey: VkServlet.service 1 = " + 1);

      HttpServletRequest httpRequest = (HttpServletRequest)req;
      String servletPath = httpRequest.getServletPath();
      
      Principal principal = httpRequest.getUserPrincipal();
      System.out.println(">>> alexey: VkServlet.service principal = " + principal);
      if (principal != null)
         System.out.println(">>> alexey: VkServlet.service principal.getName() = " + principal.getName());

      System.out.println(">>> alexey: VkServlet.service hReq.getRemoteUser() = " + httpRequest.getRemoteUser());
      System.out.println(">>> alexey: VkServlet.service hReq.getUserPrincipal() = " + httpRequest.getUserPrincipal());
      if (httpRequest.getUserPrincipal() != null)
         System.out.println(">>> alexey: VkServlet.service hReq.getUserPrincipal().getName() = "
            + httpRequest.getUserPrincipal().getName());
      System.out.println(">>> alexey: VkServlet.service hReq.getSession(false) = " + httpRequest.getSession(false));

      printParsAndAttrs(httpRequest);
      printHeaders(httpRequest);
      printCookies(httpRequest);

      if (servletPath.startsWith("/vk/private"))
      {
         System.out.println(">>> alexey: VkServlet.service 2 = MAIN");
         httpRequest.getRequestDispatcher("/main.jsp").include(req, res);
      }
      else
      {
         System.out.println(">>> alexey: VkServlet.service 2 = INDEX");
         httpRequest.getRequestDispatcher("/index.jsp").include(req, res);
      }
   }

   private void printParsAndAttrs(ServletRequest req)
   {
      Enumeration<String> parameterNames = req.getParameterNames();
      while (parameterNames.hasMoreElements())
      {
         String name = (String)parameterNames.nextElement();
         System.out.println(">>> alexey: VkServlet.logParsAndAttrs P name = " + name);
         System.out.println(">>> alexey: VkServlet.logParsAndAttrs        = " + req.getParameter(name));
      }
      Enumeration<String> attributeNames = req.getAttributeNames();
      while (attributeNames.hasMoreElements())
      {
         String name = (String)attributeNames.nextElement();
         System.out.println(">>> alexey: VkServlet.logParsAndAttrs A name = " + name);
         System.out.println(">>> alexey: VkServlet.logParsAndAttrs        = " + req.getAttribute(name));
      }
   }

   private void printHeaders(HttpServletRequest hReq)
   {
      Enumeration<String> headerNames = hReq.getHeaderNames();
      if (headerNames != null)
      {
         while (headerNames.hasMoreElements())
         {
            String name = (String)headerNames.nextElement();
            System.out.println(">>> alexey: VkServlet.printHeaders H name = " + name);
            System.out.println(">>> alexey: VkServlet.printHeaders        = " + hReq.getHeader(name));
         }
      }
   }

   private void printCookies(HttpServletRequest hReq)
   {
      Cookie[] cookies = hReq.getCookies();
      if (cookies != null)
      {
         for (Cookie cookie : cookies)
         {
            System.out.println(">>> alexey: VkServlet.printCookies C name = " + cookie.getName());
            System.out.println(">>> alexey: VkServlet.printCookies        = " + cookie.getValue());
         }
      }
   }

}
