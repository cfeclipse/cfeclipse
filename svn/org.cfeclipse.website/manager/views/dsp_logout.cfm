<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Manager Login</title>
</head>
<body>
<table width="100%" height="100%">
	<tr>
	
		<td align="center" valign="middle">
			
			<cfoutput><form action="#viewstate.getValue('myself')#login" method="post"></cfoutput>
			<table>
				<tr>
					<td colspan="2">
					<h1>You have been logged out...</h1>
					</td>
				</tr>
				<tr>
				<td>username:</td>
				<td><input type="text" name="j_username"></td>
				</tr>
				<tr>
				<td>password:</td>
				<td><input type="password" name="j_password"></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" name="do_login" value="login">
					</td>
				</tr>
			</table>
			</form>
		</td>	
	</tr>
</table>

</body>
</html>
