using static System.Net.Mime.MediaTypeNames;
using System.Drawing.Printing;
using System.Windows.Forms;
using System.Xml.Linq;

namespace DesktopClient
{
    partial class ManageReservation
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            groupBox1 = new GroupBox();
            button8 = new Button();
            button7 = new Button();
            label4 = new Label();
            button6 = new Button();
            button3 = new Button();
            label5 = new Label();
            button1 = new Button();
            listBox1 = new ListBox();
            button4 = new Button();
            textBox1 = new TextBox();
            textBox2 = new TextBox();
            button2 = new Button();
            label2 = new Label();
            label3 = new Label();
            label1 = new Label();
            button5 = new Button();
            groupBox1.SuspendLayout();
            SuspendLayout();
            // 
            // groupBox1
            // 
            groupBox1.Controls.Add(button8);
            groupBox1.Controls.Add(button7);
            groupBox1.Controls.Add(label4);
            groupBox1.Controls.Add(button6);
            groupBox1.Controls.Add(button3);
            groupBox1.Controls.Add(label5);
            groupBox1.Controls.Add(button1);
            groupBox1.Controls.Add(listBox1);
            groupBox1.Location = new Point(15, 64);
            groupBox1.Margin = new Padding(4);
            groupBox1.Name = "groupBox1";
            groupBox1.Padding = new Padding(4);
            groupBox1.Size = new Size(700, 700);
            groupBox1.TabIndex = 1;
            groupBox1.TabStop = false;
            groupBox1.Text = "Date";
            // 
            // button8
            // 
            button8.Location = new Point(581, 81);
            button8.Name = "button8";
            button8.Size = new Size(112, 34);
            button8.TabIndex = 13;
            button8.Text = "Aflyse Reservation";
            button8.UseVisualStyleBackColor = true;
            button8.Click += AflysReservation;
            // 
            // button7
            // 
            button7.Location = new Point(155, 80);
            button7.Name = "button7";
            button7.Size = new Size(112, 34);
            button7.TabIndex = 12;
            button7.Text = "Day";
            button7.UseVisualStyleBackColor = true;
            button7.Click += CheckOut;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(8, 81);
            label4.Margin = new Padding(4, 0, 4, 0);
            label4.Name = "label4";
            label4.Size = new Size(94, 25);
            label4.TabIndex = 11;
            label4.Text = "Check Out";
            // 
            // button6
            // 
            button6.Location = new Point(581, 28);
            button6.Name = "button6";
            button6.Size = new Size(112, 39);
            button6.TabIndex = 10;
            button6.Text = "Registreret";
            button6.UseVisualStyleBackColor = true;
            button6.Click += CheckedIn_Click;
            // 
            // button3
            // 
            button3.Location = new Point(155, 28);
            button3.Margin = new Padding(4);
            button3.Name = "button3";
            button3.Size = new Size(113, 36);
            button3.TabIndex = 3;
            button3.Text = "Day";
            button3.UseVisualStyleBackColor = true;
            button3.Click += Day;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(8, 34);
            label5.Margin = new Padding(4, 0, 4, 0);
            label5.Name = "label5";
            label5.Size = new Size(79, 25);
            label5.TabIndex = 2;
            label5.Text = "Check In";
            label5.Click += label5_Click;
            // 
            // button1
            // 
            button1.Location = new Point(285, 29);
            button1.Margin = new Padding(4);
            button1.Name = "button1";
            button1.Size = new Size(118, 36);
            button1.TabIndex = 1;
            button1.Text = "Month";
            button1.UseVisualStyleBackColor = true;
            button1.Click += Month;
            // 
            // listBox1
            // 
            listBox1.FormattingEnabled = true;
            listBox1.ItemHeight = 25;
            listBox1.Location = new Point(8, 121);
            listBox1.Margin = new Padding(4);
            listBox1.Name = "listBox1";
            listBox1.Size = new Size(692, 579);
            listBox1.TabIndex = 0;
            listBox1.SelectedIndexChanged += ListBox1_SelectedIndexChanged;
            // 
            // button4
            // 
            button4.Location = new Point(1022, 293);
            button4.Name = "button4";
            button4.Size = new Size(289, 34);
            button4.TabIndex = 9;
            button4.Text = "Søg Reservation via Dato";
            button4.UseVisualStyleBackColor = true;
            button4.Click += Search;
            // 
            // textBox1
            // 
            textBox1.Location = new Point(981, 129);
            textBox1.Margin = new Padding(4);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(378, 31);
            textBox1.TabIndex = 2;
            textBox1.TextChanged += textBox1_TextChanged;
            // 
            // textBox2
            // 
            textBox2.Location = new Point(981, 177);
            textBox2.Margin = new Padding(4);
            textBox2.Name = "textBox2";
            textBox2.Size = new Size(378, 31);
            textBox2.TabIndex = 3;
            textBox2.TextChanged += textBox2_TextChanged;
            // 
            // button2
            // 
            button2.Location = new Point(1022, 225);
            button2.Margin = new Padding(4);
            button2.Name = "button2";
            button2.Size = new Size(289, 36);
            button2.TabIndex = 5;
            button2.Text = "Søg Reservation via navn";
            button2.UseVisualStyleBackColor = true;
            button2.Click += SearchByName;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(876, 129);
            label2.Margin = new Padding(4, 0, 4, 0);
            label2.Name = "label2";
            label2.Size = new Size(97, 25);
            label2.TabIndex = 6;
            label2.Text = "Fornavn";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(878, 180);
            label3.Margin = new Padding(4, 0, 4, 0);
            label3.Name = "label3";
            label3.Size = new Size(95, 25);
            label3.TabIndex = 7;
            label3.Text = "Efternavn";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(1085, 64);
            label1.Margin = new Padding(4, 0, 4, 0);
            label1.Name = "label1";
            label1.Size = new Size(156, 25);
            label1.TabIndex = 0;
            label1.Text = "Guest Information";
            label1.Click += label1_Click;
            // 
            // button5
            // 
            button5.Location = new Point(1247, 730);
            button5.Name = "button5";
            button5.Size = new Size(112, 34);
            button5.TabIndex = 9;
            button5.Text = "Tilbage";
            button5.UseVisualStyleBackColor = true;
            button5.Click += Tilbage;
            // 
            // ManageReservation
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1400, 800);
            Controls.Add(button5);
            Controls.Add(button4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(button2);
            Controls.Add(textBox2);
            Controls.Add(textBox1);
            Controls.Add(groupBox1);
            Controls.Add(label1);
            Margin = new Padding(4);
            Name = "ManageReservation";
            Text = "Manage Reservation";
            groupBox1.ResumeLayout(false);
            groupBox1.PerformLayout();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion
        private GroupBox groupBox1;
        private Button button1;
        private ListBox listBox1;
        private TextBox textBox1;
        private TextBox textBox2;
        private Button button2;
        private Label label2;
        private Label label3;
        private Label label1;
        private Label label5;
        private Button button3;
        private Button button4;
        private Button button5;
        private Button button6;
        private Label label4;
        private Button button7;
        private Button button8;
    }
}