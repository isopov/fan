<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />
<#import "common.ftl" as c />


<#assign pageTitle><@spring.message "index.title" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<h1><@c.i18ned content "title" /></h1>
    <p><@c.i18ned content "text" /></p>
</@layout.layout>
