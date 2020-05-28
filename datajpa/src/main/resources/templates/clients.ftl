
<html>
<head>
	<meta charset="utf-8"/>

</head>
<body>


<div class="c-wrapper">

<H2>
	 Клиенты
</H2>

	<table>
		<thead>
			<th>id</th><th>Имя</th>
		</thead>
		<tbody>
		<#if model["clients"]??>
		<#list model["clients"] as client>
		<tr>
			<td>
				<#if client.id??>${client.id}</#if>
			</td>
			<td><#if client.name??><a href='${model["app_path"]}/addclient?id=${client.id}'>${client.name}</a></#if></td>
		</tr>
		</#list>
		</#if>
	</tbody>
	</table>

</div>
</body>
</html>