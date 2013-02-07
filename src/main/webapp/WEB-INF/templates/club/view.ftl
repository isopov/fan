<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@c.i18ned club "name" /></#assign>

<@l.layout pageTitle=pageTitle>
<h1>${pageTitle}</h1>
    <@s.message "view.derby.with"/>: <input type="search" placeholder="<@s.message "enter.team.title" />"
                                            data-provide="typeahead"
                                            id="search"/>
<script type="text/javascript">
    $(function () {
        //TODO really ugly - typeahead is not what should be used here
        titles = [];
        map = {};
        var data = [
            <#list teamsPlayedWith as team>
                {"teamId": "${team.id}", "teamTitle": "${team.getTitle(lang)}"},
            </#list>
        ];
        $.each(data, function (i, team) {
            map[team.teamTitle] = team;
            titles.push(team.teamTitle);
        });

        var search = $('input#search');
        $(search).typeahead({
            source: function (query, process) {
                process(titles);
            },
            updater: function (item) {
                document.location.href = "<@s.url "/club/derby/" />" + "${club.id}" + "/vs/" + map[item].teamId;
            },
            items: 5
        });
    });
</script>
<@c.gamesTable lastGames />
</@l.layout>
