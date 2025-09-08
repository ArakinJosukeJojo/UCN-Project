namespace DesktopClient.GUI
{
    partial class MainClient
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
            button3 = new Button();
            label1 = new Label();
            button1 = new Button();
            SuspendLayout();
            // 
            // button3
            // 
            button3.Location = new Point(135, 107);
            button3.Name = "button3";
            button3.Size = new Size(135, 29);
            button3.TabIndex = 4;
            button3.Text = "Reservation";
            button3.UseVisualStyleBackColor = true;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(266, 28);
            label1.Name = "label1";
            label1.Size = new Size(83, 20);
            label1.TabIndex = 5;
            label1.Text = "Main Menu";
            // 
            // button1
            // 
            button1.Location = new Point(350, 107);
            button1.Name = "button1";
            button1.Size = new Size(135, 29);
            button1.TabIndex = 4;
            button1.Text = "Room Status";
            button1.UseVisualStyleBackColor = true;
            // 
            // MainClient
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(640, 360);
            Controls.Add(button1);
            Controls.Add(label1);
            Controls.Add(button3);
            Margin = new Padding(2);
            Name = "MainClient";
            Text = "MainClient";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button button3;
        private Label label1;
        private Button button1;
    }
}