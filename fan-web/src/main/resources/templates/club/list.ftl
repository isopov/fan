<#import "../spring.ftl" as spring />
<#import "../layout.ftl" as layout />

<#assign pageTitle><@spring.message "club.list" /></#assign>

<@layout.layout pageTitle="${pageTitle}">
	<table>
		<tr>
			<th>Link</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	<#list clubs as club>
		<tr>
			<td>
				<a href="<@spring.url "/club/view/${club.id?c}"/>">
                    <@layout.i18nedProperty club "name" />
				</a>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</#list>
	</table>
</@layout.layout>
