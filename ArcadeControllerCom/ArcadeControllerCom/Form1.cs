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

            this.SizeChanged += new EventHandler(form1_sizeEventHandler);
            this.Enabled = false;

            init();

        }

        private void form1_sizeEventHandler(object sender, EventArgs e)
        {
            if (WindowState == FormWindowState.Minimized)
            {
                this.Enabled = false;
                this.Visible = false;
            }
        }

        protected override void SetVisibleCore(bool value)
        {
            if (!this.IsHandleCreated)
            {
                CreateHandle();
                value = false;
            }
            base.SetVisibleCore(value);
        }

        protected override bool ShowWithoutActivation
        {
            get { return true; } // prevents form creation from stealing focus
        }

        private void OnMessageFromPipe(object sender, MessageEventArgs e)
        {
            if(e != null && e.Message != null && e.Message.Length > 0)
            {
                try
                {
                    serialCom.sendSerialMessage(e.Message);
                }
                catch (Exception) 
                {
                    outPipe.setMessageToSend("Conn:0");
                }
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
            Thread pipeServerThread = new Thread(inPipe.DoWork);
            pipeServerThread.Start();


            while (!pipeServerThread.IsAlive) ;


            Thread outPipeThread = new Thread(outPipe.DoWork);
            outPipeThread.Start();
            while (!outPipeThread.IsAlive) ;

            Thread serialWorkerThread = new Thread(serialCom.DoWork);
            serialWorkerThread.Start();

            //wait for Thread to become alive
            while (!serialWorkerThread.IsAlive) ;
        }


        private void Form1_Load(object sender, EventArgs e)
        {
            //this.WindowState = FormWindowState.Minimized;
           
            //init();
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.Enabled = true;
            this.Visible = true;
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
            Environment.Exit(0);
        }
    }
}
