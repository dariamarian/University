namespace Tema1
{
    partial class Form1
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
            this.dataGridViewClienti = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.dataGridViewComenzi = new System.Windows.Forms.DataGridView();
            this.refreshComenzi = new System.Windows.Forms.Button();
            this.adaugaComanda = new System.Windows.Forms.Button();
            this.stergeComanda = new System.Windows.Forms.Button();
            this.modificaComanda = new System.Windows.Forms.Button();
            this.textBoxDetalii = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.textBoxFinal = new System.Windows.Forms.TextBox();
            this.textBoxStart = new System.Windows.Forms.TextBox();
            this.comboBoxAdmini = new System.Windows.Forms.ComboBox();
            this.comboBoxBucatari = new System.Windows.Forms.ComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewClienti)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComenzi)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewClienti
            // 
            this.dataGridViewClienti.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.dataGridViewClienti.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewClienti.GridColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGridViewClienti.Location = new System.Drawing.Point(31, 44);
            this.dataGridViewClienti.Name = "dataGridViewClienti";
            this.dataGridViewClienti.RowHeadersWidth = 62;
            this.dataGridViewClienti.RowTemplate.Height = 28;
            this.dataGridViewClienti.Size = new System.Drawing.Size(750, 281);
            this.dataGridViewClienti.TabIndex = 0;
            this.dataGridViewClienti.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewClientiCellClicked);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Georgia", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(25, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(106, 32);
            this.label1.TabIndex = 1;
            this.label1.Text = "Clienti:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Georgia", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(25, 328);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(132, 32);
            this.label2.TabIndex = 2;
            this.label2.Text = "Comenzi:";
            // 
            // dataGridViewComenzi
            // 
            this.dataGridViewComenzi.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.dataGridViewComenzi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComenzi.GridColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGridViewComenzi.Location = new System.Drawing.Point(31, 363);
            this.dataGridViewComenzi.Name = "dataGridViewComenzi";
            this.dataGridViewComenzi.RowHeadersWidth = 62;
            this.dataGridViewComenzi.RowTemplate.Height = 28;
            this.dataGridViewComenzi.Size = new System.Drawing.Size(750, 291);
            this.dataGridViewComenzi.TabIndex = 3;
            this.dataGridViewComenzi.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewComenziCellClicked);
            // 
            // refreshComenzi
            // 
            this.refreshComenzi.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.refreshComenzi.Font = new System.Drawing.Font("Georgia", 7F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.refreshComenzi.Location = new System.Drawing.Point(834, 100);
            this.refreshComenzi.Name = "refreshComenzi";
            this.refreshComenzi.Size = new System.Drawing.Size(151, 38);
            this.refreshComenzi.TabIndex = 5;
            this.refreshComenzi.Text = "Refresh Comenzi";
            this.refreshComenzi.UseVisualStyleBackColor = false;
            this.refreshComenzi.Click += new System.EventHandler(this.refreshComenzi_Click);
            // 
            // adaugaComanda
            // 
            this.adaugaComanda.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.adaugaComanda.Font = new System.Drawing.Font("Georgia", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adaugaComanda.Location = new System.Drawing.Point(834, 160);
            this.adaugaComanda.Name = "adaugaComanda";
            this.adaugaComanda.Size = new System.Drawing.Size(151, 38);
            this.adaugaComanda.TabIndex = 6;
            this.adaugaComanda.Text = "Adauga comanda";
            this.adaugaComanda.UseVisualStyleBackColor = false;
            this.adaugaComanda.Click += new System.EventHandler(this.adaugaComanda_Click);
            // 
            // stergeComanda
            // 
            this.stergeComanda.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.stergeComanda.Font = new System.Drawing.Font("Georgia", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.stergeComanda.Location = new System.Drawing.Point(834, 222);
            this.stergeComanda.Name = "stergeComanda";
            this.stergeComanda.Size = new System.Drawing.Size(151, 38);
            this.stergeComanda.TabIndex = 7;
            this.stergeComanda.Text = "Sterge comanda";
            this.stergeComanda.UseVisualStyleBackColor = false;
            this.stergeComanda.Click += new System.EventHandler(this.stergeComanda_Click);
            // 
            // modificaComanda
            // 
            this.modificaComanda.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(255)))));
            this.modificaComanda.Font = new System.Drawing.Font("Georgia", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.modificaComanda.Location = new System.Drawing.Point(834, 601);
            this.modificaComanda.Name = "modificaComanda";
            this.modificaComanda.Size = new System.Drawing.Size(151, 38);
            this.modificaComanda.TabIndex = 8;
            this.modificaComanda.Text = "Modifica comanda";
            this.modificaComanda.UseVisualStyleBackColor = false;
            this.modificaComanda.Click += new System.EventHandler(this.modificaComanda_Click);
            // 
            // textBoxDetalii
            // 
            this.textBoxDetalii.Location = new System.Drawing.Point(893, 559);
            this.textBoxDetalii.Name = "textBoxDetalii";
            this.textBoxDetalii.Size = new System.Drawing.Size(132, 26);
            this.textBoxDetalii.TabIndex = 14;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(791, 391);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(67, 21);
            this.label4.TabIndex = 16;
            this.label4.Text = "admin:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(791, 446);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(76, 21);
            this.label5.TabIndex = 17;
            this.label5.Text = "bucatar:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(791, 486);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(52, 21);
            this.label6.TabIndex = 18;
            this.label6.Text = "start:";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(791, 525);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(52, 21);
            this.label7.TabIndex = 19;
            this.label7.Text = "final:";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(791, 561);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(65, 21);
            this.label8.TabIndex = 20;
            this.label8.Text = "detalii:";
            // 
            // textBoxFinal
            // 
            this.textBoxFinal.Location = new System.Drawing.Point(893, 522);
            this.textBoxFinal.Name = "textBoxFinal";
            this.textBoxFinal.Size = new System.Drawing.Size(132, 26);
            this.textBoxFinal.TabIndex = 13;
            // 
            // textBoxStart
            // 
            this.textBoxStart.Location = new System.Drawing.Point(893, 484);
            this.textBoxStart.Name = "textBoxStart";
            this.textBoxStart.Size = new System.Drawing.Size(132, 26);
            this.textBoxStart.TabIndex = 12;
            // 
            // comboBoxAdmini
            // 
            this.comboBoxAdmini.FormattingEnabled = true;
            this.comboBoxAdmini.Location = new System.Drawing.Point(893, 391);
            this.comboBoxAdmini.Name = "comboBoxAdmini";
            this.comboBoxAdmini.Size = new System.Drawing.Size(132, 28);
            this.comboBoxAdmini.TabIndex = 21;
            // 
            // comboBoxBucatari
            // 
            this.comboBoxBucatari.FormattingEnabled = true;
            this.comboBoxBucatari.Location = new System.Drawing.Point(893, 438);
            this.comboBoxBucatari.Name = "comboBoxBucatari";
            this.comboBoxBucatari.Size = new System.Drawing.Size(132, 28);
            this.comboBoxBucatari.TabIndex = 22;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1045, 666);
            this.Controls.Add(this.comboBoxBucatari);
            this.Controls.Add(this.comboBoxAdmini);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.textBoxDetalii);
            this.Controls.Add(this.textBoxFinal);
            this.Controls.Add(this.textBoxStart);
            this.Controls.Add(this.modificaComanda);
            this.Controls.Add(this.stergeComanda);
            this.Controls.Add(this.adaugaComanda);
            this.Controls.Add(this.refreshComenzi);
            this.Controls.Add(this.dataGridViewComenzi);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridViewClienti);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewClienti)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComenzi)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewClienti;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView dataGridViewComenzi;
        private System.Windows.Forms.Button refreshComenzi;
        private System.Windows.Forms.Button adaugaComanda;
        private System.Windows.Forms.Button stergeComanda;
        private System.Windows.Forms.Button modificaComanda;
        private System.Windows.Forms.TextBox textBoxDetalii;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox textBoxFinal;
        private System.Windows.Forms.TextBox textBoxStart;
        private System.Windows.Forms.ComboBox comboBoxAdmini;
        private System.Windows.Forms.ComboBox comboBoxBucatari;
    }
}

