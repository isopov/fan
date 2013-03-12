<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#import "common.ftl" as c />

<#assign pageTitle><@spring.message "simple.register" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
<h2>You have successfully registered and may now login</h2>
</@layout.layout>
