package org.zav.server;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zav.auth.VkAuthentificator;
import org.zav.auth.VkAuthentificatorValve;

public class VkFilter implements Filter
{
   
   private static final Logger LOG = LoggerFactory.getLogger(VkAuthentificatorValve.class);

   @Override
   public void destroy()
   {
      // nothing to do.
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException
   {
      LOG.debug("Entering the method");
      
      HttpServletResponse httpResponse = (HttpServletResponse)response;
      HttpServletRequest httpRequest = (HttpServletRequest)request;

      {
         Principal principal = httpRequest.getUserPrincipal();
         System.out.println(">>> alexey: VkServlet.service principal = " + principal);
         if (principal != null)
            System.out.println(">>> alexey: VkServlet.service principal.getName() = " + principal.getName());
      }

      Principal principal = authenticate(httpRequest, httpResponse);
      if (principal != null)
      {
         chain.doFilter(new VkHttpServletRequestWrapper(httpRequest, principal), httpResponse);
      }
      else
      {
         chain.doFilter(httpRequest, httpResponse);
      }

   }

   private Principal authenticate(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
   {
      System.out.println(">>> alexey: VkFilter.authenticate 1 = " + 1);
      return VkAuthentificator.authenticate(httpRequest);
   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException
   {
      // nothing to do.
   }

   /**
    * @see javax.servlet.http.HttpServletResponseWrapper .
    */
   final class VkHttpServletRequestWrapper extends HttpServletRequestWrapper
   {
      /**
       * User principal.
       */
      private Principal principal;

      /**
       * @param request the original request.
       * @param principal the user principal.
       */
      public VkHttpServletRequestWrapper(final HttpServletRequest request, final Principal principal)
      {
         super(request);
         this.principal = principal;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public String getRemoteUser()
      {
         return getUserPrincipal().getName();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public Principal getUserPrincipal()
      {
         return this.principal;
      }
   }

}
