<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@c.i18ned firstTeam "name" /> <@s.message "simple.vs"/> <@c.i18ned  secondTeam "name"/></#assign>

<@l.layout pageTitle>
<#if (games?has_content) >
    <@c.gamesTable games />
</#if>

</@l.layout>
