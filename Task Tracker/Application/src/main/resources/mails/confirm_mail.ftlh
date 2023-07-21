<html>
<head>
    <meta charset=“UTF-8”>
    <title>Подтверждение регистрации</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        h1 {
            text-align: center;
        }
        div {
            background-color: #cfdee2;
            display: block;
            float: left;
        }
    </style>
</head>
<body>
    <div>
        <h1>Уважаемые, ${username} !</h1>
        <h2>Пройдите для подтверждения регистрации по
            <a href="${confirmLink}">ссылке</a>
        </h2>
    </div>
</body>
</html>
