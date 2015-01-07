using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace ArcadeControllerCom
{
    public partial class Form1 : Form
    {

        private SerialCom serialCom;
        private InPipe inPipe;
        private OutPipe outPipe;
        public Form1()
        {
            InitializeComponent();
            this.ShowInTaskbar = false;

            outPipe = new OutPipe();

            serialCom = new SerialCom();
            serialCom.sendMessage += new EventHandler<MessageEventArgs>(OnMessageReceived);

            inPipe = new InPipe();
            inPipe.sendMessage += new EventHandler<MessageEventArgs>(OnMessageFromPipe);

            //init();
            //fileIOtest();
        }

        private void OnMessageFromPipe(object sender, MessageEventArgs e)
        {
            if(e != null && e.Message != null && e.Message.Length > 0)
            {
                serialCom.sendSerialMessage(e.Message);
            }
        }

        private void OnMessageReceived(object sender, MessageEventArgs e)
        {
            if(e != null && e.Message != null && e.Message.Length > 0)
            { 
                if(!e.sendToPipe)
                {
                    AddToListBox(e.Message);
                }
                else
                {
                    outPipe.setMessageToSend(e.Message);
                }
                
            }
            
        }

        private void AddToListBox(string oo)
        {
            Invoke(new MethodInvoker( delegate { listBox1.Items.Add(oo); } ));
        }

        

        private void init()
        {
            Thread serialWorkerThread = new Thread(serialCom.DoWork);
            serialWorkerThread.Start();
            
            //wait for Thread to become alive
            while (!serialWorkerThread.IsAlive) ;


            Thread pipeServerThread = new Thread(inPipe.DoWork);
            pipeServerThread.Start();


            while (!pipeServerThread.IsAlive) ;


            Thread outPipeThread = new Thread(outPipe.DoWork);
            outPipeThread.Start();
            while (!outPipeThread.IsAlive) ;
            


            //Stopwatch SW_OpenPort = new Stopwatch();
            //SW_OpenPort.Start();
            //while (!serialCom.isPortOpen)
            //{
            //    if (SW_OpenPort.ElapsedMilliseconds >2000)
            //    {
            //        listBox1.Items.Add("Zeit für Connection mit Port überschritten");
            //        break;
            //    }
            //}
            //SW_OpenPort.Stop();
            //SW_OpenPort = null;


            //Console.WriteLine("MAINTHREAD GO");

            
            //serialCom.sendMessage("16392,16392,16392,16392,16392,16392,16392,16392,16392,16392,");
            
            
            
            


            /*
            string[] ports = SerialPort.GetPortNames();
            foreach (string port in ports)
            {
                listBox1.Items.Add(port);
            }

            string msg = "hal}";
            SerialPort currentPort = new SerialPort("COM1", 9600);
            currentPort.Open();
            currentPort.WriteLine(msg);
            Thread.Sleep(100);
            Console.WriteLine("BytesToRead: " + currentPort.BytesToRead);
            string read = currentPort.ReadLine();
            listBox1.Items.Add(read);

            listBox1.Items.Add("DONE");
            string read2 = currentPort.ReadLine();
            listBox1.Items.Add("read2:" + read2);
            currentPort.Close();
            
            */
        }


        private void Form1_Load(object sender, EventArgs e)
        {
            init();
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            //MessageBox.Show("HALLO");
            //this.Visible = true;
            //this.Show();
            this.WindowState = FormWindowState.Normal;

        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            serialCom.RequestStop();
            inPipe.RequestStop();
            outPipe.RequestStop();
        }

        //private void fileIOtest()
        //{
        //    int counter = 0;
        //    string line;
        //    List<string> liste = new List<string>();


        //    // Read the file and display it line by line.
        //    System.IO.StreamReader file = new System.IO.StreamReader(@"C:\Users\Public\Arcade\KeyboardLayoutRAW.txt");
        //    while ((line = file.ReadLine()) != null)
        //    {
        //        line = line.Trim();
        //        string key = line.Replace("KEY_", "");
        //        liste.Add("Serial.print(\"" + key + ":\");");
        //        liste.Add("Serial.println(" + line + ");");
        //        System.Console.WriteLine(line);

        //    }
        //    file.Close();

        //    // Suspend the screen.

        //    System.IO.File.WriteAllLines(@"C:\Users\Public\Arcade\KeyboardLayoutProcessed.txt", liste);

        //}
    }
}
