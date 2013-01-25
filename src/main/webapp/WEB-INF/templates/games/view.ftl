<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle>Hello World!</#assign>

<@layout.layout pageTitle=pageTitle>
	<h1>${pageTitle}</h1>
</@layout.layout>
