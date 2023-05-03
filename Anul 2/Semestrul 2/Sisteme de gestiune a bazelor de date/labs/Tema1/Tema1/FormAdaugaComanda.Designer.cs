namespace Tema1
{
    partial class FormAdaugaComanda
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
            this.comboBoxIdAdmin = new System.Windows.Forms.ComboBox();
            this.comboBoxIdBucatar = new System.Windows.Forms.ComboBox();
            this.textBoxDetalii = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.adaugaComandaButton = new System.Windows.Forms.Button();
            this.textBoxStart = new System.Windows.Forms.TextBox();
            this.textBoxFinal = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // comboBoxIdAdmin
            // 
            this.comboBoxIdAdmin.FormattingEnabled = true;
            this.comboBoxIdAdmin.Location = new System.Drawing.Point(196, 35);
            this.comboBoxIdAdmin.Name = "comboBoxIdAdmin";
            this.comboBoxIdAdmin.Size = new System.Drawing.Size(200, 28);
            this.comboBoxIdAdmin.TabIndex = 0;
            // 
            // comboBoxIdBucatar
            // 
            this.comboBoxIdBucatar.FormattingEnabled = true;
            this.comboBoxIdBucatar.Location = new System.Drawing.Point(196, 124);
            this.comboBoxIdBucatar.Name = "comboBoxIdBucatar";
            this.comboBoxIdBucatar.Size = new System.Drawing.Size(200, 28);
            this.comboBoxIdBucatar.TabIndex = 1;
            // 
            // textBoxDetalii
            // 
            this.textBoxDetalii.Location = new System.Drawing.Point(196, 378);
            this.textBoxDetalii.Name = "textBoxDetalii";
            this.textBoxDetalii.Size = new System.Drawing.Size(200, 26);
            this.textBoxDetalii.TabIndex = 4;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(27, 39);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(73, 24);
            this.label1.TabIndex = 5;
            this.label1.Text = "admin:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(27, 126);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(83, 24);
            this.label2.TabIndex = 6;
            this.label2.Text = "bucatar:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(27, 263);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(143, 24);
            this.label3.TabIndex = 7;
            this.label3.Text = "start comanda:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(27, 320);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(143, 24);
            this.label4.TabIndex = 8;
            this.label4.Text = "final comanda:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(27, 378);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(158, 24);
            this.label5.TabIndex = 9;
            this.label5.Text = "detalii comanda:";
            // 
            // adaugaComandaButton
            // 
            this.adaugaComandaButton.Font = new System.Drawing.Font("Georgia", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adaugaComandaButton.Location = new System.Drawing.Point(131, 456);
            this.adaugaComandaButton.Name = "adaugaComandaButton";
            this.adaugaComandaButton.Size = new System.Drawing.Size(182, 33);
            this.adaugaComandaButton.TabIndex = 12;
            this.adaugaComandaButton.Text = "Adauga comanda";
            this.adaugaComandaButton.UseVisualStyleBackColor = true;
            this.adaugaComandaButton.Click += new System.EventHandler(this.adaugaComandaButton_Click);
            // 
            // textBoxStart
            // 
            this.textBoxStart.Location = new System.Drawing.Point(196, 261);
            this.textBoxStart.Name = "textBoxStart";
            this.textBoxStart.Size = new System.Drawing.Size(200, 26);
            this.textBoxStart.TabIndex = 13;
            // 
            // textBoxFinal
            // 
            this.textBoxFinal.Location = new System.Drawing.Point(196, 318);
            this.textBoxFinal.Name = "textBoxFinal";
            this.textBoxFinal.Size = new System.Drawing.Size(200, 26);
            this.textBoxFinal.TabIndex = 14;
            // 
            // FormAdaugaComanda
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.ClientSize = new System.Drawing.Size(467, 607);
            this.Controls.Add(this.textBoxFinal);
            this.Controls.Add(this.textBoxStart);
            this.Controls.Add(this.adaugaComandaButton);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBoxDetalii);
            this.Controls.Add(this.comboBoxIdBucatar);
            this.Controls.Add(this.comboBoxIdAdmin);
            this.Name = "FormAdaugaComanda";
            this.Text = "FormAdaugaComanda";
            this.Load += new System.EventHandler(this.FormAdaugaComanda_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox comboBoxIdAdmin;
        private System.Windows.Forms.ComboBox comboBoxIdBucatar;
        private System.Windows.Forms.TextBox textBoxDetalii;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button adaugaComandaButton;
        private System.Windows.Forms.TextBox textBoxStart;
        private System.Windows.Forms.TextBox textBoxFinal;
    }
}