/*<![CDATA[*/


var ws;
var highBayRackGauge;
var pcbTrayGauge;
var robotIsWorkingIndicator;

window.onload = function ()
{
    document.getElementById("overlay").style.height = "100%";
    document.getElementById("overlay").style.opacity = 1;
    setTimeout(hideOverlay, 500);
    initWebsocket();
    
    updateHighBayRackGauge();
    updatePcbTrayGauge();
    updateRobotIsWorking();
}

function initWebsocket()
{
    var url = window.location + "ws";
    ws = new WebSocket(url.replace('http', 'ws'));
    ws.onopen = function ()
    {
        console.log("WebSocket connected!")
    }
    ws.onmessage = incomingWebsocketMessage;
}

function incomingWebsocketMessage(m)
{
    var parsedJson = JSON.parse(m.data);
    // Check if object is array
    if (parsedJson instanceof Array)
    {
        for (var i = 0; i < parsedJson.length; i++)
        {
            var obj = parsedJson[i];
            if (obj.bufPos != undefined && obj.pNo != undefined)
            {
                addHighbayRackItem(obj);
            } else if (obj.boxPos != undefined && obj.oNo != undefined)
            {
                addPcbTrayItem(obj);
            } else if (obj.oNo != undefined)
            {
                addManufacturingItem(obj, i);
            }
        }
    } else
    {
        if (parsedJson.identifier != undefined)
        {
            updateRobotIsWorking(parsedJson.value);
        } else if (parsedJson.bufPos != undefined && parsedJson.pNo != undefined)
        {
            addHighbayRackItem(parsedJson);
        } else if (parsedJson.bufPos != undefined && parsedJson.oNo != undefined)
        {
            addPcbTrayItem(parsedJson);
        } else if (obj.oNo != undefined)
        {
            addManufacturingItem(parsedJson, 1);
        }
    }

    updateHighBayRackItemsCount();
    updatePcbTrayItemsCount();
}

function hideOverlay()
{
    document.getElementById("overlay").style.opacity = 0;
    document.getElementById("overlay").style.height = "0%";
}

function addManufacturingItem(item, index)
{
    // Clear table if item index is 0
    if(index < 1)
    {
        document.getElementById("manuFacturingOrderTable").innerHTML = "";
    }
    var existingItem = document.getElementById("ManufacturingItem" + item.oNo);
    if (existingItem != undefined)
    {
        existingItem.innerHTML = '<td class="oNo">' + item.oNo + '</td><td class="pNo">'
                + item.pNo + '</td><td class="state">' + item.state + '</td><td>' + item.description + '</td>';
    } else
    {
        var container = document.getElementById("manuFacturingOrderTable");
        container.innerHTML += '<tr id="ManufacturingItem' + item.oNo + '"><td class="oNo">' + item.oNo + '</td><td class="pNo">' + item.pNo
                + '</td><td class="state">' + item.state + '</td><td>' + item.description + '</td></tr>';
    }
}

function addHighbayRackItem(item)
{
    var existingItem = document.getElementById("HighBayRackItem" + item.bufPos);
    if (existingItem != undefined)
    {
        existingItem.innerHTML = '<td class="bufPos">' + item.bufPos + '</td><td class="pNo">'
                + item.pNo + '</td><td>' + item.description + '</td>';
    } else
    {
        var container = document.getElementById("highBayRackTable");
        container.innerHTML += '<tr id="HighBayRackItem' + item.bufPos + '"><td class="bufPos">' + item.bufPos + '</td><td class="pNo">' + item.pNo
                + '</td><td>' + item.description + '</td></tr>';
    }
}

function addPcbTrayItem(item)
{
    var existingItem = document.getElementById("PcbTrayItem" + item.boxPos);
    if (existingItem != undefined)
    {
        existingItem.innerHTML = '<td class="boxPos">' + item.boxPos + '</td><td class="pNo">'
                + item.pNo + '</td><td>' + item.description + '</td>';
    } else
    {
        var container = document.getElementById("pcbTrayTable");
        container.innerHTML += '<tr id="PcbTrayItem' + item.boxPos + '"><td class="boxPos">' + item.boxPos + '</td><td class="pNo">'
                + item.pNo + '</td><td>' + item.description + '</td></tr>';
    }
}

function updateHighBayRackItemsCount()
{
    var highBayRackTable = document.getElementById("highBayRackTable");
    var counter = 0;
    for (var i = 0; i < highBayRackTable.rows.length; i++)
    {
        var highBayRackItem = highBayRackTable.rows[i];
        if (highBayRackItem.cells[2].innerHTML != "nothing")
        {
            counter++;
        }
    }
    updateHighBayRackGauge(counter);
}

function updatePcbTrayItemsCount()
{
    var pcbTrayTable = document.getElementById("pcbTrayTable");
    var counter = 0;
    for (var i = 0; i < pcbTrayTable.rows.length; i++)
    {
        var pcbTrayItem = pcbTrayTable.rows[i];
        if (pcbTrayItem.cells[2].innerHTML != "nothing")
        {
            counter++;
        }
    }
    updatePcbTrayGauge(counter);
}

function updateRobotIsWorking(status)
{
    if (robotIsWorkingIndicator == undefined)
    {
        robotIsWorkingIndicator = new Indicator({containerId: "robotIsWorkingIndicator"});
    } else
    {
        if (status == "false")
            robotIsWorkingIndicator.draw("green");
        else
            robotIsWorkingIndicator.draw("red");
    }
}

function updateHighBayRackGauge(newValue)
{
    if (highBayRackGauge == undefined)
    {
        highBayRackGauge = new Gauge({containerId: "HighBayRackGauge", minValue: 0, maxValue: 32, color: "#8bfc83", useDecimals: false, suffix: "/32"});
    }
    highBayRackGauge.update(newValue);
}

function updatePcbTrayGauge(newValue)
{
    if (pcbTrayGauge == undefined)
    {
        pcbTrayGauge = new Gauge({containerId: "PcbTrayGauge", minValue: 0, maxValue: 12, color: "#ffcb5b", useDecimals: false, suffix: "/12"});
    }
    pcbTrayGauge.update(newValue);
}

/*]]>*/