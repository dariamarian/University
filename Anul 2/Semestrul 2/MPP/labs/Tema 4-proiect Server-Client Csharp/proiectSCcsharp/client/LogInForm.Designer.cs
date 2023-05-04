namespace proiectSCcsharp.client
{
    partial class LogInForm
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            textBoxUsername = new TextBox();
            buttonLogIn = new Button();
            textBoxPassword = new TextBox();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Georgia", 28F, FontStyle.Regular, GraphicsUnit.Point);
            label1.ForeColor = Color.DeepPink;
            label1.Location = new Point(56, 9);
            label1.Name = "label1";
            label1.Size = new Size(276, 65);
            label1.TabIndex = 0;
            label1.Text = "Welcome!";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Georgia", 10F, FontStyle.Regular, GraphicsUnit.Point);
            label2.ForeColor = Color.DeepPink;
            label2.Location = new Point(94, 74);
            label2.Name = "label2";
            label2.Size = new Size(187, 24);
            label2.TabIndex = 1;
            label2.Text = "please log-in below:";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Font = new Font("Georgia", 16F, FontStyle.Regular, GraphicsUnit.Point);
            label3.ForeColor = Color.DeepPink;
            label3.Location = new Point(97, 198);
            label3.Name = "label3";
            label3.Size = new Size(171, 38);
            label3.TabIndex = 2;
            label3.Text = "Username:";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Font = new Font("Georgia", 16F, FontStyle.Regular, GraphicsUnit.Point);
            label4.ForeColor = Color.DeepPink;
            label4.Location = new Point(100, 346);
            label4.Name = "label4";
            label4.Size = new Size(163, 38);
            label4.TabIndex = 3;
            label4.Text = "Password:";
            // 
            // textBoxUsername
            // 
            textBoxUsername.BackColor = Color.White;
            textBoxUsername.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            textBoxUsername.Location = new Point(65, 248);
            textBoxUsername.Name = "textBoxUsername";
            textBoxUsername.Size = new Size(234, 28);
            textBoxUsername.TabIndex = 4;
            // 
            // buttonLogIn
            // 
            buttonLogIn.BackColor = Color.DeepPink;
            buttonLogIn.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            buttonLogIn.Location = new Point(117, 458);
            buttonLogIn.Name = "buttonLogIn";
            buttonLogIn.Size = new Size(130, 50);
            buttonLogIn.TabIndex = 6;
            buttonLogIn.Text = "Log in";
            buttonLogIn.UseVisualStyleBackColor = false;
            buttonLogIn.Click += buttonLogIn_Click;
            // 
            // textBoxPassword
            // 
            textBoxPassword.BackColor = Color.White;
            textBoxPassword.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            textBoxPassword.Location = new Point(65, 387);
            textBoxPassword.Name = "textBoxPassword";
            textBoxPassword.PasswordChar = '*';
            textBoxPassword.Size = new Size(234, 28);
            textBoxPassword.TabIndex = 7;
            // 
            // LogInForm
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.White;
            ClientSize = new Size(384, 636);
            Controls.Add(textBoxPassword);
            Controls.Add(buttonLogIn);
            Controls.Add(textBoxUsername);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Name = "LogInForm";
            Text = "LogInForm";
            Load += LogInForm_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private TextBox textBoxUsername;
        private Button buttonLogIn;
        private TextBox textBoxPassword;
    }
}