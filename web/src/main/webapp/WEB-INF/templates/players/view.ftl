<#import "../layout.ftl" as l />
<#import "../spring.ftl" as s />
<#import "../common.ftl" as c />
<#import "../games/gameCommon.ftl" as g />

<#assign pageTitle><@c.i18ned player "firstName" /> <@c.i18nedOrNull player "middleName" /> <@c.i18nedOrNull player "lastName" /></#assign>

<@l.layout pageTitle=pageTitle>
<h1>${pageTitle}</h1>
<table class="table table-striped table-bordered table-condensed">
    <caption><@s.message "games.last" /> <@c.pages previousUrl nextUrl /></caption>
    <thead>
    <tr>
        <th><@s.message "simple.game" /></th>
        <@g.playersInGameDetailsHeaderContent />
    </tr>
    </thead>
    <tbody>
        <#list lastGames as playerInGame >
        <tr>
            <th>
                <div style="white-space: nowrap;">
                    <@compress single_line=true>
                        <a href="<@s.url "/club/view?id=${playerInGame.playerInTeam.team.id}" />">
                            <i class="icon-user"></i>
                            <#if (playerInGame.teamInGame.isHost()) >
                                <i class="icon-home"></i>
                            </#if>
                        ${playerInGame.playerInTeam.team.getTitle(lang)}
                        </a>&nbsp;
                        <a href="<@s.url "/games/view/${playerInGame.teamInGame.game.id}" />">
                        ${playerInGame.teamInGame.goals}:${playerInGame.teamInGame.other().goals}
                        </a>&nbsp;
                    <a href="<@s.url "/club/view?id=${playerInGame.teamInGame.other().team.id}" />">
                        <#if (playerInGame.teamInGame.other().isHost()) >
                            <i class="icon-home"></i>
                        </#if>${playerInGame.teamInGame.other().team.getTitle(lang)}
                        <a/>
                    </@compress>
                </div>
            </th>
            <@g.playerInGameDetailsRowContent playerInGame />
        </tr>
        </#list>
    </tbody>
</table>
</@l.layout>

