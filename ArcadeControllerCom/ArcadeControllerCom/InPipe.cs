using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Pipes;
using System.Linq;
using System.Text;
using System.Threading;

namespace ArcadeControllerCom
{
    class InPipe
    {

        private volatile bool _shouldStop;
        TeensyButtonConversion buttonConversion;
        public event EventHandler<MessageEventArgs> sendMessage;
        private readonly object syncLock = new object();
        private string messageToSend;

        public InPipe() 
        {
            messageToSend = null;
            buttonConversion = new TeensyButtonConversion();
        }

        protected virtual void OnSendMessage(MessageEventArgs e)
        {
            sendMessage(this, e);
        }


        private string getMessageToSend()
        {
            lock(syncLock)
            {
                string tmp = messageToSend;
                messageToSend = null;
                return tmp;
            }
        }

        public void setMessageToSend(string input)
        {
            lock(syncLock)
            {
                if(!string.IsNullOrEmpty(input))
                {
                    messageToSend = input;
                }
            }
        }

        public void DoWork()
        {
            while(!_shouldStop)
            {
                Thread.Sleep(10);
                NamedPipeServerStream inPipe =
                new NamedPipeServerStream("javaOUTpipe", PipeDirection.InOut, 4);
              

                inPipe.WaitForConnection();

                try
                {
                    StreamReader sr = new StreamReader(inPipe);

                    string inputFromPipe = sr.ReadLine();
                    processInput(inputFromPipe);

                    inPipe.Disconnect();
                }
                catch (IOException e)
                {

                }
                finally {
                    inPipe.Close();
                }
                
            }

        }

        private void processInput(string input)
        {
            if(!string.IsNullOrEmpty(input))
            {
                input = input.Trim();
                string[] split = input.Split(':');
                if(split.Length > 1)
                {
                    if(!string.IsNullOrEmpty(split[0]))
                    {
                        switch (split[0])
                        {
                            case "btSET":
                                processButtonSet(split[1]);
                                   
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        private bool processButtonSet(string input) 
        {
            if (!string.IsNullOrEmpty(input))
            {
                input.Replace("\\n", "");
                string[] parts = input.Split(',');


                string concatCommand = "b";

                foreach(string button in parts)
                {
                    if(!string.IsNullOrEmpty(button))
                    {
                        string converted = buttonConversion.getKeyCodeFromString(button);

                        if(!string.IsNullOrEmpty(converted))
                        {
                            concatCommand += converted + ",";
                        }
                    }
                }

                if (concatCommand.Length > 1) 
                {
                    MessageEventArgs mArgs = new MessageEventArgs();
                    mArgs.Message = concatCommand;
                    OnSendMessage(mArgs);
                }
            }
            return true;
        }

        public void RequestStop()
        {
            _shouldStop = true;

        }

    }
}
