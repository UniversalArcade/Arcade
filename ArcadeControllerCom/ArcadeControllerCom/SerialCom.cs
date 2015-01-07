using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO.Ports;

namespace ArcadeControllerCom
{
    class SerialCom
    {
        public event EventHandler<MessageEventArgs> sendMessage;

        // Volatile is used as hint to the compiler that this data
        // member will be accessed by multiple threads.
        private volatile bool _shouldStop;
        private SerialPort currentPort;

        public bool isPortOpen { get; set; }

        public SerialCom(){}

        protected virtual void OnSendMessage(MessageEventArgs e)
        {
            sendMessage(this, e);
        }

        
        private void SendMessageToForm(string message)
        {
            this.SendMessageToForm(message, false);
        }
        
        private void SendMessageToForm(string message, bool toPipe) 
        {
            MessageEventArgs mArgs = new MessageEventArgs();
            mArgs.Message = message;
            if(toPipe)
            {
                mArgs.sendToPipe = true;
            }
            OnSendMessage(mArgs);  
        }


        public void DoWork()
        {
            if(findSerialPort())
            {
                while (!_shouldStop)
                {
                    Thread.Sleep(1);
                }
                currentPort.Close();
                //SendMessageToForm("Thread Terminated");
            }
            else 
            {
                SendMessageToForm("Error: konnte Controller nicht finden");
            }
        }

        private bool findSerialPort() 
        {
            SendMessageToForm("Versuche Verbindung mit Controller herzustellen");
            string[] ports = SerialPort.GetPortNames();
            if(ports.Length > 0)
            {
                foreach(string port in ports)
                {
                    try
                    {
                        currentPort = new SerialPort(port, 115200);
                        currentPort.Open();
                        currentPort.WriteLine("hHS:1");
                        SendMessageToForm("Versuche Port: " + port);
                        currentPort.ReadTimeout = 2000;
                        try
                        {
                            string read = currentPort.ReadLine();
                            if (read != null && read.Length > 0)
                            {
                                read = read.Trim();
                                read = read.Replace("\n", "");
                                string[] split = read.Split(':');
                                if (split.Length == 2)
                                {
                                    if (split[0].Equals("HS") && split[1].Equals("2"))
                                    {
                                        initComPort();
                                        SendMessageToForm("Handshake mit Controller an Port " + port + " erfolgreich");
                                        return true;
                                    }
                                }
                                //break;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        catch (System.TimeoutException ex)
                        {
                            SendMessageToForm("Handshake mit Controller an Port " + port + " fehlgeschlagen: Zeitüberschreitung");
                            continue;
                        }
                    }
                    catch (System.UnauthorizedAccessException)
                    {

                        continue;
                    }

                }
                SendMessageToForm("Handshake mit Controller fehlgeschlagen");
            }
            else 
            {
                SendMessageToForm("Keine Serial Ports gefunden");
                return false;
            }

            return false;
        }

        private void initComPort()
        {
            currentPort.DataReceived += currentPort_DataReceived;
            isPortOpen = true;
        }

        public void sendSerialMessage(string msg)
        {
            if(currentPort.IsOpen && isPortOpen)
            {
                currentPort.WriteLine(msg);
                SendMessageToForm("SENDING: " + msg);
            }
        }

        void currentPort_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            if(currentPort.BytesToRead > 0)
            {
                string read = currentPort.ReadLine();
                currentPort.DiscardInBuffer();
                SendMessageToForm("RECEIVING: " + read);
                processIncomingSerialData(read);
                
            }
        }

        private void processIncomingSerialData(string data)
        {
            if(data != null && data.Length > 0)
            {
                //trim whitespaces
                data = data.Trim();
                //gather command
                string[] command = data.Split(':');
                

                switch (command[0])
                {
                    case "spFunc":
                        processSpecialFunc(command[1]);    

                        break;
                    case "bcSET":
                        processReturnedButtonConfig(command[1]);
                        break;
                    default:
                        SendMessageToForm("Error:");
                        break;
                }

                //Console.WriteLine("Command: " + command[0]);
                //Console.WriteLine("Body: " + command[1]);
            }
        }

        private void processSpecialFunc(string command)
        {
           if(command != null && command.Length > 0)
           {
               switch (command)
               {
                   case "0":
                       SendMessageToForm("Execute Overlay");
                       SendMessageToForm("spFunc:0", true);
                       break;
                   case "1":
                       SendMessageToForm("Cancel Game");
                       SendMessageToForm("spFunc:1",true);
                       break;
                   default:
                       SendMessageToForm("Error: SpecialFunc (" + command + ") nicht gefunden");
                       break;
               }
           }
        }

        private void processReturnedButtonConfig(string command)
        {
            //SendMessageToForm(command);
            // Process...
        }

        public void RequestStop()
        {
            _shouldStop = true;
        }
    }
}
