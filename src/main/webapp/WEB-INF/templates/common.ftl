<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />

<#macro seasonView season>
<h3><@layout.i18nedProperty season.contest "name" /></h3>
<h4>${season.start} - ${season.end}</h4>
    <@gamesTable    season.games />
</#macro>

<#macro gamesTable games>
<table>
    <thead>
    <tr>
        <th>
            DateTime
        </th>
        <th>
            Host
        </th>
        <th>
            Score
        </th>
        <th>
            Guest
        </th>
    </tr>
    </thead>
    <tbody>
        <#list games as game>
        <tr>
            <td align="center">
                <#if (game.date??) >
                ${game.date}
                <#else>
                    -
                </#if>
            </td>
            <td>
                <a href="/club/view?id=${game.host.team.id}">
                ${game.host.team.getTitle(lang)}
                </a>
            </td>
            <td>
                <a href="<@spring.url "/games/view/${game.id}"/>">
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
