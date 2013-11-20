<%-- 
    Document   : index
    Created on : Oct 23, 2013, 2:50:12 PM
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
    <link href="stylesheets/bootstrap-responsive.css" rel="stylesheet">
    <link href="stylesheets/docs-compiled.css" rel="stylesheet">
    <link href="stylesheets/ms_linechart.css" rel="stylesheet">
    <link rel="stylesheet/less" type="text/css" href="stylesheets/preboot.less" />
   
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="img/ico/apple-touch-icon-57-precomposed.png">
    <script src="js/less.js" type="text/javascript"></script>
</head>

<body>
    <div class="container-fluid">
        
    </div>
  
     <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                    <div class="container-fluid">
                        <a class="brand" href="#">Analyzerch</a>
                        <ul class="nav" style="padding-top: 4px; padding-top: 4px">
                          <li class="active"><a href="index.jsp">Overview</a></li>
                          <li><a href="visualization.jsp">Visualization</a></li>
                       </ul>
                    </div>
            </div>
    </div>
    <div class="container-fluid" style="padding-top: 40px">
        <div class="row-fluid" style="padding-bottom: 20px"></div>
        <div class="span3 customaboutbox">
                <div class="customaboutbox1-inner">
                    <h1 class="header-divider">Analyzerch</h1>
                    <p style="font-color: #FFF; font-size: 14px; font-weight: bold">Vision Statement</p>
                    <p style="font-size: 12px">Analyzerch aims to perform a social network analysis of Singapore Management University's Faculty with respect to their research  and publications. By creating a collaboration graph, Analyzerch investigates how two or more researchers are linked by way of the kind of publication that they work on (this can range from conference papers to monographs and books) and the level of collaboration involved (number of publications and research areas).</p>
                    <!--<p style="font-color: #FFF; font-size: 14px; font-weight: bold">Resources</p>
                    <p style="font-size: 11px">This visualization is built with a number of resources that are primarily published under the Creative Commons or MIT license<br/>
                    1) D3.js by Mike Bostock<br/>
                    2) Twitter Bootstrap by Mike Otto and Jason Thorton<br/>
                    3) Preboot.js by Mike Otto<br/>
                    4) Less.js by Alexis Sellier<br/>
                    <br/>-->
                    <p style="font-size: 12px">You can view the project wiki and source folders <a href="https://wiki.smu.edu.sg/1314T1is428/Analyzerch">here</a></p>
                    <p style="font-color: #FFF; font-size: 14px; font-weight: bold">Team Analyzerch</p>
                    <p style="font-size: 12px">For more information, contact us here!<br/>Aung Myat Ko: aungmyatko.2010@sis.smu.edu.sg<br/>
                        Ila Gokarn: ingokarn.2011@sis.smu.edu.sg</p>
                </div>
            </div>
            <div class="span9">
                <h3>Overview of Research at Singapore Management University</h3>
                 <div id="loadingGif" align="center"><img src="img/loading.gif"/></div>
                <div id="networkChart">
                </div>
            </div>
           
            
        
     </div>
    
<br/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
		<script>window.jQuery || document.write('<script src="js/jquery.js"><\/script>');</script>
		<script src="js/bootstrap.js"></script>
		<script src="js/md5-min.js"></script>
                <script src="js/md5-min.js"></script>
                <script src="http://d3js.org/d3.v3.min.js"></script>
                <script src="js/overview.js"></script>
                <script src="js/underscore.js"></script>
                <script>
                     $(document).ready(function () {
                        $("#loadingGif").hide();
                        showTrend();
                    });
                </script>
</body>
</html>