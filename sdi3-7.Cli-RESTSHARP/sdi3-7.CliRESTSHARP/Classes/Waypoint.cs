using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_7.CliRESTSHARP
{
    public class Waypoint
    {


        public double lat { set; get; }
        public double lon { set; get; }


        public Waypoint(double lat, double lon) {

            this.lat = lat;
            this.lon = lon;
        }

    }
}
