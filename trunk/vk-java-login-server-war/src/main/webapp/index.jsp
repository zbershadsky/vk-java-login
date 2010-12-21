<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<% final Logger LOG = LoggerFactory.getLogger("index.jsp");%>
<%LOG.debug("Entering...");%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <meta http-equiv="content-type" content="text/html; charset=windows-1251" /> 
    <title>Open API sample page</title> 
    <link rel="stylesheet" href="http://vkontakte.ru/css/rustyle.css" type="text/css" /> 
    <link rel="stylesheet" href="http://vkontakte.ru/css/wiki.css" type="text/css" /> 
    <script src="http://vkontakte.ru/js/common.js"></script>  
    <link rel="stylesheet" href="http://10100.dyndns.org/vk/openapi_sample.css" type="text/css" /> 
    <script src="http://10100.dyndns.org/vk/openapi_sample.js"></script> 
  </head> 
  <body> 
    <div id="vk_api_transport"></div> 
    <script type="text/javascript"> 
      window.vkAsyncInit = function() {
        VK.Auth.getLoginStatus(function(response) {
          if (response.session) {
            window.location = baseURL + 'vk/vk/private/';
          } else {
            VK.UI.button('vk_login');
          }
        });
        VK.Observer.subscribe('auth.login', function(response) {
          window.location = baseURL + 'vk/vk/private/';
        });
        VK.Observer.subscribe('auth.logout', function() {
          //console.log('logout');
        });
        VK.Observer.subscribe('auth.statusChange', function(response) {
          //console.log('statusChange');
        });
        VK.Observer.subscribe('auth.sessionChange', function(r) {
          //console.log('sessionChange');
        });
        
        VK.init({
          apiId: 2045117,
          nameTransportPath: 'vk/xd_receiver.html'
        });
        VK.UI.button('vk_login');
      };
      setTimeout(function() {
        var el = document.createElement('script');
        el.type = 'text/javascript';
        el.src = 'http://vkontakte.ru/js/api/openapi.js';
        el.async = true;
        document.getElementById('vk_api_transport').appendChild(el);
      }, 0);
    </script> 
    <div id="openapi_header"> 
      <div id="openapi_title">10100<p style="margin:3px 0px 0px; font-size: 11px; color:#CDD9E4">Hello</div> 
      <div style="clear:both;"></div> 
    </div> 
    <div id="openapi_login_wrap"> 
      <div style="margin: 5px 0pt 20px; font-weight: normal; text-align: justify;" class="dld">Login</div> 
      <div id="vk_login" style="margin: 0 auto 20px auto;" onclick="doLogin();"></div> 
    </div> 
  </body> 
</html>