using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_7.CliRESTSHARP
{
    public class User
    {
        public enum userStatus
        {
            ACTIVE,
            CANCELLED,
        }

        //{"id":310,"login":"usuario1","name":"Carlos","surname":"Rodriguez Perez","email":"carlosrp@mail.com","status":"ACTIVE"}

        public string email { set; get; }

        public long id { set;  get; }

        public string login { set; get; }

        public string name { set; get; }

        public string password { set; get; }

        public userStatus status { set; get; }

        public string surname { set; get; }

        public User(long id, string login, string name,string password,userStatus status,string surname)
        {
            this.id = id;
            this.login = login;
            this.name = name;
            this.password = password;
            this.surname = surname;

        }

        public String toString() {

            return "ID:" + id + " " + name + ", "
                 + surname + "  email: " + email;

        }
    }
}
