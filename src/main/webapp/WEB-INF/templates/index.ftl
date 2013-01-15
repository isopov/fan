<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />


<#assign pageTitle><@spring.message "index.title" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@spring.message "welcome.message" /></h1>
</@layout.layout>
