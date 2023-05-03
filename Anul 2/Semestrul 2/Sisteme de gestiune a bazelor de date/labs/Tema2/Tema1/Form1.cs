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
//using Microsoft.Data.SqlClient;
using System.Configuration;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace Tema1
{
    public partial class Form1 : Form
    {
        SqlConnection connection = new SqlConnection(@"Server=LAPTOP-DARIA;Database=Restaurant;
        Integrated Security=true;TrustServerCertificate=true;");
        private string childTableName = ConfigurationManager.AppSettings["ChildTableName"];
        private string parentTableName = ConfigurationManager.AppSettings["ParentTableName"];
        private SqlDataAdapter sqlDataAdapter = new SqlDataAdapter();
        private int nr = Convert.ToInt32(ConfigurationManager.AppSettings["ChildNumberOfColumns"]);
        private List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildLabelNames"].Split(','));
        private List<string> paramsNames = new List<string>(ConfigurationManager.AppSettings["ColumnNamesParameters"].Split(','));
        private System.Windows.Forms.TextBox[] textBoxes;
        private System.Windows.Forms.Label[] labels;
        public Form1()
        {
            InitializeComponent();
            InitializePanel();
            InitializeGridViews();
            labelChild.Text = childTableName;
            labelParent.Text = parentTableName;
        }

        private void InitializePanel()
        {

            textBoxes = new System.Windows.Forms.TextBox[nr];
            labels = new System.Windows.Forms.Label[nr];

            for (int i = 0; i < nr; i++)
            {
                textBoxes[i] = new System.Windows.Forms.TextBox();
                labels[i] = new System.Windows.Forms.Label();
                labels[i].Text = columnNames[i];
            }
            for (int i = 0; i < nr; i++)
            {
                panel.Controls.Add(labels[i]);
                panel.Controls.Add(textBoxes[i]);
            }
        }
        private void InitializeGridViews()
        {
            dataGridViewClienti.SelectionChanged += new EventHandler(LoadChildren);
            string select = ConfigurationManager.AppSettings["SelectParent"];
            sqlDataAdapter.SelectCommand = new SqlCommand(select, connection);
            DataSet dataSet = new DataSet();
            sqlDataAdapter.Fill(dataSet);
            dataGridViewClienti.DataSource = dataSet.Tables[0];

            stergeComanda.Enabled = false;
            modificaComanda.Enabled = false;
        }

        private void LoadChildren(object sender, EventArgs e)
        {
            try
            {
                int parentId = (int)dataGridViewClienti.CurrentRow.Cells[0].Value;
                string select = ConfigurationManager.AppSettings["SelectChild"];
                SqlCommand cmd = new SqlCommand(select, connection);
                cmd.Parameters.AddWithValue("@id", parentId);
                SqlDataAdapter daChild = new SqlDataAdapter(cmd);
                DataSet dataSet = new DataSet();
                daChild.Fill(dataSet);
                dataGridViewComenzi.DataSource = dataSet.Tables[0];
            }
            catch (Exception ex)
            {
                DataSet dataSet = new DataSet();
                dataGridViewComenzi.DataSource = dataSet;
            }
        }

        private void DataGridViewClientiCellClicked(object sender, DataGridViewCellEventArgs e)
        {
            adaugaComanda.Enabled = true;
            modificaComanda.Enabled = false;
            stergeComanda.Enabled = false;
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
                MessageBox.Show(ex.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
                    string idClient = dataGridViewClienti.Rows[index].Cells["id_client"].FormattedValue.ToString();
                    Form formAdaugaComanda = new FormAdaugaComanda(idClient);
                    formAdaugaComanda.ShowDialog();
                    LoadChildren(null, null);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
                    string confirmare = "Actualizati?";
                    DialogResult dialogResult = MessageBox.Show(confirmare, "Confirmare actualizare", MessageBoxButtons.OKCancel);
                    if (dialogResult == DialogResult.OK)
                    {
                        string update = ConfigurationManager.AppSettings["UpdateQuery"];
                        SqlCommand cmd = new SqlCommand(update, connection);
                        for (int i = 0; i < nr; i++)
                        {
                            cmd.Parameters.AddWithValue(paramsNames[i], textBoxes[i].Text);
                        }
                        cmd.Parameters.AddWithValue("@id", dataGridViewComenzi.Rows[index].Cells[0].Value);
                        connection.Open();
                        cmd.ExecuteNonQuery();
                        connection.Close();

                        MessageBox.Show("Actualizata cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        LoadChildren(null, null);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
                connection.Close();
            }
        }

        private void stergeComanda_Click(object sender, EventArgs e)
        {
            try
            {
                int index = dataGridViewComenzi.CurrentCell.RowIndex;
                string id = dataGridViewComenzi.Rows[index].Cells[0].FormattedValue.ToString();
                DialogResult dialogResult = MessageBox.Show("Stergeti inregistrarea ?", "Confirmare stergere", MessageBoxButtons.OKCancel);
                if (dialogResult == DialogResult.OK)
                {
                    string delete = ConfigurationManager.AppSettings["DeleteChild"];
                    SqlCommand cmd = new SqlCommand(delete, connection);
                    cmd.Parameters.AddWithValue("@id", id);
                    SqlDataAdapter daChild = new SqlDataAdapter(cmd);
                    DataSet dataSet = new DataSet();
                    connection.Open();
                    cmd.ExecuteNonQuery();
                    LoadChildren(null, null);
                    connection.Close();

                    MessageBox.Show("Stearsa cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
