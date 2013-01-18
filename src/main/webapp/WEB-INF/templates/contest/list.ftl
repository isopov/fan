<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "contest.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
    <#list contests as contest>
    <h3>${contest.name}</h3>
        <#if seasons.containsKey(contest)>
            <#assign season=seasons.get(contest) />
        <h4>${season.start} - ${season.end}</h4>
            <#if season.games?? && !season.games.isEmpty()>
                <#list season.games as game>
                <table>
                    <thead>
                    <tr>
                        <th>
                            DateTime
                        </th>
                        <th>
                            First team
                        </th>
                        <th>
                            Second team
                        </th>
                    </tr>
                    </thead>
                <tbody>
                <tr>
                    <td>
                    ${game.date}
                    </td>
                    <#if game.guest??>
                        <td title="Guest">
                        ${game.guest.getTitle(lang)}
                        </td>
                        <td title="Host">
                        ${game.host.getTitle(lang)}
                        </td>
                    <#else>
                        <td>
                        ${game.teams.get(0)}
                        </td>
                        <td>
                        ${game.teams.get(1)}
                        </td>
                    </#if>
                    </tr>
                    </tbody>
                    </table>
                </#list>
            </#if>
        </#if>
    </#list>
<table>
    <tr>
        <th>Link</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>

    <tr>
        <td>
            <a href="<@spring.url "/club/view?id=${club.id}"/>">
                <@layout.i18nedProperty club "name" />
            </a>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

</table>
</@layout.layout>
