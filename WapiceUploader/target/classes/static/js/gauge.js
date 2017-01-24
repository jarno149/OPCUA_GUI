function Gauge(parameters)
{
    this.init();
    this.parseParameters(parameters);
    
    this.container = document.getElementById(this.containerId);
    this.canvas = document.createElement("canvas");
    this.container.appendChild(this.canvas);
    this.context = this.canvas.getContext("2d");
    this.canvas.height = this.container.offsetHeight;
    this.canvas.width = this.container.offsetWidth;
    
    // Init dimensions
    this.width = this.canvas.width;
    this.height = this.canvas.height;
    
    if(this.width > this.height)
        this.radius = this.height / 2;
    else
        this.radius = this.width / 2;
    
    window.addEventListener("resize", this.reDraw.bind(this));
   // document.body.onresize += this.reDraw.bind(this);
}

Gauge.prototype.reDraw = function()
{
    this.canvas.width =  this.container.offsetWidth;
    this.canvas.height = this.container.offsetHeight;
    this.width = this.canvas.width;
    this.height = this.canvas.height;
    
    if(this.width > this.height)
        this.radius = this.height / 2;
    else
        this.radius = this.width / 2;
    
    this.drawBackground();
    this.drawText(this.currentValue);
    this.drawProgress();
    
    console.log("redrawing");
}

Gauge.prototype.parseParameters = function(params)
{
    if(params.background != undefined)
        this.background = params.background;
    if(params.color != undefined)
        this.color = params.color;
    if(params.thickness != undefined)
        this.thickness = params.thickness;
    if(params.containerId != undefined)
        this.containerId = params.containerId;
    if(params.suffix != undefined)
        this.suffix = params.suffix;
    if(params.maxValue != undefined)
        this.maxValue = params.maxValue;
    if(params.minValue != undefined)
    {
        this.minValue = params.minValue;
        this.currentValue = this.minValue;
    }
    if(params.useDecimals != undefined)
        this.useDecimals = params.useDecimals;
    else
        this.useDecimals = true;
}

Gauge.prototype.init = function()
{
    this.background = "#222";
    this.color = "green";
    this.thickness = 30;
    this.suffix = "";
    this.maxValue = 100;
    this.minValue = 0;
    
    this.newValue = 0;
    this.currentValue = 0;
}


Gauge.prototype.drawText = function(data)
{
    // Draw text
    var fontSize = this.radius / 4;
    this.context.fillStyle = this.color;
    this.context.font = fontSize + "px monospace";
    var text = data;
    if(data % 1 == 0)
        text += ".0";
    text += this.suffix;
    var textWidth = this.context.measureText(text).width;
    this.context.fillText(text, this.width / 2 - textWidth/2, this.height / 2 + (fontSize / 4));
    this.context.stroke();
}

Gauge.prototype.drawBackground = function()
{
    // Draw background
    this.context.beginPath();
    this.context.strokeStyle = this.background;
    this.context.lineWidth = this.radius * (this.thickness / 100);
    var rads = Math.PI*2;
    this.context.arc(this.width/2,this.height/2,this.radius-this.radius * (this.thickness / 100),rads*0.35, rads*0.15);
    this.context.stroke();
}

Gauge.prototype.drawProgress = function()
{
    if(this.currentValue < this.minValue)
        return;
    
    this.context.beginPath();
    this.context.strokeStyle = this.color;
    this.context.lineWidth = this.radius * (this.thickness / 100);
    var rads = Math.PI/180;
    var step;
    if(this.minValue < 0)
        step = rads * (360 - 70) / (Math.abs(this.maxValue) + Math.abs(this.minValue));
    else
        step = rads * (360 - 70) / (Math.abs(this.maxValue) - Math.abs(this.minValue)); 
    
    var value = this.currentValue;
    if(this.minValue < 0)
        value += Math.abs(this.minValue);
    else
        value -= Math.abs(this.minValue);
    
    this.context.arc(this.width/2,this.height/2,this.radius-this.radius * (this.thickness / 100),rads*125, rads*125 + step * value);
    this.context.stroke();
}

Gauge.prototype.update = function(value)
{
    if(this.animLoop != undefined)
        clearInterval(this.animLoop);
    
    this.newValue = value;
    var diff = Math.abs(this.newValue - this.currentValue);
    this.animLoop = setInterval(this.animateTo.bind(this), 200/diff);
}

Gauge.prototype.animateTo = function()
{
    if(this.currentValue == this.newValue)
        clearInterval(this.animLoop);
    
    if(this.currentValue < this.newValue)
        this.currentValue = Math.round((this.currentValue+0.1) * 10) / 10;
    else if(this.currentValue > this.newValue)
        this.currentValue = Math.round((this.currentValue-0.1) * 10) / 10;
    this.init2();
}

Gauge.prototype.init2 = function()
{
    this.context.clearRect(0,0, this.width, this.height);
    this.drawBackground();
    this.drawText(this.currentValue);
    this.drawProgress();
}