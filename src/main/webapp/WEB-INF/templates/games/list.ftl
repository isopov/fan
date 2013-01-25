<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#import "../common.ftl" as common />

<#assign pageTitle><@spring.message "games.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
    <@common.gamesTable games />
</@layout.layout>
