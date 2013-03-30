<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "contest.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
    <#list seasons as season>
    <h3>${season.contest.name}</h3>
        <caption>Season ${season.startDate} - ${season.endDate}</caption>
        	 <ul>
             <#list season.teams as team>
             	<li>
             		 ${team.getTitle(lang)}
             	</li>
            </#list>
        	</ul>
    </#list>
</@layout.layout>
