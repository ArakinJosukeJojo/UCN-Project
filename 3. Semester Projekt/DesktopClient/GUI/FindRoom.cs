using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DesktopClient.BusinessLogicLayer;
using DesktopClient.ModelLayer;

namespace DesktopClient.GUI
{
    public partial class FindRoom : Form
    {
        RoomLogic roomLogic = new RoomLogic();
        RoomForm _form2;
        public FindRoom(RoomForm form2)
        {
            _form2 = form2;
            InitializeComponent();
            button1.Click += new EventHandler(this.button1_Click);
        }
        private void button1_Click(object sender, EventArgs e)
        {
            string roomNo = textBox1.Text;
            if (!int.TryParse(roomNo, out int output))
            {
                MessageBox.Show("Du har ikke indtastet et tal!");
                return;
            }
            try
            {
                Room room = roomLogic.GetRoom(roomNo);
                _form2.FindRoom(room);
                this.Dispose();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Database fejl, tastede du det rigtie RoomNumber");
            }
        
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
