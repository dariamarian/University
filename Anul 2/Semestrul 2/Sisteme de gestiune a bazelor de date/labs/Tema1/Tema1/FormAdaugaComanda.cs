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
using System.Windows.Forms.VisualStyles;

namespace Tema1
{
    public partial class FormAdaugaComanda : Form
    {
        private readonly string idClient;
        SqlConnection connection = new SqlConnection(@"Server=DESKTOP-MB25663;Database=Restaurant;
        Integrated Security=true;TrustServerCertificate=true;");

        SqlDataAdapter adminiAdapter = new SqlDataAdapter();
        DataSet dsAdmini = new DataSet();
        SqlDataAdapter bucatariAdapter = new SqlDataAdapter();
        DataSet dsBucatari = new DataSet();
        SqlDataAdapter comenziAdapter = new SqlDataAdapter();
        public FormAdaugaComanda(string idClient)
        {
            this.idClient = idClient;
            InitializeComponent();
        }
        private void FillComboBoxAdmini()
        {
            adminiAdapter.SelectCommand = new SqlCommand("select * from Admini", connection);
            dsAdmini.Clear();
            adminiAdapter.Fill(dsAdmini);
            comboBoxIdAdmin.DataSource = dsAdmini.Tables[0];
            comboBoxIdAdmin.DisplayMember = "nume_admin";
        }
        private void FillComboBoxBucatari()
        {
            bucatariAdapter.SelectCommand = new SqlCommand("select * from Bucatari", connection);
            dsBucatari.Clear();
            bucatariAdapter.Fill(dsBucatari);
            comboBoxIdBucatar.DataSource = dsBucatari.Tables[0];
            comboBoxIdBucatar.DisplayMember = "nume_bucatar";
        }
        private void FormAdaugaComanda_Load(object sender, EventArgs e)
        {
            FillComboBoxAdmini();
            FillComboBoxBucatari();
        }

        private void adaugaComandaButton_Click(object sender, EventArgs e)
        {
            try
            {
                int idAdmin = (int)dsAdmini.Tables[0].Rows[comboBoxIdAdmin.SelectedIndex]["id_admin"];
                int idBucatar = (int)dsBucatari.Tables[0].Rows[comboBoxIdBucatar.SelectedIndex]["id_bucatar"];
                DateTime start = Convert.ToDateTime(textBoxStart.Text);
                DateTime final = Convert.ToDateTime(textBoxFinal.Text);
                string detalii = textBoxDetalii.Text;

                string confirmare = "Adaugati comanda?";

                DialogResult dialogResult = MessageBox.Show(confirmare, "Confirmare adaugare", MessageBoxButtons.OKCancel);
                if (dialogResult == DialogResult.OK)
                {
                    comenziAdapter.DeleteCommand = new SqlCommand("insert into Comenzi(id_client, id_admin, id_bucatar, start_comanda, " +
                        "final_comanda, detalii_comanda) values " +
                        "(@idClient, @idAdmin, @idBucatar, @start, @final, @detalii)", connection);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@idClient", idClient);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@idAdmin", idAdmin);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@idBucatar", idBucatar);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@start", start);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@final", final);
                    comenziAdapter.DeleteCommand.Parameters.AddWithValue("@detalii", detalii);

                    connection.Open();
                    comenziAdapter.DeleteCommand.ExecuteNonQuery();
                    connection.Close();

                    MessageBox.Show("Comanda adaugata cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
