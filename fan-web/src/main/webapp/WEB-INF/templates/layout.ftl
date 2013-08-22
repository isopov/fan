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

    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
    <link href="//cdnjs.cloudflare.com/ajax/libs/chosen/1.0/chosen.min.css" rel="stylesheet">

	<!-- Don't know if it will work with Jquery 2 - in either case I don't test on IE ever -->
    <!--[if lt IE 9]>
    	<script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.6.2/html5shiv.min.js"></script>
   		<script src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.2.0/respond.min.js"></script>
    <![endif]-->

    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-38185772-1']);
        _gaq.push(['_setDomainName', 'football-analytics.com']);
        _gaq.push(['_trackPageview']);

        (function () {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();

    </script>
</head>
</#macro>

<#macro layout pageTitle>
<!DOCTYPE html>
<html>
    <@head pageTitle/>
<body>
<div class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<@spring.url "/"/>"><@spring.message "index.title" /></a>
        </div>

        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown"><@spring.message "simple.articles" /> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <#list categories as category>
                            <li class="nav-header">${category.name}</li>
                            <list category.articles as article>
                                <li><a href="<@spring.url "/articles/${article.shortTitle}"/>">${article.title}</a></li>
                            </list>
                        </#list>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown"><@spring.message "simple.divisions" /> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<@spring.url "/contest/list"/>"><@spring.message "simple.list" /></a></li>
                        <li class="divider"></li>
                        <li class="dropdown-header">Quick view</li>
                        <li><a href="<@spring.url "/contest/games"/>"><@spring.message "simple.games" /></a></li>
                        <li><a href="<@spring.url "/contest/teams"/>"><@spring.message "simple.teamsInGame" /></a></li>
                        <@security.authorize ifAnyGranted="EDITOR">
                            <li><a href="<@spring.url "/edit/division/new"/>"><@spring.message "simple.new" /></a>
                            </li>
                        </@security.authorize>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.clubs" /> <b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<@spring.url "/club/list"/>"><@spring.message "simple.list" /></a></li>
                        <@security.authorize ifAnyGranted="EDITOR">
                            <li><a href="<@spring.url "/edit/club/new"/>"><@spring.message "simple.new" /></a></li>
                        </@security.authorize>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.games" /> <b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<@spring.url "/games/list"/>"><@spring.message "simple.list" /></a></li>
                        <@security.authorize ifAnyGranted="EDITOR">
                            <li><a href="<@spring.url "/edit/game/new"/>"><@spring.message "simple.new" /></a></li>
                        </@security.authorize>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.players" />
                        <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<@spring.url "/players/list"/>"><@spring.message "simple.list" /></a></li>
                        <@security.authorize ifAnyGranted="EDITOR">
                            <li><a href="<@spring.url "/edit/player/new"/>"><@spring.message "simple.new" /></a>
                            </li>
                        </@security.authorize>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@spring.message "simple.language" />
                        <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="${languageChangeUrl}en">English version</a></li>
                        <li><a href="${languageChangeUrl}ru">Русская версия</a></li>
                    </ul>
                </li>

                <@security.authorize access="isAuthenticated()">
                    <li><a href="<@spring.url "/j_spring_security_logout"/>"><@spring.message "simple.logout" /></a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAnonymous()">
                    <li><a href="<@spring.url "/login"/>"><@spring.message "simple.login" /></a></li>
                </@security.authorize>

            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>

<div class="container">
    <#nested/>
</div>
</body>
</html>
</#macro>