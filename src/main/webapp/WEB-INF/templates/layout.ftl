<#import "spring.ftl" as spring />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />


<#macro i18nedProperty object property>

	<#if object.getI18n(lang)??>
		${object.getI18n(lang)[property]}
	<#else>
		${object[property]}
	</#if>
</#macro>

<#macro head pageTitle>
	<head>
    	<meta charset="utf-8">
        <title>${pageTitle}</title>
        
        <link href="<@spring.url "/css/bootstrap.css"/>" rel="stylesheet">
    	<style>
      		body {
        		padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      		}
    	</style>
    	
    	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    	<!--[if lt IE 9]>
      		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    	<![endif]-->

        <script src="<@spring.url "/js/jquery.js"/>" type="text/javascript"></script>
		<script src="<@spring.url "/js/bootstrap.js"/>" type="text/javascript"></script>
    </head>
</#macro>

<#macro layout pageTitle>
<!DOCTYPE html>
<html>
    <@head pageTitle/>
    <body>
    
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <#-- TODO what is this btn-navbar for? -->
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="<@spring.url "/"/>"><@spring.message "index.title" /></a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.divisions" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li><a href="<@spring.url "/divisions"/>"><@spring.message "simple.list" /></a></li>
                	<li><a href="<@spring.url "/division/clubs"/>"><@spring.message "simple.clubs" /></a></li>
              		<@security.authorize ifAnyGranted="EDITOR">
              			<li><a href="<@spring.url "/edit/division/new"/>"><@spring.message "simple.new" /></a></li>
             	 	</@security.authorize>
                </ul>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.clubs" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li><a href="<@spring.url "/clubs"/>"><@spring.message "simple.list" /></a></li>
                	<li><a href="<@spring.url "/club/players"/>"><@spring.message "club.players" /></a></li>
					<@security.authorize ifAnyGranted="EDITOR">
              			<li><a href="<@spring.url "/edit/club/new"/>"><@spring.message "simple.new" /></a></li>
              			<li><a href="<@spring.url "/edit/club/team/new"/>"><@spring.message "club.team.new" /></a></li>
              		</@security.authorize>
                </ul>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.language" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                	<li><a href="<@spring.url "/?lang=en"/>">English version</a></li>
              		<li><a href="<@spring.url "/?lang=ru"/>">Русская версия</a></li>
                </ul>
              </li>
			
			  <@security.authorize access="isAuthenticated()">
	              <li><a href="<@spring.url "/j_spring_security_logout"/>"><@spring.message "simple.logout" /></a></li>
			  </@security.authorize>
			   <@security.authorize access="isAnonymous()">
	              <li><a href="<@spring.url "/login"/>"><@spring.message "simple.login" /></a></li>
			  </@security.authorize>

            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    
    <div class="container">
   		<#nested/>
   	</div>
    </body>
</html>
</#macro>