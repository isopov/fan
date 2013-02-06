<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@c.i18ned club "name" /></#assign>

<@l.layout pageTitle=pageTitle>
<h1>${pageTitle}</h1>
View derby with: <input type="search" placeholder="<@s.message "enter.team.title" />" data-provide="typeahead"
                        id="search"/>
<script type="text/javascript">
    var search = $('input#search');
    $(search).typeahead({
        source: [
            <#list teamsPlayedWith as team>
                '${team.getTitle(lang)}',
            </#list>
        ],
        items: 5
    });
    $(search).change(function () {
                alert('Handler for .change() called.');
            }
    );

</script>
<table class="table table-striped table-bordered table-condensed">
    <caption>Last games</caption>
    <thead>
    <tr>
        <th>Position</th>
        <th>Score</th>
        <th>vs</th>
    </tr>
    </thead>
    <#list lastGames as teamInGame>
        <tr>
            <td>${teamInGame.position}</td>
            <td><a href="<@s.url "/games/view/${teamInGame.game.id}" />">${teamInGame.goals}
                :${teamInGame.other().goals}</a></td>
            <td>
                <a href="<@s.url "/club/view?id=${teamInGame.other().team.id}" />">${teamInGame.other().team.getTitle(lang)}</a>
            </td>
        </tr>
    </#list>
</table>
</@l.layout>
