<#import "spring.ftl" as s />
<#import "layout.ftl" as layout />

<#macro seasonView season>
<h3><@layout.i18nedProperty season.contest "name" /></h3>
<h4>${season.start} - ${season.end}</h4>
    <@gamesTable    season.games />
</#macro>

<#macro i18ned object property>

    <#if object.getI18n(lang)??>
    ${object.getI18n(lang)[property]}
    <#else>
    ${object[property]}
    </#if>
</#macro>

<#macro i18nedOrNull object property defaultValue="">
    <#if object.getI18n(lang)?? && object.getI18n(lang)[property]??>
    ${object.getI18n(lang)[property]}
    <#elseif object[property]??>
    ${object[property]}
    <#else>
    ${defaultValue}
    </#if>
</#macro>

<#macro orNull object property defaultVal="-">
    <#if (object[property]??)>
    ${object[property]}
    <#else>
    ${defaultVal}
    </#if>
</#macro>

<#macro gameView game>
<em>
    <@orNull game "date" "Unknown date"/>
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
        <td><@orNull game.host "shots" /></td>
        <td><@orNull game.guest "shots" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.shotsOnTarget" /></th>
        <td><@orNull game.host "shotsOnTarget" /></td>
        <td><@orNull game.guest "shotsOnTarget" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.corners" /></th>
        <td><@orNull game.host "corners" /></td>
        <td><@orNull game.guest "corners" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.passes" /></th>
        <td><@orNull game.host "passes" /></td>
        <td><@orNull game.guest "passes" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.failedPasses" /></th>
        <td><@orNull game.host "failedPasses" /></td>
        <td><@orNull game.guest "failedPasses" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.offsides" /></th>
        <td><@orNull game.host "offsides" /></td>
        <td><@orNull game.guest "offsides" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.fouls" /></th>
        <td><@orNull game.host "fouls" /></td>
        <td><@orNull game.guest "fouls" /></td>
    </tr>

    <tr>
        <th><@s.message "simple.yellowCards" /></th>
        <td><@orNull game.host "yellowCards" /></td>
        <td><@orNull game.guest "yellowCards" /></td>
    </tr>
    <tr>
        <th><@s.message "simple.redCards" /></th>
        <td><@orNull game.host "redCards" /></td>
        <td><@orNull game.guest "redCards" /></td>
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
                            <li><@i18ned playerInGame.playerInTeam.player "firstName" /> <@i18nedOrNull playerInGame.playerInTeam.player "middleName" /> <@i18nedOrNull playerInGame.playerInTeam.player "lastName" /></li>
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
    <th><@i18ned playerInGame.playerInTeam.player "firstName" /> <@i18nedOrNull playerInGame.playerInTeam.player "middleName" /> <@i18nedOrNull playerInGame.playerInTeam.player "lastName" /></th>
    <td><@orNull playerInGame "shots" /></td>
    <td><@orNull playerInGame "shotsOnTarget" /></td>
    <td>${playerInGame.goals?size}</td>
    <td><@orNull playerInGame "passes" /></td>
    <td><@orNull playerInGame "offsides" /></td>
    <td><@orNull playerInGame "foulsDrawn" /></td>
    <td><@orNull playerInGame "fouls" /></td>
    <td><@orNull playerInGame "saves" /></td>
    <td><@orNull playerInGame "yellowCards" /></td>
    <td><@orNull playerInGame "redCards" /></td>
</tr>
</#macro>

<#macro playersInGameDetailsHeader>
<thead>
<tr>
    <th>Player</th>
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
</tr>
</thead>
</#macro>

<#macro gamesTable games>
<table>
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Host</th>
        <th>Score</th>
        <th>Guest</th>
    </tr>
    </thead>
    <tbody>
        <#list games as game>
        <tr>
            <td align="center">
                <@orNull game "date" />
            </td>
            <td>
                <a href="/club/view?id=${game.host.team.id}">
                ${game.host.team.getTitle(lang)}
                </a>
            </td>
            <td>
                <a href="<@s.url "/games/view/${game.id}"/>">
                ${game.host.goals}:${game.guest.goals}
                </a>
            </td>
            <td>
                <a href="/club/view?id=${game.guest.team.id}">
                ${game.guest.team.getTitle(lang)}
                </a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

</#macro>
