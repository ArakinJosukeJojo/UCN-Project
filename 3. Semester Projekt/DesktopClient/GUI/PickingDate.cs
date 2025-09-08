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
    public partial class PickingDate : Form
    {
        public DateTime StartDate { get; private set; }
        public DateTime EndDate { get; private set; }
        public PickingDate()
        {
            InitializeComponent();
        }

        private void confirm(object sender, EventArgs e)
        {
            StartDate = dateTimePicker1.Value.Date;
            EndDate = dateTimePicker2.Value.Date;
 
            //MessageBox.Show("The dates are " + StartDate + " and " + EndDate); //TODO Remember to delete this.
           
            DialogResult = DialogResult.OK; // Signals successful interaction
            Close(); // This will return control to the calling method

        }
    }
}
