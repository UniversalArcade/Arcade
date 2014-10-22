
var offsetx=20;
var offsety=0;

function InfoBoxAusblenden() {
      document.getElementById('InfoBox').style.visibility = "hidden";
}

function InfoBoxAnzeigen(e,Inhalte,offsetX,offsetY)
{
        if (offsetX) {offsetx=offsetX;} else {offsetx=20;}
        if (offsetY) {offsety=offsetY;} else {offsety=0;}
        var PositionX = 0;
        var PositionY = 0;
        if (!e) var e = window.event;
        if (e.pageX || e.pageY)
        {
                PositionX = e.pageX;
                PositionY = e.pageY;
        }
        else if (e.clientX || e.clientY)
        {
                PositionX = e.clientX + document.body.scrollLeft;
                PositionY = e.clientY + document.body.scrollTop;
        }
        
        document.getElementById("BoxInhalte").innerHTML = Inhalte;
        document.getElementById('InfoBox').style.left = (PositionX+offsetx)+"px";
        document.getElementById('InfoBox').style.top = (PositionY+offsety)+"px";
        document.getElementById('InfoBox').style.visibility = "visible";
}
