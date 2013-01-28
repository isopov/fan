<#import "../spring.ftl" as s />
<#import "../layout.ftl" as l />
<#import "../common.ftl" as c />

<#assign pageTitle><@s.message "contest.list" /></#assign>

<@l.layout pageTitle="${pageTitle}">
    <@contestsTable contests />
</@l.layout>

<#macro contestsTable contests>
<table>
    <thead>
    <th></th>
    </thead>
    <tbody>
        <#list contests as contest>
        <tr>
           <@c.i18ned contest "name" />
        </tr>
        </#list>
    </tbody>
</table>
</#macro>



