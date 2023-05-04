using proiectSCcsharp.domain;
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
using proiectSCcsharp.services;
using proiectSCcsharp.server;
using client;
using Microsoft.VisualBasic.Logging;

namespace proiectSCcsharp.client
{
    public partial class MainForm : Form
    {
        private ClientController clientController;
        private List<Spectacol> showList;
        private List<Spectacol> showFilteredList;
        public MainForm(ClientController clientController)
        {
            InitializeComponent();
            this.clientController = clientController;
            InitialiseList();
            clientController.UpdateEvent += UserUpdate;
        }
        private void InitialiseList()
        {
            showList = clientController.getAllSpectacole();
            dataGridViewSpectacole.DataSource = showList;
            dataGridViewSpectacole.Columns["id"].Visible = false;
            if(dateTimePicker1 != null)
            {
                var unfiltered = clientController.getAllSpectacole().ToList();
                showFilteredList = unfiltered.Where(x => x.date == dateTimePicker1.Text).ToList();
                dataGridViewSpectacoleCautate.DataSource = showFilteredList;
            }
        }

        private void UserUpdate(object sender, EmployeeEventArgs e)
        {
            if (e.EmployeeEventType != EmployeeEvent.BILET_CUMPARAT) return;
            var ticket = (Bilet)e.Data;
            foreach (var show in showList)
            {
                if (show.id != ticket.idSpectacol) continue;
                show.availableSeats -= ticket.nrSeats;
                show.soldSeats += ticket.nrSeats;
            }
            dataGridViewSpectacole.BeginInvoke(new UpdateListBoxCallback(UpdateListBox), new Object[] { dataGridViewSpectacole, showList });
            dataGridViewSpectacoleCautate.BeginInvoke(new UpdateListBoxCallback(UpdateListBox), new Object[] { dataGridViewSpectacoleCautate, showFilteredList });
        }
        private static void UpdateListBox(DataGridView gridView, List<Spectacol> newData)
        {
            gridView.DataSource = null;
            gridView.DataSource = newData;
            gridView.Columns["id"].Visible = false;
        }

        private delegate void UpdateListBoxCallback(DataGridView list, List<Spectacol> data);
        private void MainForm_Load(object sender, EventArgs e)
        {
            dateTimePicker1.Format = DateTimePickerFormat.Custom;
            dateTimePicker1.CustomFormat = @"dd-MM-yyyy";
        }

        private void dataGridViewSpectacole_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (e.ColumnIndex == 4 && e.Value != null)
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
            var date = dateTimePicker1.Text;
            var unfiltered = clientController.getAllSpectacole().ToList();
            showFilteredList = unfiltered.Where(x => x.date == date).ToList();
            dataGridViewSpectacoleCautate.DataSource = showFilteredList;
        }

        private void buttonLogOut_Click(object sender, EventArgs e)
        {
            this.Hide();
            clientController.logout();
            clientController.UpdateEvent -= UserUpdate;
            var login = new LogInForm(new ClientController(clientController.Service));
            login.ShowDialog();
            this.Close();
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
                Spectacol spectacol = dataGridViewSpectacoleCautate.Rows[index].DataBoundItem as Spectacol;
                string cumparatorName = textBoxNume.Text;
                int nrLocuri = Convert.ToInt32(textBoxLocuri.Text);
                Bilet bilet = new Bilet(1, cumparatorName, spectacol.id, nrLocuri);
                clientController.addBilet(bilet);
                InitialiseList();
                MessageBox.Show("Bilet cumparat", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonRefresh_Click(object sender, EventArgs e)
        {
            InitialiseList();
        }
        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            clientController.logout();
            clientController.UpdateEvent -= UserUpdate;
        }
    }
}
