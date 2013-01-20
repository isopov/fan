<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "contest.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
    <#list seasons as season>
    <h3>${season.contest.name}</h3>
        <h4>${season.start} - ${season.end}</h4>
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
                    <td align="center">
                    <#if (game.date??) >
                    	${game.date}
                    <#else>
                    	-
                    </#if>
                    </td>
                    <#if game.guest??>
                        <td title="Guest">
                        ${game.guest.team.getTitle(lang)}
                        </td>
                        <td title="Host">
                        ${game.host.team.getTitle(lang)}
                        </td>
                    <#else>
                        <td>
                        ${game.teams[0].team.getTitle(lang)}
                        </td>
                        <td>
                        ${game.teams[1].team.getTitle(lang)}
                        </td>
                    </#if>
                  </tr>
               </tbody>
               </table>
            </#list>
    </#list>
</@layout.layout>
