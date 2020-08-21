using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Test;

namespace MCAuthClient
{
    public class Util
    {
        public const string ApiUrl = "https://immense-sea-29739.herokuapp.com/";
        public const string UuidUrl = "https://api.minetools.eu/uuid/";

        public static async Task<string> HttpGet(string path)
        {
            return await MainWindow.client.GetStringAsync(ApiUrl + path);
        }

        public static bool PostAuth(string path, string twofa, string uuid)
        {
            using (var request = new HttpRequestMessage(new HttpMethod("POST"), ApiUrl + path))
            {

                AuthAttempt attempt = new AuthAttempt(GetIp(), twofa, uuid);
                Console.WriteLine(System.Text.Json.JsonSerializer.Serialize(attempt));
                request.Content = new StringContent(System.Text.Json.JsonSerializer.Serialize(attempt), Encoding.UTF8, "application/json");


                HttpResponseMessage response = MainWindow.client.SendAsync(request).Result;
                Console.WriteLine(response.Content.ReadAsStringAsync().Result);
                return response.IsSuccessStatusCode;

            }
        }

        private static string GetIp()
        {
            return MainWindow.client.GetStringAsync("https://api.ipify.org").Result;
        }

        public static string  GetUuid(string username)
        {
            string response = MainWindow.client.GetStringAsync(UuidUrl + username).Result;
            dynamic parsed = JObject.Parse(response);
            return parsed.id;
        }
    }
}