using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DesktopClient.GUI
{
    public partial class MainClient : Form
    {
        public MainClient()
        {
            InitializeComponent();
            button3.Click += new EventHandler(this.Reservation);
            button1.Click += new EventHandler(this.button1_Click);

        }

        private void Reservation(object sender, EventArgs e)
        {
            var form1 = new ManageReservation();
            form1.Show();
            Hide();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var form2 = new RoomForm();
            form2.Show();
            Hide();
        }
    }
}
