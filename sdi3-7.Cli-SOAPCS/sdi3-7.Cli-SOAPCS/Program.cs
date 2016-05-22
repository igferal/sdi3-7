using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_7.Cli_SOAPCS
{
    class Program
    {
        public const string info = "Introduzca:\n1 para listar usuarios \n2 para cancelar usuarios\n3 para listar los ultimos comentarios\n4 para borrar comentarios\n0 para salir";

        static void Main(string[] args)
        {

       
            while (true)
            {
                Console.WriteLine(info);
                int choice = int.Parse(Console.ReadLine());

                switch (choice)
                {

                    case 1:
                        listUsers();
                        break;
                    case 2:
                        cancelUser();
                        break;
                    case 3:
                        listRatings();
                        break;
                    case 4:
                        cancelRatings();
                        break;
                    case 0:
                        return;
                    default:
                        Console.WriteLine("Entrada invalida, use una de las siguiente:" + "\n" + info);
                        break;
                }
            }


        }

        private static void listUsers()
        {


            EjbUserServiceService ejb = new EjbUserServiceService();

            user[] list = ejb.findAllUsers();
            printHeader();
            EjbTripServiceService tservice = new EjbTripServiceService();
            foreach (user us in list)
            {
                PrintUser(us, tservice);
            }
        }


        private static void cancelRatings()
        {

            EjbRatingServiceService rService = new EjbRatingServiceService();

            rating[] ratings = rService.findAll();
            if (ratings == null)
            {

                Console.WriteLine("No existen comentarios en el sistema");
                return;
            }

            Console.WriteLine("_ID_RATING_" +
                    "_DESTINO__________________________________________" +
                    "_IDUSER_" + "_SOBRE_IDUSER_", "_VALORACION_" +
                    "_COMENTARIO___________________");

            EjbTripServiceService tServices = new EjbTripServiceService();
            trip trip;

            foreach (rating rating in ratings)
            {
                trip = tServices.findTrip(rating.seatFromTripId, true);
                Console.WriteLine(
                        rating.id + "     " +
                      trip.destination.address + "  " +
                        rating.seatFromUserId + "  " + rating.seatAboutUserId + "  " +
                        rating.value + "  " + rating.comment);
            }

            Console.WriteLine("IDRATING a eliminar");
            long id = long.Parse(Console.ReadLine());

            int result = rService.delete(id, true);

            if (result == 1)
                Console.WriteLine("Rating eliminado correctamente");
            else
                Console.WriteLine("El Rating no existe...");



        }

        private static void listRatings()
        {


            rating[] ratings = new EjbRatingServiceService().findLastMonthDone();

            Console.WriteLine(
                     "_DESTINO__________________________________________" +
                     "_IDUSER_" + "_SOBRE_IDUSER_" + "_VALORACION_" +
                     "_COMENTARIO___________________");

            EjbTripServiceService tServices = new EjbTripServiceService();
            trip trip;

            if (ratings != null)
            {
                foreach (rating rating in ratings)
                {
                    trip = tServices.findTrip(rating.id, true);
                    Console.WriteLine(
                            trip.destination.city + " " +
                            rating.seatFromUserId + " " + rating.seatAboutUserId + " " +
                            rating.value + "" + rating.comment);
                }
            }




        }


        private static void cancelUser()
        {



            Console.WriteLine("Introduzca el ID del usuario a deshabilitar");
            long id = long.Parse(Console.ReadLine());

            EjbUserServiceService ejbUser = new EjbUserServiceService();
            user us = ejbUser.findById(id, true);

            if (us == null)
            {
                Console.WriteLine("No existe ese usuario");
                return;
            }


            EjbSeatServiceService ejbSeat = new EjbSeatServiceService();
            EjbTripServiceService tService = new EjbTripServiceService();

            seat[] seats = ejbSeat.findByUserAndNotExcludedAndOpenTrip(us.id, true);
            trip trip;
            if (seats != null)
            {
                foreach (seat seat in seats)
                {
                    if (seat != null)
                    {
                        trip = tService.findTrip(seat.tripId, true);
                        ejbSeat.moveSeatToExcluded(seat);
                        trip.availablePax++;

                        tService.update(trip);
                    }
                }
            }

            us.status = userStatus.CANCELLED;
            ejbUser.updateUser(us);
            Console.WriteLine("Usuario deshabilitado correctamente");




        }


        private static void printHeader()
        {
            Console.WriteLine("\n\t\t ** Listado de usuarios del sistema **\n\n");
            string format = "{0,-20} {1, -15} {2, -10} {3, -20} {4, -10}  {5, -20}  {6, -20} ";
            string line = String.Format(format,
            "APELLIDOS______",
            "NOMBRE_________",
            "EMAIL________________",
            "ID USER______",
            "Estado_______",
            "NUM VIAJES PROMOVIDOS",
            "NUM VIAJES PARTICIPANDO");
            Console.WriteLine(line);
        }

        static void PrintUser(user u, EjbTripServiceService tservice)
        {

            int promotedTravels = tservice.travelsPromoter(u.id, true).Count();
            int participatedTravels = tservice.tripsTakePartOf(u.id, true).Count();

            string format = "{0,-20} {1, -15} {2, 10} {3, -20}  {4, -10} {5, -20}  {6, -20}";
            string line = String.Format(format,
            u.surname,
            u.name,
            u.email,
            u.id, u.status, promotedTravels, participatedTravels);
            Console.WriteLine(line);


        }
    }
    
}
