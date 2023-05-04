using System.Data.SqlClient;
using System.Data;
using proiectSCcsharp.services;
using proiectSCcsharp.server;
using proiectSCcsharp.domain;

namespace proiectSCcsharp.client
{
    public partial class LogInForm : Form
    {
        private ClientController clientController;
        //private Angajat currentAngajat;
        public LogInForm(ClientController clientController)
        {
            this.clientController = clientController;
            InitializeComponent();
        }

        private void LogInForm_Load(object sender, EventArgs e)
        {
            textBoxUsername.Text = "dariam";
        }

        private void buttonLogIn_Click(object sender, EventArgs e)
        {
            try
            {
                String username = textBoxUsername.Text;
                String password = textBoxPassword.Text;
                
                clientController.login(username,password);
                this.Hide();
                Form mainForm = new MainForm(clientController);
                mainForm.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}