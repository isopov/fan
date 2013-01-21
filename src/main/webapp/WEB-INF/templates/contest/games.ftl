<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#import "common.ftl" as c />

<#assign pageTitle><@spring.message "contest.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
    <#list seasons as season>
        <@c.seasonTable season />
    </#list>
</@layout.layout>
