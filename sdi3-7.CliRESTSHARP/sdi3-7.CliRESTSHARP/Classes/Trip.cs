using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_7.CliRESTSHARP
{
    public class Trip
    {
        public enum tripStatus
        {

            /// <comentarios/>
            OPEN,

            /// <comentarios/>
            CLOSED,

            /// <comentarios/>
            CANCELLED,

            /// <comentarios/>
            DONE,
        }

        public DateTime arrivalDate { set; get; }

        public int availablePax { set; get; }

        public DateTime closingDate { set; get; }

        public string comments { set; get; }

        public AddressPoint departure { set; get; }

        public DateTime departureDate { set; get; }

        public AddressPoint destination { set; get; }

        public double estimatedCost { set; get; }

        public long id { set; get; }

        public int maxPax { set; get; }

        public long promoterID { set; get; }

        public tripStatus status { set; get; }


        public Trip(string arrivalDate,int availablePax, string closingDate,string comments,AddressPoint departure,
            string departureDate, AddressPoint destination,double estimatedCost , long id, int maxPax,long promoterID) {

            this.arrivalDate = new DateTime(long.Parse(arrivalDate));
           
            this.availablePax = availablePax;
            this.closingDate = new DateTime(long.Parse(closingDate));
            this.comments = comments;
            this.departure = departure;
            this.departureDate = new DateTime(long.Parse(departureDate));
            this.destination = destination;
            this.estimatedCost = estimatedCost;
            this.id = id;
            this.maxPax = maxPax;
            this.promoterID = promoterID;

        }

        public String toString() {


            return "ID: " + id + " "
                + departure.city + " ("
                + departure.country + ") - "
                + destination.city + " ("
                + destination.country + ")";
        }

    }
}
