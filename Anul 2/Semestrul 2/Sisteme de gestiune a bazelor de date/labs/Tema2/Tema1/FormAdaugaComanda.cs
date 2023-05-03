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
using System.Configuration;

namespace Tema1
{
    public partial class FormAdaugaComanda : Form
    {
        private readonly string idClient;
        SqlConnection connection = new SqlConnection(@"Server=LAPTOP-DARIA;Database=Restaurant;
        Integrated Security=true;TrustServerCertificate=true;");

        SqlDataAdapter adminiAdapter = new SqlDataAdapter();
        DataSet dsAdmini = new DataSet();
        SqlDataAdapter bucatariAdapter = new SqlDataAdapter();
        DataSet dsBucatari = new DataSet();
        SqlDataAdapter comenziAdapter = new SqlDataAdapter();

        private int nr = Convert.ToInt32(ConfigurationManager.AppSettings["ChildNumberOfColumns"]);
        private System.Windows.Forms.TextBox[] textBoxes;
        private System.Windows.Forms.Label[] labels;
        private List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["ChildLabelNames"].Split(','));
        private string childTableName = ConfigurationManager.AppSettings["ChildTableName"];
        private string parentTableName = ConfigurationManager.AppSettings["ParentTableName"];
        private string columnNamesInsertParameters = ConfigurationManager.AppSettings["ColumnNamesParameters"];
        private List<string> paramsNames = new List<string>(ConfigurationManager.AppSettings["ColumnNamesParameters"].Split(','));
        public FormAdaugaComanda(string idClient)
        {
            this.idClient = idClient;
            InitializeComponent();
            InitializePanel();
            labelId.Text += this.idClient;
        }
        private void InitializePanel()
        {

            textBoxes = new System.Windows.Forms.TextBox[nr];
            labels = new System.Windows.Forms.Label[nr];

            for (int i = 1; i < nr; i++)
            {
                textBoxes[i] = new System.Windows.Forms.TextBox();
                labels[i] = new System.Windows.Forms.Label();
                labels[i].Text = columnNames[i];
            }
            for (int i = 1; i < nr; i++)
            {
                panel.Controls.Add(labels[i]);
                panel.Controls.Add(textBoxes[i]);
            }
        }

        private void adaugaComandaButton_Click(object sender, EventArgs e)
        {
            try
            {
                string confirmare = "Adaugati?";

                DialogResult dialogResult = MessageBox.Show(confirmare, "Confirmare adaugare", MessageBoxButtons.OKCancel);
                if (dialogResult == DialogResult.OK)
                {
                    SqlCommand cmd = new SqlCommand("insert into " + childTableName + " ( " + ConfigurationManager.AppSettings["ChildLabelNames"] + " ) values ( " + columnNamesInsertParameters + " )", connection);
                    cmd.Parameters.AddWithValue(paramsNames[0],idClient);
                    for (int i = 1; i < nr; i++)
                    {
                        cmd.Parameters.AddWithValue(paramsNames[i], textBoxes[i].Text);
                    }
                    SqlDataAdapter daChild = new SqlDataAdapter(cmd);
                    DataSet dataSet = new DataSet();
                    daChild.Fill(dataSet);

                    MessageBox.Show("Adaugata cu succes", "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Notificare", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
