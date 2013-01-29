<#import "../layout.ftl" as l />
<#import "../spring.ftl" as s />
<#import "../common.ftl" as c />

<#macro gameView game>
<em>
    <@c.orNull game "date" "Unknown date"/>
</em>
<h3 class="text-align: center;">
    <a href="<@s.url "/club/view?id=${game.host.team.id}" />">
    ${game.host.team.getTitle(lang)}
    </a>
    <a href="<@s.url "/games/view/${game.id}"/>">
    ${game.host.goals}:${game.guest.goals}
    </a>
    <a href="<@s.url "/club/view?id=${game.guest.team.id}" />">
    ${game.guest.team.getTitle(lang)}
    </a>
</h3>
<table class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>#</th>
        <th>${game.host.team.getTitle(lang)}</th>
        <th>${game.guest.team.getTitle(lang)}</th>
    </tr>
    </thead>

    <tbody>
    <tr>
        <th><@s.message "simple.goals" /></th>
        <td>${game.host.goals}</td>
        <td>${game.guest.goals}</td>
    </tr>
    <tr>
        <th><@s.message "simple.shots" /></th>
        <td><@c.orNull game.host "shots" /></td>
        <td><@c.orNull game.guest "shots" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.shotsOnTarget" /></th>
        <td><@c.orNull game.host "shotsOnTarget" /></td>
        <td><@c.orNull game.guest "shotsOnTarget" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.corners" /></th>
        <td><@c.orNull game.host "corners" /></td>
        <td><@c.orNull game.guest "corners" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.passes" /></th>
        <td><@c.orNull game.host "passes" /></td>
        <td><@c.orNull game.guest "passes" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.failedPasses" /></th>
        <td><@c.orNull game.host "failedPasses" /></td>
        <td><@c.orNull game.guest "failedPasses" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.offsides" /></th>
        <td><@c.orNull game.host "offsides" /></td>
        <td><@c.orNull game.guest "offsides" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.fouls" /></th>
        <td><@c.orNull game.host "fouls" /></td>
        <td><@c.orNull game.guest "fouls" /></td>
    </tr>

    <tr>
        <th><@s.message "simple.yellowCards" /></th>
        <td><@c.orNull game.host "yellowCards" /></td>
        <td><@c.orNull game.guest "yellowCards" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.redCards" /></th>
        <td><@c.orNull game.host "redCards" /></td>
        <td><@c.orNull game.guest "redCards" /></td>
    </tr>
    </tbody>
</table>
    <#list game.teams as teamInGame >
        <#if (teamInGame.players?? && teamInGame.players?has_content)>

        <div class="row-fluid">
            <div class="span9">
                <table class="table table-striped table-bordered table-condensed">
                    <caption>${teamInGame.team.getTitle(lang)} Detailed Statistics</caption>
                    <@playersInGameDetailsHeader />
                    <tbody>
                        <#list game.host.players as playerInGame >
                        <#if (playerInGame.minuteStart??)>
                            <@playerInGameDetailsRow playerInGame />
                        </#if>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="span3">
                <ul>
                    <caption>${teamInGame.team.getTitle(lang)} Substitutes</caption>
                    <#list game.host.players as playerInGame >
                        <#if (!playerInGame.minuteStart??)>
                            <li><@c.i18ned playerInGame.playerInTeam.player "firstName" /> <@c.i18nedOrNull playerInGame.playerInTeam.player "middleName" /> <@c.i18nedOrNull playerInGame.playerInTeam.player "lastName" /></li>
                        </#if>
                    </#list>
                </ul>
            </div>
        </div>
        </#if>
    </#list>

</#macro>

<#macro playerInGameDetailsRow playerInGame>
<tr>
    <th><@c.i18ned playerInGame.playerInTeam.player "firstName" /> <@c.i18nedOrNull playerInGame.playerInTeam.player "middleName" /> <@c.i18nedOrNull playerInGame.playerInTeam.player "lastName" /></th>
    <@playerInGameDetailsRowContent playerInGame />
</tr>
</#macro>

<#macro playerInGameDetailsRowContent playerInGame>
<td><@c.orNull playerInGame "shots" /></td>
<td><@c.orNull playerInGame "shotsOnTarget" /></td>
<td>${playerInGame.goals?size}</td>
<td><@c.orNull playerInGame "passes" /></td>
<td><@c.orNull playerInGame "offsides" /></td>
<td><@c.orNull playerInGame "foulsDrawn" /></td>
<td><@c.orNull playerInGame "fouls" /></td>
<td><@c.orNull playerInGame "saves" /></td>
<td><@c.orNull playerInGame "yellowCards" /></td>
<td><@c.orNull playerInGame "redCards" /></td>
</#macro>

<#macro playersInGameDetailsHeader>
<thead>
<tr>
    <th>Player</th>
    <@playersInGameDetailsHeaderContent />
</tr>
</thead>
</#macro>

<#macro playersInGameDetailsHeaderContent>
<th><@s.message "simple.shots" /></th>
<th><@s.message "simple.shotsOnTarget" /></th>
<th><@s.message "simple.goals" /></th>
<th><@s.message "simple.passes" /></th>
<th><@s.message "simple.offsides" /></th>
<th><@s.message "simple.foulsDrawn" /></th>
<th><@s.message "simple.fouls" /></th>
<th><@s.message "simple.saves" /></th>
<th><@s.message "simple.yellowCards" /></th>
<th><@s.message "simple.redCards" /></th>
</#macro>
