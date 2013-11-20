/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showTrend(){

var div = d3.select("#networkChart");
div.select("svg").remove();
$("#loadingGif").show();

if (window.innerWidth && window.innerHeight) {
     var w = 1000;
     var h = 500;
}
var margin = {top: 20, right: 20, bottom: 110, left: 60},
width = w - margin.left - margin.right,
height = h - margin.top - margin.bottom;

var format = d3.time.format("%Y");
var start = new Date(2000,0,1);
var end = new Date(2013,0,1);
var range = d3.time.years(start,end);


var x = d3.time.scale()
    .range([0,width-200])
    .domain([start,end]);

var y = d3.scale.linear()
    .range([height, 0]);

var color = function(i) {
  return d3.hcl(55*i,55,38).toString();
};

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .ticks(10);

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(10);

var line = d3.svg.line()
    .interpolate("basis")
    .defined(function(d) { return d != null; })
    .x(function(d,i) { return x(range[i]); })
    .y(function(d) { return y(d); });

var svg = d3.select("#networkChart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.csv("/Analyzerch/getProfessorTrend?name=all", function(raw) {
    
  $("#loadingGif").hide();
  var data = [];
  var goods = _(raw).pluck("Name");
  
  _(raw).each(function(series) {
    var value = {};
    data.push({
      id: series["Series ID"],
      name: series["Name"],
      values: _(range).map(function(month) {
                return parseFloat(series[format(month)]) || null;
              })
    });
  });

  var values = _(data).chain().pluck('values').flatten().value();
  
  y.domain([
    0,
    d3.max(values)
  ]);

   
  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .style("font-weight", "bold")
      .text("Number of Publications for each");

  var series = svg.selectAll(".series")
      .data(data)
    .enter().append("g")
      .attr("class", "series");

  series.append("path")
      .attr("class", "line")
      .attr("d", function(d) { return line(d.values); })
      .style("stroke", function(d,i) { return color(i); });

  series.append("path")
      .attr("class", "invisible hover")
      .attr("d", function(d) { return line(d.values); });

  var labels = data.map(function(d) { return {name: d.name, y: y(d.values[d.values.length - 1])}});

  series.append("text")
      .attr("class", "label hover")
      .data(labels)
      .attr("transform", function(d) { return "translate(" + x(end) + "," + d.y + ")"; })
      .attr("x", 3)
      .attr("dy", ".35em")
      .style("fill", function(d,i) { return color(i); })
      .text(function(d) { return d.name; });

  // constraint relaxation on labels
  var alpha = 1.5;
  var spacing = 12;
  function relax() {
    var again = false;
    labels.forEach(function(a,i) {
      labels.slice(i+1).forEach(function(b) {
        var dy = a.y - b.y;
        if (Math.abs(dy) < spacing) {
          again = true;
          var sign = dy > 0 ? 1 : -1;
          a.y += sign*alpha;
          b.y -= sign*alpha;
        }
      });
    });

    /* uncomment to see each step */
    // d3.selectAll(".label")
    //    .attr("transform", function(d) { return "translate(" + x(end) + "," + d.y + ")"; });

    if (!again) {
      // all done
      d3.selectAll(".label")
        .transition().duration(800)
        .attr("transform", function(d) { return "translate(" + x(end) + "," + d.y + ")"; });
      return true;
    }
    return false;
  };

  d3.timer(relax);

  series.selectAll(".hover")
      .on("mouseover", function(d,i) {
        d3.selectAll(".line")
          .style("opacity", 0.12)
          .filter(function(p) { return p.name == d.name; })
          .style("opacity", 1)
          .style("stroke-width", 2.5);
        d3.selectAll(".series text")
          .style("font-weight", function(p,i) { return p.name == d.name ? "bold" : null; })
          .style("fill", function(p,i) { return p.name == d.name ? color(i) : "#aaa"; });
      })
      .on("mouseout", function(d,i) {
        d3.selectAll(".line")
          .style("opacity", 1)
          .style("stroke-width", null);
        d3.selectAll(".series text")
          .style("font-weight", null)
          .style("fill", function(d,i) { return color(i); });
      });
});
 }

