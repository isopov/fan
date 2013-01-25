<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />
<#import "../common.ftl" as c />

<#assign pageTitle><@layout.i18nedProperty season.contest "name" /></#assign>

<@layout.layout pageTitle=pageTitle>
    <@c.seasonView season />
</@layout.layout>