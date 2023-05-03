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

namespace Tema1
{
    public partial class Form1 : Form
    {
        SqlConnection connection = new SqlConnection(@"Server=LAPTOP-DARIA;Database=Restaurant;
        Integrated Security=true;TrustServerCertificate=true;");

        DataSet ds = new DataSet();
        SqlDataAdapter parentAdapter = new SqlDataAdapter();
        SqlDataAdapter childAdapter = new SqlDataAdapter();
        BindingSource parentBS = new BindingSource();
        BindingSource childBS = new BindingSource();
        SqlDataAdapter adminiAdapter = new SqlDataAdapter();
        DataSet dsAdmini = new DataSet();
        SqlDataAdapter bucatariAdapter = new SqlDataAdapter();
        DataSet dsBucatari = new DataSet();

        public Form1()
        {
            InitializeComponent();
        }
        private void FillComboBoxAdmini()
        {
            adminiAdapter.SelectCommand = new SqlCommand("select * from Admini", connection);
            dsAdmini.Clear();
            adminiAdapter.Fill(dsAdmini);
            comboBoxAdmini.DataSource = dsAdmini.Tables[0];
            comboBoxAdmini.DisplayMember = "nume_admin";
        }
        private void FillComboBoxBucatari()
        {
            bucatariAdapter.SelectCommand = new SqlCommand("select * from Bucatari", connection);
            dsBucatari.Clear();
            bucatariAdapter.Fill(dsBucatari);
            comboBoxBucatari.DataSource = dsBucatari.Tables[0];
            comboBoxBucatari.DisplayMember = "nume_bucatar";
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                    connection.Open();
                    FillComboBoxAdmini();
                    FillComboBoxBucatari();
                    parentAdapter.SelectCommand = new SqlCommand("SELECT * FROM Clienti;",
                        connection);
                    childAdapter.SelectCommand = new SqlCommand("SELECT * FROM Comenzi;",
                        connection);
                    parentAdapter.Fill(ds, "Clienti");
                    childAdapter.Fill(ds, "Comenzi");
                    parentBS.DataSource = ds.Tables["Clienti"];
                    dataGridViewClienti.DataSource = parentBS;
                    DataColumn parentColumn = ds.Tables["Clienti"].Columns["id_client"];
                    DataColumn childColumn = ds.Tables["Comenzi"].Columns["id_client"];
                    DataRelation relation = new DataRelation("FK_Clienti_Comenzi",
                        parentColumn, childColumn);
                    ds.Relations.Add(relation);
                    childBS.DataSource = parentBS;
                    childBS.DataMember = "FK_Clienti_Comenzi";
                    dataGridViewComenzi.DataSource = childBS;

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void refreshComenzi_Click(object sender, EventArgs e)
        {
            try
            {
                    childAdapter.SelectCommand = new SqlCommand("SELECT * FROM Comenzi;",
                        connection);
                    if (ds.Tables.Contains("Comenzi"))
                        ds.Tables["Comenzi"].Clear();
                    childAdapter.Fill(ds, "Comenzi");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void DataGridViewClientiCellClicked(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                    if (dataGridViewClienti.Rows[e.RowIndex] != null)
                    {
                        childAdapter.SelectCommand = new SqlCommand("select * from Comenzi where id_client=@clientId", connection);
                        childAdapter.SelectCommand.Parameters.AddWithValue("@clientId", dataGridViewClienti.Rows[e.RowIndex].Cells["id_client"].FormattedValue);
                        ds.Tables["Comenzi"].Clear();
                        childAdapter.Fill(ds, "Comenzi");
                        dataGridViewComenzi.DataSource = childBS;

                        adaugaComanda.Enabled = true;
                        modificaComanda.Enabled = false;
                        stergeComanda.Enabled = false;
                    }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }


        private void DataGridViewComenziCellClicked(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                    if (dataGridViewComenzi.Rows[e.RowIndex] != null)
                    {
                        modificaComanda.Enabled = true;
                        stergeComanda.Enabled = true;
                        adaugaComanda.Enabled = false;
                    }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void adaugaComanda_Click(object sender, EventArgs e)
        {
            try
            {
                if (dataGridViewClienti.CurrentCell != null)
                {
                    int index = dataGridViewClienti.CurrentCell.RowIndex;
                    string idComanda = dataGridViewClienti.Rows[index].Cells["id_client"].FormattedValue.ToString();
                    Form formAdaugaComanda = new FormAdaugaComanda(idComanda);
                    formAdaugaComanda.ShowDialog();
                    ds.Tables["Comenzi"].Clear();
                    childAdapter.Fill(ds, "Comenzi");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void modificaComanda_Click(object sender, EventArgs e)
        {
            try
            {
                
                    if (dataGridViewComenzi.CurrentCell != null)
                    {
                        int index = dataGridViewComenzi.CurrentCell.RowIndex;

                        int id = (int)dataGridViewComenzi.Rows[index].Cells["id_comanda"].Value;
                        int idClient = (int)dataGridViewComenzi.Rows[index].Cells["id_client"].Value;
                        int idAdmin = (int)dsAdmini.Tables[0].Rows[comboBoxAdmini.SelectedIndex]["id_admin"];
                        int idBucatar = (int)dsBucatari.Tables[0].Rows[comboBoxBucatari.SelectedIndex]["id_bucatar"];
                        DateTime startComanda = Convert.ToDateTime(textBoxStart.Text);
                        DateTime finalComanda = Convert.ToDateTime(textBoxFinal.Text);
                        string detalii = textBoxDetalii.Text;

                        string confirmare = "Actualizati comanda?";
                        DialogResult dialogResult = MessageBox.Show(confirmare, "Confirmare actualizare", MessageBoxButtons.OKCancel);
                        if (dialogResult == DialogResult.OK)
                        {
                            childAdapter.UpdateCommand = new SqlCommand("update Comenzi " +
                                "set id_client = @idClient, id_admin = @idAdmin, id_bucatar = @idBucatar, start_comanda = @startComanda," +
                                "final_comanda = @finalComanda, detalii_comanda = @detalii " +
                                "where id_comanda = @id", connection);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@idClient", idClient);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@idAdmin", idAdmin);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@idBucatar", idBucatar);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@startComanda", startComanda);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@finalComanda", finalComanda);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@detalii", detalii);
                            childAdapter.UpdateCommand.Parameters.AddWithValue("@id", id);

                            childAdapter.UpdateCommand.ExecuteNonQuery();
                            connection.Close();

                            MessageBox.Show("Comanda actualizata cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);

                            if (ds.Tables.Contains("Comenzi"))
                                ds.Tables["Comenzi"].Clear();
                            childAdapter.Fill(ds, "Comenzi");
                        }
                    }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void stergeComanda_Click(object sender, EventArgs e)
        {
            try
            {
                int index = dataGridViewComenzi.CurrentCell.RowIndex;
                string comandaId = dataGridViewComenzi.Rows[index].Cells["id_comanda"].FormattedValue.ToString();
                DialogResult dialogResult = MessageBox.Show("Stergeti inregistrarea cu id " + comandaId + "?", "Confirmare stergere", MessageBoxButtons.OKCancel);
                if (dialogResult == DialogResult.OK)
                {
                    childAdapter.DeleteCommand = new SqlCommand("delete from Comenzi where id_comanda = @id", connection);
                    childAdapter.DeleteCommand.Parameters.AddWithValue("@id", comandaId);

                    childAdapter.DeleteCommand.ExecuteNonQuery();
                    connection.Close();

                    MessageBox.Show("Comanda stearsa cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);

                    if (ds.Tables.Contains("Comenzi"))
                        ds.Tables["Comenzi"].Clear();
                    childAdapter.Fill(ds, "Comenzi");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
