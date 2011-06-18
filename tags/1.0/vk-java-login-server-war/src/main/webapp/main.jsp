<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<% final Logger LOG = LoggerFactory.getLogger("main.jsp");%>
<%LOG.debug("Entering...");%>

<html xmlns="http://www.w3.org/1999/xhtml"> 
  <head> 
    <meta http-equiv="content-type" content="text/html; charset=windows-1251" /> 
    <title>Open API sample page</title> 
    <base href="http://vkontakte.ru/" target="_blank" /> 
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
        VK.Observer.subscribe('auth.login', function(response) {
          loginOpenAPI();
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
      <div id="openapi_block"> 
        <div id="openapi_userpic"> 
          <a href="#" id="openapi_userlink"><img src="http://vkontakte.ru/images/question_c.gif" id="openapi_userphoto" /></a> 
        </div> 
        <div id="openapi_profile"> 
          <div id="openapi_greeting">Hello,<span id="openapi_user"></span></div> 
          <div id="openapi_actions"><span onclick="doLogout();" onmouseover="addClass(this, 'openapi_actions_hover');" onmouseout="removeClass(this, 'openapi_actions_hover');">Exit</span></div> 
        </div> 
      </div> 
      <div style="clear:both;"></div> 
    </div> 
    <div id="openapi_wrap"> 
      <div id="info"> 
        <div class="bOpen"> 
          <div class="flexHeader clearFix"> 
            <div><h2 id="news_title"></h2></div> 
          </div> 
        </div> 
        <div> 
        <div id="logo"><a id="group_link" href="#"><img id="logo_img" src="http://vkontakte.ru/images/question_100.gif" alt="VK team" /></a></div> 
        <div id="news"> 
          <div class="dataWrap"><div class='wikiText'> 
          </div></div> 
        </div> 
        </div> 
      </div> 
      <div id="friends"> 
        <div class="bOpen"> 
          <div class="flexHeader clearFix"> 
            <div><h2>Friends</h2></div> 
          </div> 
        </div> 
        <div id="friends_list"></div> 
        <div style="clear: left; padding-top: 10px;"></div> 
      </div> 
      <div id="comments"> 
        <div class="bOpen"> 
          <div class="flexHeader clearFix"> 
            <div><h2>Comments</h2></div> 
          </div> 
        </div> 
        <div id="comments_list"></div> 
        <div style="clear: left;"></div> 
        <div style="clear: both;"></div> 
      </div> 
    </div>
  </body> 
</html>