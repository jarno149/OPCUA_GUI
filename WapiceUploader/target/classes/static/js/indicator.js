function Indicator(parameters)
{
    this.init();
    this.parseParameters(parameters);
    
    this.container = document.getElementById(this.containerId);
    this.canvas = document.createElement("canvas");
    this.container.appendChild(this.canvas);
    this.context = this.canvas.getContext("2d");
    this.canvas.height = this.container.offsetHeight;
    this.canvas.width = this.container.offsetWidth;
    
    // Add eventListener
    window.addEventListener("resize", this.reDraw.bind(this));
    
    this.reDraw();
}

Indicator.prototype.parseParameters = function(parameters)
{
    if(parameters.defaultColor != undefined)
        this.defaultColor = parameters.defaultColor;
    
    this.containerId = parameters.containerId;
}

Indicator.prototype.init = function()
{
    this.defaultColor = "#a8a8a8";
    this.currentColor = this.defaultColor;
}

Indicator.prototype.reDraw = function()
{
    this.context.beginPath();
    this.canvas.width = this.container.offsetWidth;
    this.canvas.height = this.container.offsetHeight;
    this.width = this.canvas.width;
    this.height = this.canvas.height;
    
    if(this.width > this.height)
        this.radius = this.height / 2;
    else this.radius = this.width / 2;
    this.radius = this.radius * 0.8;
    
    this.context.fillStyle = this.currentColor;
    this.context.arc(this.width / 2, this.height / 2, this.radius, 0, Math.PI * 2);
    this.context.fill();
}

Indicator.prototype.draw = function(color)
{
    this.currentColor = color;
    this.reDraw();
}