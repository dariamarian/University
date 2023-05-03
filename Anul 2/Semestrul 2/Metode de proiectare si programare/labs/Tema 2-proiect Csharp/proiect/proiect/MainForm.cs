using proiect.model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using proiect.service;

namespace proiect
{
    public partial class MainForm : Form
    {
        private Service service;
        private Angajat loggedAngajat = null;
        DataSet dsSpectacole = new DataSet();
        SqlDataAdapter spectacoleAdapter = new SqlDataAdapter();
        BindingSource spectacoleBS = new BindingSource();
        SqlDataAdapter spectacoleDTOAdapter = new SqlDataAdapter();
        public MainForm(Service service, Angajat angajat)
        {
            this.service = service;
            this.loggedAngajat = angajat;
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            dataGridViewSpectacole.DataSource = null;
            dataGridViewSpectacole.Columns.Clear();
            dataGridViewSpectacole.DataSource = service.getAllSpectacole();
        }

        private void dataGridViewSpectacole_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.Value != null)
            {
                int available = Convert.ToInt32(e.Value);

                if (available == 0)
                {
                    dataGridViewSpectacole.Rows[e.RowIndex].DefaultCellStyle.BackColor = Color.Red;
                }
            }
        }

        private void dataGridViewSpectacoleCautate_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.Value != null)
            {
                int available = Convert.ToInt32(e.Value);

                if (available == 0)
                {
                    dataGridViewSpectacoleCautate.Rows[e.RowIndex].DefaultCellStyle.BackColor = Color.Red;
                }
            }
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {
            dataGridViewSpectacoleCautate.DataSource = null;
            dataGridViewSpectacoleCautate.Columns.Clear();
            dataGridViewSpectacoleCautate.DataSource = service.getAllSpectacoleCautate(DateOnly.FromDateTime(dateTimePicker1.Value));
        }

        private void buttonLogOut_Click(object sender, EventArgs e)
        {
            this.Hide();
            Form loginForm = new LogInForm(service);
            loginForm.Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void buttonAddBilet_Click(object sender, EventArgs e)
        {
            try
            {
                int index = dataGridViewSpectacoleCautate.CurrentCell.RowIndex;
                string artistName = dataGridViewSpectacoleCautate.Rows[index].Cells["artistName"].FormattedValue.ToString();
                string time = dataGridViewSpectacoleCautate.Rows[index].Cells["time"].FormattedValue.ToString();
                TimeOnly time2 = TimeOnly.Parse(time);
                string place = dataGridViewSpectacoleCautate.Rows[index].Cells["place"].FormattedValue.ToString();
                Spectacol spectacol = service.getSpectacolByAll(artistName, time2, place);

                string cumparatorName = textBoxNume.Text;
                int nrLocuri = Convert.ToInt32(textBoxLocuri.Text);
                service.addBilet(cumparatorName, spectacol.id, nrLocuri);

                MessageBox.Show("Bilet cumparat", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonRefresh_Click(object sender, EventArgs e)
        {
            dataGridViewSpectacole.DataSource = null;
            dataGridViewSpectacole.Columns.Clear();
            dataGridViewSpectacole.DataSource = service.getAllSpectacole();

            dataGridViewSpectacoleCautate.DataSource = null;
            dataGridViewSpectacoleCautate.Columns.Clear();
            dataGridViewSpectacoleCautate.DataSource = service.getAllSpectacoleCautate(DateOnly.FromDateTime(dateTimePicker1.Value));
        }
    }
}
