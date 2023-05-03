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
            this.labelId = new System.Windows.Forms.Label();
            this.buttonAdauga = new System.Windows.Forms.Button();
            this.panel = new System.Windows.Forms.FlowLayoutPanel();
            this.SuspendLayout();
            // 
            // labelId
            // 
            this.labelId.AutoSize = true;
            this.labelId.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelId.Location = new System.Drawing.Point(188, 9);
            this.labelId.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelId.Name = "labelId";
            this.labelId.Size = new System.Drawing.Size(76, 24);
            this.labelId.TabIndex = 16;
            this.labelId.Text = "Adauga";
            // 
            // buttonAdauga
            // 
            this.buttonAdauga.BackColor = System.Drawing.Color.Plum;
            this.buttonAdauga.Font = new System.Drawing.Font("Georgia", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonAdauga.Location = new System.Drawing.Point(113, 565);
            this.buttonAdauga.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonAdauga.Name = "buttonAdauga";
            this.buttonAdauga.Size = new System.Drawing.Size(235, 35);
            this.buttonAdauga.TabIndex = 9;
            this.buttonAdauga.Text = "Adauga";
            this.buttonAdauga.UseVisualStyleBackColor = false;
            this.buttonAdauga.Click += new System.EventHandler(this.adaugaComandaButton_Click);
            // 
            // panel
            // 
            this.panel.Font = new System.Drawing.Font("Georgia", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.panel.Location = new System.Drawing.Point(26, 55);
            this.panel.Margin = new System.Windows.Forms.Padding(3, 4, 3, 4);
            this.panel.Name = "panel";
            this.panel.Size = new System.Drawing.Size(418, 475);
            this.panel.TabIndex = 20;
            // 
            // FormAdaugaComanda
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.BackColor = System.Drawing.Color.HotPink;
            this.ClientSize = new System.Drawing.Size(481, 643);
            this.Controls.Add(this.panel);
            this.Controls.Add(this.buttonAdauga);
            this.Controls.Add(this.labelId);
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.Name = "FormAdaugaComanda";
            this.Text = "FormAdaugaComanda";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label labelId;
        private System.Windows.Forms.Button buttonAdauga;
        private System.Windows.Forms.FlowLayoutPanel panel;
    }
}