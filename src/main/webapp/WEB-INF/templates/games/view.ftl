<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@layout.i18nedProperty club "name" /></#assign>

<@layout.layout pageTitle=pageTitle>
	<h1>${pageTitle}</h1>
</@layout.layout>
