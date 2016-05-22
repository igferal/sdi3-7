using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_7.CliRESTSHARP
{

    public class AddressPoint
    {


        public string address { set; get; }

        public string city { set; get; }

        public string country { set; get; }

        public string state { set; get; }

        public Waypoint waypoint { set; get; }

        public string zipCode { set; get; }

        public AddressPoint(string address,string city,string country, string state,string zipcode ) {


            this.address = address;
            this.city = city;
            this.country = country;
            this.state = state;
            this.zipCode = zipCode;


        }

       
        

    }
        
}
