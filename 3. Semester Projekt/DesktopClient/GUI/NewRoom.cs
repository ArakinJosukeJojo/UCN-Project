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
    
    public partial class NewRoom : Form
    {
        RoomLogic _roomLogic = new RoomLogic();
        

        public NewRoom()
        {
            InitializeComponent();
            button1.Click += new EventHandler(this.button1_Click);
            button2.Click += new EventHandler(this.button2_Click);
          List<RoomType> roomTypes = _roomLogic.GetRoomTypes();
            comboBox1.DataSource = roomTypes;
            comboBox1.DisplayMember = "roomTypeName";
            
        }
        private void button2_Click(object sender, EventArgs e)

        {
            try
            {
                if (int.TryParse(textBox1.Text, out int output))
                {
                    Room room = new Room();
                    room.RoomNo = textBox1.Text;
                    room.RoomInfo = textBox2.Text;
                    RoomType r = (RoomType)comboBox1.SelectedItem;
                    room.roomTypeId = r.RoomTypeId;
                    _roomLogic.PostRoom(room);
                    MessageBox.Show("Rum Lavet");
                }
                else {
                    MessageBox.Show("Intast Gyldigt RoomNumber");
                }
            } catch (Exception ex) {
                MessageBox.Show("Database Fejl, Mulighvis Genbrugt RoomNumber");
            }
        }
        private void button1_Click(object sender, EventArgs e)
        {
          this.Dispose();
            //Close();
        }
    }
}
