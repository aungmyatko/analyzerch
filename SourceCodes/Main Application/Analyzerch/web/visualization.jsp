<%-- 
    Document   : visualization
    Created on : Nov 7, 2013, 8:45:54 AM
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
     <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                    <div class="container-fluid">
                        <a class="brand" href="#">Analyzerch</a>
                        <ul class="nav" style="padding-top: 4px; padding-top: 4px">
                          <li><a href="index.jsp">Overview</a></li>
                          <li class="active"><a href="visualization.jsp">Visualization</a></li>
                       </ul>
                    </div>
            </div>
    </div>
    <div class="container-fluid" style="padding-top: 40px">
        <div class="row-fluid" style="padding-bottom: 20px" align="center" id="banner">
            <table>
                <tr>
                    <td><img src="img/banner.png" align="center"/></td>
                </tr>
                <tr>
                    <td><button class="btn btn btn-small pull-right" id="closeBanner" onclick="closeBanner();">Close Banner</button></td>
                </tr>
            </table>
        </div>
        <div class="row-fluid" style="padding-bottom: 20px"></div>
            <div class="row-fluid">
                <div class="span2 customfilterbox">
                    <div class="customfilterbox-inner">
                        <h4 class="h4c">Filters</h4>
                        <p style="font-size: 14px; font-weight: bold">School</p>
                        <select style="width: 170px" id="school" onchange="fillResearcher();">
                            
                        </select><br/><br/>
                        <p style="font-size: 14px; font-weight: bold">Research Area</p>
                        <select style="width: 170px" id="researcharea">
                            
                        </select><br/><br/>
                        <p style="font-size: 14px; font-weight: bold">Publication Type</p>
                        <select style="width: 170px"id="publicationtype">
                            
                        </select><br/><br/>
                        <p style="font-size: 14px; font-weight: bold">Researcher Name</p>
                        <select style="width: 170px" id="researcher" onchange="fillAllYear();">
                            
                        </select><br/><br/>
                        <!--<div id="researcherName" style="width: 170px" contenteditable="true">Start typing a name.</div><br/>-->
                        <p style="font-size: 14px; font-weight: bold">Years</p>
                        <table>
                            <tr>
                                <td><font size="2" style="bold">For: </font></td>
                                <td><select class="pull-right" style="width: 90px" id="startYear"></select></td>
                            </tr>
                            <!--<tr>
                                <td><font size="2" style="bold">To: </font></td>
                                <td><select class="pull-right" style="width: 90px" id="endYear"></select></td>
                            </tr>-->
                        </table>
                        
                        <p style="padding-top: 8px; font-size: 1;"align="center">
                            <button id="rebuildChart" class="btn btn-danger" onclick="getChart();">Apply</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button id="refreshChart" class="btn btn-danger" onclick="refreshChart();">Reset</button><br/>
                            
                        </p>
                    </div>
                </div>
                <div class="span7" style="padding-top: 40px">
                    <div id="errorDiv" class="error">There are no publications for this year and category.</div>
                    <div id="loadingGif" align="center"><img src="img/loading.gif"/></div>
                    <div id="networkChart">
                    </div>
                </div>
                <div class="span3 customaboutbox FixedHeightContainer">
                    <div class="customprofbox-inner">
                        Legend:<br/>
                        <p style="padding: 10px; font-size: '1'" align="center">
                            <img src="img/colors.png" style="width: 200px"/>
                        </p>
                        <h4 style="color: #ffffff">About the Researcher</h4>
                        <div class="content" id="professorDetails">
                            <div id="headingAbout" class="aboutHeadings"></div>
                            <div id="aboutDiv" class="aboutContent"></div>
                            <div id="pubHeading" class="aboutHeadings"></div>
                            <div id="publicationsInfo" class="aboutContent"></div>
                        </div>
                    </div>
                    <p align="center">
                        <button class="btn btn" id="showTrend" align="center" onclick="showTrend();">Show Trend for Professor</button>
                        <button class="btn btn" id="hideTrend" align="center" onclick="getChartAgain();">Back to Visualization</button>
                    </p>
                </div>
            </div>
     </div>
    
<br/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery.js"><\/script>');</script>
<script src="js/bootstrap.js"></script>
<script src="js/md5-min.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="js/network.js"></script>
<script src="js/trends.js"></script>
<script src="js/underscore.js"></script>
<!--<script src="js/bootstrap-typeahead.js"></script>
<script src="js/bootstrap-tagautocomplete.js"></script>
<script src="js/rangy-core.js"></script>
<script src="js/caret-position.js"></script>-->
<script src="js/bootstrap-tooltip.js"></script>
<script src="js/bootstrap-popover.js"></script>
<script src="js/dataretrieval.js"></script>
<script>
    $(document).ready(function () {
        $("#loadingGif").hide();
        $("#errorDiv").hide();
        $("#hideTrend").hide();
        fillSchool();
        fillResearchArea();
        fillPublicationType();;
        fillResearcher();
        fillStartYear();
        //fillResearcherAutocomplete();
        
        
    });
    function closeBanner(){
        $("#banner").hide();
    }
    function getChartAgain(){
        $("#hideTrend").hide();
        $("#showTrend").show();
        getChart();
    }
</script>

		
</body>
</html>