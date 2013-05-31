<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@s.message "players.list" /></#assign>

<@l.layout pageTitle="${pageTitle}">
<ul>
    <#list players as player>
        <a href="<@s.url "/players/view/${player.id}" />">
            <li><@c.i18nedOrNull player "firstName"/> <@c.i18nedOrNull player "middleName"/> <@c.i18nedOrNull player "lastName"/></li>
        </a>
    </#list>
</ul>
</@l.layout>



