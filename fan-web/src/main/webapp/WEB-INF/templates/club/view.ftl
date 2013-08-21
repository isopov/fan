<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@c.i18ned club "name" /></#assign>
<@l.layout pageTitle=pageTitle>

<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/chosen/1.0/chosen.jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
        var select = $(".chzn-select");
        select.chosen();
        select.change(function(){
            document.location.href = "<@s.url "/club/derby/" />${club.id}/vs/" + select.val();
        });
    });
</script>

<h1>${pageTitle}</h1>

    <@s.message "view.derby.with"/>:
<select data-placeholder="<@s.message "teams.choose" />" class="chzn-select" style="width:350px;">
    <option value=""></option>
    <#list teamsPlayedWith as team>
        <option value="${team.id}">${team.getTitle(lang)}</option>
    </#list>
</select>


<#--
//Typeahead implementation of Derby selection
    <@s.message "view.derby.with"/>: <input type="search" placeholder="<@s.message "enter.team.title" />"
                                            data-provide="typeahead"
                                            id="search"/>
<script type="text/javascript">
    $(function () {

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
-->
    <@c.gamesTable lastGames />
</@l.layout>
