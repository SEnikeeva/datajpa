
<html>
<head>
	<meta charset="utf-8"/>

</head>
<body>



<div class="c-wrapper">

<H2>
	 Клиент
</H2>

	<form action='${model["app_path"]}/addclient' method="post">


		<div>Имя <label>
            <input type="text" name="name" />
        </label></div>
		<div>Адрес <label>
            <input type="text" name="address" />
        </label></div>
		<div>Паспорт <label>
            <input type="text" name="passport" />
        </label></div>

		<input type="submit" value="Сохранить"/>
	</form>

</div>
</body>
</html>