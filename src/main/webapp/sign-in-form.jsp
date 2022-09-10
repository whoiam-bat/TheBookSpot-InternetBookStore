<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/7/2022
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<form action="personal-cabinet" method="post">
    <table>
        <tbody>
        <tr>
            <td><label for="email">Email:</label></td>
            <td><input type="email" name="email" id="email"></td>
        </tr>

        <tr>
            <td><label for="password">Password:</label></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Sign in" class="signIn"/></td>
            <td><input type="hidden" name="command" value="LIST"/></td>
        </tr>
        </tbody>
    </table>
</form>

</body>
</html>
