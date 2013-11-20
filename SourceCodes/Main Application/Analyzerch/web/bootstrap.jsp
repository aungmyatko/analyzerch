<%-- 
    Document   : bootstrap
    Created on : Oct 28, 2013, 12:45:48 PM
    Author     : ingokarn.2011
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">

    <title>Analyzerch</title>
    <meta name="description" content="">
    <meta name="author" content="">


    <!-- Le styles -->
    <link href="stylesheets/bootstrap.css" rel="stylesheet">
   
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="img/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>
 
    <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                    <div class="container-fluid">
                            <a class="brand" href="#">Analyzerch</a>
                    </div>
            </div>
    </div>
    <br/><br/><br/>
    
    <div class="container">
        <h2>Analyzerch Admin Bootstrap</h2>
        <b>Instructions</b>
        <i>
            <ul>
                <li>Choose a .zip file</li>
                <li>File type inside the .zip folder must be in .csv format</li>
            </ul>
        </i>
        </br>  

        <form action="/Analyzerch/jsonBootstrap" method="post" enctype="multipart/form-data">
            <input type="file" name="file" accept=".zip"/> <br /> 
            <input type="submit" name="Submit" value="Bootstrap!" class="btn btn-primary"/>
        </form>
        </br></br>
    </div>
    
<br/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script>window.jQuery || document.write('<script src="js/jquery.js"><\/script>');</script>
		<script src="js/bootstrap.js"></script>
		<script src="js/md5-min.js"></script>
		
</body>
</html>