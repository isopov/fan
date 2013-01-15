<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />


<#assign pageTitle><@spring.message "error.500" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "error.500" /></h1>
</@layout.layout>
