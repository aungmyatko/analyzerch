/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function enterVisualization(){
    $.get("/Analyzerch/start", 
      function(data) {
          if(data.status=="success"){
              window.location.replace("index.jsp");
          }
      },
      "json"
    );
}
