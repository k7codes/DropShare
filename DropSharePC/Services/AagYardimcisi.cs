using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;

namespace DropShare.Desktop.Services
{
    public static class AagYardimcisi
    {
        public class YerelAagBilgisi
        {
            public string IpAdresi { get; set; } = "";
            public string YayınAdresi { get; set; } = ""; // Broadcast IP (e.g. 192.168.1.255)
            public string AltAagMaskesi { get; set; } = "";
            public string AagAdi { get; set; } = "";
        }

        public static List<YerelAagBilgisi> TumAktifAagArayuzleriniGetir()
        {
            var liste = new List<YerelAagBilgisi>();

            try
            {
                foreach (var ni in NetworkInterface.GetAllNetworkInterfaces())
                {
                    if (ni.OperationalStatus == OperationalStatus.Up &&
                        ni.NetworkInterfaceType != NetworkInterfaceType.Loopback &&
                        !ni.Description.ToLower().Contains("virtual") &&
                        !ni.Description.ToLower().Contains("wsl") &&
                        !ni.Description.ToLower().Contains("hyper-v") &&
                        !ni.Description.ToLower().Contains("vmware"))
                    {
                        var ipProps = ni.GetIPProperties();
                        foreach (var ip in ipProps.UnicastAddresses)
                        {
                            if (ip.Address.AddressFamily == AddressFamily.InterNetwork && !IPAddress.IsLoopback(ip.Address))
                            {
                                string ipStr = ip.Address.ToString();
                                string maskStr = ip.IPv4Mask?.ToString() ?? "255.255.255.0";
                                string bcastStr = YayinAdresiHesapla(ip.Address, ip.IPv4Mask ?? IPAddress.Parse("255.255.255.0"));

                                liste.Add(new YerelAagBilgisi
                                {
                                    IpAdresi = ipStr,
                                    AltAagMaskesi = maskStr,
                                    YayınAdresi = bcastStr,
                                    AagAdi = ni.Name
                                });
                            }
                        }
                    }
                }
            }
            catch { }

            if (liste.Count == 0)
            {
                liste.Add(new YerelAagBilgisi
                {
                    IpAdresi = "127.0.0.1",
                    AltAagMaskesi = "255.255.255.0",
                    YayınAdresi = "255.255.255.255",
                    AagAdi = "Yerel"
                });
            }

            return liste;
        }

        public static string BirincilIpGetir()
        {
            var arayuzler = TumAktifAagArayuzleriniGetir();
            var wiFiOrEth = arayuzler.FirstOrDefault(a => a.IpAdresi.StartsWith("192.168.") || a.IpAdresi.StartsWith("10.") || a.IpAdresi.StartsWith("172."));
            return wiFiOrEth?.IpAdresi ?? arayuzler.FirstOrDefault()?.IpAdresi ?? "127.0.0.1";
        }

        public static string YayinAdresiHesapla(IPAddress address, IPAddress subnetMask)
        {
            byte[] ipBytes = address.GetAddressBytes();
            byte[] maskBytes = subnetMask.GetAddressBytes();

            if (ipBytes.Length != maskBytes.Length) return "255.255.255.255";

            byte[] broadcastBytes = new byte[ipBytes.Length];
            for (int i = 0; i < broadcastBytes.Length; i++)
            {
                broadcastBytes[i] = (byte)(ipBytes[i] | (maskBytes[i] ^ 255));
            }

            return new IPAddress(broadcastBytes).ToString();
        }

        public static string BaytFormatla(long bayt)
        {
            if (bayt <= 0) return "0 B";
            string[] birimler = { "B", "KB", "MB", "GB", "TB" };
            int i = (int)Math.Floor(Math.Log(bayt, 1024));
            i = Math.Clamp(i, 0, birimler.Length - 1);
            double val = bayt / Math.Pow(1024, i);
            return $"{val:0.##} {birimler[i]}";
        }

        public static string HizFormatla(double baytSaniye)
        {
            return $"{BaytFormatla((long)baytSaniye)}/s";
        }

        public static string SureFormatla(long saniye)
        {
            if (saniye <= 0) return "0s";
            if (saniye < 60) return $"{saniye} sn";
            long dak = saniye / 60;
            long kal = saniye % 60;
            return $"{dak} dk {kal} sn";
        }
    }
}
