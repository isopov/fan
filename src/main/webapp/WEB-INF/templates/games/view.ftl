<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle>${game.host.team.getTitle(lang)} - ${game.guest.team.getTitle(lang)}</#assign>

<@l.layout pageTitle=pageTitle>
    <@c.gameView game />
</@l.layout>
