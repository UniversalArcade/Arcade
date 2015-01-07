using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Pipes;
using System.Linq;
using System.Text;
using System.Threading;

namespace ArcadeControllerCom
{
    class OutPipe
    {

        private volatile bool _shouldStop;
        private readonly object syncLock = new object();
        private string messageToSend;

        public OutPipe()
        {
            messageToSend = null;
        }


        private string getMessageToSend()
        {
            lock (syncLock)
            {
                string tmp = messageToSend;
                messageToSend = null;
                return tmp;
            }
        }

        public void setMessageToSend(string input)
        {
            lock (syncLock)
            {
                if (!string.IsNullOrEmpty(input))
                {
                    messageToSend = input;
                }
            }
        }


        public void DoWork()
        {
            while (!_shouldStop)
            {
                Thread.Sleep(10);

                NamedPipeServerStream pipeServer =
                new NamedPipeServerStream("javaINpipe", PipeDirection.Out, 4);

                pipeServer.WaitForConnection();
                try
                {
                    StreamWriter sw = new StreamWriter(pipeServer);
                    sw.AutoFlush = true;

                    string message = getMessageToSend();
                    if(!string.IsNullOrEmpty(message))
                    {
                        sw.WriteLine(message);  
                    }
                    else 
                    {
                        sw.WriteLine("");
                    }

                    pipeServer.Disconnect();
                }
                catch (IOException e)
                {

                }
                pipeServer.Close();
            }

        }

        public void RequestStop()
        {
            _shouldStop = true;

        }

    }
}
