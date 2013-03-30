<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "simple.admin" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
<script src="<@spring.url "/js/angular-1.1.1.js"/>" type="text/javascript"></script>
<script src="<@spring.url "/js/fanedit.js"/>" type="text/javascript"></script>
<div ng-app="fanedit">
    <div ng-view></div>
</div>
</@layout.layout>
