<#import "../layout.ftl" as l />
<#import "../spring.ftl" as s />
<#import "../common.ftl" as c />
<#import "gameCommon.ftl" as g />

<#assign pageTitle>${game.host.teamInSeason.team.getTitle(lang)} - ${game.guest.teamInSeason.team.getTitle(lang)}</#assign>

<@l.layout pageTitle=pageTitle>
    <@g.gameView game />
</@l.layout>

