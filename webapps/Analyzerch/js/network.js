/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getChart(){
    $("#loadingGif").show();
    var div = d3.select("#networkChart");
    div.select("svg").remove();
    
    var width = 540,
    height = 500;

    if (window.innerWidth && window.innerHeight) {
     width = 0.6*window.innerWidth;
     height = 0.8*window.innerHeight;
    }
    var force = d3.layout.force()
            .gravity(0.3)
            .charge(-150)
            .linkDistance(40)
            .size([width, height]);

    var svg = d3.select("#networkChart").append("svg")
        .attr("width", width)
        .attr("height", height)
        .attr("transform","translate("+width/4+","+height/3+")");

    var school = document.getElementById("school").value;
    var rArea = document.getElementById("researcharea").value;
    var pubType = document.getElementById("publicationtype").value;
    var rName = document.getElementById("researcher").value;
    var startYear = document.getElementById("startYear").value;
    //var endYear = document.getElementById("endYear").value;
    
    
    d3.json("/Analyzerch/network?school="+school+"&&rArea="+rArea+"&&pubType="+pubType+"&&rName="+rName+"&&startYear="+startYear, function(error, graph) {
      $("#loadingGif").hide();
        if(graph.nodes==="empty"){
          $("#errorDiv").show();
      }else{
          $("#errorDiv").hide();
        force
          .nodes(graph.nodes)
          .links(graph.links)
          .start();

      var link = svg.selectAll(".link")
          .data(graph.links)
        .enter().append("line")
          .attr("class", "link")
          .style("stroke-width", function(d) { Math.sqrt(d.value); });

      var node = svg.selectAll(".node")
          .data(graph.nodes)
        .enter().append("circle")
          .attr("class", "node")
          .attr("r", function(d){return (d.size<2) ? 3:Math.sqrt(d.size) ; })
          .style("fill", function(d) { return d3.rgb(d.color); })
          .call(force.drag);

      node.append("title")
          .text(function(d) { return d.name; });
      
      node.on("click", click);

      force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });

        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });
      });
     }
    });
   function click(d){
       getDetails(d.name);
    }
}

function getDetails(name){
    var hOutput =name;
    document.getElementById("headingAbout").innerHTML=hOutput;
    $.get("/Analyzerch/getDataService", 
      {
        "filter":"individual",
        "name":name
      },
      function(data) {
          var about='';
          if(data.school!==""){
            var about='<table><tr><td rowspan="3" style="width : 101px"><img src="'+data.photoURL+'" style="width: 100px;"/></td><td><b>Email:</b><br/>'+data.email+'</td></tr><tr><td><b>Phone number:</b><br/>'+data.phoneNumber+'</td></tr><tr><td><b>CV:</b><br/><a href="'+data.cv+'">Click here</a></td></tr></table>';
            about+='<b>Designation:</b><br/>'+data.designation+'</br><b>School:</b><br/>'+data.school+'</br><b>Qualification:</b><br/>'+data.qualification+'<br/><br/>'
          }
          document.getElementById("aboutDiv").innerHTML=about;
      },
      "json"
    );
    
    var pubType = document.getElementById("publicationtype").value;
    var startYear = document.getElementById("startYear").value;
    var pOutput=pubType+' published in '+startYear;
    document.getElementById("pubHeading").innerHTML=pOutput;
    $.get("/Analyzerch/getDataService", 
      {
        "filter":"publications",
        "name":name,
        "pubType":pubType,
        "startYear":startYear
      },
      function(data) {
          var pub='';
          for(var i=0; i<data.length; i++){
              var index=i+1;
              pub+=index+') '+data[i].publication+'<br/>';
          }
          document.getElementById("publicationsInfo").innerHTML=pub;
      },
      "json"
    );
    document.getElementById("professorDetails").innerHTML = output;
}