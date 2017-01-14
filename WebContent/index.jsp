<!DOCTYPE html>
<html>
<title>MeetingApplication</title>
<body>

<h1>MeetingApplication</h1>
<br/>
<form action="./calendar" method="post">
<input type="hidden" value="reg" name="op" />
<input type="submit" value="Register">
</form>
<br/>
<form action="./calendar" method="post">
<input type="hidden" value="addEvent" name="op" />
<input type="submit" value="AddEvent">
</form>
<br/>
<form action="./calendar" method="post">
<input type="hidden" value="getDetails" name="op" />
<input type="submit" value="GetDetails">
</form>
</body>
</html>
