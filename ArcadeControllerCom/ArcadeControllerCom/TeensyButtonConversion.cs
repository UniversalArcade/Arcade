using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ArcadeControllerCom
{
    class TeensyButtonConversion
    {
        Hashtable buttonTable;

        public TeensyButtonConversion()
        {
            buttonTable = new Hashtable();
            init();    
        }

        private void init() 
        {
            string line;
            List<string> liste = new List<string>();


            // Read the file and display it line by line.
            System.IO.StreamReader file = new System.IO.StreamReader(@"C:\Users\Public\Arcade\TeensyKeyCodes.txt");
            while ((line = file.ReadLine()) != null)
            {
                if(!string.IsNullOrEmpty(line))
                {
                    line = line.Trim();
                    string[] parts = line.Split(':');
                    buttonTable.Add(parts[0], parts[1]);
                }
            }
            file.Close();
        }


        public string getKeyCodeFromString(string input)
        {
            if (buttonTable.Contains(input)) 
            {
                return (string)buttonTable[input];
            }
            return "";
        }
    }
}
