using System.Data.SqlClient;
using System.Data;
using proiect.service;
using proiect.model;

namespace proiect
{
    public partial class LogInForm : Form
    {
        private Service service;
        private Angajat currentAngajat;
        public LogInForm(Service service)
        {
            this.service = service;
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
                currentAngajat = service.getAngajatByUsername(username);
                if (currentAngajat == null)
                {
                    MessageBox.Show("Angajat doesn't exist");
                    return;
                }
                if (password != currentAngajat.password)
                {
                    MessageBox.Show("Incorrect password");
                    return;
                }
                this.Hide();
                Form mainForm = new MainForm(service, currentAngajat);
                mainForm.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}