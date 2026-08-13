using System;
using System.Windows;
using DropShare.Desktop.ViewModels;
using Microsoft.Win32;

namespace DropShare.Desktop
{
    public partial class MainWindow : Window
    {
        private readonly MainViewModel _vm;

        public MainWindow()
        {
            InitializeComponent();
            _vm = new MainViewModel();
            DataContext = _vm;
            
            // Pencereyi taşıma
            this.MouseLeftButtonDown += (s, e) => 
            {
                if (e.ChangedButton == System.Windows.Input.MouseButton.Left)
                    this.DragMove();
            };
        }

        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);
            _vm.Durdur();
        }

        // Pencere kontrol metodları
        private void Minimize_Click(object sender, RoutedEventArgs e) => WindowState = WindowState.Minimized;
        private void Maximize_Click(object sender, RoutedEventArgs e) => WindowState = WindowState == WindowState.Maximized ? WindowState.Normal : WindowState.Maximized;
        private void Close_Click(object sender, RoutedEventArgs e) => Close();

        private async void BtnAgiTara_Click(object sender, RoutedEventArgs e)
        {
            await _vm.AgiTekrarTaraAsync();
        }

        private void BtnKlasoruAc_Click(object sender, RoutedEventArgs e)
        {
            _vm.IndirmeKlasorunuAc();
        }

        private void BtnManuelIpEkle_Click(object sender, RoutedEventArgs e)
        {
            _vm.ManuelIpEkle();
        }

        private async void BtnDosyaSec_Click(object sender, RoutedEventArgs e)
        {
            var dialog = new OpenFileDialog();
            if (dialog.ShowDialog() == true)
            {
                await _vm.DosyaGonderAsync(dialog.FileName);
            }
        }

        private async void BtnMetinGonder_Click(object sender, RoutedEventArgs e)
        {
            await _vm.MetinGonderAsync();
        }

        private void DropZone_DragOver(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
            {
                e.Effects = DragDropEffects.Copy;
            }
            else
            {
                e.Effects = DragDropEffects.None;
            }
            e.Handled = true;
        }

        private async void DropZone_Drop(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
            {
                string[] files = (string[])e.Data.GetData(DataFormats.FileDrop);
                if (files != null && files.Length > 0)
                {
                    foreach (var file in files)
                    {
                        await _vm.DosyaGonderAsync(file);
                    }
                }
            }
        }
    }
}