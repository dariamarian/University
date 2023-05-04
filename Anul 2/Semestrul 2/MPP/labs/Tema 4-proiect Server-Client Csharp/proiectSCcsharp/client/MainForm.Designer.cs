namespace proiectSCcsharp.client
{
    partial class MainForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            label1 = new Label();
            dateTimePicker1 = new DateTimePicker();
            textBoxNume = new TextBox();
            textBoxLocuri = new TextBox();
            buttonRefresh = new Button();
            buttonAddBilet = new Button();
            buttonLogOut = new Button();
            dataGridViewSpectacole = new DataGridView();
            dataGridViewSpectacoleCautate = new DataGridView();
            button1 = new Button();
            ((System.ComponentModel.ISupportInitialize)dataGridViewSpectacole).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewSpectacoleCautate).BeginInit();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Georgia", 14F, FontStyle.Bold, GraphicsUnit.Point);
            label1.ForeColor = Color.DeepPink;
            label1.Location = new Point(484, 6);
            label1.Name = "label1";
            label1.Size = new Size(178, 32);
            label1.TabIndex = 0;
            label1.Text = "Spectacole:";
            // 
            // dateTimePicker1
            // 
            dateTimePicker1.CalendarFont = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            dateTimePicker1.CalendarForeColor = Color.DeepPink;
            dateTimePicker1.Location = new Point(13, 336);
            dateTimePicker1.Name = "dateTimePicker1";
            dateTimePicker1.Size = new Size(974, 31);
            dateTimePicker1.TabIndex = 2;
            dateTimePicker1.Value = new DateTime(2023, 3, 31, 0, 0, 0, 0);
            dateTimePicker1.ValueChanged += dateTimePicker1_ValueChanged;
            // 
            // textBoxNume
            // 
            textBoxNume.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            textBoxNume.Location = new Point(13, 680);
            textBoxNume.Name = "textBoxNume";
            textBoxNume.Size = new Size(231, 28);
            textBoxNume.TabIndex = 4;
            // 
            // textBoxLocuri
            // 
            textBoxLocuri.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            textBoxLocuri.Location = new Point(299, 680);
            textBoxLocuri.Name = "textBoxLocuri";
            textBoxLocuri.Size = new Size(232, 28);
            textBoxLocuri.TabIndex = 5;
            // 
            // buttonRefresh
            // 
            buttonRefresh.BackgroundImage = (Image)resources.GetObject("buttonRefresh.BackgroundImage");
            buttonRefresh.BackgroundImageLayout = ImageLayout.Stretch;
            buttonRefresh.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            buttonRefresh.Location = new Point(1022, 314);
            buttonRefresh.Margin = new Padding(0);
            buttonRefresh.Name = "buttonRefresh";
            buttonRefresh.Size = new Size(88, 75);
            buttonRefresh.TabIndex = 6;
            buttonRefresh.UseVisualStyleBackColor = true;
            buttonRefresh.Click += buttonRefresh_Click;
            // 
            // buttonAddBilet
            // 
            buttonAddBilet.BackColor = Color.DeepPink;
            buttonAddBilet.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            buttonAddBilet.Location = new Point(609, 668);
            buttonAddBilet.Name = "buttonAddBilet";
            buttonAddBilet.Size = new Size(188, 56);
            buttonAddBilet.TabIndex = 7;
            buttonAddBilet.Text = "Cumpara bilet";
            buttonAddBilet.UseVisualStyleBackColor = false;
            buttonAddBilet.Click += buttonAddBilet_Click;
            // 
            // buttonLogOut
            // 
            buttonLogOut.BackgroundImage = (Image)resources.GetObject("buttonLogOut.BackgroundImage");
            buttonLogOut.BackgroundImageLayout = ImageLayout.Stretch;
            buttonLogOut.Font = new Font("Georgia", 9F, FontStyle.Regular, GraphicsUnit.Point);
            buttonLogOut.Location = new Point(1031, 655);
            buttonLogOut.Name = "buttonLogOut";
            buttonLogOut.Size = new Size(79, 81);
            buttonLogOut.TabIndex = 8;
            buttonLogOut.UseVisualStyleBackColor = true;
            buttonLogOut.Click += buttonLogOut_Click;
            // 
            // dataGridViewSpectacole
            // 
            dataGridViewSpectacole.BackgroundColor = Color.White;
            dataGridViewSpectacole.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewSpectacole.Location = new Point(13, 51);
            dataGridViewSpectacole.Name = "dataGridViewSpectacole";
            dataGridViewSpectacole.RowHeadersWidth = 62;
            dataGridViewSpectacole.RowTemplate.Height = 33;
            dataGridViewSpectacole.Size = new Size(1097, 260);
            dataGridViewSpectacole.TabIndex = 9;
            dataGridViewSpectacole.CellFormatting += dataGridViewSpectacole_CellFormatting;
            // 
            // dataGridViewSpectacoleCautate
            // 
            dataGridViewSpectacoleCautate.BackgroundColor = Color.White;
            dataGridViewSpectacoleCautate.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewSpectacoleCautate.Location = new Point(13, 397);
            dataGridViewSpectacoleCautate.Name = "dataGridViewSpectacoleCautate";
            dataGridViewSpectacoleCautate.RowHeadersWidth = 62;
            dataGridViewSpectacoleCautate.RowTemplate.Height = 33;
            dataGridViewSpectacoleCautate.Size = new Size(1097, 250);
            dataGridViewSpectacoleCautate.TabIndex = 10;
            dataGridViewSpectacoleCautate.CellFormatting += dataGridViewSpectacoleCautate_CellFormatting;
            // 
            // button1
            // 
            button1.Font = new Font("Segoe UI", 8F, FontStyle.Regular, GraphicsUnit.Point);
            button1.Location = new Point(1058, 3);
            button1.Name = "button1";
            button1.Size = new Size(52, 44);
            button1.TabIndex = 11;
            button1.Text = "x";
            button1.UseVisualStyleBackColor = true;
            button1.Click += button1_Click;
            // 
            // MainForm
            // 
            AutoScaleDimensions = new SizeF(10F, 25F);
            AutoScaleMode = AutoScaleMode.Font;
            AutoSize = true;
            BackColor = Color.White;
            BackgroundImageLayout = ImageLayout.Stretch;
            ClientSize = new Size(1122, 759);
            Controls.Add(button1);
            Controls.Add(dataGridViewSpectacoleCautate);
            Controls.Add(dataGridViewSpectacole);
            Controls.Add(buttonLogOut);
            Controls.Add(buttonAddBilet);
            Controls.Add(buttonRefresh);
            Controls.Add(textBoxLocuri);
            Controls.Add(textBoxNume);
            Controls.Add(dateTimePicker1);
            Controls.Add(label1);
            Name = "MainForm";
            Text = "MainForm";
            FormClosing += MainForm_FormClosing;
            Load += MainForm_Load;
            ((System.ComponentModel.ISupportInitialize)dataGridViewSpectacole).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewSpectacoleCautate).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Label label1;
        private DateTimePicker dateTimePicker1;
        private TextBox textBoxNume;
        private TextBox textBoxLocuri;
        private Button buttonRefresh;
        private Button buttonAddBilet;
        private Button buttonLogOut;
        private DataGridView dataGridViewSpectacole;
        private DataGridView dataGridViewSpectacoleCautate;
        private Button button1;
    }
}