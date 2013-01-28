<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@s.message "games.list" /></#assign>

<@l.layout pageTitle="${pageTitle}">
    <@c.gamesTable games />
</@l.layout>



