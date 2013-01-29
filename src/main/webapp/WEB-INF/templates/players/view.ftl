<#import "../layout.ftl" as l />
<#import "../spring.ftl" as s />
<#import "../common.ftl" as c />
<#import "../games/gameCommon.ftl" as g />

<#assign pageTitle><@c.i18ned player "firstName" /> <@c.i18nedOrNull player "middleName" /> <@c.i18nedOrNull player "lastName" /></#assign>

<@l.layout pageTitle=pageTitle>
<h1>${pageTitle}</h1>
<table class="table table-striped table-bordered table-condensed">
    <caption>Last games</caption>
    <thead>
    <tr>
        <th>Game</th>
        <@g.playersInGameDetailsHeaderContent />
    </tr>
    </thead>
    <tbody>
        <#list lastGames as playerInGame >
        <tr>
            <th>${playerInGame.playerInTeam.team.getTitle(lang)} ${playerInGame.teamInGame.goals} : ${playerInGame.teamInGame.other().goals} ${playerInGame.teamInGame.other().team.getTitle(lang)}</th>
            <@g.playerInGameDetailsRowContent playerInGame />
        </tr>
        </#list>
    </tbody>
</table>
</@l.layout>

