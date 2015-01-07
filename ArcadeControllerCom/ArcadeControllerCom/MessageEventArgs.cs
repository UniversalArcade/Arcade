using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ArcadeControllerCom
{
    class MessageEventArgs : EventArgs
    {
        public bool sendToPipe { get; set; }
        public string Message { get; set; }
    }
}
