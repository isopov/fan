<#import "spring.ftl" as s />
<#import "layout.ftl" as layout />

<#macro seasonView season>
<h3><@layout.i18nedProperty season.contest "name" /></h3>
<h4>${season.startDate} - ${season.endDate}</h4>
    <@gamesTable season.games />
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

<#macro gamesTable games>
<table class="table table-striped table-bordered table-condensed">
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
                <@orNull game "gameDate" />
            </td>
            <td>
                <a href="<@s.url "/club/view?id=${game.host.team.id}" />">
                ${game.host.team.getTitle(lang)}
                </a>
            </td>
            <td>
                <a href="<@s.url "/games/view/${game.id}"/>">
                ${game.host.goals}:${game.guest.goals}
                </a>
            </td>
            <td>
                <a href="<@s.url "/club/view?id=${game.guest.team.id}" />">
                ${game.guest.team.getTitle(lang)}
                </a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</#macro>