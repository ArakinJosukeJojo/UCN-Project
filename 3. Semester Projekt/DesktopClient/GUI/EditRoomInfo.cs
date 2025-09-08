using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DesktopClient.ModelLayer;

namespace DesktopClient.GUI
{
    public partial class EditRoomInfo : Form
    {
        Room _room;
        RoomForm _form;
        public EditRoomInfo(RoomForm form2, Room room)
        {
            _room = room;
            _form = form2;
            InitializeComponent();
            button1.Click += new EventHandler(this.button1_Click);
            textBox1.Text = room.RoomInfo;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            _room.RoomInfo = textBox1.Text;
            _form.EditRoomInfo(_room);
            this.Dispose();
        }

        private void button1_Click_1(object sender, EventArgs e)
        {

        }
    }
}
