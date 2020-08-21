using System;
using System.Windows;
using System.Windows.Input;
using System.Net.Http;
using System.Windows.Controls;
using System.Windows.Media;

namespace MCAuthClient
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow
    {
        public static readonly HttpClient client = new HttpClient();

        public MainWindow()
        {


            InitializeComponent();
        }

        private void MainWindow_OnMouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
                DragMove();
        }

        private void MainWindow_OnMouseRightButtonDown(object sender, MouseButtonEventArgs e)
        {
            e.Handled = true;
        }

        private void MainWindow_OnMouseRightButtonUp(object sender, MouseButtonEventArgs e)
        {
            e.Handled = true;
        }

        public String TwoFAField { get; set; }
        public String UsernameField { get; set; }

        private void ButtonBase_OnClick(object sender, RoutedEventArgs e)
        {
            TextBlock errorBox = (FindName("ErrorBox") as TextBlock);
            string UsernameField = (FindName("UsernameBox") as TextBox).Text;
            string TwoFAField = (FindName("AuthBox") as TextBox).Text;

            if (String.IsNullOrEmpty(TwoFAField) || String.IsNullOrEmpty(UsernameField))
            {
                errorBox.Foreground = Brushes.Red;
                errorBox.Text = "Please fill out all of the fields!";
                return;
            }

            string uuid = Util.GetUuid(UsernameField);
            bool b = Util.PostAuth("auth", TwoFAField, uuid);

            if (b)
            {
                errorBox.Foreground = Brushes.ForestGreen;
                errorBox.FontFamily = new FontFamily("Roboto");
                errorBox.Text = "Authenticated " + UsernameField;
                (FindName("AuthBox") as TextBox).Text = "";
                (FindName("UsernameBox") as TextBox).Text = "";

            }
            else
            {
                errorBox.Foreground = Brushes.Red;
                errorBox.Text = "Incorrect credentials!";
            }
        }
    }
}