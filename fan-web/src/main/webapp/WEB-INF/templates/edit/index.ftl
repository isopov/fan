<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "simple.admin" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.1.5/angular.min.js" type="text/javascript"></script>
<script src="<@spring.url "/js/fanedit.js"/>" type="text/javascript"></script>
<div ng-app="fanedit">
    <div ng-view></div>
</div>
</@layout.layout>
