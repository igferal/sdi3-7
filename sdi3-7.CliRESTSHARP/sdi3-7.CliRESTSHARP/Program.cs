using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using RestSharp.Authenticators;
using Newtonsoft.Json;

namespace sdi3_7.CliRESTSHARP
{
    class Program
    {

         static User user;
         static List<Trip> trips;
         static List<User> users;

        static void Main(string[] args)
        {

            var client = new RestClient("http://localhost:8280/sdi3-7.Web/rest/TripServiceRs");
            client.Authenticator = new HttpBasicAuthenticator("sdi", "password");
            while(!login(client));
            showTrips(client);
            long idTrip = -1;
            while (!isTripInList(idTrip))
            {
                Console.WriteLine("Escoje el viaje para el que deseas confirmar pasajeros mediante su ID");
                idTrip = long.Parse(Console.ReadLine());
            }

            showPendingUsers(user.id, idTrip, client);
            if (users.Count() == 0) { Console.WriteLine("No hay usuarios interesados en ese viaje"); return; }

            long idUser = -1;
            while (!isUserInList(idUser))
            {
                Console.WriteLine("Escoje el usuario para el que quiera confirmar su asistencia");
                idUser = long.Parse(Console.ReadLine());
            }
            confirmUserInTrip(client, idUser, idTrip);
            
        }

        private static void confirmUserInTrip(RestClient client, long idUser, long idTrip) {

            var request = new RestRequest("/confirmUser{idUser}/InTrip{idTrip}", Method.POST);
            request.AddUrlSegment("idTrip", idTrip + "");
            request.AddUrlSegment("idUser", idUser + "");
            IRestResponse response =  client.Execute(request);
            if(response!=null)
                Console.WriteLine("Confirmacion realizada");
            else
                Console.WriteLine("Ha habido un problema en la petición");


        }

        private static bool isUserInList(long idUser)
        {

            return users.Find(x => x.id == idUser) != null;

        }


        private static void showPendingUsers(long userId,long tripId,RestClient client) {

            var request = new RestRequest("/involvedUsers{idTrip}/id{idPromoter}", Method.GET);
            request.AddUrlSegment("idTrip",tripId+ "");
            request.AddUrlSegment("idPromoter", userId + "");
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            users = JsonConvert.DeserializeObject<List<User>>(content);
            foreach (User user in users) { Console.WriteLine(user.toString()); }

        }


        private static bool isTripInList(long idTrip) {

            return trips.Find(x=>x.id==idTrip) !=null;

        }



        private static void showTrips(RestClient client) {
            

            Console.WriteLine("Estos son sus viajes");
            var request = new RestRequest("/getMyTrips{idUser}", Method.GET);
            request.AddUrlSegment("idUser", user.id+"");
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            trips = JsonConvert.DeserializeObject<List<Trip>>(content);
            foreach (Trip trip in trips)
            {

                Console.WriteLine(trip.toString());

            }
        }

        private static bool login(RestClient client)
        {
            var request = new RestRequest("/login{name}/{password}", Method.GET);
            Console.WriteLine("Inserte su usuario");
            string usuario = Console.ReadLine();
            Console.WriteLine("Inserte su contraseña");
            string password = Console.ReadLine();
            request.AddUrlSegment("name", usuario);
            request.AddUrlSegment("password", password);
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            try
            {
                user = JsonConvert.DeserializeObject<User>(content);
                Console.WriteLine(user.toString());
                return true;

            }
            catch (Exception e) {
                return false;
            }
        }
    }
}
