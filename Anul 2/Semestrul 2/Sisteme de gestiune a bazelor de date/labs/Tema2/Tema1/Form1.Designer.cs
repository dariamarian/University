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
            this.dataGridViewComenzi = new System.Windows.Forms.DataGridView();
            this.labelParent = new System.Windows.Forms.Label();
            this.labelChild = new System.Windows.Forms.Label();
            this.labelMessageToUser = new System.Windows.Forms.Label();
            this.adaugaComanda = new System.Windows.Forms.Button();
            this.stergeComanda = new System.Windows.Forms.Button();
            this.modificaComanda = new System.Windows.Forms.Button();
            this.panel = new System.Windows.Forms.FlowLayoutPanel();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewClienti)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComenzi)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewClienti
            // 
            this.dataGridViewClienti.BackgroundColor = System.Drawing.Color.Plum;
            this.dataGridViewClienti.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewClienti.Location = new System.Drawing.Point(24, 29);
            this.dataGridViewClienti.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.dataGridViewClienti.Name = "dataGridViewClienti";
            this.dataGridViewClienti.RowHeadersWidth = 51;
            this.dataGridViewClienti.Size = new System.Drawing.Size(966, 298);
            this.dataGridViewClienti.TabIndex = 0;
            this.dataGridViewClienti.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewClientiCellClicked);
            // 
            // dataGridViewComenzi
            // 
            this.dataGridViewComenzi.BackgroundColor = System.Drawing.Color.Plum;
            this.dataGridViewComenzi.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewComenzi.Location = new System.Drawing.Point(24, 382);
            this.dataGridViewComenzi.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.dataGridViewComenzi.Name = "dataGridViewComenzi";
            this.dataGridViewComenzi.RowHeadersWidth = 51;
            this.dataGridViewComenzi.Size = new System.Drawing.Size(966, 292);
            this.dataGridViewComenzi.TabIndex = 1;
            this.dataGridViewComenzi.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewComenziCellClicked);
            // 
            // labelParent
            // 
            this.labelParent.AutoSize = true;
            this.labelParent.Location = new System.Drawing.Point(36, 46);
            this.labelParent.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelParent.Name = "labelParent";
            this.labelParent.Size = new System.Drawing.Size(0, 20);
            this.labelParent.TabIndex = 2;
            // 
            // labelChild
            // 
            this.labelChild.AutoSize = true;
            this.labelChild.Location = new System.Drawing.Point(36, 478);
            this.labelChild.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelChild.Name = "labelChild";
            this.labelChild.Size = new System.Drawing.Size(0, 20);
            this.labelChild.TabIndex = 3;
            // 
            // labelMessageToUser
            // 
            this.labelMessageToUser.AutoSize = true;
            this.labelMessageToUser.Location = new System.Drawing.Point(36, 654);
            this.labelMessageToUser.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelMessageToUser.Name = "labelMessageToUser";
            this.labelMessageToUser.Size = new System.Drawing.Size(0, 20);
            this.labelMessageToUser.TabIndex = 4;
            // 
            // adaugaComanda
            // 
            this.adaugaComanda.BackColor = System.Drawing.Color.Plum;
            this.adaugaComanda.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adaugaComanda.Location = new System.Drawing.Point(144, 701);
            this.adaugaComanda.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.adaugaComanda.Name = "adaugaComanda";
            this.adaugaComanda.Size = new System.Drawing.Size(288, 35);
            this.adaugaComanda.TabIndex = 5;
            this.adaugaComanda.Text = "Adauga";
            this.adaugaComanda.UseVisualStyleBackColor = false;
            this.adaugaComanda.Click += new System.EventHandler(this.adaugaComanda_Click);
            // 
            // stergeComanda
            // 
            this.stergeComanda.BackColor = System.Drawing.Color.Plum;
            this.stergeComanda.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.stergeComanda.Location = new System.Drawing.Point(619, 701);
            this.stergeComanda.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.stergeComanda.Name = "stergeComanda";
            this.stergeComanda.Size = new System.Drawing.Size(273, 35);
            this.stergeComanda.TabIndex = 6;
            this.stergeComanda.Text = "Sterge";
            this.stergeComanda.UseVisualStyleBackColor = false;
            this.stergeComanda.Click += new System.EventHandler(this.stergeComanda_Click);
            // 
            // modificaComanda
            // 
            this.modificaComanda.BackColor = System.Drawing.Color.Plum;
            this.modificaComanda.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.modificaComanda.Location = new System.Drawing.Point(1056, 551);
            this.modificaComanda.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.modificaComanda.Name = "modificaComanda";
            this.modificaComanda.Size = new System.Drawing.Size(285, 35);
            this.modificaComanda.TabIndex = 7;
            this.modificaComanda.Text = "Actualizeaza";
            this.modificaComanda.UseVisualStyleBackColor = false;
            this.modificaComanda.Click += new System.EventHandler(this.modificaComanda_Click);
            // 
            // panel
            // 
            this.panel.Location = new System.Drawing.Point(1029, 29);
            this.panel.Name = "panel";
            this.panel.Size = new System.Drawing.Size(337, 495);
            this.panel.TabIndex = 20;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.HotPink;
            this.ClientSize = new System.Drawing.Size(1378, 803);
            this.Controls.Add(this.modificaComanda);
            this.Controls.Add(this.stergeComanda);
            this.Controls.Add(this.adaugaComanda);
            this.Controls.Add(this.labelMessageToUser);
            this.Controls.Add(this.labelChild);
            this.Controls.Add(this.labelParent);
            this.Controls.Add(this.dataGridViewComenzi);
            this.Controls.Add(this.dataGridViewClienti);
            this.Controls.Add(this.panel);
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.Name = "Form1";
            this.Text = "Clienti si Comenzi";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewClienti)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewComenzi)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewClienti;
        private System.Windows.Forms.DataGridView dataGridViewComenzi;
        private System.Windows.Forms.Label labelParent;
        private System.Windows.Forms.Label labelChild;
        private System.Windows.Forms.Label labelMessageToUser;
        private System.Windows.Forms.Button adaugaComanda;
        private System.Windows.Forms.Button stergeComanda;
        private System.Windows.Forms.Button modificaComanda;
        private System.Windows.Forms.FlowLayoutPanel panel;
    }
}
