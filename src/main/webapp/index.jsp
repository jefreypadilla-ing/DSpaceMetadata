<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="UTF-8">
        <title>File Upload Example in JSP and Servlet - Java web application</title>

        <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="js/fileinput.min.js" type="text/javascript"></script>
    </head>

    <body> 
        <div class="container">
            <h3> Choose File to Upload in Server </h3>
            <form action="LoadFile" method="post" enctype="multipart/form-data">
                <input id="file" name="file" type="file" class="file">
            </form>          
        </div>
    </body>
</html>