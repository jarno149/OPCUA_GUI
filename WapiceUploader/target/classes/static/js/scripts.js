window.onload = function()
{
    google.charts.load("current", {packages:['corechart']});
    google.charts.setOnLoadCallback(createAndGetBarChart);
}

function createAndGetBarChart(containerElement)
{
    var data = google.visualization.arrayToDataTable(
    [
        ["Element", "Density", {role: "style"}],
        ["Copper", 8.4, "red"],
        ["Silver", 11, "green"]
    ]);
    
    var view = new google.visualization.DataView(data);
    
    var opts = {
        title: "TestiTesti"
    };
    
    var chart = new google.visualization.ColumnChart(containerElement);
    chart.draw(view, opts);
    return chart;
}