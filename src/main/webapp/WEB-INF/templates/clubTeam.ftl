<#import "spring.ftl" as spring />
<#import "layout.ftl" as layout />


<@layout.layout pageTitle="${clubTeam.club.name}">
	<h1><@spring.message "welcome.message" /></h1>
</@layout.layout>
