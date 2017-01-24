// 
//
//     Jarno Rajala 21.01.2017
//
//
//

function BarChart(containerId, bars)
{
    this.itemCount = bars.length;
    this.currentValues = new Array(this.itemCount);
    this.currentValues.fill(0);
    this.barValues = bars;
    
    this.container = document.getElementById(containerId);
    this.canvas = document.createElement("canvas");
    this.width = this.container.offsetWidth;
    this.height = this.container.offsetHeight;
    this.canvas.height = this.height;
    this.canvas.width = this.width;
    this.context = this.canvas.getContext("2d");
    this.container.appendChild(this.canvas);
    
    // TESTING, CREATE VALUES
    this.background = "#222";
    this.spacing = 10;
    this.maxValue = 100;
    this.minValue = 0;
    
    this.drawBackground();
    
    window.addEventListener("resize", this.reDraw.bind(this));
}

BarChart.prototype.reDraw = function()
{
    this.canvas.width = this.container.offsetWidth;
    this.canvas.height = this.container.offsetHeight;
    this.width = this.canvas.width;
    this.height = this.canvas.height;
    
    this.drawBackground();
    this.drawProgress();
}

BarChart.prototype.drawBackground = function()
{
    var barWidth = (this.width - ((this.itemCount - 1) * this.spacing)) / this.itemCount;
    
    for(var i = 0; i < this.itemCount; i++)
    {
        var x;
        if(i == 0)
            x = barWidth / 2;
        else
            x = barWidth * (i + 0.5) + (this.spacing * i);
        
        this.context.beginPath();
        this.context.strokeStyle = this.background;
        this.context.lineWidth = barWidth
        this.context.moveTo(x, this.height);
        this.context.lineTo(x, 0);
        this.context.stroke();
    }
}

BarChart.prototype.drawProgress = function()
{
    var barWidth = (this.width - ((this.itemCount - 1) * this.spacing)) / this.itemCount;
    var step = this.height / this.maxValue;
    
    for(var i = 0; i < this.itemCount; i++)
    {
        var x;
        if(i == 0)
            x = barWidth / 2;
        else
            x = barWidth * (i + 0.5) + (this.spacing * i);
        
        this.context.beginPath();
        this.context.strokeStyle = this.barValues[i].color != undefined ? this.barValues[i].color : "green";
        this.context.lineWidth = barWidth;
        this.context.moveTo(x, this.height);
        this.context.lineTo(x, this.height - this.currentValues[i] * step);
        this.context.stroke();
    }
}

BarChart.prototype.update = function(values)
{
    if(this.animLoop != undefined)
        clearInterval(this.animLoop);
    
    this.newValues = values;
    var diff = this.getBiggestDiff();
    this.animLoop = setInterval(this.animateTo.bind(this), 2000/ diff);
}

BarChart.prototype.getBiggestDiff = function()
{
    var diff = 1;
    for(var i = 0; i < this.itemCount; i++)
    {
        var thisDiff = Math.abs(this.newValues[i] - this.currentValues[i]);
        if(thisDiff > diff)
            diff = thisDiff;
    }
    return diff;
}

BarChart.prototype.animateTo = function()
{
    var clear = true;
    for(var i = 0; i < this.itemCount; i++)
    {
        if(this.currentValues[i] != this.newValues[i])
            clear = false;
    }
    
    if(clear)
        clearInterval(this.animLoop);
    
    for(var i = 0; i < this.itemCount; i++)
    {
        if(this.currentValues[i] < this.newValues[i])
            this.currentValues[i]++;
        else if(this.currentValues[i] > this.newValues[i])
            this.currentValues[i]--;
    }
    this.context.clearRect(0,0,this.width, this.height);
    this.drawBackground();
    this.drawProgress();
}