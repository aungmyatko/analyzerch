/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function fillSchool(){
     $.get("/Analyzerch/getDataService", 
      {
        "filter":"school"
      },
      function(data) {
          document.getElementById("school").options.length = 0; 
          var ddl=document.getElementById("school");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
}
function fillResearchArea(){
     $.get("/Analyzerch/getDataService", 
      {
        "filter":"rArea"
      },
      function(data) {
          document.getElementById("researcharea").options.length = 0; 
          var ddl=document.getElementById("researcharea");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
}

function fillResearcher(){
    var school = document.getElementById("school").value;
    $.get("/Analyzerch/getDataService", 
      {
        "filter":"researcher",
        "school":school
      },
      function(data) {
          document.getElementById("researcher").options.length = 0; 
          var ddl=document.getElementById("researcher");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
}

function fillPublicationType(){
     $.get("/Analyzerch/getDataService", 
      {
        "filter":"publicationtype"
      },
      function(data) {
          document.getElementById("publicationtype").options.length = 0; 
          var ddl=document.getElementById("publicationtype");
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            if(i==0){
                el.selected=true;
            }
            ddl.appendChild(el);
          }
      },
      "json"
    );
}

function fillStartYear(){
    document.getElementById("startYear").options.length = 0; 
    var ddl=document.getElementById("startYear");
    var el = document.createElement("option");
    for(var i = 2000; i < 2014; i++) {
      var opt = i;
      var el = document.createElement("option");
      el.textContent = opt;
      el.value = opt;
      ddl.appendChild(el);
    }
}

function fillEndYear(){
    document.getElementById("endYear").options.length = 0; 
    var start = parseInt(document.getElementById("startYear").value);
    var ddl=document.getElementById("endYear");
    var el = document.createElement("option");
    for(var i = start+1; i < 2014; i++) {
        var opt = i;
      var el = document.createElement("option");
      el.textContent = opt;
      el.value = opt;
      ddl.appendChild(el);
    }
}
function fillAllYear(){
    var year = document.getElementById("researcher").value;
    //alert(year);
    if(year !== ""){
        document.getElementById("startYear").options.length = 0; 
        var ddl=document.getElementById("startYear");
        var el = document.createElement("option");
        for(var i = 2000; i < 2014; i++) {
            var opt = i;
          var el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          ddl.appendChild(el);
        }
        var el = document.createElement("option");
          el.textContent = "All";
          el.value = "All";
          ddl.appendChild(el);
    }else{
         document.getElementById("startYear").options.length = 0; 
        var ddl=document.getElementById("startYear");
        var el = document.createElement("option");
        for(var i = 2000; i < 2014; i++) {
            var opt = i;
          var el = document.createElement("option");
          el.textContent = opt;
          el.value = opt;
          ddl.appendChild(el);
        }
    }
}
    
    
function refreshChart(){
    $.get("/Analyzerch/getDataService", 
      {
        "filter":"school"
      },
      function(data) {
          document.getElementById("school").options.length = 0;
          var ddl=document.getElementById("school");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          el.selected = true;
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
     $.get("/Analyzerch/getDataService", 
      {
        "filter":"rArea"
      },
      function(data) {
          document.getElementById("researcharea").options.length = 0;
          var ddl=document.getElementById("researcharea");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          el.selected = true;
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
    var school = document.getElementById("school").value;
    
     $.get("/Analyzerch/getDataService", 
      {
        "filter":"researcher",
        "school":school
      },
      function(data) {
          document.getElementById("researcher").options.length = 0;
          var ddl=document.getElementById("researcher");
          var el = document.createElement("option");
          el.textContent = "All";
          el.value = "";
          el.selected = true;
          ddl.appendChild(el);
          for(var i = 0; i < data.length; i++) {
            var opt = data[i].name;
            var el = document.createElement("option");
            el.textContent = opt;
            el.value = opt;
            ddl.appendChild(el);
          }
      },
      "json"
    );
    fillPublicationType();
    fillStartYear();
    fillEndYear();
    getChart();
}

/*function fillResearcherAutocomplete(){
    var school = document.getElementById("school").value;
    $.get("/Analyzerch/getDataService", 
      {
        "filter":"researcher",
        "school":school
      },
      function(data) {
          names=[];
          for(var i=0; i<data.length; i++){
              names.push(data[i].name);
          }
          names.push("All");
          $('#researcherName').tagautocomplete({
                  source: names
          });
          $('#researcherName').first().focus();
      },
      "json"
    );
}*/