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
    public partial class RoomForm : Form
    {
        Room room;
        RoomLogic _roomLogic = new RoomLogic();
        public RoomForm()
        {
            
            InitializeComponent();
            button4.Hide();
            button5.Hide();
            button6.Hide();
            button7.Hide();
            label1.Hide();
            label2.Hide();
            label3.Hide();
            label4.Hide();
            button1.Click += new EventHandler(this.button1_Click);
            button2.Click += new EventHandler(this.button2_Click);
            button3.Click += new EventHandler(this.button3_Click);
            button4.Click += new EventHandler(this.button4_Click);
            button5.Click += new EventHandler(this.button5_Click);
            button6.Click += new EventHandler(this.button6_Click);
            button7.Click += new EventHandler(this.button7_Click);
        }
        //Find Room Button
        private void button1_Click(object sender, EventArgs e)
        {
          var findRoom = new FindRoom(this);
            findRoom.Show();
        }
        //New Room Button
        private void button2_Click(object sender, EventArgs e)
        {
            var newRoom = new NewRoom();
            newRoom.Show();
           
            
        }
        //back Button
        private void button3_Click(object sender, EventArgs e)
        {
                
            var MainClient = new MainClient();
            MainClient.Show();
            Dispose();
            


        }
        public void FindRoom(Room thisroom)
        {

                label4.Hide();
                button7.Hide();
                button4.Show();
                button5.Show();
                button6.Show();
                label1.Show();
                label2.Show();
                label3.Show();
                label1.Text = thisroom.RoomNo.ToString();
                label2.Text = thisroom.RoomInfo;
            room = thisroom;
            
                if (thisroom.IsOccupied)
                {
                    label3.Text = "Optaget";
                    label4.Show();
                    button7.Show();
                    label4.Text = thisroom.reservationNo.ToString();
                }
                else
                {
                    label3.Text = "ikke Optaget";
                }
            }
            
        // Delete Room Button
        private void button5_Click(object sender, EventArgs e)
        {
            string roomNo = label1.Text;
            try
            {
                _roomLogic.DeleteRoom(roomNo);
                MessageBox.Show("Rum Sletted");
            }
            catch 
            {
                MessageBox.Show("Database Fejl");
            }
        }
        //Edit Room Button
        private void button4_Click(object sender, EventArgs e)
        {
            var EditRoomInfo = new EditRoomInfo(this, room);
            EditRoomInfo.Show();
        }
        public void EditRoomInfo(Room room)
        {
            try
            {
                _roomLogic.EditRoomInfo(room);
                MessageBox.Show("Rum opdateret");
            }
            catch
            {
                MessageBox.Show("Database Fejl");
            }

        }
        private void button6_Click(object sender, EventArgs e)
        {
            MessageBox.Show("ikke Implemteret");

        }
        private void button7_Click(object sender, EventArgs e)
        {

            MessageBox.Show("ikke Implemteret");
        }
    }
}
